import java.util.List;

import joueur.BotRandom;
import joueur.BotVictoire;
import joueur.Joueur;
import partie.Partie;

public class Main {

	public static void main(String[] args) {
		//Partie simple à 4 joueurs
		Joueur[] joueurs = new Joueur[4];
		
		//2 bots aléatoires et 2 bots victoire
		joueurs[0] = new BotRandom("Random 1");
		joueurs[1] = new BotVictoire("Victoire 2");
		joueurs[2] = new BotRandom("Random 3");
		joueurs[3] = new BotVictoire("Victoire 4");
		
		Partie p = new Partie(joueurs);
		p.setPrinting(true);
		
		List<Joueur> gagnants = p.game();
		
		System.out.println("=========================");
		System.out.println("Fin de partie");
		System.out.println("=========================");
		
		for (Joueur j : gagnants) {
			System.out.println(j + " a gagné!");
		}
	}

}
