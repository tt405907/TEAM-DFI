package cartes;

import joueur.Joueur;

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
