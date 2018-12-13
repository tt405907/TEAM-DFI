package cartes;

import joueur.Joueur;

/**
 * Les sabots d'argent offrent une faveur mineure activable quand le joueur le
 * souhaite
 */
public class SabotsArgent extends CarteRenfort {

	public SabotsArgent() {
		super("Les Sabots d'Argent", 2, 0, 2);
	}

	@Override
	public void effetRenfort(Joueur j) {
		j.faveurMineure(1);
	}

}
