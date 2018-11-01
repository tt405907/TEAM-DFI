package de;

import joueur.Joueur;

public class FaceOr extends Face{
	private int nombreOr;
	private int prix;

	public FaceOr(int n,int prix) {
		this.nombreOr = n;
		this.prix = prix;
	}

	public void appliquer(Joueur J) {
		J.addOr(nombreOr);
	}
	public int getPrix()
	{
		return this.prix;
	}
}
