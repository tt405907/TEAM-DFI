package joueur;

import java.util.List;

import cartes.Carte;
import cartes.CarteRenfort;
import de.De;
import de.Face;
import sanctuaire.ListeAchat;

public abstract class Bot {
	private Joueur joueur;
	
	/**
	 * Demande au bot de remettre à zéro (ou d'initialiser) son état interne (si il en a un).
	 */
	public void reset() {}
	
	/**
	 * Change le joueur du bot et l'initialise. Cette fonction sera toujours appelée avant que l'on demande quoi que ce soit au bot.
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
		reset();
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Propose plusieurs faces au bot pour qu'il donne l'indice de la face qu'il
	 * souhaite appliquer dans le tableau.
	 * 
	 * @param faces les faces que le bot peut appliquer
	 * @return l'indice de la face à appliquer dans le tableau
	 */
	public abstract int choixFace(Face... faces);
	
	/**
	 * Propose plusieurs faces au bot pour qu'il donne l'indice de la face qu'il
	 * souhaite appliquer négativement (minotaure) dans le tableau.
	 * 
	 * @param faces les faces que le bot peut appliquer
	 * @return l'indice de la face à appliquer dans le tableau
	 */
	public abstract int choixFaceNegatif(Face... faces);

	/**
	 * Demande au bot de forger la face donnée sur un de ces dés.
	 * 
	 * @param face la face à forger
	 */
	public abstract void forge(Face face);

	/**
	 * Demande au bot de faire ses achats dans la liste d'achat donnée.
	 * 
	 * @param liste ListeAchat générée avec Sanctuaire.getAchatsPossible
	 */
	public abstract void faireAchatsFace(ListeAchat liste);

	/**
	 * Demande au bot si il veut faire son tour au sanctuaire ou faire des exploits
	 * 
	 * @return true si sanctuaire, false si exploit
	 */
	public abstract boolean tourSanctuaire();

	/**
	 * demande si il veut un tour en plus pour 2 soleils, n'est pas appelé si il
	 * peut pas
	 * 
	 * @return true si oui false sinon
	 */
	public abstract boolean faireTourSupplementaire();

	/**
	 * Demande au bot si il veut acheter une carte dans la liste donnée.
	 * 
	 * @param cartes que le bot peut acheter
	 * @return la Carte que le bot veut acheter ou nul si il ne veut rien acheter
	 */
	public abstract Carte faireAchatCartes(List<Carte> cartes);

	/**
	 * choisit le dé que le joueur veut lancer lors d'une faveur mineure
	 */
	public abstract De choixFaveurMineure();

	/**
	 * demande au bot quelle carte renfort il veut activer dans la liste de ceux
	 * qu'il n'a pas encore activee (donnee en parametre) ou null si il veut finir
	 */
	public abstract CarteRenfort choixRenfort(List<CarteRenfort> liste);

	/**
	 * Demande au bot combien d'or veut-il mettre dans son marteau
	 * 
	 * @param or : Or qu'il est possible de placer dans le marteau.
	 * @return Or que le bot veut placer dans le marteau.
	 */
	public abstract int changeOrEnMarteau(int or);

}
