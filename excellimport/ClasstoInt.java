package excellimport;




public class ClasstoInt implements ItoInt{
	
	private int celltype;
	
	public ClasstoInt(int _celltype){
		
		celltype=_celltype;
	}

	@Override
	public int toInt() {
		return celltype;
	}
	


}
