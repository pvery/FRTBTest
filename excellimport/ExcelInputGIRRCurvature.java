package excellimport;


import java.util.Map;


import org.apache.poi.ss.usermodel.Row;

import CalculFRTB.Market;
import girr.GIRRCurvatureRiskFactor;
import staticdata.Currency;


public class ExcelInputGIRRCurvature extends ExcelInput<InputEnumGIRRCurvature> {

	private Currency currency;
	private Double curvaturedown;
	private Double curvatureup;
	
	
	public ExcelInputGIRRCurvature(SensitivitiesImportUtils _utils, Row _row, Map<String, Integer> _ColumnNames,
			Market _market) {
		super(_utils, _row, _ColumnNames, _market);
		this.KType=InputEnumGIRRCurvature.class;
	}
	

	

//	@Override
//	protected List<GenericCell<InputTypeGIRRCurvature>> GetInputTypes() {
//
//		return  Arrays.stream(InputTypeGIRRCurvature.values())
//				.map(e-> new GenericCell<InputTypeGIRRCurvature>(e,e.toString()))
//				.collect(Collectors.toList());
//
//	}
	
	@Override
	protected boolean CheckDataIntegrity() {
		
		boolean test=true;
		
		try {
			currency = Currency.valueOf(utils.Transform(this.GetInputField(InputEnumGIRRCurvature.IR_ccy,String.class)));
		} catch (Exception e) {
			test=false;
		}
	
		try {
			curvaturedown = this.GetInputField(InputEnumGIRRCurvature.Curvature_Down,double.class);
		} catch (Exception e) {
			test=false;
		}
		
		try {
			curvatureup = this.GetInputField(InputEnumGIRRCurvature.Curvature_Up,double.class);
		} catch (Exception e) {
			test=false;
		}
		
		
		return test;
	}

	@Override
	protected boolean ImportSensitivity() {
		
		GIRRCurvatureRiskFactor girrcurvature = market.factory.GetGIRRCurvature(currency);
		this.market.myLog.log(java.util.logging.Level.INFO,"working on element : " + girrcurvature.Key().toString());
		
		
		girrcurvature.PushCurve(curvatureup, curvaturedown);
		this.market.myLog.log(java.util.logging.Level.INFO,"Pushing  : " + curvaturedown.toString() + " to CurveDown and  " + curvatureup.toString() + " to CurveUp : ");
		
		
		
		
		return false;
	}

}
