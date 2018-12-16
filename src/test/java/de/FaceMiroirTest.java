package de;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.MiroirAbyssal;
import joueur.BotDefault;
import joueur.Joueur;
import partie.Partie;

class FaceMiroirTest {
	private Face face;
	private Joueur bot0;
	private Joueur bot1;

	@BeforeEach
	void setBefore() {
		face = new FaceMiroir();
		bot0 = new Joueur("joueurVictoire");
		bot1 = new Joueur("joueurLune");
		face = new FaceMiroir();
		De de1 = bot0.getDe1();
		De de2 = bot0.getDe2();
		De de3 = bot1.getDe1();
		De de4 = bot1.getDe2();
		for (int i = 0; i < 6; i++) {
			de1.forge(Faces.OR_1, i);
			de2.forge(Faces.OR_1, i);
			de3.forge(Faces.VICTOIRE_4, i);
			de4.forge(Faces.VICTOIRE_4, i);
		}
		Partie partie = new Partie(bot0, bot1);
	}

	@Test
	void getProxiedFace() {
		assertEquals(face.getProxiedFace(bot0), Faces.VICTOIRE_4);
		assertEquals(face.getProxiedFace(bot1), Faces.OR_1);
	}

	@Test
	void appliquer() {
		assertEquals(bot0.getVictoire(), 0);
		face.appliquer(bot0);
		assertEquals(bot0.getVictoire(), 4);
		assertEquals(bot1.getOr(), 0);
		face.appliquer(bot1);
		assertEquals(bot1.getOr(), 1);
	}

	@Test
	void appliquerX3() {
		assertEquals(bot0.getVictoire(), 0);
		face.appliquerX3(bot0);
		assertEquals(bot0.getVictoire(), 12);
		assertEquals(bot1.getOr(), 0);
		face.appliquerX3(bot1);
		assertEquals(bot1.getOr(), 3);
	}

	@Test
	void ToString() {
		assertEquals(face.toString(), "Miroir");
	}

	@Test
	void appliquerNegatif() {
		bot0.addVictoire(7);
		face.appliquerNegatif(bot0);
		assertEquals(bot0.getVictoire(), 3);
		bot1.addOr(7);
		face.appliquerNegatif(bot1);
		assertEquals(bot1.getOr(), 6);
	}

	@Test
	void appliquerNegatifX3() {
		bot0.addVictoire(17);
		face.appliquerNegatifX3(bot0);
		assertEquals(bot0.getVictoire(), 5);
		bot1.addOr(7);
		face.appliquerNegatifX3(bot1);
		assertEquals(bot1.getOr(), 4);
	}
}
