package joueur;

import de.Face;

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

}
