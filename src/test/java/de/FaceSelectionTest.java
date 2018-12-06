package de;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import joueur.BotDefault;
import joueur.Joueur;

public class FaceSelectionTest {

	@Test
	void testSelectionFace() {
		Face face = new FaceSelection(2,new FaceOr(4,0),new FaceLune(2,0));
		Joueur bot0 = new BotDefault("joeur") {
			public int choixFace(Face... faces) {
				return 0;
			}
		};
		
		Joueur bot1 = new BotDefault("joeur") {
			public int choixFace(Face... faces) {
				return 1;
			}
		};
		
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
	}

}
