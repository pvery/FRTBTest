package girr;
import containers.CurvatureRiskFactor;
import containers.IRisk;

public class GIRRCurvatureRiskFactor 
extends CurvatureRiskFactor 
implements  IRisk<GIRRCurvatureRiskFactor, GIRRCurvatureRiskFactorKey>
{
	
	protected GIRRCurvatureRiskFactorKey key;
	protected GIRRCalculator calculator;
	
    

    public GIRRCurvatureRiskFactor(GIRRCurvatureRiskFactorKey _key)
    {
        this.key = _key;
        
        calculator=key.girrcurvaturebucketkey().Getriskinterbucketkey().Getglobalriskkey().GetFactory().Getgirrcalculator();
    }
    
  

	public GIRRCurvatureRiskFactorKey Key() {
		
		return key;
	}



	@Override
	public Double GetCorrelation(GIRRCurvatureRiskFactor other) {
		
		
		return calculator.GetCurvatureCorrelation();
	}






}
