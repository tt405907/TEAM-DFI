package de;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.BotDefault;
import joueur.Joueur;

public class FaceSelectionTest {
	private Face face;
	private Joueur bot0;
	private Joueur bot1;

	@BeforeEach
	void setBefore() {
		face = new FaceSelection(2, new FaceOr(4, 0), new FaceLune(2, 0));
		bot0 = new Joueur("bot0").setBot(new BotDefault() {
			public int choixFace(Face... faces) {
				return 0;
			}
			public int choixFaceNegatif(Face... faces) {
				return 0;
			}
		});

		bot1 = new Joueur("bot1").setBot(new BotDefault() {
			public int choixFace(Face... faces) {
				return 1;
			}
			public int choixFaceNegatif(Face... faces) {
				return 1;
			}
		});
	}

	@Test
	void appliquer() {
		assertEquals(0, bot0.getOr());
		assertEquals(0, bot0.getLune());
		face.appliquer(bot0);
		assertEquals(4, bot0.getOr());
		assertEquals(0, bot0.getLune());

		assertEquals(0, bot1.getOr());
		assertEquals(0, bot1.getLune());
		face.appliquer(bot1);
		assertEquals(0, bot1.getOr());
		assertEquals(2, bot1.getLune());
		assertEquals(4, bot0.getOr());
		assertEquals(0, bot0.getLune());
	}

	@Test
	void appliquerX3() {
		face.appliquerX3(bot0);
		assertEquals(12, bot0.getOr());
		assertEquals(0, bot0.getLune());
	}
	@Test
	void ToString() {
		assertEquals(face.toString(),"4 Or ou 2 Lune");
	}
	@Test
	void appliquerNegatif() {
		bot0.addOr(6);
		bot0.addLune(6);
		face.appliquerNegatif(bot0);
		assertEquals(2, bot0.getOr());
		assertEquals(6, bot0.getLune());
	}
	@Test
	void appliquerNegatifX3() {
		bot0.addOr(12);
		bot0.addLune(6);
		face.appliquerNegatifX3(bot0);
		assertEquals(0, bot0.getOr());
		assertEquals(6, bot0.getLune());
	}
	
}
