package de;

import joueur.Joueur;

public abstract class Face {
	public abstract void appliquer(Joueur J);

	private int prix;
	public int getPrix()
	{
		return this.prix;
	}
	public Face(int prix)
	{
		this.prix = prix;
	}


}
