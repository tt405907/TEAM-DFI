import java.util.List;

import joueur.*;
import partie.Partie;

public class Detail {
	public static final String RESET = "\u001B[0;0m";
	public static final String B = "\u001B[0;30m";
	public static final String RED= "\u001B[1;41m";
	public static final String PURPLE= "\u001B[;45m";
	public static final String BLUE= "\u001B[1;46m";
	public static final String GREEN = "\u001B[1;42m";

	public static void main(String[] args) {
		int nbJoueurs = Integer.parseInt(args[0]);
		if (nbJoueurs < 2 || nbJoueurs > 4) throw new IllegalArgumentException(nbJoueurs + " n'est pas un nombre de joueur valide.");
		
		//Partie simple à 4 joueurs
		Joueur[] joueurs = new Joueur[nbJoueurs];
		
		//2 bots aléatoires et 2 bots victoire
		joueurs[0] = new Joueur(RED+ B + "Soleil 1" + RESET).setBot(new BotSoleil());
		joueurs[1] = new Joueur(PURPLE+ B + "Ultime 2"+ RESET ).setBot(new BotUltime());
		if (nbJoueurs >= 3) joueurs[2] = new Joueur(BLUE+ B + "Victoire 3"+ RESET).setBot(new BotVictoire());
		if (nbJoueurs >= 4) joueurs[3] = new Joueur(GREEN + B + "Random 4" + RESET).setBot(new BotRandom());
		
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
