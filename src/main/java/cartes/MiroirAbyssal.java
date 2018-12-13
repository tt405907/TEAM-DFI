package cartes;

import de.Faces;
import joueur.Joueur;

/**
 * Le miroir abyssal donne une face miroir au joueur à forger.
 */
public class MiroirAbyssal extends Carte {

	public MiroirAbyssal() {
		super("Le Miroir Abyssal", 0, 5, 10);
	}

	@Override
	public void effetExploit(Joueur j) {
		j.forge(Faces.MIROIR);
	}
}
