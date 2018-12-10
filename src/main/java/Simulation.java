import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joueur.Bot;
import joueur.BotRandom;
import joueur.BotVictoire;
import joueur.Joueur;
import partie.Partie;

public class Simulation {
	private static Map<Bot, Statistics> scores = new HashMap<>();

	public static void main(String[] args) {
		List<Bot> bots = new ArrayList<>();
		bots.add(new BotVictoire());
		bots.add(new BotRandom());
		
		int nbParties = 500;
		
		for (Bot b : bots) scores.put(b, new Statistics());
		
		long time = -System.currentTimeMillis();
		for (int i = 0; i < nbParties; i++) {
			Collections.shuffle(bots);
			fairePartie(bots);
		}
		time += System.currentTimeMillis();
		
		System.out.println(nbParties + " parties en " + (time/1000.0) + " secondes");
		Collections.sort(bots, Comparator.comparingInt(b -> -scores.get(b).victoires));
		
		for (Bot b : bots) {
			Statistics stats = scores.get(b);
			double winrate = (100*stats.victoires)/(double)nbParties;
			double score = stats.points/(double)nbParties;
			System.out.println(b.getClass().getSimpleName() + " : " + winrate + "% de victoire (" + stats.victoires + " victoires), moyenne de " + score + " points de victoire");
		}
	}
	
	private static void fairePartie(List<Bot> bots) {
		Joueur[] joueurs = new Joueur[bots.size()];
		
		for (int i = 0; i < joueurs.length; i++) {
			Bot bot = bots.get(i);
			joueurs[i] = new Joueur(bot.getClass().getSimpleName() + " " + (i+1)).setBot(bot);
		}
		
		Partie partie = new Partie(joueurs);
		List<Joueur> gagnants = partie.game();
		
		for (Bot b : bots) {
			Statistics stats = scores.get(b);
			stats.points += b.getJoueur().getVictoire();
			if (gagnants.contains(b.getJoueur())) stats.victoires++;
		}
	}
	
	private static class Statistics {
		private int victoires, points;
	}

}
