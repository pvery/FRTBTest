package containers;

public interface IRisk<T extends IRisk<T,K>,K extends IRiskKey<T,K>> extends IRiskElem {

//  Interface generique de la valeur pour le pattern Clef/valeur g�n�rique
	
	K Key();
	Double GetCorrelation(T other);

}
