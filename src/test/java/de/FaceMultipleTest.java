package de;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.Joueur;

public class FaceMultipleTest {
	private Joueur joueur;
	
	@BeforeEach
    void setBefore()
    {
        joueur = new Joueur("joeur");
    }

	@Test
	public void testFaceOrFaceLune() {
		FaceMultiple face = new FaceMultiple(2,new FaceOr(4,0),new FaceLune(2,0));
		
		assertEquals(0, joueur.getOr());
		assertEquals(0, joueur.getLune());
		face.appliquer(joueur);
		assertEquals(4, joueur.getOr());
		assertEquals(2, joueur.getLune());
		face.appliquerNegatif(joueur);
		assertEquals(0, joueur.getOr());
		assertEquals(0, joueur.getLune());
		assertEquals(face.toString(),"4[1;33m Or[0m + 2[1;34m Lune[0m");
		}
	
	@Test
	public void testFaceOrFaceLuneFaceSoleilFaceVictoire() {
		FaceMultiple face = new FaceMultiple(4,new FaceOr(4,0),new FaceLune(2,0),new FaceVictoire(1,0),new FaceSoleil(3,0));
		
		assertEquals(0, joueur.getOr());
		assertEquals(0, joueur.getLune());
		assertEquals(0, joueur.getVictoire());
		assertEquals(0, joueur.getSoleil());
		face.appliquer(joueur);
		assertEquals(4, joueur.getOr());
		assertEquals(2, joueur.getLune());
		assertEquals(3, joueur.getSoleil());
		assertEquals(1, joueur.getVictoire());
		face.appliquerNegatif(joueur);
		assertEquals(0, joueur.getOr());
		assertEquals(0, joueur.getLune());
		assertEquals(0, joueur.getVictoire());
		assertEquals(0, joueur.getSoleil());
		assertEquals(face.toString(),"4[1;33m Or[0m + 2[1;34m Lune[0m + 1[1;32m Victoire[0m + 3[1;31m Soleil[0m");
		}

}
