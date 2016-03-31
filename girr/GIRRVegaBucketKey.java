package girr;


import containers.IRiskKey;
import containers.RiskFactory;
import containers.RiskInterBucketKey;
import staticdata.Currency;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;

public class GIRRVegaBucketKey 
implements  IRiskKey<GIRRVegaBucket, GIRRVegaBucketKey>
{
	protected RiskInterBucketKey riskinterbucketkey;
    protected Currency currency;

    public GIRRVegaBucketKey( RiskFactory _factory,Currency _currency)
    {
       
    	riskinterbucketkey= new RiskInterBucketKey(_factory,RiskFactorClass.GIRR,SensitivityType.Delta);
        this.currency = _currency;
    }
	

    public RiskInterBucketKey Getriskinterbucketkey(){
    	
    	return riskinterbucketkey;
    }

    @Override public boolean Equals(GIRRVegaBucketKey other)
    {
        return (this.currency==other.currency);

    }
    
    @Override public boolean equals(Object obj){
		
    	GIRRVegaBucketKey other = (GIRRVegaBucketKey)obj;
		return other != null && this.Equals(other);
		
	}
    
   
    
    @Override public  int hashCode()
    {
        return this.currency.hashCode()^GIRRVegaBucketKey.class.hashCode();
    }
    



	@Override
	public GIRRVegaBucket NewRiskFactor() {
	
		return new GIRRVegaBucket(this);
	}

}

