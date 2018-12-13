package cartes;

import joueur.Joueur;

/**
 * Le minotaure force les adversaires à lancer leurs dés et à appliquer l'opposé
 * du résultat.
 */
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
