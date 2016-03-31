package excellimport;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;

import CalculFRTB.Market;

import org.apache.poi.ss.usermodel.Cell;

// The generic class that corresponds to one excel input , that is one row in the excel sensitivities file

public abstract class ExcelInput<K extends Enum<K> & ItoInt> implements IExcelInput {

// The generic container that effectively contains the excel fields
	protected GenericEnumContainer<K> InputFields;

// Additional objects needed to store the data
	protected Market market;
	protected SensitivitiesImportUtils utils;
	protected Class<K> KType;
	
//  Temporary Data sets
	protected Row row;
	protected Map<String,Integer> ColumnNames;
	
// abstract virtual methods
//	protected abstract List<GenericCell<K>> GetInputTypes();
	protected abstract boolean CheckDataIntegrity();
	protected abstract boolean ImportSensitivity();
	
	

	
	protected List<GenericCell<K>> GetInputTypes() {

		return  Arrays.stream(KType.getEnumConstants())
				.map(e-> new GenericCell<K>(e,e.toString()))
				.collect(Collectors.toList());

	}

// Constructor
	public ExcelInput(SensitivitiesImportUtils _utils,Row _row,Map<String,Integer> _ColumnNames,Market _market){
		
		InputFields=new GenericEnumContainer<K>();
		utils=_utils;
		row=_row;
		ColumnNames=_ColumnNames;
		market=_market;
		
	}

	
// the most important methods that transform and store the data in the functional objects by call the Risk Factor Factory
	public boolean LoadNetRF(){
		
		boolean test=false;
		
		// First check the data types (by particular using enums that correspond to the risk factor type)
		// by calling the  'CheckDataIntegrity' virtual method
		if (this.CheckDataIntegrity()) {
			// effectively store the data and build the functional objects 
			// by calling the 'ImportSensitivity' virtual method
			test=ImportSensitivity();
		}
		
		return test;
		
	}
	
	public <T> T GetInputField(K key,Class<T> type){
		
		return InputFields.Get(key, type);
		
	}
	
	public boolean Build()
	{
	  
	  boolean globaltest=true;
	  
	  // select all the input needed by using a particular enum collection that correspond to the risk factor type
	  // by call the virtual function 'GetInputTypes' 
	  for (GenericCell<K> genericcell : this.GetInputTypes()) {
		  
		Cell excelcell=null;
		boolean localtest=true;
		// test if a column name that corresponds to the particular enum
		try {
			excelcell = row.getCell(ColumnNames.get(String.class.cast(genericcell.getValue())));
		} catch (Exception e) {
			localtest=false;
		}
		
		// test if the excel type found in the file corresponds to the expected excel type defined in the enum 
		if ((localtest==true)&&(genericcell.getCelltype().toInt()==excelcell.getCellType())) {
			GenericCell<ClasstoInt> cellvalue = utils.GetCellValue(excelcell); 
			
		  	try {
				this.InputFields.put(genericcell.getCelltype(), cellvalue.getValue());
			} catch (Exception e) {
				globaltest=false;
			} 
			
		} else {
			globaltest=false;
		}
		
	  }
	  
	  return globaltest;
	}
}
