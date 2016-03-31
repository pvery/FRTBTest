package staticdata;


public enum Option_Maturity {
	_05yr(0.5),
	_1yr(1.0),
	_3yr(3.0),
	_5yr(5.0),
	_10yr(10.0);
	
	public double maturitydouble;
	
	Option_Maturity(double _maturitydouble){
		
		maturitydouble=_maturitydouble;
	}

}
