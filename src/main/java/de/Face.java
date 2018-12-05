package de;

import joueur.Joueur;

public abstract class Face {
	public abstract void appliquer(Joueur j);
	
	public void appliquerX3(Joueur j) {
		//Implémentation par défaut
		appliquer(j);
		appliquer(j);
		appliquer(j);
	}

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
