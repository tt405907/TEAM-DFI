package joueur;

import java.util.List;

import cartes.Carte;
import de.De;
import de.Face;
import partie.Partie;
import sanctuaire.ListeAchat;
public abstract class Joueur {
	private int or, orMax;
	private int soleil, soleilMax;
	private int lune, luneMax;
	private int victoire;
	
	private Partie partie;

	public void setPartie(Partie partie) {
		this.partie = partie;
		de1.setPartie(partie);
		de2.setPartie(partie);
	}
	
	//Fonctions de bot
	/**
	 * Propose plusieurs faces au bot pour qu'il donne l'indice de la face qu'il souhaite appliquer dans le tableau.
	 * @param faces les faces que le bot peut appliquer
	 * @return l'indice de la face à appliquer dans le tableau
	 */
	public abstract int choixFace(Face... faces);
	/**
	 * Demande au bot de forger la face donnée sur un de ces dés.
	 * @param face la face à forger
	 */
	public abstract void forge(Face face);
	/**
	 * Demande au bot de faire ses achats dans la liste d'achat donnée.
	 * @param liste ListeAchat générée avec Sanctuaire.getAchatsPossible
	 */
	public abstract void faireAchatsFace(ListeAchat liste);
	/**
	 * Demande au bot si il veut faire son tour au sanctuaire ou faire des exploits
	 * @return true si sanctuaire, false si exploit
	 */
	public abstract boolean tourSanctuaire();
	/**
	 * demande si il veut un tour en plus pour 2 soleils, n'est pas appelé si il peut pas
	 * @return true si oui false sinon
	 */
	public abstract boolean faireTourSupplementaire();
	
	/**
	 * Demande au bot si il veut acheter une carte dans la liste donnée.
	 * @param cartes que le bot peut acheter
	 * @return la Carte que le bot veut acheter ou nul si il ne veut rien acheter
	 */
	public abstract Carte faireAchatCartes(List<Carte> cartes);
	
	public boolean peutFaireTourSupplementaire() {
		return soleil >= 2;
	}
	
	// Pour l'affichage
	private String nom;
	protected De de1, de2;


	public Joueur(String nom) {
		super();
		this.nom = nom;
		orMax = 12;
		soleilMax = 6;
		luneMax = 6;

		// Des initaux
		de1 = new De(De.de1);
		de2 = new De(De.de2);
		de1.setPartie(partie);
		de2.setPartie(partie);
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
		//int old = this.or;
		this.or = Math.min(orMax, Math.max(0, this.or + or));
		//if (old != this.or) System.out.println(this + ": Or " + old + " -> " + this.or);
	}

	public void addSoleil(int soleil) {
		//int old = this.soleil;
		this.soleil = Math.min(soleilMax, Math.max(0, this.soleil + soleil));
		//if (old != this.soleil) System.out.println(this + ": Soleil " + old + " -> " + this.soleil);
	}

	public void addLune(int lune) {
		//int old = this.lune;
		this.lune = Math.min(luneMax, Math.max(0, this.lune + lune));
		//if (old != this.lune) System.out.println(this + ": Lune " + old + " -> " + this.lune);
	}

	public void addVictoire(int victoire) {
		//int old = this.victoire;
		this.victoire += victoire;
		//if (old != this.victoire) System.out.println(this + ": Victoire " + old + " -> " + this.victoire);
	}


	/**
	 * Cela nous permet de lancer et d'appliquer la face en question
	 */
	public void appliquerDe() {
		de1.appliquerDe(this);
		de2.appliquerDe(this);
		if(partie != null) partie.printRoll(this, de1.getLastFace(), de2.getLastFace());
	}
	
    public String getStatus() {
        String resources = this + " a "
        		+ getOr() + "/" + orMax + " or, "
        		+ getLune() + "/" + luneMax + " éclats de lune, "
        		+ getSoleil() + "/" + soleilMax + " éclats de soleil et "
        		+ getVictoire() + " points de victoire";
        String strde1 = "\nDé 1 : " + de1;
        String strde2 = "\nDé 2 : " + de2;
        return resources + strde1 + strde2;
    }
	
	@Override
	public String toString() {
		return nom;
	}
	
	public De getDe1() {
		return de1;
	}
	
	public De getDe2() {
		return de2;
	}
}