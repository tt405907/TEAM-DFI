package cartes;

import de.Faces;
import joueur.Joueur;

public class CasqueInvisibilite extends Carte {

	public CasqueInvisibilite() {
		super("Le Casque d'Invisibilité", 5, 0, 4);
	}

	@Override
	public void effetExploit(Joueur j) {
		j.forge(Faces.X3);
	}
}
