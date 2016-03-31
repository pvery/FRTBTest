package excellimport;

public final class GenericCell <K extends ItoInt>{

//  Simple class that encapsulates an excel cell : its type('string','double', ...) and its value 
	private K celltype;
	private Object value;
	
//	Constructor
	public GenericCell(K _celltype,Object _value){
		celltype=_celltype;
		value=_value;
	
	}

	
//	Getters
	public K getCelltype() {
		return celltype;
	}

	public Object getValue() {
		return value;
	}


	


	
	

}
