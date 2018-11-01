package de;

import joueur.Joueur;

public class FaceSoleil extends Face{
	private int nombreSoleil;
	private int prix;

	public FaceSoleil(int n,int prix) {
		this.nombreSoleil = n;
		this.prix = prix;
	}

	public void appliquer(Joueur J) {
		J.addSoleil(nombreSoleil);
	}
	public int getPrix()
	{
		return this.prix;
	}
}
