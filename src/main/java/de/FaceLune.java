package de;

import joueur.Joueur;

public class FaceLune extends Face{
	private int nombreLune;

	public FaceLune(int n,int prix) {
		super(prix);
		this.nombreLune = n;
	}

	public void appliquer(Joueur J) {
		J.addLune(nombreLune);
	}

	@Override
	public String toString() {
		return nombreLune + " Lune";
	}

}
