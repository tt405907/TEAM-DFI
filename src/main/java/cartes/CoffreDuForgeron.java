package cartes;

import joueur.Joueur;

public class CoffreDuForgeron extends Carte {

	public CoffreDuForgeron() {
		super("Le Coffre du Forgeron", 1, 0, 2);
	}

	// Le coffre du forgeron augmente la capacité d'un joueur en or, soleil et lune.
	@Override
	public void effetExploit(Joueur j) {
		j.setOrMax(j.getOrMax() + 4);
		j.setLuneMax(j.getLuneMax() + 3);
		j.setSoleilMax(j.getSoleilMax() + 3);
	}
}
