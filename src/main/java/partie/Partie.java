package partie;

import java.util.ArrayList;
import java.util.List;

import cartes.Carte;
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
	
	//Fonctions d'affichage
	//Un joueur achète une carte
	public void printExploit(Joueur acheteur, Carte carte) {
		if (!printing) return;

		String cost = "(gratuitement)";
		if (carte.getPrixLune() > 0 && carte.getPrixSoleil() > 0)
		{
			cost = "(pour " + carte.getPrixLune() + " lune et " + carte.getPrixSoleil() + " soleil)";
		}
		else if (carte.getPrixLune() > 0)
		{
			cost = "(pour " + carte.getPrixLune() + " lune)";
		}
		else if (carte.getPrixSoleil() > 0)
		{
			cost = "(pour " + carte.getPrixSoleil() + " soleil)";
		}
		
		System.out.println("Exploit: " + carte + " +" + carte.getVictoire() + " victoire " + cost);
	}
	
	//Un joueur est chassé de son île
	public void printChasse(Joueur chasse) {
		if (!printing) return;
		System.out.println(chasse + " reçoit une faveur des dieux pour avoir bougé");
	}
	
	//Un joueur a lancé ces dés
	public void printRoll(Joueur joueur, Face face1, Face face2) {
		if (!printing) return;
		System.out.println(joueur + ": " + face1 + " et " + face2);
	}
	
	//Un dé se fait forger une face
	public void printForge(Face old, Face newz) {
		if (!printing) return;
		System.out.println("Forge: " + old + " -> " + newz + " (pour " + newz.getPrix() + " or)");
	}

}
