package containers;

public abstract class Risk {
	
	protected Double capitalstandalone;
	protected Double capitalcontribution;
	protected Double linearstandalone;
	
	public Risk(){
		
		capitalstandalone=0.0;
		capitalcontribution=0.0;
	}
	

	public Double GetCapitalStandAlone() {
		
		return capitalstandalone;
	}
	
	public Double GetLinearStandAlone() {
		
		return linearstandalone;
	}


	public Double GetCapitalContribution() {

		return capitalcontribution;
	}
	
	public void SetCapitalStandAlone(double value) {
		
		capitalstandalone=value;
	}


	public void SetCapitalContribution(double value) {

		 capitalcontribution=value;
	}
	
	public void SetLinearStandAlone(double value) {
		
		linearstandalone=value;
	}
	

}
