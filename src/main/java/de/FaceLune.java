package de;

import joueur.Joueur;

public class FaceLune extends Face{
	private int nombreLune;
	public static final String RESET = "\u001B[0m";
	public static final String BLUE = "\u001B[1;34m";

	public FaceLune(int n,int prix) {
		super(prix);
		this.nombreLune = n;
	}

	public void appliquer(Joueur J) {
		J.addLune(nombreLune);
	}

	@Override
	public String toString() {return nombreLune + BLUE + " Lune" + RESET; }

	@Override
	public void appliquerNegatif(Joueur j) {
		j.addLune(-nombreLune);
	}

}
