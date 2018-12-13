package cartes;

import joueur.Joueur;

/**
 * Le coffre du forgeron augmente la capacité d'un joueur en or de 4, en soleil
 * de 3 et en lune de 3.
 */
public class CoffreDuForgeron extends Carte {

	public CoffreDuForgeron() {
		super("Le Coffre du Forgeron", 1, 0, 2);
	}

	//
	@Override
	public void effetExploit(Joueur j) {
		j.setOrMax(j.getOrMax() + 4);
		j.setLuneMax(j.getLuneMax() + 3);
		j.setSoleilMax(j.getSoleilMax() + 3);
	}
}
