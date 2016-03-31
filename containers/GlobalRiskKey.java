package containers;


public class GlobalRiskKey
	implements IRiskKey<GlobalRisk, GlobalRiskKey>
{

	protected RiskFactory factory;

	
	
	public GlobalRiskKey( RiskFactory _factory)
    {
       
        this.factory = _factory;
    }
	
	public RiskFactory GetFactory(){
		
		return factory;
	}
	
	@Override
	public boolean Equals(GlobalRiskKey other) {
		return false;
	}

	
	@Override public boolean equals(Object obj){
		
		return false;
		
	}
	
	@Override public int hashCode()
    {
		
        return GlobalRiskKey.class.hashCode();
    }

	
	@Override
	public GlobalRisk NewRiskFactor() {

		return new GlobalRisk(this);
	}






	

}
