package girr;

import containers.IRisk;
import containers.LinearRiskBucket;


public class GIRRDeltaBucket 
extends LinearRiskBucket<GIRRDeltaRiskFactor,GIRRDeltaRiskFactorKey>
implements  IRisk<GIRRDeltaBucket, GIRRDeltaBucketKey>{

	
	protected GIRRDeltaBucketKey key;
	protected GIRRCalculator calculator;
	
	public GIRRDeltaBucket(GIRRDeltaBucketKey _key){
		
		key=_key;
		calculator=key.Getriskinterbucketkey().Getglobalriskkey().GetFactory().Getgirrcalculator();
		
	}
	
	@Override
	public GIRRDeltaBucketKey Key() {
		
		return key;
	}

	@Override
	public Double GetCorrelation(GIRRDeltaBucket other) {

		return calculator.GetDeltaGamma();
	}

	


}
