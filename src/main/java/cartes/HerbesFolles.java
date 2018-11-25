package cartes;

import joueur.Joueur;

public class HerbesFolles extends Carte {

	public HerbesFolles() {
		super("Les Herbes Folles", 0, 1, 2);
	}

	// Les Herbes Folles donnent simplement 3 ors et 3 lunes à l'achat
	@Override
	public void effetExploit(Joueur j) {
		j.addLune(3);
		j.addOr(3);
	}
}
