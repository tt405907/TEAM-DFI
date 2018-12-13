package cartes;

import de.Faces;
import joueur.Joueur;

/**
 * Le casque d'invisibilité donne une face x3 au joueur et le fait la forger sur
 * un de ses dés
 */

public class CasqueInvisibilite extends Carte {

	public CasqueInvisibilite() {
		super("Le Casque d'Invisibilité", 5, 0, 4);
	}

	@Override
	public void effetExploit(Joueur j) {
		j.forge(Faces.X3);
	}
}
