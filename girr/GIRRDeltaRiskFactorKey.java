package girr;
import containers.IRiskKey;
import containers.IRiskSubKey;
import containers.RiskFactory;
import staticdata.Currency;
import staticdata.IRCurveType;
import staticdata.IRTenor;

public class GIRRDeltaRiskFactorKey 
	implements IRiskKey<GIRRDeltaRiskFactor, GIRRDeltaRiskFactorKey>, 
	IRiskSubKey<GIRRDeltaBucketKey>
{

	protected GIRRDeltaBucketKey girrdeltabucketkey;
	protected IRCurveType ircurvetype;
	protected IRTenor irtenor;
	
	public GIRRDeltaRiskFactorKey(RiskFactory _factory,Currency _currency,IRCurveType _ircurvetype,IRTenor _irtenor) 
    {
		this.girrdeltabucketkey=new GIRRDeltaBucketKey(_factory,_currency);
		this.ircurvetype = _ircurvetype;
		this.irtenor=_irtenor;
		
    }

	public GIRRDeltaBucketKey Getgirrdeltabucketkey(){
		
		return girrdeltabucketkey;
		
	}
	
	@Override public boolean Equals(GIRRDeltaRiskFactorKey other) {
		
		return this.girrdeltabucketkey.Equals(other.girrdeltabucketkey)&&(this.ircurvetype == other.ircurvetype)&&(this.irtenor == other.irtenor);
	}
	
	@Override public boolean equals(Object obj){
		
		GIRRDeltaRiskFactorKey other = (GIRRDeltaRiskFactorKey)obj;
		return other != null && this.Equals(other);
		
	}
	
	@Override public int hashCode()
    {
		
        return this.girrdeltabucketkey.hashCode()^this.ircurvetype.hashCode()^this.irtenor.hashCode()^GIRRDeltaRiskFactorKey.class.hashCode();
    }


	@Override
	public GIRRDeltaRiskFactor NewRiskFactor() {
		return new GIRRDeltaRiskFactor(this);
	}
	



	@Override
	public GIRRDeltaBucketKey GetSuperKey() {
		return this.girrdeltabucketkey;
	}


	


	
}
