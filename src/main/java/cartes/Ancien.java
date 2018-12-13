package cartes;

import joueur.Joueur;

/**
 * La carte Ancien est une carte renfort qui donne à l'activation 4 points de
 * victoire en échange de 3 ors.
 */
public class Ancien extends CarteRenfort {

	public Ancien() {
		super("L'Ancien", 0, 1, 0);
	}

	@Override
	public boolean peutActiver(Joueur j) {
		return j.getOr() >= 3;
	}

	@Override
	public void effetRenfort(Joueur j) {
		j.addOr(-3);
		j.addVictoire(4);
	}

}
