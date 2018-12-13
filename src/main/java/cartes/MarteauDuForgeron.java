package cartes;

import joueur.Joueur;

/**
 * La carte Marteau du Forgeron ajoute un nouveau marteau avec un jeton nul à
 * l'inventaire du joueur lors de l'achat.
 */
public class MarteauDuForgeron extends Carte {

	public MarteauDuForgeron() {
		super("Le Marteau du Forgeron", 1, 0, 0);
	}

	//
	@Override
	public void effetExploit(Joueur j) {
		UnMarteau marteau = new UnMarteau(j);
		j.addMarteau(marteau);
	}
}
