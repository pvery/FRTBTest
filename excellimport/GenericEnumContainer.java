package excellimport;


import java.util.HashMap;
import java.util.Map;



public class  GenericEnumContainer <K extends Enum<K>>{
	
// generic container that allows to store excel cells whatever their type ('string','boolean','String')
// The Key relates to an particular enum type ('InputTypeGIRRDelta','InputTypeGIRRvega',...)

// There is potentially a risk of overlapping enum types but the container is called in a context where only one type stands
// However, this could be corrected in future versions using generic keys instead of integers

private Map<K,Object> elems;
	
// Constructor	
	public GenericEnumContainer()
	{
		this.elems = new HashMap<K,Object>();
		
	}
	
//  Get a particular element that exists in the container
//  The method is generic and can be called whatever the type of the excel cell
//	The cast is clean however and performed at compilation time
	
	public <T> T Get(K key,Class<T> type)
	{
	    return type.cast(this.elems.get(key));
	}
	
//  Put a new excel cell in the container
	public <T> void put(K key, T elem)
	{
		this.elems.put(key, elem);
		
	}
	
}
