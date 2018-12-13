package joueur;

public class BotSoleil extends BotRessource {
	public BotSoleil() {
		super(BotUtils.VALEUR_SOLEIL);
	}

	@Override
	public boolean tourSanctuaire() {
		// Les cartes qui valent beaucoup de point de victoire coutent plus de 3
		if (getJoueur().getLune() >= 3 || getJoueur().getSoleil() >= 3) return false;
		// Les faces qui rapportent du soleil coûtent 8 ou plus
		else if (getJoueur().getOr() >= 8) return true;
		// Sinon prends une carte à bas prix
		else return false;
	}

	@Override
	public boolean faireTourSupplementaire() {
		// Oui si il peut prendre une carte ou une face de valeur
		return (getJoueur().getLune() >= 3 || getJoueur().getSoleil() >= 5 || getJoueur().getOr() >= 8);
	}
}
