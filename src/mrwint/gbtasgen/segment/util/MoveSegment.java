package mrwint.gbtasgen.segment.util;

import static mrwint.gbtasgen.state.Gameboy.curGb;
import mrwint.gbtasgen.move.Move;
import mrwint.gbtasgen.segment.Segment;
import mrwint.gbtasgen.state.State;
import mrwint.gbtasgen.state.StateBuffer;

public class MoveSegment implements Segment {

	public static final int MAX_DELAY = 4;

	private Move move;
	private int maxDelay;
	private boolean useCache;

	public MoveSegment(Move move) {
		this(move,MAX_DELAY);
	}

	public MoveSegment(Move move, int maxDelay) {
		this(move,maxDelay, true);
	}

	public MoveSegment(Move move, int maxDelay, boolean useCache) {
		this.move = move;
		this.useCache = useCache;
		this.maxDelay = move.isDelayable() ? maxDelay : 0;
	}

	@Override
	public StateBuffer execute(StateBuffer in) {
		StateBuffer out = new StateBuffer();
		for(State s : in.getStates()) {
			move.clearCache();
			if (move.isCachable())
			  curGb.restore(s);
			for(int delay = 0; delay <= maxDelay; delay++) {
				if (!move.isCachable())
				  curGb.restore(s);
				move.prepare(delay,useCache);
				if (move.doMove()) {
				  State newS = curGb.createState(true);
				  newS.delayStepCount += delay;
					out.addState(newS);
				}
			}
		}
		return out;
	}
}
