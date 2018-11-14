package cartes;

import joueur.Joueur;

public class Carte {
	private int lune, soleil, victoire;
	private String nom;
	
	public String getNom() {
		return nom;
	}

	public Carte(String nom, int lune, int soleil, int victoire) {
		this.lune = lune;
		this.soleil = soleil;
		this.victoire = victoire;
		this.nom = nom;
	}
	
	public boolean peutAcheter(Joueur j) {
		return j.getLune() >= lune && j.getSoleil() >= soleil;
	}
	
	public void acheter(Joueur j) {
		j.addLune(-lune);
		j.addSoleil(-soleil);
		j.addVictoire(victoire);
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
