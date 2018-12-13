package cartes;

import joueur.Joueur;

/**
 * La pince lance 2 fois chaque dés du joueur lors de l'achat
 */
public class Pince extends Carte {

	public Pince() {
		super("La Pince", 6, 0, 8);
	}

	@Override
	public void effetExploit(Joueur j) {
		j.appliquerDe();
		j.appliquerDe();
	}
}
