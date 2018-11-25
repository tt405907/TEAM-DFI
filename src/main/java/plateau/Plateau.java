package plateau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import cartes.Carte;
import joueur.Joueur;
import partie.Partie;

public class Plateau {

	private List<Carte> cartes;
	
	private Partie partie;
	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	// nos cartes de bases, chacune en un exemplaire et sur un portail prédéfini par
	// son emplacement dans le tableau
	private static final Carte[][] ILES = {
			{ new Carte("Le Marteau du Forgeron", 1, 0, 0), new Carte("Le Coffre du Forgeron", 1, 0, 2) },
			{ new Carte("Les Sabots d'Argent", 2, 0, 2), new Carte("Les Satyres", 3, 0, 6) },
			{ new Carte("Le Passeur", 4, 0, 12), new Carte("Le Casque d'Invisibilité", 5, 0, 4) },
			{ new Carte("La Pince", 6, 0, 8), new Carte("L'Hydre", 5, 5, 26), new Carte("L'Enigme", 0, 6, 10) },
			{ new Carte("La Méduse", 0, 4, 14), new Carte("Le Miroir Abyssal", 0, 5, 10) },
			{ new Carte("Les Ailes de La Gardienne", 0, 2, 4), new Carte("Le Minotaure", 0, 3, 8) },
			{ new Carte("L'Ancien", 0, 1, 0), new Carte("Les Herbes Folles", 0, 1, 2) } };

	// portails représente l'emplacement de chaque joueur
	// sur les différents portails
	private Joueur portails[] = new Joueur[ILES.length];

	// ce constructeur sera uniquement utile pour les tests a priori
	public Plateau(Carte... carte) {
		this.cartes = new ArrayList<>();
		this.cartes.addAll(Arrays.asList(carte));
	}

	// constructeur de plateau qui prend le nombre de joueurs et qui
	// met le bon nombre d'exemplaire de cartes en conséquence
	public Plateau(int nbJoueurs) {
		cartes = new ArrayList<>();
		for (Carte[] i : ILES) {
			for (Carte c : i) {
				ArrayList<Carte> exemplaires = new ArrayList<Carte>(Collections.nCopies(nbJoueurs, c));
				cartes.addAll(exemplaires);
			}
		}
	}

	// fonction utilitaire pour les tests
	public void remove(Carte carte) {
		cartes.remove(carte);
	}

	// Donne les cartes distinctes que le joueur j peut acheter
	public List<Carte> getCartesAchetables(Joueur j) {
		List<Carte> disponibles = cartes.stream()
				.filter(c -> c.peutAcheter(j)).distinct()
				.collect(Collectors.toCollection(ArrayList::new));
		return disponibles;
	}

	// Donne l'indice du portail d'une carte
	// si elle se trouve dans les cartes restantes, renvoie -1 sinon
	public int getPortail(Carte carte) {

		for (int i = 0; i < ILES.length; i++) {
			for (int j = 0; j < ILES[i].length; j++) {
				if (ILES[i][j] == carte)
					return i;
			}
		}
		return -1;
	}

	public void acheter(Carte carte, Joueur acheteur) {
		int p = getPortail(carte);
		if (carte != null && this.cartes.remove(carte)) {
			// Dépense les ressources et donne les points de victoire au joueur
			// si l'achat est effectif et la carte bien encore présente
			carte.acheter(acheteur);
			
			if (partie != null) partie.printExploit(acheteur, carte);

			// Retire l'acheteur de son éventuel portail actuel
			for (int j = 0; j < portails.length; j++) {
				if (portails[j] == acheteur)
					portails[j] = null;
			}

			// Lance les dés de l'éventuel joueur chassé
			if (p >= 0 && portails[p] != null) {
				if (partie != null) partie.printChasse(portails[p]);
				portails[p].appliquerDe();
			}

			// Enfin, place l'acheteur sur le portail de la carte qu'il achète
			if (p >= 0)
				portails[p] = acheteur;

		}
	}
}
