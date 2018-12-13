package cartes;

import joueur.Joueur;

public class Carte {
	private int lune, soleil, victoire;
	private String nom;

	public String getNom() {
		return nom;
	}

	/**
	 * @param nom      Le nom de la carte dans le jeu Dice Forge
	 * @param lune     Le coût en lune de la carte
	 * @param soleil   Le coût en soleil de la carte
	 * @param victoire Le gain en victoire immédiat de la carte
	 */
	public Carte(String nom, int lune, int soleil, int victoire) {
		this.lune = lune;
		this.soleil = soleil;
		this.victoire = victoire;
		this.nom = nom;
	}

	/**
	 * Permet de savoir si un joueur peut achet cette carte
	 * 
	 * @param j joueur sur qui la fonction est appliquée
	 * @return vrai si j peut acheter la carte et faux sinon
	 */
	public boolean peutAcheter(Joueur j) {
		return j.getLune() >= lune && j.getSoleil() >= soleil;
	}

	/**
	 * Applique le coût de la carte sur le joueur et lui donne les points de
	 * victoire. Applique également l'effet à l'achat de la carte si il y en a une.
	 * 
	 * @param j le joueur qui achète cette carte.
	 */
	public void acheter(Joueur j) {
		j.addLune(-lune);
		j.addSoleil(-soleil);
		j.addVictoire(victoire);
		this.effetExploit(j);
	}

	/**
	 * Effet des cartes à l'achat, ne fait rien normalement mais va être override
	 * dans les carte qui ont un effet à l'achat.
	 * 
	 * @param j : Le joueur qui vient d'acheter la carte et sur qui l'effet à
	 *          l'achat va être appliqué.
	 */
	public void effetExploit(Joueur j) {
	}

	public int getPrixLune() {
		return lune;
	}

	public int getPrixSoleil() {
		return soleil;
	}

	public int getVictoire() {
		return victoire;
	}

	@Override
	public String toString() {
		return nom;
	}
}
