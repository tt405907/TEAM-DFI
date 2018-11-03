package partie;

import java.util.ArrayList;
import java.util.List;

import joueur.Joueur;
import sanctuaire.Sanctuaire;

public class Partie {
	private Joueur[] joueurs;
	private int nbTour;
	private Sanctuaire sanctuaire;

	public Partie(Joueur... joueurs) {
		this.joueurs = joueurs;
		if (joueurs.length == 3) {
			nbTour = 10;
		}
		else {
			nbTour = 9;
		}
		
		sanctuaire = new Sanctuaire(joueurs.length);
	}
	
	/**effectuer un tour pour chaque joueurs
	 * 
	 */
	public void faireTour() {
		for (Joueur act : joueurs) {
			System.out.println("-------------------------");
			System.out.println("Tour de " + act);
			System.out.println("-------------------------");
			System.out.println("Faveur des dieux");
			for (Joueur j : joueurs) {
				j.appliquerDe();
				if (joueurs.length == 2) {
					j.appliquerDe();
				}
			}

			System.out.println("Action");
			//Pas de cartes pour le moment
			act.faireAchats(sanctuaire.getAchatsPossible(act));
			
			System.out.println("Fin du tour");
			for (Joueur j : joueurs)
			{
				System.out.println(j.getStatus());
			}
		}
	}
	
	/**
	 * commence la partie et renvoie les gagnants
	 */
	public List<Joueur> game() {
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i].addOr(4-i);
		}
		while (nbTour > 0) {
			int max = joueurs.length == 3 ? 10 : 9;
			System.out.println("=========================");
			System.out.println("Tour " + (max-nbTour+1) + "/" + max);
			System.out.println("=========================");
			
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
			}
			else if (j.getVictoire() == max) {
				gagnant.add(j);
			}
		}
		return gagnant;
	}
	
}
