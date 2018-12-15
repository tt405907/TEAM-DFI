package de;

import joueur.Joueur;

public class FaceVictoire extends Face{
	private int nombreVictoire;
	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[1;32m";

	public FaceVictoire(int n,int prix) {
		super(prix);
        this.nombreVictoire=n;
    }

	public void appliquer(Joueur J) {
		J.addVictoire(nombreVictoire);
	}

	@Override
	public String toString() {return nombreVictoire + GREEN + " Victoire" + RESET; }
	
	public void appliquerNegatif(Joueur j) {
		j.addVictoire(-nombreVictoire);
	}
	
}
