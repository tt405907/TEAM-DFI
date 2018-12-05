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
	
	/**
	 * Si cette face va être "remplacée" par une autre via son effet (Miroir Abyssal), elle retourne cette face.
	 * Permet de détecter les x3 copiés par le Miroir.
	 */
	public Face getProxiedFace(Joueur j) {
		return this;
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
