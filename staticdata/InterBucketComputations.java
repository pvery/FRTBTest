package staticdata;


public enum InterBucketComputations {

	
	GIRRDelta(RiskFactorClass.GIRR,SensitivityType.Delta),
	GIRRVega(RiskFactorClass.GIRR,SensitivityType.Vega),
	GIRRCurvature(RiskFactorClass.GIRR,SensitivityType.Curvature);
	
	public RiskFactorClass riskfactorclass;
	public SensitivityType sensitivitytype;
	
	InterBucketComputations(RiskFactorClass _riskfactorclass,SensitivityType _sensitivitytype){
		
		riskfactorclass=_riskfactorclass;
		sensitivitytype=_sensitivitytype;
	}
}


	
	

