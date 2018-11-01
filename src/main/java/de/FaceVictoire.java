package de;

import joueur.Joueur;

public class FaceVictoire extends Face{
	private int nombreVictoire;
	private int prix;

	public FaceVictoire(int n,int prix) {
        this.nombreVictoire=n;
        this.prix = prix;
    }

	public void appliquer(Joueur J) {
		J.addVictoire(nombreVictoire);
	}
	public int getPrix()
	{
		return this.prix;
	}
}
