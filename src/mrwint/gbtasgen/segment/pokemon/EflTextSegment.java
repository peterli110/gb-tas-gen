package mrwint.gbtasgen.segment.pokemon;

import static mrwint.gbtasgen.state.Gameboy.curGb;

import java.util.TreeMap;

import mrwint.gbtasgen.move.Move;
import mrwint.gbtasgen.segment.Segment;
import mrwint.gbtasgen.state.State;
import mrwint.gbtasgen.state.StateBuffer;
import mrwint.gbtasgen.util.EflUtil;

public class EflTextSegment implements Segment {

	private int skipMove;
  private int bufferSize;


	public EflTextSegment() {
		this(Move.A);
	}

	public EflTextSegment(int skipMove) {
		this(skipMove, StateBuffer.MAX_BUFFER_SIZE);
	}

	public EflTextSegment(int skipMove, int bufferSize) {
    EflUtil.assertEfl();

    this.skipMove = skipMove;
		this.bufferSize = bufferSize;
	}

	@Override
	public StateBuffer execute(StateBuffer in) {
		StateBuffer curBuffer = new StateBuffer(bufferSize);
		for(State s : in.getStates())
			skipToStart(curBuffer, s);

		StateBuffer goalBuffer = new StateBuffer(bufferSize);

		boolean first = true;

		TreeMap<Integer, StateBuffer> next = new TreeMap<Integer, StateBuffer>();
		if (curBuffer.size() > 0)
		  next.put(0, curBuffer);

		while(!next.isEmpty()) {
		  Integer fewestSkips = next.firstKey();
      curBuffer = next.get(fewestSkips);
//      System.out.println("EflTextSegment2: fewestSkips="+fewestSkips+" curBuffer="+curBuffer.size());
		  next.remove(fewestSkips);

			for(State s : curBuffer.getStates())
				progress(s, fewestSkips, next, goalBuffer, first);
			first = false;
		}

		return goalBuffer;
	}

	private void addNext(State s, int delays, TreeMap<Integer, StateBuffer> next) {
	  if (!next.containsKey(delays))
	    next.put(delays, new StateBuffer(bufferSize));
    next.get(delays).addState(s);
	}

  private void progress(State s, int delays, TreeMap<Integer, StateBuffer> next, StateBuffer goalBuffer, boolean first) {
    curGb.restore(s);
    int textSpeed = curGb.readMemory(curGb.pokemon.optionsAddress) & curGb.pokemon.optionsTextSpeedMask;
//  System.out.println("progress: textSpeed: " + textSpeed);
    if (textSpeed <= 1)
      progressWith(0, textSpeed, s, delays, next, goalBuffer, first);
    if (textSpeed >= 1)
      progressWith(skipMove, textSpeed, s, delays, next, goalBuffer, first);
  }

  private void progressWith(int usedMove, int textSpeed, State s, int delays, TreeMap<Integer, StateBuffer> next, StateBuffer goalBuffer, boolean first) {
//      System.out.println("progress: usedMove: " + usedMove);
    curGb.restore(s);
    curGb.step(usedMove, curGb.pokemon.printLetterDelayDoneAddress);
    if (hasMoreText(textSpeed)) {
      boolean atDoneAddress = false;
      while (EflUtil.runToAddressOrNextInputFrameNoLimit(skipMove, atDoneAddress ? curGb.pokemon.printLetterDelayJoypadAddress : curGb.pokemon.printLetterDelayDoneAddress)
          != 0) { // this is guaranteed to be for the next letter delay through hasMoreText()
        if (!atDoneAddress)
          delays++;
        atDoneAddress = !atDoneAddress;
      }
      addNext(curGb.createState(true), delays, next);
    } else {
//      System.out.println("EflTextSegment2: done after delays: " + delays);
//      EflUtil.runToAddressOrNextInputFrameNoLimit(0b11111111, curGb.pokemon.printLetterDelayDoneAddress);
      goalBuffer.addState(curGb.createState(true)); // done (no inputs)
    }
  }

  // checks whether next input frame is still for print letter delay
  private boolean hasMoreText(int textSpeed) {
    State tmp = curGb.newState();
    int initialSteps = curGb.currentStepCount;
    boolean inPrintLetterDelay = true;

//    System.out.println("hasMoreText steps: "+curGb.currentStepCount);
    // advance to next input frame, keeping track of whether you are in or outside the PrintLetterDelay loop
    while (true) {
      int add = EflUtil.runToAddressOrNextInputFrameLimit(skipMove, textSpeed + 1, inPrintLetterDelay ? curGb.pokemon.printLetterDelayDoneAddress : curGb.pokemon.printLetterDelayJoypadAddress);
      if (add == -1 || curGb.currentStepCount - initialSteps > textSpeed + 1) {
//        System.out.println("TextSegment: " + curGb.currentStepCount + " - " + initialSteps + " > " + textSpeed);
        curGb.restore(tmp);
        return false;
      }
      if (add == 0)
        break;
      if (add == curGb.pokemon.printLetterDelayJoypadAddress) {
//        System.out.println("in");
        inPrintLetterDelay = true;
      }
      if (add == curGb.pokemon.printLetterDelayDoneAddress) {
//        System.out.println("out");
        inPrintLetterDelay = false;
      }
    }
//    System.out.println("hasMoreText at input frame steps: " + curGb.currentStepCount + " inPrintLetterDelay: " + inPrintLetterDelay);

    // advance to vblank joypad read, keeping track of whether you are in or outside the PrintLetterDelay loop
    while (true) {
      int add = curGb.step(skipMove, curGb.rom.readJoypadInputLo, inPrintLetterDelay ? curGb.pokemon.printLetterDelayDoneAddress : curGb.pokemon.printLetterDelayJoypadAddress);
      if (add == 0)
        throw new RuntimeException("runToNextInputFrame returned invalid input frame!");
      if (add == curGb.rom.readJoypadInputLo)
        break;
      if (add == curGb.pokemon.printLetterDelayJoypadAddress) {
//        System.out.println("in");
        inPrintLetterDelay = true;
      }
      if (add == curGb.pokemon.printLetterDelayDoneAddress) {
//        System.out.println("out");
        inPrintLetterDelay = false;
      }
    }
//    System.out.println("hasMoreText at vblank steps: " + curGb.currentStepCount + " inPrintLetterDelay: " + inPrintLetterDelay);

    // advance to read joypad, keeping track of whether you are in or outside the PrintLetterDelay loop
    while (true) {
      int add = EflUtil.runToAddressNoLimit(0, 0, curGb.rom.readJoypadAddress, inPrintLetterDelay ? curGb.pokemon.printLetterDelayDoneAddress : curGb.pokemon.printLetterDelayJoypadAddress);
      if (add == curGb.rom.readJoypadAddress)
        break;
      if (add == curGb.pokemon.printLetterDelayJoypadAddress) {
//        System.out.println("in");
        inPrintLetterDelay = true;
      }
      if (add == curGb.pokemon.printLetterDelayDoneAddress) {
//        System.out.println("out");
        inPrintLetterDelay = false;
      }
    }
//    System.out.println("hasMoreText at joypad steps: " + curGb.currentStepCount + " inPrintLetterDelay: " + inPrintLetterDelay);

    curGb.restore(tmp); // back to beginning
    return inPrintLetterDelay;
  }

	private void skipToStart(StateBuffer startBuffer, State s) {
	  curGb.restore(s);
	  EflUtil.runToFirstInputFrameAfterAddressNoLimit(skipMove, curGb.pokemon.printLetterDelayJoypadAddress); // PrintLetterDelay -> GetJoypadState call
		startBuffer.addState(curGb.createState(true));
	}
}
