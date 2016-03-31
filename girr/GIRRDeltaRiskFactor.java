package girr;


import containers.IRisk;
import containers.LinearRiskFactor;



public class GIRRDeltaRiskFactor  extends LinearRiskFactor implements  IRisk<GIRRDeltaRiskFactor, GIRRDeltaRiskFactorKey>
{

	
	protected GIRRDeltaRiskFactorKey key;
	protected GIRRCalculator calculator;


    public GIRRDeltaRiskFactor(GIRRDeltaRiskFactorKey _key)
    {
    	this.key = _key;
    	calculator=key.Getgirrdeltabucketkey().Getriskinterbucketkey().Getglobalriskkey().GetFactory().Getgirrcalculator();
        
    }
    
   


    @Override
	public GIRRDeltaRiskFactorKey Key() {
		
		return key;
	}

	@Override
	public Double GetCorrelation(GIRRDeltaRiskFactor other) {

		return calculator.GetDeltaCorrelation(this.key.irtenor.tenordouble, other.key.irtenor.tenordouble,this.key.ircurvetype,other.key.ircurvetype);
	}





	@Override
	public double GetWeight() {
		return calculator.GetDeltaWeight(this.key.irtenor);
	}



}
