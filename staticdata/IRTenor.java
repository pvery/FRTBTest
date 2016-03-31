package staticdata;


public enum IRTenor {
	_025yr(0.25),
	_05yr(0.5),
	_1yr(1.0),
	_2yr(2.0),
	_3yr(3.0),
	_5yr(5.0),
	_10yr(10.0),
	_15yr(15.0),
	_20yr(20.0),
	_30yr(30.0),
	_inflation(100.0),
	_XCCY_basis__USD(1000.0);
	
	public double tenordouble;
	
	IRTenor(double _tenordouble){
		
		tenordouble=_tenordouble;
	}
	public String getNom()
	{
		return this.toString().substring(1);
	}
}
