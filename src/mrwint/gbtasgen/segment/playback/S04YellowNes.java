package mrwint.gbtasgen.segment.playback;

import mrwint.gbtasgen.move.Move;
import mrwint.gbtasgen.rom.pokemon.gen1.YellowRomInfo;
import mrwint.gbtasgen.segment.SingleGbSegment;
import mrwint.gbtasgen.segment.pokemon.gen1.ace.Intro;
import mrwint.gbtasgen.segment.pokemon.gen1.ace.OakSpeech;
import mrwint.gbtasgen.segment.util.SeqSegment;
import mrwint.gbtasgen.util.SingleGbRunner;

public class S04YellowNes extends SingleGbSegment {

	@Override
	protected void execute() {
//		seq(new Intro());
//		save("intro");
//
//		load("intro");
//		seq(new OakSpeech());
//		save("oakSpeech");
//
//		load("oakSpeech");
//		seq(new SeqSegment() {
//      @Override
//      protected void execute() {
//        seqEflSkipInput(1);
//        // go downstairs
//        for (int i = 0; i < 3; i++)
//          seqEflButton(Move.RIGHT);
//        for (int i = 0; i < 5; i++)
//          seqEflButton(Move.UP);
//        for (int i = 0; i < 2; i++)
//          seqEflButton(Move.RIGHT);
//        // go back up
//        seqEflSkipInput(10);
//        for (int i = 0; i < 2; i++)
//        seqEflButton(Move.DOWN);
//        for (int i = 0; i < 1; i++)
//          seqEflButton(Move.UP);
//        // go to NES
//        seqEflSkipInput(10);
//        for (int i = 0; i < 2+1; i++)
//          seqEflButton(Move.DOWN);
//        for (int i = 0; i < 2; i++)
//          seqEflButton(Move.LEFT);
//        for (int i = 0; i < 3; i++)
//          seqEflButton(Move.DOWN);
//        for (int i = 0; i < 2; i++)
//          seqEflButton(Move.LEFT);
//        for (int i = 0; i < 1; i++)
//          seqEflButton(Move.UP);
//        // Talk to NES
//        seqEflButton(Move.A);
//        seqEflSkipInput(66 + 60);
//        seqEflButton(Move.A); // skip text 1
//        seqEflSkipInput(94);
//        seqEflButton(Move.A); // skip text 2
//        seqEflSkipInput(70 + 60);
//        seqEflButton(Move.A); // skip text 3
//        // go downstairs
//        for (int i = 0; i < 3; i++)
//          seqEflButton(Move.RIGHT);
//        for (int i = 0; i < 3; i++)
//          seqEflButton(Move.UP);
//        for (int i = 0; i < 2; i++)
//          seqEflButton(Move.RIGHT);
//        for (int i = 0; i < 2; i++)
//          seqEflButton(Move.UP);
//
//        // go outside
//        seqEflSkipInput(10);
//        for (int i = 0; i < 6; i++)
//          seqEflButton(Move.DOWN);
//        for (int i = 0; i < 4; i++)
//          seqEflButton(Move.LEFT);
//        for (int i = 0; i < 2; i++)
//          seqEflButton(Move.DOWN);
//        // go to HoF warp
//        seqEflSkipInput(2);
//        for (int i = 0; i < 1; i++)
//          seqEflButton(Move.DOWN);
//        for (int i = 0; i < 8 + 1; i++)
//          seqEflButton(Move.RIGHT);
//        for (int i = 0; i < 1; i++)
//          seqEflButton(Move.UP);
//        // HoF warp
//        seqEflSkipInput(707); // sync music (cycle 1916)
//        seqEflButton(Move.UP);
//        seqEflSkipInput(7); // wait for music to fade
//        seqEflSkipInput(48 + 60);
//        seqEflButton(Move.A); // skip text 1
//        seqEflSkipInput(88);
//        seqEflButton(Move.A); // skip text 2
//        seqEflSkipInput(30 + 60);
//        seqEflButton(Move.A); // skip text 3
//      }
//    });
//    save("playback_s04");
    load("playback_s04");
	}

	public static void main(String[] args) {
    SingleGbRunner.run(new YellowRomInfo() {{ romFileName = "roms/yellow_hack.gbc"; }}, new S04YellowNes(), true);
	}
}
