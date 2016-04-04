package containers;

import java.util.List;
import java.util.stream.Collectors;

public class CurvatureInterBucketAgregator <T extends IRisk<T,K>, K extends IRiskKey<T,K>> 
extends InterBucketAgregator<T,K>
{
	
	
	
	public CurvatureInterBucketAgregator(List<T> _list){
		
		super(_list);
	}

	
	
	
	private double GetSumlinearsquares(List<Double> linearlist){
		
		Double local=0.0;
		double Si=0.0;
		double Sj=0.0;
		double psi=0.0;
		for (int i = 0; i<bucketlist.size();i++){
			Si=linearlist.get(i);
			for(int j = 0; j<i;j++){
				Sj=linearlist.get(j);
				psi=((Si <0.0)&&(Sj <0)) ? 0.0 : 1.0;
				local+=2.0*Si*Sj*bucketlist.get(i).GetCorrelation(bucketlist.get(j))*psi;
			}
		}
		
		return local;
	}




	@Override
	public double GetCapitalStandAlone() {
		List<Double> capitallist = bucketlist.stream().map(x->x.GetCapitalStandAlone()).collect(Collectors.toList());
		List<Double> linearlist = bucketlist.stream().map(x->x.GetLinearStandAlone()).collect(Collectors.toList());
		
		
		// Calculation of Sum(Kb^2)
		Double Sumsquares = capitallist.stream().mapToDouble(x->x*x).sum();
		
		// Calculation of Sum(gamma*Sb*Sc)
		Double Sumlinearsquares=GetSumlinearsquares(linearlist);
		
		//Calculation of K 
		Double partial=Sumsquares+Sumlinearsquares;
		
		// test of positivity of K
		if (partial<0) {
			//Recomputation of Sb
			for (int i = 0; i<linearlist.size();i++){
				linearlist.set(i, Math.max(Math.min(linearlist.get(i), capitallist.get(i)), -capitallist.get(i)));
			}
			// Calculation of Sum(gamma*Sb*Sc)
			Sumlinearsquares=GetSumlinearsquares(linearlist);
		}
		
	
		
		return Math.sqrt(Sumsquares+Sumlinearsquares) ;
	}
	



}
