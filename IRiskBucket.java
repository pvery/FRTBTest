package containers;

import java.util.List;

public interface IRiskBucket<T> {
	
	public boolean Push(List<T> _list);

}
