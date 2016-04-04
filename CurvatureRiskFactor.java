package containers;

public abstract class CurvatureRiskFactor extends Risk{
	
	public double curveUp;
	public double curveDown;
	//public double cvr;

	
	public CurvatureRiskFactor(){
		
		super();
		curveUp=0.0;
		curveDown=0.0;
		//cvr=0.0;

	}
	
	 public void PushCurve(double _curveUp,double _curveDown)
    {
		 curveUp+= _curveUp;
		 curveDown+= _curveDown;
    	
    }
	 

	
	public Boolean ComputeStandAlone() {
		//cvr=-Math.min(curveUp,curveDown);
		this.capitalstandalone=-Math.min(curveUp,curveDown);
		
		return true;
		
	}

}
