package cartes;

import joueur.Joueur;

public class Enigme extends Carte {

	public Enigme() {
		super("L'Enigme", 0, 6, 10);
	}

	// L'enigme lance 4 faveurs mineures sur le joueur qui l'achète.
	// La faveure mineure est gérée directement dans joueur.
	@Override
	public void effetExploit(Joueur j) {
		j.faveurMineure(4);
	}
}
