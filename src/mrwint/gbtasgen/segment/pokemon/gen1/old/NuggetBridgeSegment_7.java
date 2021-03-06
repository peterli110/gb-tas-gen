package mrwint.gbtasgen.segment.pokemon.gen1.old;

import java.util.ArrayList;
import java.util.List;

import mrwint.gbtasgen.segment.Segment;
import mrwint.gbtasgen.segment.pokemon.fight.EndFightSegment;
import mrwint.gbtasgen.segment.pokemon.fight.InitFightSegment;
import mrwint.gbtasgen.segment.pokemon.fight.KillEnemyMonSegment;
import mrwint.gbtasgen.segment.pokemon.fight.NewEnemyMonSegment;
import mrwint.gbtasgen.segment.util.SequenceSegment;
import mrwint.gbtasgen.segment.util.SkipTextsSegment;
import mrwint.gbtasgen.state.StateBuffer;

public class NuggetBridgeSegment_7 implements Segment {

	SequenceSegment sequence;

	public NuggetBridgeSegment_7() {
		List<Segment> segments = new ArrayList<Segment>();

		segments.add(new InitFightSegment(15)); // start fight
		{
			KillEnemyMonSegment kems = new KillEnemyMonSegment();
			kems.attackCount[2][1] = 1; // 1x ember crit (alt: 1x mega punch crit)
			kems.numExpGainers = 1; // no level up
			kems.onlyPrintInfo = false;
			segments.add(kems); // Rattata
		}
		segments.add(NewEnemyMonSegment.any()); // next mon
		{
			KillEnemyMonSegment kems = new KillEnemyMonSegment();
			kems.attackCount[2][1] = 1; // 1x ember crit (alt: 1x mega punch crit)
			kems.numExpGainers = 1; // no level up
			kems.onlyPrintInfo = false;
			segments.add(kems); // Zubat
		}
		segments.add(new EndFightSegment(1)); // player defeated enemy

		segments.add(new SkipTextsSegment(3)); // after rocket battle texts

		sequence = new SequenceSegment(segments.toArray(new Segment[0]));
	}

	@Override
	public StateBuffer execute(StateBuffer in) {
		return sequence.execute(in);
	}
}
