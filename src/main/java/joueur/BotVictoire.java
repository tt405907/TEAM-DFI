package joueur;

import java.util.Comparator;

import de.De;
import de.Face;
import de.Faces;
import sanctuaire.ListeAchat;

/**
 * Un bot très simple qui cherche à maximiser les points de victoire sur ses dés.
 */
public class BotVictoire extends Joueur {

	public BotVictoire(String nom) {
		super(nom);
	}
	
	/**
	 * Donne combien de points de victoire le bot s'attends à recevoir d'une face.
	 */
	private int valeurVictoire(Face face) {
		//Avantage d'utiliser des singletons
		if (face == Faces.VICTOIRE_1 || face == Faces.SOLEIL_1_VICTOIRE_1 || face == Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) return 1;
		if (face == Faces.VICTOIRE_2 || face == Faces.OR_3_OU_VICTOIRE_2 || face == Faces.LUNE_2_VICTOIRE_2) return 2;
		if (face == Faces.VICTOIRE_3) return 3;
		if (face == Faces.VICTOIRE_4) return 4;
		
		return 0;
	}
	
	/**
	 * Donne combien de points de victoire le bot s'attends à recevoir d'un dé.
	 */
	private int valeurVictoire(De de) {
		int total = 0;
		for (int i = 0; i < 6; i++)
		{
			total += valeurVictoire(de.getFace(i));
		}
		
		return total;
	}
	
	/**
	 * Donne l'indice de la face qui rapporte le moins de points de victoire selon le bot.
	 */
	private int getMinFace(De de) {
		int choix = 0;
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < 6; i++)
		{
			if (valeurVictoire(de.getFace(i)) < min)
			{
				choix = i;
				min = valeurVictoire(de.getFace(i));
			}
		}
		
		return choix;
	}

	@Override
	public int choixFace(Face... faces) {
		//Choisit la face qui rapporte le plus de points de victoire
		int choix = 0;
		int max = 0;
		
		for (int i = 0; i < faces.length; i++)
		{
			if (valeurVictoire(faces[i]) > max)
			{
				choix = i;
				max = valeurVictoire(faces[i]);
			}
		}
		
		return choix;
	}

	@Override
	public void forge(Face face) {
		//Prends le dé rapportant le moins de point de victoire
		De cible = valeurVictoire(de1) < valeurVictoire(de2) ? de1 : de2;
		//Trouve la face rapportant le moins
		int index = getMinFace(cible);
		cible.forge(face, index);
	}

	@Override
	public void faireAchats(ListeAchat liste) {
		while (!liste.isEmpty())
		{
			//Prends le dé rapportant le moins de point de victoire
			De cible = valeurVictoire(de1) < valeurVictoire(de2) ? de1 : de2;
			//Trouve la valeur de la face rapportant le moins, qui sera remplacée si possible
			int value = valeurVictoire(cible.getFace(getMinFace(cible)));
			
			//Prends les faces qu'il reste valant plus que la face à remplacer (donc augmentant les points de victoire)
			//Puis prends celle qui rapporterait le plus
			Face face = liste.getAvailable().stream()
					.filter(f -> valeurVictoire(f) > value)
					.max(Comparator.comparingInt(this::valeurVictoire))
					.orElse(null);
			
			//Si aucune face n'est trouvée, les achats s'arrêtent ici
			if (face == null) break;
			
			liste.acheter(face);
		}
	}

}
