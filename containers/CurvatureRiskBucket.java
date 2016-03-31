package containers;

import java.util.List;
import java.util.stream.Collectors;


public class CurvatureRiskBucket  <T extends IRisk<T,K>, K extends IRiskKey<T,K>>
extends Risk 
implements IRiskBucket<T>{
	
	
private List<T> bucketlist; 
	
	public CurvatureRiskBucket(){
		
		
	}
	
	@Override
	public boolean Push(List<T> _list){
		
		bucketlist=_list;
		
		return true;
	}
	
	public Boolean ComputeStandAlone(){
		
		
		List<Double> capitallist = bucketlist.stream().map(x->x.GetCapitalStandAlone()).collect(Collectors.toList());
		
		
		// Calculation of Sum(CVRk^2)
		Double Sumsquares = capitallist.stream().mapToDouble(x->Math.max(x,0)*Math.max(x,0)).sum();
		
		// Calculation of Sum(rho*psi*CVRk*CVRl)
		Double Sumlinearsquares=GetSumlinearsquares(capitallist);
		
		//Calculation of Kb 
		Double partial=Sumsquares+Sumlinearsquares;

		this.capitalstandalone= Math.sqrt(Math.max(0, partial)) ;
		this.linearstandalone=bucketlist.stream().mapToDouble(x->x.GetCapitalStandAlone()).sum();
		
		return true;

	}
	
	private double GetSumlinearsquares(List<Double> capitallist){
		
		Double local=0.0;
		double psi=0.0;
		double capitali=0.0;
		double capitalj=0.0;
		for (int i = 0; i<bucketlist.size();i++){
			capitali=capitallist.get(i);
			for(int j = 0; j<i;j++){
				capitalj=capitallist.get(j);
				psi=((capitali <0.0)&&(capitalj <0)) ? 0.0 : 1.0;
				local+=2.0*capitali*capitalj*bucketlist.get(i).GetCorrelation(bucketlist.get(j))*psi;
			}
		}
		
		return local;
	}

}

