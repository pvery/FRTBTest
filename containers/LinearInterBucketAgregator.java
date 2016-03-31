package containers;



import java.util.List;
import java.util.stream.Collectors;

public class LinearInterBucketAgregator <T extends IRisk<T,K>, K extends IRiskKey<T,K>> 
extends InterBucketAgregator<T,K>
 {
	
	
	
	public LinearInterBucketAgregator(List<T> _list){
		
		super(_list);
	}

	
	
	
	private double GetSumlinearsquares(List<Double> linearlist){
		
		double local=0.0;
		double Si=0.0;
		double Sj=0.0;
		
		for (int i = 0; i<bucketlist.size();i++){
			Si=linearlist.get(i);
			for(int j = 0; j<i;j++){
				Sj=linearlist.get(j);
				local+=2.0*Si*Sj*bucketlist.get(i).GetCorrelation(bucketlist.get(j));
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
