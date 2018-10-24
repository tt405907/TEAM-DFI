package de;

import joueur.Joueur;

public class FaceVictoire {
	private int nombreVictoire;

	public FaceVictoire(int n) {
        this.nombreVictoire=n;
    }

	public void appliquer(Joueur J) {
		J.addVictoire(nombreVictoire);
	}
}
