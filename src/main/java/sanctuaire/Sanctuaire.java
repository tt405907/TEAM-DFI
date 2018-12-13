package sanctuaire;

import static de.Faces.LUNE_1;
import static de.Faces.LUNE_2;
import static de.Faces.LUNE_2_VICTOIRE_2;
import static de.Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1;
import static de.Faces.OR_1_OU_LUNE_1_OU_SOLEIL_1;
import static de.Faces.OR_2_LUNE_1;
import static de.Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2;
import static de.Faces.OR_3;
import static de.Faces.OR_3_OU_VICTOIRE_2;
import static de.Faces.OR_4;
import static de.Faces.OR_6;
import static de.Faces.SOLEIL_1;
import static de.Faces.SOLEIL_1_VICTOIRE_1;
import static de.Faces.SOLEIL_2;
import static de.Faces.VICTOIRE_3;
import static de.Faces.VICTOIRE_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import de.Face;
import joueur.Joueur;

public class Sanctuaire {

	//Faces encore en stock
	private List<Face> faces;
	
	//Les différents bassins pleins
	private static final Face[][] BASSINS = {
			{OR_3, OR_3, OR_3, OR_3},
			{LUNE_1, LUNE_1, LUNE_1, LUNE_1},
			{OR_4, OR_4, OR_4, OR_4},
			{SOLEIL_1, SOLEIL_1, SOLEIL_1, SOLEIL_1},
			{OR_6, OR_2_LUNE_1, SOLEIL_1_VICTOIRE_1, OR_1_OU_LUNE_1_OU_SOLEIL_1},
			{OR_3_OU_VICTOIRE_2, OR_3_OU_VICTOIRE_2, OR_3_OU_VICTOIRE_2, OR_3_OU_VICTOIRE_2},
			{LUNE_2, LUNE_2, LUNE_2, LUNE_2},
			{SOLEIL_2, SOLEIL_2, SOLEIL_2, SOLEIL_2},
			{VICTOIRE_3, VICTOIRE_3, VICTOIRE_3, VICTOIRE_3},
			{VICTOIRE_4, LUNE_2_VICTOIRE_2, OR_2_OU_LUNE_2_OU_SOLEIL_2, OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1}
	};
	
	public Sanctuaire(Face... faces) {
		this.faces = new ArrayList<>();
		this.faces.addAll(Arrays.asList(faces));
	}
	
	public Sanctuaire(int nbJoueurs) {
		faces = new ArrayList<>();
		
		if (nbJoueurs == 2)
		{
			Random rand = new Random();
			for (Face[] b : BASSINS)
			{
				//Retire 2 faces au hasard = n'ajoute que 2 faces car les bassins ont une taille de 4
				int face1 = rand.nextInt(4);
				int face2 = rand.nextInt(3);
				if (face2 >= face1) face2++;
				
				faces.add(b[face1]);
				faces.add(b[face2]);
			}
		}
		else
		{
			for (Face[] b : BASSINS)
			{
				for (Face f : b)
				{
					faces.add(f);
				}
			}
		}
	}
	
	/**
	 * Supprime la face désignée du stock
	 * @param face la face à enlever
	 */
	public void remove(Face face) {
		faces.remove(face);
	}
	
	/**
	 * Donne une ListeAchat faite pour le joueur, lui permettant d'acheter légalement pendant son tour.
	 * @param j le joueur qui va faire ses achats
	 * @return la ListeAchat
	 */
	public ListeAchat getAchatsPossible(Joueur j) {
		//Prends les faces distinctes que le joueur peut acheter
		List<Face> disponibles = faces.stream()
				.filter(f -> f.getPrix() <= j.getOr())
				.distinct()
				.collect(Collectors.toCollection(ArrayList::new));
		
		return new ListeAchat(this, j, disponibles);
	}
	public List<Face> getFaces() {
		return faces;
	}

}
