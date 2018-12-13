package cartes;

import java.util.ArrayList;
import java.util.List;

import de.Face;
import de.Faces;
import joueur.Joueur;

/**
 * Les Satyres quand elles sont achetées font lancer les dés à chacun des
 * adversaires puis le joueur actuel choisit 2 de ces faces à appliquer.
 */
public class Satyres extends Carte {

	public Satyres() {
		super("Les Satyres", 3, 0, 6);
	}

	@Override
	public void effetExploit(Joueur j) {
		Joueur[] ennemis = j.getPartie().getEnnemis(j);
		List<Face> faces = new ArrayList<>();

		for (Joueur e : ennemis) {
			e.getDe1().lancer();
			e.getDe2().lancer();
			faces.add(e.getDe1().getLastFace());
			faces.add(e.getDe2().getLastFace());
		}

		Face[] tableau = faces.toArray(new Face[0]);
		Face face1 = tableau[j.choixFace(tableau)];

		faces.remove(face1);
		tableau = faces.toArray(new Face[0]);
		Face face2 = tableau[j.choixFace(tableau)];

		Face realFace1 = face1.getProxiedFace(j);
		Face realFace2 = face2.getProxiedFace(j);

		// Appliquer le x3 n'a aucun effet donc pas la peine de regarder lequel est le
		// bon
		if (realFace1 == Faces.X3 || realFace2 == Faces.X3) {
			realFace1.appliquerX3(j);
			realFace2.appliquerX3(j);
		} else {
			realFace1.appliquer(j);
			realFace2.appliquer(j);
		}

		if (j.getPartie() != null)
			j.getPartie().printRoll(j, face1, face2, realFace1, realFace2);
	}
}
