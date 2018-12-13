package joueur;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

import cartes.Carte;
import cartes.CarteRenfort;
import cartes.Cartes;
import de.De;
import de.Face;
import sanctuaire.ListeAchat;

public abstract class BotRessource extends Bot {
	private ToIntFunction<Face> faceEval;
	
	public BotRessource(ToIntFunction<Face> faceEval) {
		this.faceEval = faceEval;
	}
	
	protected int eval(Face face) {
		return faceEval.applyAsInt(face);
	}

	@Override
	public int choixFace(Face... faces) {
		//Choisit la face avec la plus grande valeur
		return BotUtils.getMaxFaceIndex(faceEval, faces);
	}

	@Override
	public int choixFaceNegatif(Face... faces) {
		//Choisit la face avec la plus petite valeur
		return BotUtils.getMinFaceIndex(faceEval, faces);
	}

	@Override
	public void forge(Face face) {
		//Prends le dé avec la plus petite valeur
		De cible = BotUtils.getMinDe(faceEval, getJoueur());
		//Trouve la face avec la plus petite valeur
		int index = BotUtils.getMinFaceIndex(faceEval, cible);
		cible.forge(face, index);
	}

	@Override
	public void faireAchatsFace(ListeAchat liste) {
		while (!liste.isEmpty())
		{
			//Prends le dé avec la plus petite valeur
			De cible = BotUtils.getMinDe(faceEval, getJoueur());
			//Trouve la valeur de la face rapportant le moins, qui sera remplacée si possible
			int value = eval(cible.getFace(BotUtils.getMinFaceIndex(faceEval, cible)));
			
			//Prends les faces qu'il reste valant plus que la face à remplacer
			//Puis prends celle qui vaut le plus
			Face face = liste.getAvailable().stream()
					.filter(f -> eval(f) > value)
					.max(Comparator.comparingInt(this::eval))
					.orElse(null);
			
			//Si aucune face n'est trouvée, les achats s'arrêtent ici
			if (face == null) break;
			
			liste.acheter(face);
		}
	}

	@Override
	public Carte faireAchatCartes(List<Carte> cartes) {
		//N'achete pas si il ne peut rien acheter
		if(cartes.isEmpty()) return null;
		
		//On prends la carte achetable  qui vaut le plus de point de victoire
		Carte choix = cartes.stream()
				.max(Comparator.comparingInt(Carte::getVictoire))
				.orElse(null);
		
		return choix;
	}

	@Override
	public De choixFaveurMineure() {
		return BotUtils.getMaxDe(faceEval, getJoueur());
	}

	@Override
	public CarteRenfort choixRenfort(List<CarteRenfort> liste) {
		// utilise les sabots d'abord car peut donner de l'or pour l'ancien
		if (peutUtiliser(liste, Cartes.SABOTS)) return Cartes.SABOTS;
		else if (peutUtiliser(liste, Cartes.AILES)) return Cartes.AILES;
		else if (peutUtiliser(liste, Cartes.ANCIEN)) return Cartes.ANCIEN;
		else return null;
	}
	
	private boolean peutUtiliser(List<CarteRenfort> disponibles, CarteRenfort tente) {
		return disponibles.contains(tente) && tente.peutActiver(getJoueur());
	}

	@Override
	public int changeOrEnMarteau(int or) {
		// Garde 9 or
		int manquant = Math.max(0, 9 - getJoueur().getOr());
		return Math.max(0, or - manquant);
	}

}
