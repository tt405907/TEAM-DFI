package joueur;

public class Joueur {
	private int or, orMax;
	private int soleil, soleilMax;
	private int lune, luneMax;
	private int victoire;

	// Pour l'affichage
	private String nom;

	public Joueur(String nom) {
		super();
		this.nom = nom;
		orMax = 12;
		soleilMax = 6;
		luneMax = 6;
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

}
