package cartes;

import joueur.Joueur;

/**
 * L'enigme lance 4 faveurs mineures sur le joueur qui l'achète. 
 * La faveur mineure est gérée directement par ce dernier.
 */
public class Enigme extends Carte {

	public Enigme() {
		super("L'Enigme", 0, 6, 10);
	}

	//
	@Override
	public void effetExploit(Joueur j) {
		j.faveurMineure(4);
	}
}
