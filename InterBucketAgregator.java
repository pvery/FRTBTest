package containers;

import java.util.List;

public abstract class InterBucketAgregator <T extends IRisk<T,K>, K extends IRiskKey<T,K>>{
	
	
	protected List<T> bucketlist; 
	
	public InterBucketAgregator(List<T> _list){
		
		bucketlist=_list;
	}
	
	public abstract double GetCapitalStandAlone();


}
