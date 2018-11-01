package sanctuaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.Face;
import joueur.Joueur;

public class Sanctuaire {
	//Faces encore en stock
	private List<Face> faces;
	
	public Sanctuaire(Face... faces) {
		this.faces = new ArrayList<>();
		this.faces.addAll(Arrays.asList(faces));
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
		List<Face> disponibles = new ArrayList<>(faces);
		//Garde les faces que le joueur 
		disponibles.removeIf(f -> f.getPrix() > j.getOr());
		
		//TODO: supprimer les faces en double car un joueur ne peut acheter 2 faces identiques en 1 tour
		
		return new ListeAchat(this, j, disponibles);
	}

}
