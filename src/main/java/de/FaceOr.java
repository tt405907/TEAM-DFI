package de;

import joueur.Joueur;

public class FaceOr extends Face{
	private int nombreOr;

	public FaceOr(int n) {
		this.nombreOr = n;
	}

	public void appliquer(Joueur J) {
		J.addOr(nombreOr);
	}
}
