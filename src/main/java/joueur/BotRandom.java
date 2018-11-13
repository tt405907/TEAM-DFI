package joueur;

import java.util.List;
import java.util.Random;

import cartes.Carte;
import de.Face;
import sanctuaire.ListeAchat;

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
	public int choixFace(Face...faces) {
		return rand.nextInt(faces.length);
	}

	@Override
	public void forge(Face face) {
		if (rand.nextBoolean())
		{
			de1.forge(face, rand.nextInt(6));
		}
		else
		{
			de2.forge(face, rand.nextInt(6));
		}
	}

	@Override
	public void faireAchats(ListeAchat liste) {
		while (!liste.isEmpty())
		{
			//Prends une face restante au hasard et l'achète
			List<Face> restantes = liste.getAvailable();
			Face cible = restantes.get(rand.nextInt(restantes.size()));
			liste.acheter(cible);
		}
	}

	@Override
	public boolean tourSanctuaire() {
		return rand.nextBoolean();
	}

	@Override
	public boolean faireTourSupplementaire() {
		return rand.nextBoolean();
	}

	@Override
	public Carte faireAchatCartes(List<Carte> cartes) {
		return cartes.get(rand.nextInt(cartes.size()));
	}

}
