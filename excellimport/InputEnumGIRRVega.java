package excellimport;

import org.apache.poi.xssf.usermodel.XSSFCell;

public enum InputEnumGIRRVega implements ItoInt{

	IR_ccy(XSSFCell.CELL_TYPE_STRING),
	IR_tenor(XSSFCell.CELL_TYPE_STRING),
	Option_Maturity(XSSFCell.CELL_TYPE_STRING),
	Sensitivity(XSSFCell.CELL_TYPE_NUMERIC);
	
	private int celltype;
	
	InputEnumGIRRVega(int _celltype){
		
		celltype=_celltype;
	}
	
	@Override
	public int toInt() {

		return this.celltype;
//		return this.ordinal();
	}
}