package girr;


import containers.IRiskKey;
import containers.RiskFactory;
import containers.RiskInterBucketKey;
import staticdata.Currency;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;

public class GIRRCurvatureBucketKey 
	implements IRiskKey<GIRRCurvatureBucket, GIRRCurvatureBucketKey>
{
	
	protected RiskInterBucketKey riskinterbucketkey;
	protected Currency currency;

    public GIRRCurvatureBucketKey(RiskFactory _factory, Currency _currency)
    {
        this.riskinterbucketkey=new RiskInterBucketKey(_factory,RiskFactorClass.GIRR,SensitivityType.Curvature);
        this.currency = _currency;
    }

    @Override public boolean Equals(GIRRCurvatureBucketKey other)
    {
        return (this.currency==other.currency);

    }

    
    @Override public  int hashCode()
    {
        return this.currency.hashCode()^GIRRCurvatureBucketKey.class.hashCode();
    }

	@Override
	public GIRRCurvatureBucket NewRiskFactor() {

		return null;
	}
    
	public RiskInterBucketKey Getriskinterbucketkey(){
    	
    	return riskinterbucketkey;
    }
}

