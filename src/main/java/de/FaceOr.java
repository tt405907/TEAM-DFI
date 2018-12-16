package de;

import joueur.Joueur;

public class FaceOr extends Face{
	private int nombreOr;
	public static final String RESET = "\u001B[0m";
	public static final String YELLOW = "\u001B[1;33m";

	public FaceOr(int n,int prix) {
		super(prix);
		this.nombreOr = n;
	}

	public void appliquer(Joueur J) {
		J.addOr(nombreOr);
	}

	@Override
	public String toString() {
		return nombreOr + YELLOW + " Or" + RESET;
	}

	@Override
	public void appliquerNegatif(Joueur j) {
		j.addOr(-nombreOr);
	}
	
}
