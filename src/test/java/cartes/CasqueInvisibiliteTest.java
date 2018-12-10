package cartes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.De;
import de.FaceLune;
import de.Faces;
import joueur.Joueur;

class CasqueInvisibiliteTest {
	private Joueur joueur;
	private Carte casque;

	@BeforeEach
	void setBefore() {
		joueur = new Joueur("joeur");
		casque = new CasqueInvisibilite();
	}

	@Test
	void effetExploit() {
		casque.acheter(joueur);
		assertEquals(Faces.X3, joueur.getDe1().getFace(0));
	}

	@Test
	void TestX3() {
		De de1 = joueur.getDe1();
		De de2 = joueur.getDe2();
		for (int i = 0; i < 6; i++) {
			de1.forge(Faces.X3, i);
			de2.forge(new FaceLune(1, 1), i);
		}
		assertEquals(0, joueur.getLune());
		joueur.appliquerDe();
		assertEquals(3, joueur.getLune());

		// Ici, on test que si un joueur tombe sur 2 face x3, rien ne se passe.
		for (int i = 0; i < 6; i++) {
			de2.forge(Faces.X3, i);
		}
		joueur.appliquerDe();
		assertEquals(3, joueur.getLune());
		assertEquals(0, joueur.getOr());
		assertEquals(0, joueur.getVictoire());
		assertEquals(0, joueur.getSoleil());
	}
}
