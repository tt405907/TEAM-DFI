package cartes;

import joueur.Joueur;

public class SabotsArgent extends CarteRenfort {

	public SabotsArgent() {
		super("Les Sabots d'Argent", 2, 0, 2);
	}

	@Override
	public void effetRenfort(Joueur j) {
		j.faveurMineure(1);
	}

}
