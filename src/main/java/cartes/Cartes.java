package cartes;

// contient les instances de classes
public class Cartes {
	private Cartes() {}
	
	public static final Carte MARTEAU = new MarteauDuForgeron();
	public static final Carte COFFRE = new CoffreDuForgeron();
	public static final CarteRenfort SABOTS = new SabotsArgent();
	public static final Carte SATYRES = new Carte("Les Satyres", 3, 0, 6);
	public static final Carte PASSEUR = new Carte("Le Passeur", 4, 0, 12);
	public static final Carte CASQUE = new Carte("Le Casque d'Invisibilité", 5, 0, 4);
	public static final Carte PINCE = new Pince();
	public static final Carte HYDRE = new Carte("L'Hydre", 5, 5, 26);
	public static final Carte ENIGME = new Enigme();
	public static final Carte MEDUSE = new Carte("La Méduse", 0, 4, 14);
	public static final Carte MIROIR = new Carte("Le Miroir Abyssal", 0, 5, 10);
	public static final CarteRenfort AILES = new AilesGardienne();
	public static final Carte MINOTAURE = new Carte("Le Minotaure", 0, 3, 8);
	public static final CarteRenfort ANCIEN = new Ancien();
	public static final Carte HERBES = new HerbesFolles();
}
