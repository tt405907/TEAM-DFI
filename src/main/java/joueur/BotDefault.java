package joueur;

import java.util.List;

import cartes.Carte;
import cartes.CarteRenfort;
import de.De;
import de.Face;
import sanctuaire.ListeAchat;

/**
 * Implémentation de Bot avec des méthodes par défaut pour les tests qui
 * n'ont pas besoin de ces méthodes.
 */
public class BotDefault extends Bot {
	@Override
	public int choixFace(Face... faces) {
		return 0;
	}
	
	public int choixFaceNegatif(Face... faces) {
		return 0;
	}

	@Override
	public void forge(Face face) {
		getJoueur().getDe1().forge(face, 0);
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
		return getJoueur().getDe1();
	}

	@Override
	public CarteRenfort choixRenfort(List<CarteRenfort> liste) {
		return null;
	}

	@Override
	public int changeOrEnMarteau(int orChangeable) {
		return 0;
	}

}
