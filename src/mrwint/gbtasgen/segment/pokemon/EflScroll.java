package mrwint.gbtasgen.segment.pokemon;

import static mrwint.gbtasgen.util.EflUtil.PressMetric.PRESSED;
import mrwint.gbtasgen.move.Move;
import mrwint.gbtasgen.segment.util.SeqSegment;
import mrwint.gbtasgen.util.EflUtil;
import mrwint.gbtasgen.util.EflUtil.PressMetric;

public class EflScroll extends SeqSegment {

  boolean fast;
  int num;
  int withMove;

  PressMetric initialButtonMetric = PRESSED;

  public EflScroll(boolean fast, int num, int withMove) {
    EflUtil.assertEfl();

    this.fast = fast;
    this.num = num;
    this.withMove = withMove;
  }

  public EflScroll withInitialButtonMetric(PressMetric metric) {
    initialButtonMetric = metric;
    return this;
  }

  @Override
  protected void execute() {
    if (fast)
      fast();
    else
      slow();
  }

  public void slow() {
		int scrollMove = num > 0 ? Move.DOWN: Move.UP;
		if (num < 0)
			num = -num;
		if (num == 0 && withMove != 0)
      seqEflButtonNoDelay(withMove, initialButtonMetric);
		if(num > 0)
		  seqEflButtonNoDelay(scrollMove | (num == 1 ? withMove : 0), initialButtonMetric);
		for (int i=1; i<num; i++) {
		  seqEflSkipInput(1);
		  seqEflButtonNoDelay(scrollMove | (num == i+1 ? withMove : 0));
		}
	}

	public void fast() {
		int scrollMove = num > 0 ? Move.DOWN: Move.UP;
		if (num < 0)
			num = -num;
    if (num == 0 && withMove != 0)
      seqEflButtonNoDelay(withMove, initialButtonMetric);
		if(num > 0)
		  seqEflButtonNoDelay(scrollMove | (num == 1 ? withMove : 0), initialButtonMetric);
		for (int i=1; i<num; i++)
		  seqEflButtonNoDelay(scrollMove | ((i&1) == 0 ? Move.LEFT : Move.RIGHT) | (num == i+1 ? withMove : 0));
	}
}
