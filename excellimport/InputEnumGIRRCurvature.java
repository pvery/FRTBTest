package excellimport;

import org.apache.poi.xssf.usermodel.XSSFCell;



public enum InputEnumGIRRCurvature implements ItoInt{

	IR_ccy(XSSFCell.CELL_TYPE_STRING),
	Curvature_Up(XSSFCell.CELL_TYPE_NUMERIC),
	Curvature_Down(XSSFCell.CELL_TYPE_NUMERIC);
	
	private int celltype;
	
	InputEnumGIRRCurvature(int _celltype){
		
		celltype=_celltype;
	}
	

	@Override
	public int toInt() {

		return this.celltype;
//		return this.ordinal();
	}



	
	
}