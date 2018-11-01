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
		for (Joueur j : gagnants) {
			System.out.println(j + " a gagné!");
		}
		for (Joueur j : joueurs) {
			System.out.println(j + " a " + j.getOr() + " or, " + j.getLune() + " éclats de lune, " + j.getSoleil() + " éclats de soleil et " + j.getVictoire() + " points de victoire");
		}
	}

}
