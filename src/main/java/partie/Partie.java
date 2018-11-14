package partie;

import java.util.ArrayList;
import java.util.List;

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
		for (Joueur j : joueurs) {
			j.setPrinting(printing);
		}
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

	/**
	 * commence la partie et renvoie les gagnants
	 */
	public List<Joueur> game() {
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i].addOr(4 - i);
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

}
