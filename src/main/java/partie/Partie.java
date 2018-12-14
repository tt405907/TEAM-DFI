package partie;

import java.util.ArrayList;
import java.util.List;

import cartes.Carte;
import cartes.CarteRenfort;
import de.Face;
import joueur.Joueur;
import plateau.Plateau;
import sanctuaire.Sanctuaire;

public class Partie {
	private Joueur[] joueurs;
	private int nbTour;

	private Sanctuaire sanctuaire;

	private Plateau plateau;

	private boolean printing = false;

	public void setPrinting(boolean printing) {
		this.printing = printing;
	}

	public Partie(Joueur... joueurs) {
		this.joueurs = joueurs;
		if (joueurs.length == 3) {
			nbTour = 10;
		} else {
			nbTour = 9;
		}

		sanctuaire = new Sanctuaire(joueurs.length);
		plateau = new Plateau(joueurs.length);

		plateau.setPartie(this);
		for (Joueur j : joueurs) {
			j.setPartie(this);
		}
	}

	/**
	 * effectue les achats d'un joueur que ce soit achat de carte ou de face, selon
	 * ce qu'il veut faire
	 */
	public void faireAchats(Joueur j) {
		if (j.tourSanctuaire()) {
			j.faireAchatsFace(sanctuaire.getAchatsPossible(j));
		} else {
			plateau.acheter(j.faireAchatCartes(plateau.getCartesAchetables(j)), j);
		}
	}

	/**
	 * effectue un tour pour chaque joueur
	 */
	public void faireTour() {
		for (Joueur act : joueurs) {
			if (printing) {
				System.out.println("-------------------------");
				System.out.println("Tour de " + act);
				System.out.println("-------------------------");
				System.out.println("Faveur des dieux");
			}
			for (Joueur j : joueurs) {
				j.appliquerDe();
				if (joueurs.length == 2) {
					j.appliquerDe();
				}
			}

			act.utiliserRenforts();

			if (printing) {
				System.out.println("Action");
			}

			faireAchats(act);

			if (act.peutFaireTourSupplementaire() && act.faireTourSupplementaire()) {
				if (printing) {
					System.out.println("Tour supplémentaire");
				}
				faireAchats(act);
			}

			if (printing) {
				System.out.println("Fin du tour");
				for (Joueur j : joueurs) {
					System.out.println(j.getStatus());
				}
			}
		}
	}

	public Joueur[] getEnnemis(Joueur joueur) {
		Joueur[] ennemis = new Joueur[joueurs.length - 1];
		int index = 0;
		for (Joueur j : joueurs) {
			if (j != joueur) {
				ennemis[index] = j;
				index++;
			}
		}

		return ennemis;
	}

	/**
	 * commence la partie et renvoie les gagnants
	 */
	public List<Joueur> game() {
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i].addOr(3 - i);
		}
		if (printing) {
			for (Joueur j : joueurs) {
				System.out.println(j.getStatus());
			}
		}
		while (nbTour > 0) {
			if (printing) {
				int max = joueurs.length == 3 ? 10 : 9;
				System.out.println("=========================");
				System.out.println("Tour " + (max - nbTour + 1) + "/" + max);
				System.out.println("=========================");
			}

			faireTour();
			nbTour--;
		}
		List<Joueur> gagnant = new ArrayList<>();
		int max = 0;
		for (Joueur j : joueurs) {
			if (j.getVictoire() > max) {
				gagnant.clear();
				max = j.getVictoire();
				gagnant.add(j);
			} else if (j.getVictoire() == max) {
				gagnant.add(j);
			}
		}
		return gagnant;
	}

	// Fonctions d'affichage

	/**
	 * On affiche le fait qu'un joueur achète une carte ainsi que le coût de
	 * celle-ci et ce qu'elle lui rapporte.
	 * 
	 * @param acheteur Le joueur qui achète une carte.
	 * @param carte    La carte achetée.
	 */
	public void printExploit(Joueur acheteur, Carte carte) {
		if (!printing)
			return;

		String cost = "(gratuitement)";
		if (carte.getPrixLune() > 0 && carte.getPrixSoleil() > 0) {
			cost = "(pour " + carte.getPrixLune() + " lune et " + carte.getPrixSoleil() + " soleil)";
		} else if (carte.getPrixLune() > 0) {
			cost = "(pour " + carte.getPrixLune() + " lune)";
		} else if (carte.getPrixSoleil() > 0) {
			cost = "(pour " + carte.getPrixSoleil() + " soleil)";
		}

		System.out.println("Exploit: " + carte + " +" + carte.getVictoire() + " victoire " + cost);
	}

	/**
	 * On affiche le fait qu'un joueur soit chassé par un autre joueur qui a pris un
	 * exploit sur son portail actuel.
	 * 
	 * @param chasse le joueur concerné
	 */
	public void printChasse(Joueur chasse) {
		if (!printing)
			return;
		System.out.println(chasse + " reçoit une faveur des dieux pour avoir bougé");
	}

	/**
	 * Cette fonction décrit le lancer de dés d'un joueur. Si un miroir a copié une
	 * face, affiche quelle face a été copiée, sinon remplacement1 est égale à face1
	 * et remplacement2 à face2.
	 * 
	 * @param joueur        Le joueur lançant les dés.
	 * @param face1         La première face lancée.
	 * @param face2         La seconde face lancée.
	 * @param remplacement1 La face copiée par le miroir de la face1.
	 * @param remplacement2 La face copiée par le miroir de la face2.
	 */
	public void printRoll(Joueur joueur, Face face1, Face face2, Face remplacement1, Face remplacement2) {
		if (!printing)
			return;
		String strFace1 = face1 == remplacement1 ? face1.toString() : face1 + " (" + remplacement1 + ")";
		String strFace2 = face2 == remplacement2 ? face2.toString() : face2 + " (" + remplacement2 + ")";
		System.out.println(joueur + ": " + strFace1 + " et " + strFace2);
	}

	/**
	 * On affiche le lancer de dé unique d'un joueur, lors d'une faveur mineure par
	 * exemple.
	 * 
	 * @param joueur Le joueur qui lance le dé
	 * @param face   La face obtenue
	 */
	public void printRoll(Joueur joueur, Face face) {
		if (!printing)
			return;
		System.out.println(joueur + ": " + face);
	}

	/**
	 * On affiche le fait qu'une nouvelle face est forgée
	 * 
	 * @param old  La face remplacée
	 * @param newz La face forgée
	 */
	public void printForge(Face old, Face newz) {
		if (!printing)
			return;
		System.out.println("Forge: " + old + " -> " + newz + " (pour " + newz.getPrix() + " or)");
	}

	public void printRenfort(CarteRenfort carte) {
		if (!printing)
			return;
		System.out.println("Renfort: " + carte);
	}

	public Sanctuaire getSanctuaire() {
		return sanctuaire;
	}

	public Plateau getPlateau() {
		return plateau;
	}
}
