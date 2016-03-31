package excellimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import CalculFRTB.Market;

public final class SensitivitiesImport {

	private String filename;
	private Market market;
	private SensitivitiesImportUtils utils;
	private ExcelInputfactory excelfactory;
	
	private XSSFWorkbook workbook;
	private Map<String, Integer> ColumnNames;

	public SensitivitiesImport(String _filename, Market _market) {
		filename = _filename;
		market = _market;
		utils = new SensitivitiesImportUtils();
		ColumnNames = new HashMap<String, Integer>();
		excelfactory=new ExcelInputfactory(utils, ColumnNames, market);
	}
	
	public boolean importSheet() {
		
//		Global method to import sensitivities
		
		boolean test=false;
		
//		Get ColumNames
		if (this.OpenExcelSheet()) {
//			Get ColumNames
			this.GetColumnNames();
//			Read all rows one by one
			test=this.Process();
		}
		
//		Close workbook
		if (workbook!=null) {
			try {
				workbook.close();
			} catch (IOException e) {
				test=false;
			}
		}

		return test;
	}
	
	private boolean OpenExcelSheet(){
		
//		Just try to open the Excel Workbook or catch an exception and return false
		
		boolean test=true;
		FileInputStream file=null;
		workbook=null;
		
		
		try {
			file = new FileInputStream(new File(filename));
			try {
				workbook = new XSSFWorkbook(file);
			} catch (IOException e) {
				test=false;
			}
			
		} catch (FileNotFoundException e) {
			
			test=false;
		}

		return test;
		
	}
	
	
	private void GetColumnNames(){
		
//		Read the first row and store the column names in the map 'ColumnNames'
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();

		int j = 0;
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String columnName =  String.class.cast(utils.GetCellValue(cell).getValue());
			ColumnNames.put(utils.Transform(columnName), j);
			j++;
		}

	}
	
	
	private boolean Process(){
		
		
//		The process to read each row, one by one. 
// 		For each row an object 'excelinput' is build by the 'ExcelInputfactory'
//		The virtual 'LoadNetRF' method is then called and allow to pick generically the correct columns
//		 depending on the risk factor and sensitivity types and build functional objects
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row=null;
		
		boolean globaltest=false;
		
		rowIterator = sheet.iterator();
		rowIterator.next();
//		Iteration on the excel rows
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
//			Build the 'excelinput' object by calling the 'excel factory'
			IExcelInput excelinput = excelfactory.GetExcelInput(row);
//			Call the virtual method that builds the functional objects
			boolean localtest = excelinput.Build();
			if (localtest) {
				localtest=excelinput.LoadNetRF();
				if (localtest) {
					globaltest=true;
				}
			}


		}
		
		return globaltest;
	}



}
