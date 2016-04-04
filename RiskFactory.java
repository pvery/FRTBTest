package containers;

import girr.GIRRDeltaRiskFactor;
import girr.GIRRDeltaRiskFactorKey;
import girr.GIRRVegaBucket;
import girr.GIRRVegaRiskFactor;
import girr.GIRRVegaRiskFactorKey;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import CalculFRTB.Market;
import XMLReader.XmlreaderGIRR;
import equity.EquityCalculator;
import equity.EquityCurvatureBucket;
import equity.EquityCurvatureRiskFactor;
import equity.EquityCurvatureRiskFactorKey;
import equity.EquityDeltaBucket;
import equity.EquityDeltaRiskFactor;
import equity.EquityDeltaRiskFactorKey;
import equity.EquityVegaBucket;
import equity.EquityVegaRiskFactor;
import equity.EquityVegaRiskFactorKey;
import girr.GIRRCalculator;
import girr.GIRRCurvatureBucket;
import girr.GIRRCurvatureRiskFactor;
import girr.GIRRCurvatureRiskFactorKey;
import girr.GIRRDeltaBucket;
import staticdata.EQBucket;
import staticdata.EQRiskFactorClass;
import staticdata.IRCurveType;
import staticdata.IRTenor;
import staticdata.Option_Maturity;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;
import staticdata.IRccy;

public final class RiskFactory {
	
	protected Market market;

	protected GIRRCalculator girrcalculator;
	protected EquityCalculator equitycalculator;
	
	public RiskFactory(Market _market){
		market=_market;

		
		buildGIRRCalculator();
		buildEquityCalculator();
		
	}
	
	public GIRRCalculator Getgirrcalculator(){
		
		return girrcalculator;
	}
	
	public EquityCalculator Getequitycalculator(){
		
		return equitycalculator;
	}
	
	private boolean buildEquityCalculator(){
		
		
		XmlreaderGIRR xmlreader=new XmlreaderGIRR(); 
		girrcalculator = new GIRRCalculator(xmlreader.x(),
				xmlreader.theta(),
				xmlreader.gamma(), 
				xmlreader.lh(), 
				xmlreader.rw(),
				xmlreader.alpha(),
				xmlreader.weights());
		
		return true;
	}
	
	private boolean buildGIRRCalculator(){
		
		
		XmlreaderGIRR xmlreader=new XmlreaderGIRR(); 
		girrcalculator = new GIRRCalculator(xmlreader.x(),
				xmlreader.theta(),
				xmlreader.gamma(), 
				xmlreader.lh(), 
				xmlreader.rw(),
				xmlreader.alpha(),
				xmlreader.weights());
		
		return true;
	}
	

	public <T extends IRisk<T,K>, K extends IRiskKey<T,K> & IRiskSubKey<S>,S> Map<S,List<T>> GetRiskByBucket(Class<T> mytype)
	{
		
		//T => GIRRDeltaRiskFactor
		//K => GIRRDelteRiskFactorKey
		//S=> GIRRCurrency
		List<T> list= market.riskfactors.Get(mytype);
		
		return list.stream()
				.collect(groupingBy(x->x.Key().GetSuperKey()));
		
	}
	
	public InterBucketAgregator<?, ?> getAgregator(RiskFactorClass riskfactorclass,SensitivityType sensitivitytype){
		
		InterBucketAgregator<?, ?> agregator=null;
		
		switch (riskfactorclass) {
		case GIRR:
			switch (sensitivitytype) {
			case Delta:
				agregator=GetLinearBucketAgregator(GIRRDeltaRiskFactor.class,GIRRDeltaBucket.class);
				break;
			case Vega:
				agregator=GetLinearBucketAgregator(GIRRVegaRiskFactor.class,GIRRVegaBucket.class);
				break;
			case Curvature:
				agregator=GetCurvatureBucketAgregator(GIRRCurvatureRiskFactor.class,GIRRCurvatureBucket.class);
				break;
			}
			break;
		case Equity:
			switch (sensitivitytype) {
			case Delta:
				agregator=GetLinearBucketAgregator(EquityDeltaRiskFactor.class, EquityDeltaBucket.class);
				break;
			case Vega:
				agregator=GetLinearBucketAgregator(EquityVegaRiskFactor.class,EquityVegaBucket.class);
				break;
			case Curvature:
				agregator=GetCurvatureBucketAgregator(EquityCurvatureRiskFactor.class,EquityCurvatureBucket.class);
				break;
			}
			break;
		default:
			break;
		}
		
		
		return agregator;
	}

	
	public  <T extends IRisk<T,K>, K extends IRiskKey<T,K> & IRiskSubKey<S>,S extends IRiskKey<U,S>,U  extends IRisk<U,S> & IRiskBucket<T>>  List<U> GetListBuckets(Class<T> typeT,Class<U> typeU){
		
		List<U> liste = new ArrayList<U>();
		
		for (Map.Entry<S,List<T>> entry : this.GetRiskByBucket(typeT).entrySet()){
			
			U  bucket =this.market.riskfactors.Create(entry.getKey(), typeU);
			bucket.Push(entry.getValue());
			bucket.ComputeStandAlone();
			liste.add(bucket);
			
		}
		
		return liste;
	}
	
	public  <T extends IRisk<T,K>, K extends IRiskKey<T,K> & IRiskSubKey<S>,S extends IRiskKey<U,S>,U  extends IRisk<U,S> & IRiskBucket<T>>  InterBucketAgregator<U,S> GetLinearBucketAgregator(Class<T> typeT,Class<U> typeU){
	
		List<U> liste =this.GetListBuckets(typeT, typeU);
		
		return new LinearInterBucketAgregator<U,S>(liste);
		
	}
	
	public  <T extends IRisk<T,K>, K extends IRiskKey<T,K> & IRiskSubKey<S>,S extends IRiskKey<U,S>,U  extends IRisk<U,S> & IRiskBucket<T>>  InterBucketAgregator<U,S> GetCurvatureBucketAgregator(Class<T> typeT,Class<U> typeU){
		
		List<U> liste =this.GetListBuckets(typeT, typeU);
		
		return new CurvatureInterBucketAgregator<U,S>(liste);
		
	}
	
	
	
	
	
	
	
	
	public RiskInterBucket GetRiskInterBucket(RiskFactorClass riskfactorclass,SensitivityType sensitivitytype){
		
		RiskInterBucketKey riskinterbucketkey = new RiskInterBucketKey(this.market.factory,riskfactorclass,sensitivitytype);
	
		RiskInterBucket riskinterbucket= this.market.riskfactors.Create(riskinterbucketkey, RiskInterBucket.class);
		
		riskinterbucket.push(this.getAgregator(riskfactorclass, sensitivitytype));
		
		riskinterbucket.ComputeStandAlone();
		
		return riskinterbucket;
	}
	
	
	public GlobalRisk GetGlobalRisk(){
		
		GlobalRiskKey globalriskkey= new GlobalRiskKey(this.market.factory);
		
		return this.market.riskfactors.Create(globalriskkey, GlobalRisk.class);
		
	}
	
	
	
	public EquityDeltaRiskFactor GetEquityDelta(EQBucket bucket,EQRiskFactorClass factorclass ,String equityname)
	{
		
//		Création de la clef pour un axe GIRRDelta et ajout dans le dictionnaire global
        EquityDeltaRiskFactorKey equitydeltakey = new EquityDeltaRiskFactorKey(this.market.factory,bucket,factorclass,equityname );
       
        
        EquityDeltaRiskFactor equitydeltariskfactor=this.market.riskfactors.Create(equitydeltakey, EquityDeltaRiskFactor.class);

        return equitydeltariskfactor;

	}
	
	public EquityVegaRiskFactor GetEquityVega(EQBucket bucket,EQRiskFactorClass factorclass ,String equityname,Option_Maturity optionmaturity)
	{
		
//		Création de la clef pour un axe GIRRDelta et ajout dans le dictionnaire global
        EquityVegaRiskFactorKey equityvegakey = new EquityVegaRiskFactorKey(this.market.factory,bucket,factorclass,equityname,optionmaturity );
       
        
        EquityVegaRiskFactor equityvegariskfactor=this.market.riskfactors.Create(equityvegakey, EquityVegaRiskFactor.class);

        return equityvegariskfactor;

	}
	
	public EquityCurvatureRiskFactor GetEquityCurvature(EQBucket bucket,EQRiskFactorClass factorclass ,String equityname)
	{
		
//		Création de la clef pour un axe GIRRCurvature et ajout dans le dictionnaire global
		EquityCurvatureRiskFactorKey equitycurvaturekey = new EquityCurvatureRiskFactorKey(this.market.factory,bucket,factorclass,equityname );
		
		EquityCurvatureRiskFactor equitycurvatureriskfactor= this.market.riskfactors.Create(equitycurvaturekey, EquityCurvatureRiskFactor.class);

		return equitycurvatureriskfactor;
	}
	
	public GIRRDeltaRiskFactor GetGIRRDelta(IRccy currency,IRCurveType ircurvetype,IRTenor irtenor)
	{
		
//		Création de la clef pour un axe GIRRDelta et ajout dans le dictionnaire global
        GIRRDeltaRiskFactorKey girrdeltakey = new GIRRDeltaRiskFactorKey(this.market.factory,currency,ircurvetype,irtenor );
       
        
        GIRRDeltaRiskFactor girrdeltariskfactor=this.market.riskfactors.Create(girrdeltakey, GIRRDeltaRiskFactor.class);

        return girrdeltariskfactor;

	}
	
	
	public GIRRVegaRiskFactor GetGIRRVega(IRccy currency,Option_Maturity optionmaturity,IRTenor irtenor)
	{
	
//		Création de la clef pour un axe GIRRVega et ajout dans le dictionnaire global
        GIRRVegaRiskFactorKey girrvegakey = new GIRRVegaRiskFactorKey(this.market.factory,currency,optionmaturity,irtenor);
        
        GIRRVegaRiskFactor girrvegariskfactor= this.market.riskfactors.Create(girrvegakey, GIRRVegaRiskFactor.class);

        return girrvegariskfactor;
    	
	}
	
	public GIRRCurvatureRiskFactor GetGIRRCurvature(IRccy currency)
	{
		
//		Création de la clef pour un axe GIRRCurvature et ajout dans le dictionnaire global
		GIRRCurvatureRiskFactorKey girrcurvaturekey = new GIRRCurvatureRiskFactorKey(this.market.factory,currency);
		
		GIRRCurvatureRiskFactor girrcurvatureriskfactor= this.market.riskfactors.Create(girrcurvaturekey, GIRRCurvatureRiskFactor.class);

		return girrcurvatureriskfactor;
	}
	

}
