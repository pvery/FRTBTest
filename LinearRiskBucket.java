package containers;

import java.util.List;
import java.util.stream.Collectors;

public class LinearRiskBucket  <T extends IRisk<T,K>, K extends IRiskKey<T,K>> 
extends Risk 
implements IRiskBucket<T>{
	
	
private List<T> bucketlist; 
	
	public LinearRiskBucket(){
		
		
	}
	
	@Override
	public boolean Push(List<T> _list){
		
		bucketlist=_list;
		
		return true;
	}
	
	public Boolean ComputeStandAlone(){
		
		
		List<Double> capitallist = bucketlist.stream().map(x->x.GetCapitalStandAlone()).collect(Collectors.toList());
		
		
		// Calculation of Sum(Ws^2)
		Double Sumsquares = capitallist.stream().mapToDouble(x->x*x).sum();
		
		// Calculation of Sum(rho*Wsk*Wsl)
		Double Sumlinearsquares=GetSumlinearsquares(capitallist);
		
		//Calculation of Kb 
		Double partial=Sumsquares+Sumlinearsquares;

		this.capitalstandalone= Math.sqrt(partial) ;
		this.linearstandalone=bucketlist.stream().mapToDouble(x->x.GetCapitalStandAlone()).sum();
		
		return true;

	}
	
	private double GetSumlinearsquares(List<Double> capitallist){
		
		Double local=0.0;
		Double capitali=0.0;
		double capitalj=0.0;
		
		for (int i = 0; i<bucketlist.size();i++){
			capitali=capitallist.get(i);
			for(int j = 0; j<i;j++){
				capitalj=capitallist.get(j);
				local+=2.0*capitali*capitalj*bucketlist.get(i).GetCorrelation(bucketlist.get(j));
			}
		}
		
		return local;
	}

}
