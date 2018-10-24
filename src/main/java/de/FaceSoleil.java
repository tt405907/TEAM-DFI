package de;

import joueur.Joueur;

public class FaceSoleil {
	private int nombreSoleil;

	public FaceSoleil(int n) {
		this.nombreSoleil = n;
	}

	public void appliquer(Joueur J) {
		J.addSoleil(nombreSoleil);
	}
}
