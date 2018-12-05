package cartes;

import joueur.Joueur;

public class Minotaure extends Carte {

	public Minotaure() {
		super("Le Minotaure", 0, 3, 8);
	}

	@Override
	public void effetExploit(Joueur j) {
		Joueur[] koukou = j.getPartie().getEnnemis(j);
		for (Joueur J : koukou) {
			J.appliquerDeNegatif();
		}
	}
}
