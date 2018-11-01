package de;

import joueur.Joueur;

public class FaceLune extends Face{
	private int nombreLune;
	private int prix;

	public FaceLune(int n,int prix) {
		this.nombreLune = n;
		this.prix = prix;
	}

	public void appliquer(Joueur J) {
		J.addLune(nombreLune);
	}

	public int getPrix()
	{
		return this.prix;
	}
}
