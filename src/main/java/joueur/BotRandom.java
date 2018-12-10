package joueur;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import cartes.Carte;
import cartes.CarteRenfort;
import de.De;
import de.Face;
import sanctuaire.ListeAchat;

/**
 * Un bot qui prends ses décisions au hasard.
 */
public class BotRandom extends Bot {
	private Random rand;
	
	@Override
	public void reset() {
		rand = new Random();
	}

	@Override
	public int choixFace(Face...faces) {
		return rand.nextInt(faces.length);
	}
	
	public int choixFaceNegatif(Face...faces) {
		return rand.nextInt(faces.length);
	}

	@Override
	public void forge(Face face) {
		if (rand.nextBoolean())
		{
			getJoueur().getDe1().forge(face, rand.nextInt(6));
		}
		else
		{
			getJoueur().getDe2().forge(face, rand.nextInt(6));
		}
	}

	@Override
	public void faireAchatsFace(ListeAchat liste) {
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
		if (cartes.isEmpty()) return null;
		return cartes.get(rand.nextInt(cartes.size()));
	}

	@Override
	public De choixFaveurMineure() {
		return rand.nextBoolean() ? getJoueur().getDe1() : getJoueur().getDe2();
	}

	@Override
	public CarteRenfort choixRenfort(List<CarteRenfort> liste) {
		// ne regarde que les cartes utilisables
		List<CarteRenfort> utilisables = liste.stream()
				.filter(c -> c.peutActiver(getJoueur()))
				.collect(Collectors.toList());
		
		if (utilisables.isEmpty()) return null;
		return utilisables.get(rand.nextInt(utilisables.size()));
	}

	// à chaque fois qu'il peut placer n Or ,
	// ce bot en place un nombre aléatoire entre 0 et n inclus.
	@Override
	public int changeOrEnMarteau(int orChangeable) {
		return rand.nextInt(orChangeable+1);
	}

}
