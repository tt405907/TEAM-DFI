package joueur;

import de.De;
import de.Face;
public class Joueur {
	private int or, orMax;
	private int soleil, soleilMax;
	private int lune, luneMax;
	private int victoire;

	// Pour l'affichage
	private String nom;
	private De de1, de2;

	public Joueur(String nom) {
		super();
		this.nom = nom;
		orMax = 12;
		soleilMax = 6;
		luneMax = 6;

		// Des initaux
		de1 = new De(De.de1);
		de2 = new De(De.de2);
	}

	public int getOr() {
		return or;
	}

	public int getSoleil() {
		return soleil;
	}

	public int getLune() {
		return lune;
	}

	public int getVictoire() {
		return victoire;
	}

	public void addOr(int or) {
		this.or = Math.min(orMax, Math.max(0, this.or + or));
	}

	public void addSoleil(int soleil) {
		this.soleil = Math.min(soleilMax, Math.max(0, this.soleil + soleil));
	}

	public void addLune(int lune) {
		this.lune = Math.min(luneMax, Math.max(0, this.lune + lune));
	}

	public void addVictoire(int victoire) {
		this.victoire += victoire;
	}


	/**
	 * Cela nous permet de lancer et d'appliquer la face en question
	 */
	public void appliquerDe() {
		de1.appliquerDe(this);
		de2.appliquerDe(this);
	}

}