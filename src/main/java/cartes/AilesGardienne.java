package cartes;

import de.Face;
import de.FaceSelection;
import de.Faces;
import joueur.Joueur;

/**
 * Les Ailes de la Gardienne est une carte renfort qui donne un or ou une lune
 * ou un soleil une fois par tour.
 */
public class AilesGardienne extends CarteRenfort {
	private Face effet = new FaceSelection(0, Faces.OR_1, Faces.LUNE_1, Faces.SOLEIL_1);

	public AilesGardienne() {
		super("Les Ailes de La Gardienne", 0, 2, 4);
	}

	@Override
	public void effetRenfort(Joueur j) {
		effet.appliquer(j);
	}

}
