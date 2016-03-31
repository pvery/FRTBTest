package excellimport;

import org.apache.poi.xssf.usermodel.XSSFCell;

// General Input type that will be used to define a particular risk factor type
public enum InputEnum {

	Risk_Factor_Class(XSSFCell.CELL_TYPE_STRING),
	Sensitivity_type(XSSFCell.CELL_TYPE_STRING);
	
	public int celltype;
	
	InputEnum(int _celltype){
		
		celltype=_celltype;
	}

}
