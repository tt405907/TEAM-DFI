package joueur;

import de.Face;
import sanctuaire.ListeAchat;

/**
 * Implémentation de Joueur avec des méthodes par défaut pour les tests qui n'ont pas besoin de ces méthodes.
 */
public class BotDefault extends Joueur {
	public BotDefault(String nom) {
		super(nom);
	}

	@Override
	public int choixFace(Face[] faces) {
		return 0;
	}

	@Override
	public void forge(Face face) {
		de1.forge(face, 0);
	}

	@Override
	public void faireAchats(ListeAchat liste) {
		
	}

}
