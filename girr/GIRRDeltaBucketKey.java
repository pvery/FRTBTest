package girr;


import containers.IRiskKey;
import containers.RiskFactory;
import containers.RiskInterBucketKey;
import staticdata.Currency;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;

public class GIRRDeltaBucketKey 
	implements  IRiskKey<GIRRDeltaBucket, GIRRDeltaBucketKey>
{
	protected RiskInterBucketKey riskinterbucketkey;
    protected Currency currency;

    public GIRRDeltaBucketKey( RiskFactory _factory,Currency _currency)
    {
       
    	riskinterbucketkey= new RiskInterBucketKey(_factory,RiskFactorClass.GIRR,SensitivityType.Delta);
        this.currency = _currency;
    }

    @Override
    public boolean Equals(GIRRDeltaBucketKey other)
    {
        return (this.currency==other.currency);

    }
    
    @Override public boolean equals(Object obj){
		
    	GIRRDeltaBucketKey other = (GIRRDeltaBucketKey)obj;
		return other != null && this.Equals(other);
		
	}

    public RiskInterBucketKey Getriskinterbucketkey(){
    	
    	return riskinterbucketkey;
    }
    
    @Override public  int hashCode()
    {
        return this.currency.hashCode()^RiskInterBucketKey.class.hashCode();
    }
    
    

	@Override
	public GIRRDeltaBucket NewRiskFactor() {
		
		return new GIRRDeltaBucket(this);
	}

}
