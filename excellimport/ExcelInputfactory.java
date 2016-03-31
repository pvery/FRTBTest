package excellimport;

import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import CalculFRTB.Market;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;

public final class ExcelInputfactory {
	
	
	private SensitivitiesImportUtils utils;
	private Map<String,Integer> ColumnNames;
	private Market market;
	
	
	public ExcelInputfactory(SensitivitiesImportUtils _utils,Map<String,Integer> _ColumnNames,Market _market){
		
		utils = _utils;
		ColumnNames = _ColumnNames;
		market = _market;
	}
	
	public IExcelInput GetExcelInput(Row _row){
		
		Cell cell = _row.getCell(ColumnNames.get(InputEnum.Risk_Factor_Class.toString()));
		RiskFactorClass riskfactorclass = RiskFactorClass
				.valueOf(utils.Transform(String.class.cast(utils.GetCellValue(cell).getValue())));
		
		IExcelInput localinput = null;
		switch (riskfactorclass) {
        case GIRR: 
        	localinput=this.GetExcellInputGIRR(_row);
            break;
        default:
        	localinput=this.GetExcellInputGIRR(_row);
            break;
		}
		
		
		return localinput;
	}
	
	
	public IExcelInput GetExcellInputGIRR(Row _row){
		
		Cell cell = _row.getCell(ColumnNames.get(InputEnum.Sensitivity_type.toString()));
		SensitivityType sensitivitytype = SensitivityType
				.valueOf(utils.Transform(String.class.cast(utils.GetCellValue(cell).getValue())));
		
		IExcelInput localinput = null;
		switch (sensitivitytype) {
        case Delta: 
        	localinput=this.GetExcellInputGIRRDelta(_row);
            break;
        case Vega: 
        	localinput=this.GetExcellInputGIRRVega(_row);
            break;
        case Curvature: 
        	localinput=this.GetExcellInputGIRRCurvature(_row);
            break;
		}
		
		return localinput;
		
		
	}

	public IExcelInput GetExcellInputGIRRDelta(Row _row){
		
		return new ExcelInputGIRRDelta(utils, _row, ColumnNames,market);
	}
	
	public IExcelInput GetExcellInputGIRRVega(Row _row){
		
		return new ExcelInputGIRRVega(utils, _row, ColumnNames, market);
	}
	
	public IExcelInput GetExcellInputGIRRCurvature(Row _row){
		
		return new ExcelInputGIRRCurvature(utils, _row, ColumnNames, market);
	}

	
}
