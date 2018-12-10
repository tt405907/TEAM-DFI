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
		joueurs[0] = new Joueur("Random 1").setBot(new BotRandom());
		joueurs[1] = new Joueur("Victoire 2").setBot(new BotVictoire());
		joueurs[2] = new Joueur("Random 3").setBot(new BotRandom());
		joueurs[3] = new Joueur("Victoire 4").setBot(new BotVictoire());
		
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
