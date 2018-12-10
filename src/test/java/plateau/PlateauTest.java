package plateau;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.Carte;
import joueur.Joueur;

public class PlateauTest {

	private Joueur joueur;

	@BeforeEach
	void setBefore() {
		joueur = new Joueur("joeur");
	}

	@Test
	void testGetCartesAchetables() {
		// attribue 5 lunes et 3 soleils et regarde si elles sont bien ajoutées
		joueur.addLune(5);
		joueur.addSoleil(3);
		assertEquals(5, joueur.getLune());
		assertEquals(3, joueur.getSoleil());

		// cree 5 cartes
		Carte carte1 = new Carte("Peut payer", 3, 2, 0);
		Carte carte2 = new Carte("Pas assez de soleil", 3, 4, 0);
		Carte carte3 = new Carte("Pas assez de lune", 6, 2, 0);
		Carte carte4 = new Carte("Pas assez de lune et de soleil", 6, 4, 0);
		Carte carte5 = new Carte("Juste assez de lune et de soleil", 5, 3, 0);

		// cree un plateau avec les cartes
		Plateau plateau = new Plateau(carte1, carte2, carte3, carte4, carte5);

		// verifie dans la liste des cartes que le joueur peut acheter:
		// carte 1 et 5 (qui peuvent être achetées) y sont
		// carte 2, 3 et 4 (qui coûtent trop cher) n'y sont pas
		List<Carte> liste = plateau.getCartesAchetables(joueur);
		assertTrue(liste.contains(carte1));
		assertTrue(liste.contains(carte5));

		assertFalse(liste.contains(carte2));
		assertFalse(liste.contains(carte3));
		assertFalse(liste.contains(carte4));

	}

	@Test
	void testAcheter() {
		// attribue 5 lunes et 3 soleils et regarde si elles sont bien ajoutées
		// verifie aussi que joueur possède 0 point de victoire
		joueur.addLune(5);
		joueur.addSoleil(3);
		assertEquals(5, joueur.getLune());
		assertEquals(3, joueur.getSoleil());
		assertEquals(0, joueur.getVictoire());

		// crée carte et vérifie qu'elle peut être achetée
		Carte carte = new Carte("Geoffroy", 1, 1, 3);
		assertTrue(carte.peutAcheter(joueur));

		// vérifie que la carte est bien achetable d'après le sanctuaire
		Plateau plateau = new Plateau(carte);
		assertFalse(plateau.getCartesAchetables(joueur).isEmpty());

		// achète la carte et vérifie que les lunes et soleils ont bien
		// été déduits et que la victoire a été ajoutée
		plateau.acheter(carte, joueur);
		assertEquals(4, joueur.getLune());
		assertEquals(2, joueur.getSoleil());
		assertEquals(3, joueur.getVictoire());

		// le joueur pourrait toujours acheter la carte mais le plateau ne
		// la propose plus car elle a été déjà achetée
		assertTrue(carte.peutAcheter(joueur));
		assertTrue(plateau.getCartesAchetables(joueur).isEmpty());
	}

	@Test
	void testDeplacementPlateau() {
		// crée un plateau avec des cartes par défaut
		Plateau plateau = new Plateau(2);
		Joueur joueur2 = new Joueur("joeur");

		// donne assez aux 2 joueurs pour acheter une carte
		joueur.addSoleil(4);
		assertEquals(0, joueur.getLune());
		assertEquals(4, joueur.getSoleil());
		assertEquals(0, joueur.getOr());
		assertEquals(0, joueur.getVictoire());

		joueur2.addSoleil(4);
		assertEquals(0, joueur2.getLune());
		assertEquals(4, joueur2.getSoleil());
		assertEquals(0, joueur2.getOr());
		assertEquals(0, joueur2.getVictoire());

		// on cherche la méduse qui a une île connue
		List<Carte> liste = plateau.getCartesAchetables(joueur);
		Carte meduse = null;
		for (Carte c : liste) {
			if (c.getNom().equals("La Méduse")) {
				meduse = c;
				break;
			}
		}
		assertNotNull(meduse);

		// on verifie que les deux joueurs peuvent l'acheter
		assertTrue(meduse.peutAcheter(joueur));
		assertTrue(meduse.peutAcheter(joueur2));

		// joueur 1 achète la carte et va sur l'ile
		plateau.acheter(meduse, joueur);
		assertEquals(0, joueur.getSoleil());
		assertEquals(14, joueur.getVictoire());

		// joueur 2 fait la même chose et remplace joueur 1 sur l'île
		plateau.acheter(meduse, joueur2);
		assertEquals(0, joueur2.getSoleil());
		assertEquals(14, joueur2.getVictoire());

		// vérifie que joueur 1 a reçu une faveur vu qu'il a été déplacé
		assertTrue(joueur.getOr() > 0 || joueur.getLune() > 0 || joueur.getSoleil() > 0 || joueur.getVictoire() > 14);
	}
}
