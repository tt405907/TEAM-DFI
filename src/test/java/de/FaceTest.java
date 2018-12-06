package de;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.BotDefault;
import joueur.Joueur;

public class FaceTest {
	private Joueur joueur;
    
	@BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
    }

	@Test
		public void testFaceOr() {
			Face face = new FaceOr(4,0);
			
			assertEquals(0, joueur.getOr());
			face.appliquer(joueur);
			assertEquals(4, joueur.getOr());
			}

	@Test
		public void testFaceLune() {
			Face face = new FaceLune(4,0);
	
			assertEquals(0, joueur.getLune());
			face.appliquer(joueur);
			assertEquals(4, joueur.getLune());
		}
	
	@Test
	public void testFaceSoleil() {
		Face face = new FaceSoleil(4,0);

		assertEquals(0, joueur.getSoleil());
		face.appliquer(joueur);
		assertEquals(4, joueur.getSoleil());
	}
	
	@Test
	public void testFaceVictoire() {
		Face face = new FaceVictoire(4,0);

		assertEquals(0, joueur.getVictoire());
		face.appliquer(joueur);
		assertEquals(4, joueur.getVictoire());
	}
	
	}
	

