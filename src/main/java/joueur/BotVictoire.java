package joueur;

public class BotVictoire extends BotRessource {
	public BotVictoire() {
		super(BotUtils.VALEUR_VICTOIRE);
	}

	@Override
	public boolean tourSanctuaire() {
		//Les cartes qui valent beaucoup de point de victoire coutent plus de 3
		if(getJoueur().getLune() >= 3 || getJoueur().getSoleil() >= 3) return false;
		//Les faces qui rapportent de la victoire coûtent 5 ou plus
		else if(getJoueur().getOr() >= 5) return true;
		//Sinon prends une carte à bas prix
		else return false;
	}

	@Override
	public boolean faireTourSupplementaire() {
		// Oui si il peut prendre une carte ou une face de valeur
		return (getJoueur().getLune() >= 3 || getJoueur().getSoleil() >= 5 || getJoueur().getOr() >= 5);
	}
}
