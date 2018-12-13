package joueur;

import de.De;
import de.Face;
import static de.Faces.*;

import java.util.function.ToIntFunction;

public final class BotUtils {
	private BotUtils() {}
	
	public static final ToIntFunction<Face> VALEUR_OR = BotUtils::valeurOr;
	public static final ToIntFunction<Face> VALEUR_LUNE = BotUtils::valeurLune;
	public static final ToIntFunction<Face> VALEUR_SOLEIL = BotUtils::valeurSoleil;
	public static final ToIntFunction<Face> VALEUR_VICTOIRE = BotUtils::valeurVictoire;
	
	public static int valeurDe(ToIntFunction<Face> eval, De de) {
		int total = 0;
		for (Face f : de.getAllFaces())
		{
			total += eval.applyAsInt(f);
		}
		
		return total;
	}
	
	public static int getMinFaceIndex(ToIntFunction<Face> eval, Face... faces) {
		int choix = 0;
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < faces.length; i++)
		{
			if (eval.applyAsInt(faces[i]) < min)
			{
				choix = i;
				min = eval.applyAsInt(faces[i]);
			}
		}
		
		return choix;
	}
	
	public static int getMinFaceIndex(ToIntFunction<Face> eval, De de) {
		return getMinFaceIndex(eval, de.getAllFaces());
	}
	
	public static int getMaxFaceIndex(ToIntFunction<Face> eval, Face... faces) {
		int choix = 0;
		int max = 0;
		
		for (int i = 0; i < faces.length; i++)
		{
			if (eval.applyAsInt(faces[i]) > max)
			{
				choix = i;
				max = eval.applyAsInt(faces[i]);
			}
		}
		
		return choix;
	}
	
	public static int getMaxFaceIndex(ToIntFunction<Face> eval, De de) {
		return getMaxFaceIndex(eval, de.getAllFaces());
	}
	
	public static De getMaxDe(ToIntFunction<Face> eval, Joueur j) {
		De de1 = j.getDe1();
		De de2 = j.getDe2();
		return valeurDe(eval, de1) > valeurDe(eval, de2) ? de1 : de2;
	}
	
	public static De getMinDe(ToIntFunction<Face> eval, Joueur j) {
		De de1 = j.getDe1();
		De de2 = j.getDe2();
		return valeurDe(eval, de1) < valeurDe(eval, de2) ? de1 : de2;
	}
	
	public static int valeurOr(Face face) {
		if (face == OR_1 || face == OR_1_OU_LUNE_1_OU_SOLEIL_1 || face == OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) return 1;
		if (face == OR_2 || face == OR_2_LUNE_1 || face == OR_2_OU_LUNE_2_OU_SOLEIL_2) return 2;
		if (face == OR_3) return 3;
		if (face == OR_4) return 4;
		if (face == OR_6) return 6;
		
		return 0;
	}
	
	public static int valeurLune(Face face) {
		if (face == LUNE_1 || face == OR_2_LUNE_1 || face == OR_1_OU_LUNE_1_OU_SOLEIL_1 || face == OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) return 1;
		if (face == LUNE_2 || face == LUNE_2_VICTOIRE_2 || face == OR_2_OU_LUNE_2_OU_SOLEIL_2) return 2;
		
		return 0;
	}
	
	public static int valeurSoleil(Face face) {
		if (face == SOLEIL_1 || face == SOLEIL_1_VICTOIRE_1 || face == OR_1_OU_LUNE_1_OU_SOLEIL_1 || face == OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) return 1;
		if (face == SOLEIL_2 || face == OR_2_OU_LUNE_2_OU_SOLEIL_2) return 2;
		
		return 0;
	}
	
	public static int valeurVictoire(Face face) {
		if (face == VICTOIRE_1 || face == SOLEIL_1_VICTOIRE_1 || face == OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) return 1;
		if (face == VICTOIRE_2 || face == OR_3_OU_VICTOIRE_2 || face == LUNE_2_VICTOIRE_2) return 2;
		if (face == VICTOIRE_3) return 3;
		if (face == VICTOIRE_4) return 4;
		
		return 0;
	}
}
