package containers;




public class RiskInterBucket extends Risk implements  IRisk<RiskInterBucket, RiskInterBucketKey> {

	
	protected InterBucketAgregator<?, ?> agregator;
	protected RiskInterBucketKey key;
	
	
	public RiskInterBucket(RiskInterBucketKey _key){
		
		key=_key;
	
	}
	
	public boolean push(InterBucketAgregator<?, ?> _agregator){
		
		agregator=_agregator;
		
		return true;
	}
	
	@Override
	public RiskInterBucketKey Key() {
		
		return key;
	}

	@Override
	public Double GetCorrelation(RiskInterBucket other) {
		// For info this function won't be called since we will add the absolute value of all interbucket risks
		return 1.0;
	}

	

	@Override
	public Boolean ComputeStandAlone() {
		this.capitalstandalone=agregator.GetCapitalStandAlone();
		return true;
	}

	

}
