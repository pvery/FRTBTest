package excellimport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public final class SensitivitiesImportUtils {

	public String Transform(String oldstring)
	{
		
		return oldstring.replace(" ", "_").replace("-", "").replace("(", "").replace(")","").replace(".", "");
	}
	
	
	public  GenericCell<ClasstoInt> GetCellValue(Cell _cell)
	{
		Object value=null;
		
		int celltype =_cell.getCellType();
		
		switch (celltype) {
        case XSSFCell.CELL_TYPE_BOOLEAN: 
        	value = _cell.getBooleanCellValue();
            break;
        case XSSFCell.CELL_TYPE_BLANK:
        	value="";
        	break;
        case XSSFCell.CELL_TYPE_ERROR:
        	value="";
        	break;
        case XSSFCell.CELL_TYPE_FORMULA:
        	value="";
        	break;
        case XSSFCell.CELL_TYPE_NUMERIC:
        	value= _cell.getNumericCellValue();
        	break;
        case XSSFCell.CELL_TYPE_STRING:
        	value =_cell.getStringCellValue();
        	break;
        default:
        	value="";
        	break;

		}
		return new GenericCell<ClasstoInt>(new ClasstoInt(celltype),value);
	}
	
}
