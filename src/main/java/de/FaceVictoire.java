package de;

import joueur.Joueur;

public class FaceVictoire extends Face{
	private int nombreVictoire;

	public FaceVictoire(int n,int prix) {
		super(prix);
        this.nombreVictoire=n;
    }

	public void appliquer(Joueur J) {
		J.addVictoire(nombreVictoire);
	}
}
