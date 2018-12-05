package de;

import joueur.Joueur;

public class FaceSoleil extends Face{
	private int nombreSoleil;

	public FaceSoleil(int n,int prix) {
		super(prix);
		this.nombreSoleil = n;
	}

	public void appliquer(Joueur J) {
		J.addSoleil(nombreSoleil);
	}

	@Override
	public String toString() {
		return nombreSoleil + " Soleil";
	}

	@Override
	public void appliquerNegatif(Joueur j) {
		j.addSoleil(-nombreSoleil);
	}
	
}
