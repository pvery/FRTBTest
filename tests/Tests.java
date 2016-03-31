package tests;

import java.util.List;

import CalculFRTB.Market;
import girr.GIRRDeltaRiskFactor;
import girr.GIRRDeltaRiskFactorKey;
import girr.GIRRVegaRiskFactor;
import staticdata.Currency;
import staticdata.IRCurveType;
import staticdata.IRTenor;
import staticdata.Option_Maturity;



public final class Tests {
	 
	protected Market market;
	
	public Tests(Market _market){
		
		market=_market;

		
	}
	 	
	public  void test()
	{
		TestEquality();
		//TestMarketInsert();
		TestMarketInsertTypes();

	}
		
	
		
	public  void TestEquality()
	{
		
		IRCurveType ircurvetype = IRCurveType.XCCY_basis__USD;
        Currency currency = Currency.USD;
        IRTenor irtenor = IRTenor._10yr;
        GIRRDeltaRiskFactorKey girrdeltakey = new GIRRDeltaRiskFactorKey(market.factory, currency,ircurvetype,irtenor);

        IRCurveType ircurvetype2 = IRCurveType.XCCY_basis__USD;
        Currency currency2 = Currency.EUR;
        IRTenor irtenor2 = IRTenor._10yr;
        GIRRDeltaRiskFactorKey girrdeltakey2 = new GIRRDeltaRiskFactorKey(market.factory,currency2,ircurvetype2,irtenor2 );

        Boolean test = (girrdeltakey.Equals(girrdeltakey2));
        System.out.println("Are elements equal ? " + test.toString());
	}
		
	public  void TestMarketInsert()
    {


        IRCurveType ircurvetype = IRCurveType.XCCY_basis__USD;
        Currency currency = Currency.USD;
        IRTenor irtenor = IRTenor._10yr;

        market.factory.GetGIRRDelta(currency,ircurvetype ,irtenor);

        IRCurveType ircurvetype2 = IRCurveType.XCCY_basis__USD;
        Currency currency2 = Currency.USD;
        IRTenor irtenor2 = IRTenor._10yr;
        GIRRDeltaRiskFactor girrdelta2 =market.factory.GetGIRRDelta(currency2,ircurvetype2,irtenor2);

        Boolean test =  market.riskfactors.Has(girrdelta2.Key());
        
        System.out.println("Is the second element stored in the heap ? " + test.toString());
        

        List<GIRRDeltaRiskFactor> listgirrdelta =  market.riskfactors.Get(GIRRDeltaRiskFactor.class);
        Integer numgirrdelta = listgirrdelta.size();

        System.out.println("How many elements stored in the heap ? " + numgirrdelta.toString());

    }
		
	public  void TestMarketInsertTypes()
    {
   

        IRCurveType ircurvetype = IRCurveType.XCCY_basis__USD;
        Currency currency = Currency.USD;
        IRTenor irtenor = IRTenor._10yr;
        market.factory.GetGIRRDelta(currency,ircurvetype,irtenor);

        Currency currency2 = Currency.EUR;
        Option_Maturity optionmaturity = Option_Maturity._10yr;
        IRTenor irtenor2 = IRTenor._10yr;
        market.factory.GetGIRRVega(currency2,optionmaturity,irtenor2);

        
        List<GIRRDeltaRiskFactor> listgirrdelta = market.riskfactors.Get(GIRRDeltaRiskFactor.class);
        Integer numgirrdelta = listgirrdelta.size();
        
        System.out.println("How many GIRRDelta elements stored in the heap ? " + numgirrdelta.toString());
        
        List<GIRRVegaRiskFactor> listgirrvega = market.riskfactors.Get(GIRRVegaRiskFactor.class);
        Integer numgirrvega = listgirrvega.size();
        
        System.out.println("How many GIRRVega elements stored in the heap ? " + numgirrvega.toString());
    } 

}
