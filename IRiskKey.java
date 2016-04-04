package containers;
public interface IRiskKey<T extends IRisk<T,K>,K extends IRiskKey<T,K>> extends IRiskElemKey {

//  Interface generique de la clef pour le pattern Clef/valeur g�n�rique
	
	boolean Equals(K other);
	T NewRiskFactor();
	
}
