package mrwint.gbtasgen.metric.gen1;

import mrwint.gbtasgen.metric.StateResettingMetric;
import mrwint.gbtasgen.state.State;
import mrwint.gbtasgen.util.Util;

public class CheckLowerStatEffectMisses implements StateResettingMetric {
	@Override
	public int getMetricInternal() {
		Util.runToAddress(0, 0, 0x3f54c); // start of LowerStatEffectHandler
		int add = 0;
		while(add == 0)
			add = State.step(0, 0x3f5a9, 0x3f65a); // hit, miss
		return (add == 0x3f65a ? 1 : 0);
	}
}