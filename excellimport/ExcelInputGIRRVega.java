package excellimport;

import java.util.Map;


import org.apache.poi.ss.usermodel.Row;

import CalculFRTB.Market;
import girr.GIRRVegaRiskFactor;
import staticdata.Currency;
import staticdata.IRTenor;
import staticdata.Option_Maturity;

public class ExcelInputGIRRVega extends ExcelInput<InputEnumGIRRVega> {
	
	private Currency currency;
	private Option_Maturity optionmaturity;
	private IRTenor irtenor;
	private Double Sensitivity;
	
	
	public ExcelInputGIRRVega(SensitivitiesImportUtils _utils, Row _row, Map<String, Integer> _ColumnNames,
			Market _market) {
		super(_utils, _row, _ColumnNames, _market);
		this.KType=InputEnumGIRRVega.class;
	}
	
	


	
	@Override
	protected boolean CheckDataIntegrity() {
		
		boolean test=true;
		
		
		try {
			currency = Currency.valueOf(utils.Transform(this.GetInputField(InputEnumGIRRVega.IR_ccy,String.class)));
		} catch (Exception e) {
			test=false;
		}
		
		
		try {
			optionmaturity =Option_Maturity.valueOf(utils.Transform(this.GetInputField(InputEnumGIRRVega.Option_Maturity,String.class)));
		} catch (Exception e) {
			test=false;
		}
		
		try {
			irtenor =IRTenor.valueOf(utils.Transform("_"+ this.GetInputField(InputEnumGIRRVega.IR_tenor,String.class)));
		} catch (Exception e) {
			test=false;
		}
		
		try {
			Sensitivity = this.GetInputField(InputEnumGIRRVega.Sensitivity,double.class);
		} catch (Exception e) {

			test=false;
		}
		
		
		return test;
	}

	@Override
	protected boolean ImportSensitivity() {
		

		
		
		GIRRVegaRiskFactor girrvega = market.factory.GetGIRRVega(currency, optionmaturity,irtenor);
		this.market.myLog.log(java.util.logging.Level.INFO,"working on element : " + girrvega.Key().toString());
		
		girrvega.PushSensitivity(Sensitivity);
		this.market.myLog.log(java.util.logging.Level.INFO,"Pushing  : " + Sensitivity.toString() + " to tenorMaturityVega : " + irtenor.toString() + "_" + optionmaturity.toString()	 );
			
		
		return false;
	}
	
	
	
	
	

}
