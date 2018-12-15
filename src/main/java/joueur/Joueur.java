package joueur;

import java.util.ArrayList;
import java.util.List;

import cartes.Carte;
import cartes.CarteRenfort;
import cartes.UnMarteau;
import de.De;
import de.Face;
import de.Faces;
import partie.Partie;
import sanctuaire.ListeAchat;

public class Joueur {
	private int or, orMax;
	private int soleil, soleilMax;
	private int lune, luneMax;
	private int victoire;
	public static final String RESET = "\u001B[0m";
	public static final String RED_B = "\u001B[0;91m";    // RED
	public static final String GREEN_B = "\u001B[0;92m";  // GREEN
	public static final String YELLOW_B = "\u001B[0;93m"; // YELLOW
	public static final String BLUE_B = "\u001B[0;94m";   // BLUE

	private List<CarteRenfort> renforts;

	private List<UnMarteau> marteaux;

	private Partie partie;
	
	private Bot bot;

	public void setPartie(Partie partie) {
		this.partie = partie;
		de1.setPartie(partie);
		de2.setPartie(partie);
	}
	
	/**
	 * Change le bot qui fait jouer ce joueur et l'initialise.
	 * @return lui-même pour faciliter la construction
	 */
	public Joueur setBot(Bot bot) {
		this.bot = bot;
		this.bot.setJoueur(this);
		
		return this;
	}
	
	public Partie getPartie() {
		return partie;
	}

	// Fonctions de bot
	/**
	 * Propose plusieurs faces au bot pour qu'il donne l'indice de la face qu'il
	 * souhaite appliquer dans le tableau.
	 * 
	 * @param faces les faces que le bot peut appliquer
	 * @return l'indice de la face à appliquer dans le tableau
	 */
	public int choixFace(Face... faces) {
		return bot.choixFace(faces);
	}
	
	/**
	 * Propose plusieurs faces au bot pour qu'il donne l'indice de la face qu'il
	 * souhaite appliquer négativement (minotaure) dans le tableau.
	 * 
	 * @param faces les faces que le bot peut appliquer
	 * @return l'indice de la face à appliquer dans le tableau
	 */
	public int choixFaceNegatif(Face... faces) {
		return bot.choixFaceNegatif(faces);
	}

	/**
	 * Demande au bot de forger la face donnée sur un de ces dés.
	 * 
	 * @param face la face à forger
	 */
	public void forge(Face face) {
		bot.forge(face);
	}

	/**
	 * Demande au bot de faire ses achats dans la liste d'achat donnée.
	 * 
	 * @param liste ListeAchat générée avec Sanctuaire.getAchatsPossible
	 */
	public void faireAchatsFace(ListeAchat liste) {
		bot.faireAchatsFace(liste);
	}

	/**
	 * Demande au bot si il veut faire son tour au sanctuaire ou faire des exploits
	 * 
	 * @return true si sanctuaire, false si exploit
	 */
	public boolean tourSanctuaire() {
		return bot.tourSanctuaire();
	}

	/**
	 * demande si il veut un tour en plus pour 2 soleils, n'est pas appelé si il
	 * peut pas
	 * 
	 * @return true si oui false sinon
	 */
	public boolean faireTourSupplementaire() {
		return bot.faireTourSupplementaire();
	}

	/**
	 * Demande au bot si il veut acheter une carte dans la liste donnée.
	 * 
	 * @param cartes que le bot peut acheter
	 * @return la Carte que le bot veut acheter ou nul si il ne veut rien acheter
	 */
	public Carte faireAchatCartes(List<Carte> cartes) {
		return bot.faireAchatCartes(cartes);
	}

	/**
	 * choisit le dé que le joueur veut lancer lors d'une faveur mineure
	 */
	public De choixFaveurMineure() {
		return bot.choixFaveurMineure();
	}

	/**
	 * demande au bot quelle carte renfort il veut activer dans la liste de ceux
	 * qu'il n'a pas encore activee (donnee en parametre) ou null si il veut finir
	 */
	public CarteRenfort choixRenfort(List<CarteRenfort> liste) {
		return bot.choixRenfort(liste);
	}

	/**
	 * Demande au bot combien d'or veut-il mettre dans son marteau
	 * 
	 * @param or : Or qu'il est possible de placer dans le marteau.
	 * @return Or que le bot veut placer dans le marteau.
	 */
	public int changeOrEnMarteau(int or) {
		return bot.changeOrEnMarteau(or);
	}

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

		renforts = new ArrayList<>();
		marteaux= new ArrayList<>();
		
		//Bot par défaut
		setBot(new BotDefault());
	}
	
	public void addRenfort(CarteRenfort carte) {
		renforts.add(carte);
	}

	// utilise les cartes renforts du joueur dans l'ordre qu'il veut
	public final void utiliserRenforts() {
		List<CarteRenfort> restants = new ArrayList<>(renforts);

		while (!restants.isEmpty()) {
			CarteRenfort choisie = choixRenfort(restants);
			if (choisie == null)
				break;

			if (choisie.peutActiver(this) && restants.remove(choisie)) {
				partie.printRenfort(choisie);
				choisie.effetRenfort(this);
			}
		}
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
		if (getNextMarteau() != null && or > 0) {
			int c = changeOrEnMarteau(or);
			if (c > or)
				c = or;
			if (c > 0) {
				getNextMarteau().effetAddOr(c);
				or -= c;
			}
		}
		// int old = this.or;
		this.or = Math.min(orMax, Math.max(0, this.or + or));
		// if (old != this.or) System.out.println(this + ": Or " + old + " -> " +
		// this.or);
	}

	public void addSoleil(int soleil) {
		// int old = this.soleil;
		this.soleil = Math.min(soleilMax, Math.max(0, this.soleil + soleil));
		// if (old != this.soleil) System.out.println(this + ": Soleil " + old + " -> "
		// + this.soleil);
	}

	public void addLune(int lune) {
		// int old = this.lune;
		this.lune = Math.min(luneMax, Math.max(0, this.lune + lune));
		// if (old != this.lune) System.out.println(this + ": Lune " + old + " -> " +
		// this.lune);
	}

	public void addVictoire(int victoire) {
		// int old = this.victoire;
		this.victoire = Math.max(this.victoire + victoire, 0);
		// if (old != this.victoire) System.out.println(this + ": Victoire " + old + "
		// -> " + this.victoire);
	}

	/**
	 * Cela nous permet de lancer et d'appliquer la face en question
	 */
	public void appliquerDe() {
		de1.lancer();
		de2.lancer();
		
		Face face1 = de1.getLastFace().getProxiedFace(this);
		Face face2 = de2.getLastFace().getProxiedFace(this);
		
		//Appliquer le x3 n'a aucun effet donc pas la peine de regarder lequel est le bon
		if (face1 == Faces.X3 || face2 == Faces.X3) {
			face1.appliquerX3(this);
			face2.appliquerX3(this);
		}
		else {
			face1.appliquer(this);
			face2.appliquer(this);
		}
		
		if (partie != null)
			partie.printRoll(this, de1.getLastFace(), de2.getLastFace(), face1, face2);
	}
	
	public void appliquerDeNegatif() {
		de1.lancer();
		de2.lancer();
		
		Face face1 = de1.getLastFace().getProxiedFace(this);
		Face face2 = de2.getLastFace().getProxiedFace(this);
		
		//Appliquer le x3 n'a aucun effet donc pas la peine de regarder lequel est le bon
		if (face1 == Faces.X3 || face2 == Faces.X3) {
			face1.appliquerNegatifX3(this);
			face2.appliquerNegatifX3(this);
		}
		else {
			face1.appliquerNegatif(this);
			face2.appliquerNegatif(this);
		}
		
		if (partie != null)
			partie.printRoll(this, de1.getLastFace(), de2.getLastFace(), face1, face2);
	}

	// Effectue un lancé du dé choisi par le joueur dans choixFaveurMineure n fois
	public void faveurMineure(int n) {
		De deALancer = choixFaveurMineure();
		for (int i = 0; i < n; i++) {
			deALancer.appliquerDe(this);
			if (partie != null)
				partie.printRoll(this, deALancer.getLastFace());
		}
	}

	public String getStatus() {
		String resources = this + " a " + getOr() + "/" + orMax + YELLOW_B + " or, " + RESET + getLune() + "/" + luneMax
				+ BLUE_B +" éclats de lune, " + RESET + getSoleil() + "/" + soleilMax + RED_B + " éclats de soleil et " + RESET + getVictoire()
				+ GREEN_B  + " points de victoire"+ RESET;
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

	public int getOrMax() {
		return orMax;
	}

	public void setOrMax(int orMax) {
		this.orMax = orMax;
	}

	public int getSoleilMax() {
		return soleilMax;
	}

	public void setSoleilMax(int soleilMax) {
		this.soleilMax = soleilMax;
	}

	public int getLuneMax() {
		return luneMax;
	}

	public void setLuneMax(int luneMax) {
		this.luneMax = luneMax;
	}

	/**
	 * Supprime le marteau donné en paramètre de la liste des marteaux du joueur.
	 * Cette méthode est appellée quand un marteau est rempli entièrement.
	 * 
	 * @param marteau : Le marteau rempli à supprimer de marteaux.
	 */
	public void delMarteau(UnMarteau marteau) {
		this.marteaux.remove(marteau);
	}

	/**
	 * @return Le prochain marteau à remplir. Il est à noter que les marteaux sont
	 *         gérés avec la méthode FIFO : le premier marteau ajouté et le premier
	 *         à être incrémenté.
	 */
	public UnMarteau getNextMarteau() {
		if (this.marteaux.isEmpty())
			return null;
		return this.marteaux.get(0);
	}

	/**
	 * Le marteau est ajouté à la fin de la liste et verra son jeton incrémenté
	 * quand tous les autres marteaux ajoutés avant lui seront finis.
	 * 
	 * @param marteau : Le marteau qu'on veut ajouter
	 */
	public void addMarteau(UnMarteau marteau) {
		this.marteaux.add(marteau);

	}

}