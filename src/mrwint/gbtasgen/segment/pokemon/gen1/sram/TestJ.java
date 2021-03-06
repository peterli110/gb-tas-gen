package mrwint.gbtasgen.segment.pokemon.gen1.sram;

import static mrwint.gbtasgen.state.Gameboy.curGb;
import mrwint.gbtasgen.move.Move;
import mrwint.gbtasgen.move.PressButton;
import mrwint.gbtasgen.move.WriteMemory;
import mrwint.gbtasgen.segment.Segment;
import mrwint.gbtasgen.segment.util.SeqSegment;
import mrwint.gbtasgen.segment.util.SequenceSegment;
import mrwint.gbtasgen.util.Util;

public class TestJ extends SeqSegment {

	SequenceSegment sequence;

	@Override
	public void execute() {
		seqMove(new WriteMemory(0xd2d8, 0x7e));
		seqMove(new WriteMemory(0xd2d9, 0x29));

		seqMove(new WriteMemory(0xd2cf, 0xcf));

		seqButton(Move.START);
		seqMove(new WriteMemory(0xd2dd, 0xcf));
		seq(Segment.skip(1));
		save("tmp");
//		seq(Move.A);

		{
//			seq(Segment.scrollA(21)); // 22th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(-4)); // 18nd
//
//			seq(Segment.scrollA(-8)); // 10th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(-9)); // 1st
//
//			seq(Segment.scrollA(16)); // 17th
//			for(int i=0;i<15;i++)
//				seq(Move.B);
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(2)); // 19th
//
//			seq(Segment.scrollA(-8)); // 11th
//			for(int i=0;i<15;i++)
//				seq(Move.B);
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(-10)); // 1st
//
//			seq(Segment.skip(1)); // 1st
//			seq(Move.A); // 1st
//			for(int i=0;i<15;i++)
//				seq(Move.B);
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(1)); // 2nd
//
//			seq(Segment.scrollA(21)); // 23rd
//			for(int i=0;i<15;i++)
//				seq(Move.B);
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(5)); // 28th
//
//			seq(Move.B);
//			seq(Move.START);
//			seq(new Wait(10000));
//			seq(new WalkToSegment(7, 1));
//
//			seq(new SkipTextsSegment(1));
		}

//		{
//			seq(Segment.scrollA(1)); // 2nd
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(9)); // 11th
//
//			seq(Segment.scrollA(-1)); // 10th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(-1)); // 9th
//
//			seq(Segment.scrollA(6)); // 15th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(2)); // 17th
//
//			seq(Move.B);
//			seq(Segment.scrollA(1));
//			seq(Segment.scrollFast(30)); // Item 31
//			seq(new SwapWithSegment(2)); // Item 33
//			seq(Move.B);
//
////			seq(Move.B);
//			seq(Move.START);
//
//			seq(new SkipTextsSegment(1));
//		}
//		{
//			seq(Segment.scrollA(1)); // 2nd
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(8)); // 10th
//
//			seq(Segment.scrollA(2)); // 12th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(1)); // 13th
//
//			seq(Segment.repress(Move.A)); // 13th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(-2)); // 11th
//
//			seq(Move.B);
//			seq(Segment.scrollA(1));
//			seq(Segment.scrollFast(8)); // Item 9
//			seq(new SwapWithSegment(2)); // Item 11
//			seq(Move.B);
//
//			seq(Segment.scrollA(-1));
//
//			seq(Segment.repress(Move.A)); // 11th
//			seq(Segment.scrollA(1)); // swap
//			seq(Segment.scrollA(1)); // 12th
//			seq(Move.B);
//			save("tmp");
//			seq(Move.START);
//
//			seq(new SkipTextsSegment(1));
//		}

//		for (int i=0x6400; i<=0x6470; i++) {
		for (int i=0x0000; i<=0xffff; i++) {
			load("tmp");
			if(i % 0x100 == 0)
				System.out.println("try " + Util.toHex(i, 4));
			seqMove(new WriteMemory(0xd2ed, i & 0xFF));
			seqMove(new WriteMemory(0xd2ee, (i>>8) & 0xFF));
			seqMove(new PressButton(Move.START), 0);
			final int ca = i;
			seqMove(new Move() {
				@Override public int getInitialKey() { return 0; }
				@Override
				public boolean doMove() {
					int add = run(200, 0x5be34);
					if (add != 0) {
						System.out.println("FOUND: " + Util.toHex(ca, 4));
						curGb.step();
					}
					return true;
				}
			});
		}
	}
	// returns address or 0
	public static int run(int stepLimit, int... addresses) {
		int steps = 0;
		int[] moves = {Move.A, Move.B};
		while(steps < stepLimit) {
			int ret = curGb.step(moves[steps & 1], addresses);
			if(ret != 0)
				return ret;
			steps++;
		}
		return 0;
	}
}
