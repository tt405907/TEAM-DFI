package de;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.Joueur;

public class FaceTest {
	private Joueur joueur;
    
	@BeforeEach
    void setBefore()
    {
        joueur = new Joueur("joeur");
    }

	@Test
		public void testFaceOr() {
			Face face = new FaceOr(4,0);

			// Test Simple Appliquer
			assertEquals(0, joueur.getOr());
			face.appliquer(joueur);
			assertEquals(4, joueur.getOr());

			Face face1 = new FaceOr(1,0);
			// On Re init notre joueur a 0 Or
			joueur.addOr(-1000);

			// Test AppliquerX3
			assertEquals(0, joueur.getOr());
			face.appliquerX3(joueur);
			assertEquals(12, joueur.getOr());   // Le joueur a donc 12 Or

			// Test AppliquerNegatif , nous enlevons donc 1 Or il restera donc 11 Or
			face1.appliquerNegatif(joueur);
			assertEquals(11,joueur.getOr());

			// Test AppliquerNegatifX3 , nous enlevons donc 3 x 1 Or donc il restera 8 Or
			face1.appliquerNegatifX3(joueur);
			assertEquals(8,joueur.getOr());
			}

	@Test
		public void testFaceLune() {
			Face face = new FaceLune(4,0);
	
			assertEquals(0, joueur.getLune());
			face.appliquer(joueur);
			assertEquals(4, joueur.getLune());

			Face face1 = new FaceLune(1,0);
			// On Re init notre joueur a 0 Lune
			joueur.addLune(-1000);

			// Test AppliquerX3
			assertEquals(0, joueur.getLune());
			face.appliquerX3(joueur);
			// 4 x 3 = 12 mais il a un stock max de 6 en Lune
			assertEquals(6, joueur.getLune());   // Le joueur a donc 6 Lune

			// Test AppliquerNegatif , nous enlevons donc 1 Lune il restera donc 5 Lune
			face1.appliquerNegatif(joueur);
			assertEquals(5,joueur.getLune());

			// Test AppliquerNegatifX3 , nous enlevons donc 3 x 1 Lune donc il restera 2 Lune
			face1.appliquerNegatifX3(joueur);
			assertEquals(2,joueur.getLune());
		}
	
	@Test
	public void testFaceSoleil() {
		Face face = new FaceSoleil(4,0);

		assertEquals(0, joueur.getSoleil());
		face.appliquer(joueur);
		assertEquals(4, joueur.getSoleil());

		Face face1 = new FaceSoleil(1,0);
		// On Re init notre joueur a 0 Soleil
		joueur.addSoleil(-1000);

		// Test AppliquerX3
		assertEquals(0, joueur.getSoleil());
		face.appliquerX3(joueur);
		// 4 x 3 = 12 mais il a un stock max de 6 en Soleil
		assertEquals(6, joueur.getSoleil());   // Le joueur a donc 6 Soleil

		// Test AppliquerNegatif , nous enlevons donc 1 Soleil il restera donc 5 Soleil
		face1.appliquerNegatif(joueur);
		assertEquals(5,joueur.getSoleil());

		// Test AppliquerNegatifX3 , nous enlevons donc 3 x 1 Soleil donc il restera 2 Soleil
		face1.appliquerNegatifX3(joueur);
		assertEquals(2,joueur.getSoleil());
	}
	
	@Test
	public void testFaceVictoire() {
		Face face = new FaceVictoire(4,0);

		assertEquals(0, joueur.getVictoire());
		face.appliquer(joueur);
		assertEquals(4, joueur.getVictoire());

		Face face1 = new FaceVictoire(1,0);
		// On Re init notre joueur a 0 Victoire
		joueur.addVictoire(-1000);

		// Test AppliquerX3
		assertEquals(0, joueur.getVictoire());
		face.appliquerX3(joueur);
		assertEquals(12, joueur.getVictoire());   // Le joueur a donc 12 Victoire

		// Test AppliquerNegatif , nous enlevons donc 1 Victoire il restera donc 11 Victoire
		face1.appliquerNegatif(joueur);
		assertEquals(11,joueur.getVictoire());

		// Test AppliquerNegatifX3 , nous enlevons donc 3 x 1 Victoire donc il restera 8 Victoire
		face1.appliquerNegatifX3(joueur);
		assertEquals(8,joueur.getVictoire());
	}
	
	}
	

