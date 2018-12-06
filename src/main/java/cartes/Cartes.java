package cartes;

// contient les instances de classes
public class Cartes {
	private Cartes() {}
	
	public static final Carte MARTEAU = new MarteauDuForgeron();
	public static final Carte COFFRE = new CoffreDuForgeron();
	public static final CarteRenfort SABOTS = new SabotsArgent();
	public static final Carte SATYRES = new Satyres();
	public static final Carte PASSEUR = new Carte("Le Passeur", 4, 0, 12);
	public static final Carte CASQUE = new CasqueInvisibilite();
	public static final Carte PINCE = new Pince();
	public static final Carte HYDRE = new Carte("L'Hydre", 5, 5, 26);
	public static final Carte ENIGME = new Enigme();
	public static final Carte MEDUSE = new Carte("La Méduse", 0, 4, 14);
	public static final Carte MIROIR = new MiroirAbyssal();
	public static final CarteRenfort AILES = new AilesGardienne();
	public static final Carte MINOTAURE = new Minotaure();
	public static final CarteRenfort ANCIEN = new Ancien();
	public static final Carte HERBES = new HerbesFolles();
}
