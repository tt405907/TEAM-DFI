package joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.De;
import de.Face;
import de.Faces;

class BotUltimeTest {

	private Joueur joueur;
	private BotUltime bot;
	private Face face1;
	private Face face2;
	private Face face3;
	private Face face4;
	private Face face5;
	private Face face6;
	private Face face7;
	private De de1;
	private De de2;
	private Face face8;

	/**
	 * On initialise chacun de nos tests avec cette methode
	 */
	@BeforeEach
	void setBefore() {
		bot = new BotUltime();
		joueur = new Joueur("joeur").setBot(bot);
		face1 = Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1;
		face2 = Faces.LUNE_2_VICTOIRE_2;
		face3 = Faces.OR_3;
		face4 = Faces.SOLEIL_2;
		face5 = Faces.LUNE_1;
		face6 = Faces.VICTOIRE_2;
		face7 = Faces.OR_6;
		face8 = Faces.OR_2_LUNE_1;
		de1 = joueur.getDe1();
		de2 = joueur.getDe2();
	}

	@Test
	void choixFace() {
		int i1;
		// notre bot choisit-il bien toujours la face rapportant le plus d'or ?
		i1 = joueur.choixFace(face1, face2, face3, face4, face5);
		assertEquals(2, i1);
		// si les faces ont le même nombre d'or, il choisit bien une face,
		// arbitairement la première :
		i1 = joueur.choixFace(face4, face5);
		assertEquals(0, i1);
	}

	@Test
	void choixFaceNegatif() {
		// si on n'a pas de points de victoires et que la face rapporte uniquement des
		// Pdv
		joueur.addOr(1);
		int indice1 = joueur.choixFaceNegatif(face3, face6);
		// il devrait nous renvoyer face6 car son impact est nul, alors que face3 réduit
		// l'or de notre joueur.
		assertEquals(1, indice1);
		// Si on n'a pas de Soleil et que la face rapporte uniquement du Soleil
		joueur.addOr(-1);
		joueur.addVictoire(1);
		int indice2 = joueur.choixFaceNegatif(face6, face4);
		assertEquals(1, indice2);
		// Si on n'a pas d'Or et que la face rapporte uniquement de l'Or
		int indice3 = joueur.choixFaceNegatif(face6, face3);
		assertEquals(1, indice3);
		// Si on n'a pas de Lune et que la face rapporte uniquement de la Lune
		int indice4 = joueur.choixFaceNegatif(face6, face5);
		assertEquals(1, indice4);
		// On choisit alors la face qui nous fait perdre le moins d'Or et de valeur
		// accesoireOr parmi celles rapportant de l'or
		joueur.addLune(2);
		joueur.addOr(4);
		joueur.addSoleil(6);
		int indice5 = joueur.choixFaceNegatif(face1, face3, face4, face7, face8);
		assertEquals(4, indice5);
	}

	@Test
	void forge() {

		assertEquals(Faces.OR_1, de2.getFace(0));
		assertEquals(Faces.OR_1, de1.getFace(0));
		// Forge un X3
		joueur.forge(Faces.X3);
		assertEquals(de2.getFace(0), Faces.X3);
		de2.forge(Faces.OR_1, 0);
		// Forge miroir
		joueur.forge(Faces.MIROIR);
		assertEquals(de2.getFace(5), Faces.MIROIR);
		de2.forge(Faces.VICTOIRE_2, 5);
		// si on forge de l'or ce sera sur le dé1 jusqu'à dépasser VALEUR_OR_MINIMUM_DE1
		// qui vaut 12 ici
		joueur.forge(Faces.OR_4);
		assertEquals(de1.getFace(0), Faces.OR_4);
		joueur.forge(Faces.OR_4);
		joueur.forge(Faces.OR_4);
		assertEquals(de1.getFace(2), Faces.OR_4);
		// on a assez d'or sur le dé 1, on passe au second dé.
		joueur.forge(Faces.OR_4);
		assertEquals(de2.getFace(0), Faces.OR_4);
		de2.forge(Faces.OR_1, 0);
		de1.forge(Faces.OR_1, 0);
		de1.forge(Faces.OR_1, 1);
		de1.forge(Faces.OR_1, 2);
		// or 1 lune 1 soleil 1 victoire 1
		joueur.forge(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
		assertEquals(de1.getFace(5), Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
		de1.forge(Faces.SOLEIL_1, 5);
		// test de lune 1 et 2
		joueur.forge(Faces.LUNE_1);
		assertEquals(de2.getFace(0), Faces.LUNE_1);
		joueur.forge(Faces.LUNE_2);
		assertEquals(de2.getFace(1), Faces.LUNE_2);
		de2.forge(Faces.OR_1, 0);
		de2.forge(Faces.OR_1, 1);
		// test de victoire 4
		joueur.forge(Faces.VICTOIRE_4);
		assertEquals(de1.getFace(0), Faces.VICTOIRE_4);
		de1.forge(Faces.OR_1, 0);
		// test de OR_2_OU_LUNE_2_OU_SOLEIL_2
		joueur.forge(Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
		assertEquals(de2.getFace(0), Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
		de2.forge(Faces.OR_1, 0);
		//test de OR_3_OU_VICTOIRE_2
		joueur.forge(Faces.OR_3_OU_VICTOIRE_2);
		assertEquals(de1.getFace(0), Faces.OR_3_OU_VICTOIRE_2);
		de1.forge(Faces.OR_1, 0);
		//test de OR_2_LUNE_1
		joueur.forge(Faces.OR_2_LUNE_1);
		assertEquals(de1.getFace(0), Faces.OR_2_LUNE_1);
		de1.forge(Faces.OR_1, 0);
		//test de LUNE_2_VICTOIRE_2
		joueur.forge(Faces.LUNE_2_VICTOIRE_2);
		assertEquals(de1.getFace(0), Faces.LUNE_2_VICTOIRE_2);
		de1.forge(Faces.OR_1, 0);
		//test de SOLEIL_2
		joueur.forge(Faces.SOLEIL_2);
		assertEquals(de2.getFace(0), Faces.SOLEIL_2);
		de2.forge(Faces.OR_1, 0);
		//test de SOLEIL_1_VICTOIRE_1
		joueur.forge(Faces.SOLEIL_1_VICTOIRE_1);
		assertEquals(de2.getFace(5), Faces.SOLEIL_1_VICTOIRE_1);
		de2.forge(Faces.VICTOIRE_2, 5);
	}
}
