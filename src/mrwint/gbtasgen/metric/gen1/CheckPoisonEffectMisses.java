package mrwint.gbtasgen.metric.gen1;

import mrwint.gbtasgen.metric.StateResettingMetric;
import mrwint.gbtasgen.util.Util;

public class CheckPoisonEffectMisses implements StateResettingMetric {
	@Override
	public int getMetricInternal() {
		if(Util.runToAddress2Limit(0, 0, 100, 0x3f24f) == 0) // start of sleep effect handler
			return 0;
		int add = Util.runToAddress2(0, 0, 0x3f295, 0x3f2d7); // hit, miss
		return (add == 0x3f2d7 ? 1 : 0);
	}
}