package cartes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.De;
import de.FaceLune;
import de.FaceOr;
import joueur.BotDefault;
import joueur.Joueur;

public class CarteSimpleTest {

	private Joueur joueur;
    
	@BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
    }

	
	@Test
	void testHerbesFolle() {
		HerbesFolles herbesfolles = new HerbesFolles();
		assertEquals(0,joueur.getOr());
        assertEquals(0,joueur.getLune());
        herbesfolles.acheter(joueur);
        assertEquals(3,joueur.getOr());
        assertEquals(3,joueur.getLune());
      //les points de victoire sont déja testé dans carteTest
	}

	@Test
	void testCoffreDuforgeron() {
		CoffreDuForgeron coffreduforgeron = new CoffreDuForgeron();
		assertEquals(12,joueur.getOrMax());
        assertEquals(6,joueur.getLuneMax());
        assertEquals(6,joueur.getSoleilMax());
        coffreduforgeron.acheter(joueur);
        assertEquals(16,joueur.getOrMax());
        assertEquals(9,joueur.getLuneMax());
        assertEquals(9,joueur.getSoleilMax());
        
	}

	@Test
	void testPince() {
		De de1 = joueur.getDe1();
		De de2 = joueur.getDe2();
		for (int i = 0; i < 6; i++) {
			de1.forge(new FaceOr(1,1), i);
			de2.forge(new FaceLune(1,1), i);
		}
		Pince pince = new Pince();
		assertEquals(0,joueur.getOr());
        assertEquals(0,joueur.getLune());
        pince.acheter(joueur);
        assertEquals(2,joueur.getOr());
        assertEquals(2,joueur.getLune());
        //on sait que le de1 c'est toujours 1or et le de2 c'est toujours 1lune comme on lance les 2 des 2 fois on a 2 de chaques
	}
	
	@Test
	void testEnigme() {
		De de1 = joueur.getDe1();
		for (int i = 0; i < 6; i++) {
			de1.forge(new FaceOr(1,1), i);
		}
		Enigme enigme = new Enigme();
		assertEquals(0,joueur.getOr());
		enigme.acheter(joueur);
		assertEquals(4,joueur.getOr());
		//lance le premier de 4 fois donc 4 or
	}
	
}
