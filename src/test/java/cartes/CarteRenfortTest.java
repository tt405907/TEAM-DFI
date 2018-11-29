package cartes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.De;
import de.FaceOr;
import joueur.BotDefault;
import joueur.Joueur;

class CarteRenfortTest {
	private Joueur joueur;
    
	@BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
    }

	@Test
	void testAncien() {
		Ancien ancien = new Ancien();
		assertEquals(0,joueur.getOr());
        assertEquals(0,joueur.getVictoire());
        assertFalse(ancien.peutActiver(joueur));
        joueur.addOr(3);
        assertTrue(ancien.peutActiver(joueur));
        ancien.effetRenfort(joueur);
        assertEquals(0,joueur.getOr());
        assertEquals(4,joueur.getVictoire());
        
	}
	
	@Test
	void testSabot() {
		De de1 = joueur.getDe1();
		for (int i = 0; i < 6; i++) {
			de1.forge(new FaceOr(1,1), i);
		}
		SabotsArgent sabri = new SabotsArgent();
		assertEquals(0,joueur.getOr());
		sabri.effetRenfort(joueur);
		assertEquals(1,joueur.getOr());
	}
	
	@Test
	void testAilesGardienne() {
		AilesGardienne titi = new AilesGardienne();
		assertEquals(0,joueur.getOr());
        assertEquals(0,joueur.getLune());
        assertEquals(0,joueur.getSoleil());
        titi.effetRenfort(joueur);
        assertEquals(1,joueur.getOr()+ joueur.getSoleil()+ joueur.getLune());
	}

}
