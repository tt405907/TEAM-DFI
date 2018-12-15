package de;

import joueur.Joueur;

public class FaceSoleil extends Face{
	private int nombreSoleil;
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[1;31m";

	public FaceSoleil(int n,int prix) {
		super(prix);
		this.nombreSoleil = n;
	}

	public void appliquer(Joueur J) {
		J.addSoleil(nombreSoleil);
	}

	@Override
	public String toString() { return nombreSoleil + RED + " Soleil" + RESET; }

	@Override
	public void appliquerNegatif(Joueur j) {
		j.addSoleil(-nombreSoleil);
	}
	
}
