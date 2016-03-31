package girr;

import containers.IRisk;
import containers.CurvatureRiskBucket;

public class GIRRCurvatureBucket extends CurvatureRiskBucket<GIRRCurvatureRiskFactor,GIRRCurvatureRiskFactorKey>
implements  IRisk<GIRRCurvatureBucket, GIRRCurvatureBucketKey>{

	
	protected GIRRCurvatureBucketKey key;
	protected GIRRCalculator calculator;
	
	public GIRRCurvatureBucket(GIRRCurvatureBucketKey _key){
		
		key=_key;
		calculator=key.Getriskinterbucketkey().Getglobalriskkey().GetFactory().Getgirrcalculator();
		
	}
	
	
	@Override
	public GIRRCurvatureBucketKey Key() {
		return key;
	}

	@Override
	public Double GetCorrelation(GIRRCurvatureBucket other) {
		return calculator.GetCurvatureGamma();
	}

	


}
