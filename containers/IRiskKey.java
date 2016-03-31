package containers;
public interface IRiskKey<T extends IRisk<T,K>,K extends IRiskKey<T,K>> {

//  Interface generique de la clef pour le pattern Clef/valeur générique
	
	boolean Equals(K other);
	T NewRiskFactor();
	
}
