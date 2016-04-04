package containers;

public abstract class LinearRiskFactor extends Risk{
	
	
	protected Double sensitivity;
	
	public LinearRiskFactor(){
		
		super();
		sensitivity=0.0;

	}
	
	public abstract double GetWeight();
	
	 public void PushSensitivity(double value)
    {
    	sensitivity+= value;
    	
    }
	 
	 public Boolean ComputeStandAlone() {
			
			this.capitalstandalone=this.sensitivity*this.GetWeight();
			return true;
		}

}
