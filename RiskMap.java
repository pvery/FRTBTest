package containers;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RiskMap {
	
//	Container générique qui contient un dictionnaire general et des contraintes d'ajout à la pile
//	sous la forme de paires clef/valeur generique
	
	private Map<IRiskElemKey,IRiskElem> elems;
	
	
	public RiskMap()
	{
		this.elems = new HashMap<IRiskElemKey,IRiskElem>();
		
	}
	
	
	
	public boolean ComputeStandAlone(){
		
		for (IRiskElem elem : elems.values()){
			
			elem.ComputeStandAlone();
		}
		
		return true;
	}
	
	public <T extends IRisk<T, K>, K extends IRiskKey<T, K>> boolean Has(K key)
	{
	    
		
		return this.elems.containsKey(key);
	}

	public <T extends IRisk<T, K>, K extends IRiskKey<T, K>> T Get(K key,Class<T> type)
	{
	    return type.cast(this.elems.get(key));
	}
	

	
	public <T extends IRisk<T, K>, K extends IRiskKey<T, K>> void put(T elem)
	{
		this.elems.put(elem.Key(), elem);
		
	}
	
	public <T extends IRisk<T, K>, K extends IRiskKey<T, K>>  T Create(K key,Class<T> type)
	{
		if(this.Has(key)){
			return this.Get(key, type);
		}
		else{
			this.put(key.NewRiskFactor());
			return this.Get(key, type);
		}
		
	}

	
	public <T> List<T> Get(Class<T> mytype)
	{
		return this.elems.values().stream()
		        .filter(x -> mytype.isInstance(x))
		        .map(n -> mytype.cast(n))
		        .collect(Collectors.toList());
	}
	
	
	
	public boolean LogCapitalStandAlone(Logger mylog){
		
	
		for (Map.Entry<IRiskElemKey, IRiskElem> entry : elems.entrySet()) {
			mylog.log(java.util.logging.Level.INFO,"Computing Capital StandAlone for   : " + entry.getKey().GetString() + "  :   " + entry.getValue().GetCapitalStandAlone().toString());
		}
		
		return true;
	}
	

	

}
