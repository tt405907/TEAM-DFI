package joueur;

import java.util.Random;

import de.Face;

/**
 * Un bot qui prends ses décisions au hasard.
 */
public class BotRandom extends Joueur {
	private Random rand;

	public BotRandom(String nom) {
		super(nom);
		rand = new Random();
	}

	@Override
	public int choixFace(Face[] faces) {
		return rand.nextInt(faces.length);
	}

}
