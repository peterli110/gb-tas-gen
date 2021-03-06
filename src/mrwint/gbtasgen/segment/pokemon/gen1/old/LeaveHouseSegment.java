package mrwint.gbtasgen.segment.pokemon.gen1.old;

import java.util.ArrayList;
import java.util.List;

import mrwint.gbtasgen.move.pokemon.ChangeOptionsMove;
import mrwint.gbtasgen.segment.Segment;
import mrwint.gbtasgen.segment.pokemon.WalkToSegment;
import mrwint.gbtasgen.segment.util.MoveSegment;
import mrwint.gbtasgen.segment.util.SequenceSegment;
import mrwint.gbtasgen.state.StateBuffer;

public class LeaveHouseSegment implements Segment {

	SequenceSegment sequence;

	public LeaveHouseSegment() {
		List<Segment> segments = new ArrayList<Segment>();
		segments.add(new MoveSegment(ChangeOptionsMove.get(false))); // set options
//		segments.add(new WalkToSegment(7, 3)); // walk some steps
		segments.add(new WalkToSegment(7, 1)); // go downstairs
		segments.add(new WalkToSegment(3, 8, false)); // leave house
		segments.add(new WalkToSegment(10, 1)); // walk into grass

		sequence = new SequenceSegment(segments.toArray(new Segment[0]));
	}

	@Override
	public StateBuffer execute(StateBuffer in) {
		return sequence.execute(in);
	}
}
