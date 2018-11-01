package sanctuaire;

import java.util.List;

import de.Face;
import joueur.Joueur;

/**
 * Objet intermédiaire permettant à un Joueur de faire ses achats pendant un tour
 */
public class ListeAchat {
	private List<Face> restantes;
	private Sanctuaire sanctuaire;
	private Joueur acheteur;
	
	public ListeAchat(Sanctuaire sanctuaire, Joueur acheteur, List<Face> restantes) {
		this.sanctuaire = sanctuaire;
		this.restantes = restantes;
		this.acheteur = acheteur;
	}
	
	/**
	 * Donne les faces que le joueur peut encore acheter.
	 * La liste ne doit pas être modifiée, les éléments désirés doivent être donnés dans acheter().
	 */
	public List<Face> getAvailable() {
		return restantes;
	}
	
	public void acheter(Face face) {
		//Tente de retirer la face désirée
		if (restantes.remove(face))
		{
			//Dépense l'or et supprime la face du sanctuaire
			acheteur.addOr(-face.getPrix());
			sanctuaire.remove(face);
			
			//Cache les faces que le joueur ne peut plus acheter
			restantes.removeIf(f -> f.getPrix() > acheteur.getOr());
		}
	}
}
