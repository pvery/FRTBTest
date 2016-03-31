package girr;
import containers.IRiskKey;
import containers.IRiskSubKey;
import containers.RiskFactory;
import staticdata.Currency;
import staticdata.IRTenor;
import staticdata.Option_Maturity;

public class GIRRVegaRiskFactorKey
	implements IRiskKey<GIRRVegaRiskFactor, GIRRVegaRiskFactorKey>,
	IRiskSubKey<GIRRVegaBucketKey>
{
	
	protected GIRRVegaBucketKey girrvegabucketkey;
	protected Option_Maturity optionmaturity;
	protected IRTenor irtenor;

	public GIRRVegaRiskFactorKey(RiskFactory _factory,Currency _currency,Option_Maturity _optionmaturity,IRTenor _irtenor) 
    {
		this.girrvegabucketkey = new GIRRVegaBucketKey(_factory,_currency);
		optionmaturity=_optionmaturity;
		irtenor=_irtenor;
    }

	
	@Override public boolean Equals(GIRRVegaRiskFactorKey other) {
		
		return this.girrvegabucketkey.Equals(other.girrvegabucketkey)&&(this.optionmaturity == other.optionmaturity)&&(this.irtenor == other.irtenor);
	}
	
	@Override public boolean equals(Object obj){
		
		GIRRVegaRiskFactorKey other = (GIRRVegaRiskFactorKey)obj;
		return other != null && this.Equals(other);
		
	}
	
	@Override public int hashCode()
    {
		
        return girrvegabucketkey.hashCode()^this.optionmaturity.hashCode()^this.irtenor.hashCode()^GIRRVegaRiskFactorKey.class.hashCode();
     
    }


	@Override
	public GIRRVegaRiskFactor NewRiskFactor() {
		return new GIRRVegaRiskFactor(this);
	}
	

	
	public GIRRVegaBucketKey Getgirrvegabucketkey(){
		
		return girrvegabucketkey;
		
	}

	@Override
	public GIRRVegaBucketKey GetSuperKey() {
		return this.girrvegabucketkey;
	}


	
	
}
