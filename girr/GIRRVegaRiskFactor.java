package girr;



import containers.IRisk;
import containers.LinearRiskFactor;


public class GIRRVegaRiskFactor extends LinearRiskFactor implements IRisk<GIRRVegaRiskFactor, GIRRVegaRiskFactorKey>
{

	protected GIRRVegaRiskFactorKey key;
	protected GIRRCalculator calculator;
	


    public GIRRVegaRiskFactor(GIRRVegaRiskFactorKey _key)
    {
        this.key = _key;
        calculator=key. Getgirrvegabucketkey().Getriskinterbucketkey().Getglobalriskkey().GetFactory().Getgirrcalculator();
        

    }
    
    

	public GIRRVegaRiskFactorKey Key() {
		
		return key;
	}

	
	@Override
	public Double GetCorrelation(GIRRVegaRiskFactor other) {

		return calculator.GetVegaCorrelation(this.key.irtenor.tenordouble, other.key.irtenor.tenordouble,this.key.optionmaturity.maturitydouble ,other.key.optionmaturity.maturitydouble);
	}





	@Override
	public double GetWeight() {
		return calculator.GetVegaWeight();
	}


}