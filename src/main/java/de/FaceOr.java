package de;

import joueur.Joueur;

public class FaceOr {
	private int nombreOr;

	public FaceOr(int n) {
		this.nombreOr = n;
	}

	public void appliquer(Joueur J) {
		J.addOr(nombreOr);
	}
}
