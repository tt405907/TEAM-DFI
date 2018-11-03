import java.util.List;

import joueur.BotRandom;
import joueur.Joueur;
import partie.Partie;

public class Main {

	public static void main(String[] args) {
		//Partie simple à 4 joueurs
		Joueur[] joueurs = new Joueur[4];
		for (int i = 0 ; i < joueurs.length ; i++) {
			joueurs[i] = new BotRandom("Joueur " + (i+1));
		}
		Partie p = new Partie(joueurs);
		
		List<Joueur> gagnants = p.game();
		
		System.out.println("=========================");
		System.out.println("Fin de partie");
		System.out.println("=========================");
		
		for (Joueur j : gagnants) {
			System.out.println(j + " a gagné!");
		}
	}

}
