package de;

import joueur.Joueur;

public class FaceLune {
	private int nombreLune;

	public FaceLune(int n) {
		this.nombreLune = n;
	}

	public void appliquer(Joueur J) {
		J.addLune(nombreLune);
	}
}
