package joueur;

import java.util.List;

import cartes.Carte;
import de.De;
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
	public int choixFace(Face... faces) {
		return 0;
	}

	@Override
	public void forge(Face face) {
		de1.forge(face, 0);
	}

	@Override
	public void faireAchatsFace(ListeAchat liste) {
		
	}

	@Override
	public boolean tourSanctuaire() {
		return false;
	}

	@Override
	public boolean faireTourSupplementaire() {
		return false;
	}

	@Override
	public Carte faireAchatCartes(List<Carte> cartes) {
		return null;
	}


	@Override
	public De choixFaveurMineure() {
		return de1;
	}

}
