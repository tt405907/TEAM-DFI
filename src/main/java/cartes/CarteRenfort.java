package cartes;

import joueur.Joueur;

public abstract class CarteRenfort extends Carte {
	public CarteRenfort(String nom, int lune, int soleil, int victoire) {
		super(nom, lune, soleil, victoire);
	}

	// renvoie true si le joueur peut activer la carte a ce moment
	public boolean peutActiver(Joueur j) {
		return true;
	}
	
	// fait l'effet de la carte quand elle est activee par le joueur
	public abstract void effetRenfort(Joueur j);
	
	@Override
	public void effetExploit(Joueur j) {
		j.addRenfort(this);
	}
}
