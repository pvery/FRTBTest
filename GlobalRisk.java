package containers;


import java.util.ArrayList;
import java.util.List;

import staticdata.InterBucketComputations;
import staticdata.RiskFactorClass;
import staticdata.SensitivityType;

public class GlobalRisk extends Risk implements  IRisk<GlobalRisk, GlobalRiskKey>{

	protected GlobalRiskKey key;
	protected List<RiskInterBucket> list;
	
	public GlobalRisk(GlobalRiskKey _key)
    {
        this.key = _key;
        
        
        list = new ArrayList<RiskInterBucket>();
        
        for (InterBucketComputations computation : InterBucketComputations.values()) {
			
			RiskFactorClass riskfactorclass=computation.riskfactorclass;
			SensitivityType sensitivitytype =computation.sensitivitytype;
			
			list.add(key.factory.GetRiskInterBucket(riskfactorclass, sensitivitytype));
			
		}
        
        this.ComputeStandAlone();
    }
	

	
	
	@Override
	public GlobalRiskKey Key() {

		return this.key;
	}

	@Override
	public Double GetCorrelation(GlobalRisk other) {
		return 1.0;
	}




	@Override
	public Boolean ComputeStandAlone() {
		
		//double local=0.0;
		
//		for (RiskInterBucket interbucket : list){
//			local+=interbucket.GetCapitalStandAlone();
//						
//		}
		
		//this.capitalstandalone=local;
		
		this.capitalstandalone= list.stream().mapToDouble(x -> x.GetCapitalStandAlone()).sum();
		
		return true;
	}

}
