package de;

import joueur.Joueur;

public class FaceLune extends Face{
	private int nombreLune;

	public FaceLune(int n) {
		this.nombreLune = n;
	}

	public void appliquer(Joueur J) {
		J.addLune(nombreLune);
	}
}
