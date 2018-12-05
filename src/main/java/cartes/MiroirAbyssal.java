package cartes;

import de.Faces;
import joueur.Joueur;

public class MiroirAbyssal extends Carte {

	public MiroirAbyssal() {
		super("Le Miroir Abyssal", 0, 5, 10);
	}

	@Override
	public void effetExploit(Joueur j) {
		j.forge(Faces.MIROIR);
	}
}
