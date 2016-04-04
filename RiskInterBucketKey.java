package containers;


import staticdata.RiskFactorClass;
import staticdata.SensitivityType;

public class RiskInterBucketKey 
	implements  IRiskKey<RiskInterBucket, RiskInterBucketKey>,
	IRiskSubKey<GlobalRiskKey>
	{

	protected GlobalRiskKey globalriskkey;
	protected RiskFactorClass riskfactorclass;
	protected SensitivityType sensitivitytype;
	
	public RiskInterBucketKey( RiskFactory _factory,RiskFactorClass _riskfactorclass,SensitivityType _sensitivitytype)
    {
       
		this.globalriskkey = new GlobalRiskKey(_factory);
        this.riskfactorclass=_riskfactorclass;
        this.sensitivitytype=_sensitivitytype;
    }



	public GlobalRiskKey Getglobalriskkey(){
		
		return globalriskkey;
	}
	
	@Override
	public GlobalRiskKey GetSuperKey() {
		return this.globalriskkey;
	}

	@Override
	public boolean Equals(RiskInterBucketKey other) {
	
		return (this.riskfactorclass==other.riskfactorclass)&&(this.sensitivitytype==other.sensitivitytype);
	}
	
	@Override public boolean equals(Object obj){
		
		RiskInterBucketKey other = (RiskInterBucketKey)obj;
		return other != null && this.Equals(other);
		
	}
	
	@Override public int hashCode()
    {
		
        return this.riskfactorclass.hashCode()^this.sensitivitytype.hashCode()^RiskInterBucketKey.class.hashCode();
    }

	@Override
	public RiskInterBucket NewRiskFactor() {
		
		return new RiskInterBucket(this);
	}



	@Override
	public String GetString() {

		return this.riskfactorclass.toString() + "_" + this.sensitivitytype.toString();
	}


}
