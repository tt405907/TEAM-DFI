package cartes;

import joueur.Joueur;

/**
 * Les cartes renforts ont un effet activable.
 */
public abstract class CarteRenfort extends Carte {
	public CarteRenfort(String nom, int lune, int soleil, int victoire) {
		super(nom, lune, soleil, victoire);
	}

	/**
	 * Dit si un joueur peut activer la carte
	 * 
	 * @param j Le joueur qui détient la carte.
	 * @return Renvoie true à priori sauf cas particulier, où cette fonction sera
	 *         donc override.
	 */
	public boolean peutActiver(Joueur j) {
		return true;
	}

	/**
	 * Methode qui applique l'effet de renfort quand le joueur l'active.s
	 * 
	 * @param j le joueur qui active l'effet et sur lequel celui-ci sera activé.
	 */
	public abstract void effetRenfort(Joueur j);


	@Override
	public void effetExploit(Joueur j) {
		j.addRenfort(this);
	}
}
