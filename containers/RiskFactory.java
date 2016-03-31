package containers;

import girr.GIRRDeltaRiskFactor;
import girr.GIRRDeltaRiskFactorKey;
import girr.GIRRVegaBucket;
import girr.GIRRVegaBucketKey;
import girr.GIRRVegaRiskFactor;
import girr.GIRRVegaRiskFactorKey;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import CalculFRTB.Market;
import XMLReader.XMLReader;
import XMLReader.XmlreaderGIRR;
import girr.GIRRCalculator;
import girr.GIRRCurvatureBucket;
import girr.GIRRCurvatureBucketKey;
import girr.GIRRCurvatureRiskFactor;
import girr.GIRRCurvatureRiskFactorKey;
import girr.GIRRDeltaBucket;
import girr.GIRRDeltaBucketKey;
import staticdata.IRCurveType;
import staticdata.IRTenor;
import staticdata.Option_Maturity;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;
import staticdata.Currency;

public final class RiskFactory {
	
	protected Market market;
	protected GIRRCalculator girrcalculator;
	
	public RiskFactory(Market _market){
		market=_market;
		
		
		buildGIRRCalculator();
		
	}
	
	public GIRRCalculator Getgirrcalculator(){
		
		return girrcalculator;
	}
	
	private boolean buildGIRRCalculator(){
		XmlreaderGIRR xmlreader=new XmlreaderGIRR();
		
		girrcalculator = new GIRRCalculator(xmlreader.x(),
				xmlreader.theta(),
				xmlreader.gamma(), 
				xmlreader.lh(), 
				xmlreader.rw(),
				xmlreader.alpha(),
				xmlreader.GIRRweights());
		
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
				//agregator= GIRRDeltaAgregator();
				agregator=Gen(GIRRDeltaRiskFactor.class,GIRRDeltaBucket.class);
				break;
			case Vega:
				agregator= GIRRVegaAgregator();
				break;
			case Curvature:
				agregator= GIRRCurvatureAgregator();
				break;
			}
			
			break;
		default:
			break;
		}
		
		
		return agregator;
	}
	
	public LinearInterBucketAgregator<GIRRDeltaBucket, GIRRDeltaBucketKey> GIRRDeltaAgregator(){
		
		
		List<GIRRDeltaBucket> liste = new ArrayList<GIRRDeltaBucket>();
		for (Map.Entry<GIRRDeltaBucketKey,List<GIRRDeltaRiskFactor>> entry : this.GetRiskByBucket(GIRRDeltaRiskFactor.class).entrySet()){
			
			//GIRRDeltaBucket  girrdeltabucket = this.GetGIRRDeltaBucket(entry.getKey());
			GIRRDeltaBucket  girrdeltabucket =this.market.riskfactors.Create(entry.getKey(), GIRRDeltaBucket.class);
			girrdeltabucket.Push(entry.getValue());
			girrdeltabucket.ComputeStandAlone();
			liste.add(girrdeltabucket);
			
		}
		return new LinearInterBucketAgregator<GIRRDeltaBucket,GIRRDeltaBucketKey>(liste);
		
	}
	
	
	public  <T extends IRisk<T,K>, K extends IRiskKey<T,K> & IRiskSubKey<S>,S extends IRiskKey<U,S>,U  extends IRisk<U,S> & IRiskBucket<T>>  InterBucketAgregator<U,S> Gen(Class<T> typeT,Class<U> typeU){
		
		List<U> liste = new ArrayList<U>();
		
		for (Map.Entry<S,List<T>> entry : this.GetRiskByBucket(typeT).entrySet()){
			
			U  girrdeltabucket =this.market.riskfactors.Create(entry.getKey(), typeU);
			girrdeltabucket.Push(entry.getValue());
			girrdeltabucket.ComputeStandAlone();
			liste.add(girrdeltabucket);
			
		}
		
		return new LinearInterBucketAgregator<U,S>(liste);
	}
	
	public LinearInterBucketAgregator<GIRRVegaBucket, GIRRVegaBucketKey> GIRRVegaAgregator(){
		
		
		List<GIRRVegaBucket> liste = new ArrayList<GIRRVegaBucket>();
		for (Map.Entry<GIRRVegaBucketKey,List<GIRRVegaRiskFactor>> entry : this.GetRiskByBucket(GIRRVegaRiskFactor.class).entrySet()){
			

			GIRRVegaBucket  girrvegabucket =this.market.riskfactors.Create(entry.getKey(), GIRRVegaBucket.class);
			girrvegabucket.Push(entry.getValue());
			girrvegabucket.ComputeStandAlone();
			liste.add(girrvegabucket);
			
		}
		return new LinearInterBucketAgregator<GIRRVegaBucket,GIRRVegaBucketKey>(liste);
		
	}
	
	public CurvatureInterBucketAgregator<GIRRCurvatureBucket, GIRRCurvatureBucketKey> GIRRCurvatureAgregator(){
		
		
		List<GIRRCurvatureBucket> liste = new ArrayList<GIRRCurvatureBucket>();
		for (Map.Entry<GIRRCurvatureBucketKey,List<GIRRCurvatureRiskFactor>> entry : this.GetRiskByBucket(GIRRCurvatureRiskFactor.class).entrySet()){
			

			GIRRCurvatureBucket  girrcurvaturebucket =this.market.riskfactors.Create(entry.getKey(), GIRRCurvatureBucket.class);
			girrcurvaturebucket.Push(entry.getValue());
			girrcurvaturebucket.ComputeStandAlone();
			liste.add(girrcurvaturebucket);
			
		}
		return new CurvatureInterBucketAgregator<GIRRCurvatureBucket,GIRRCurvatureBucketKey>(liste);
		
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
	
	
	
	
	public GIRRDeltaRiskFactor GetGIRRDelta(Currency currency,IRCurveType ircurvetype,IRTenor irtenor)
	{
		
//		Création de la clef pour un axe GIRRDelta et ajout dans le dictionnaire global
        GIRRDeltaRiskFactorKey girrdeltakey = new GIRRDeltaRiskFactorKey(this.market.factory,currency,ircurvetype,irtenor );
       
        
        GIRRDeltaRiskFactor girrdeltariskfactor=this.market.riskfactors.Create(girrdeltakey, GIRRDeltaRiskFactor.class);

        return girrdeltariskfactor;

	}
	
	
	public GIRRVegaRiskFactor GetGIRRVega(Currency currency,Option_Maturity optionmaturity,IRTenor irtenor)
	{
	
//		Création de la clef pour un axe GIRRVega et ajout dans le dictionnaire global
        GIRRVegaRiskFactorKey girrvegakey = new GIRRVegaRiskFactorKey(this.market.factory,currency,optionmaturity,irtenor);
        
        GIRRVegaRiskFactor girrvegariskfactor= this.market.riskfactors.Create(girrvegakey, GIRRVegaRiskFactor.class);

        return girrvegariskfactor;
    	
	}
	
	public GIRRCurvatureRiskFactor GetGIRRCurvature(Currency currency)
	{
		
//		Création de la clef pour un axe GIRRCurvature et ajout dans le dictionnaire global
		GIRRCurvatureRiskFactorKey girrcurvaturekey = new GIRRCurvatureRiskFactorKey(this.market.factory,currency);
		
		GIRRCurvatureRiskFactor girrcurvatureriskfactor= this.market.riskfactors.Create(girrcurvaturekey, GIRRCurvatureRiskFactor.class);

		return girrcurvatureriskfactor;
	}
	

}
