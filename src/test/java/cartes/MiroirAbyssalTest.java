package cartes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.*;
import joueur.BotDefault;
import joueur.Joueur;
import partie.Partie;

class MiroirAbyssalTest {

	private Joueur j1;
	private Joueur j2;
	private Carte miroir;
	private Face faceMiroir;

	// Avant chaque test, nous créons une partie contenant 2 joueurs ayant des dés
	// sans faces en commun, ainsi que une carte miroir abyssal et une face miroir.
	@BeforeEach
	void setBefore() {
		j1 = new BotDefault("joueurVictoire");
		j2 = new BotDefault("joueurLune");
		miroir = new MiroirAbyssal();
		faceMiroir = new FaceMiroir();
		De de1 = j1.getDe1();
		De de2 = j1.getDe2();
		De de3 = j2.getDe1();
		De de4 = j2.getDe2();
		new Partie(j1, j2);
		for (int i = 0; i < 6; i++) {
			de1.forge(new FaceOr(1, 1), i);
			de2.forge(new FaceOr(1, 1), i);
			de3.forge(new FaceVictoire(4, 12), i);
			de4.forge(new FaceVictoire(4, 12), i);
		}
	}

	// Test de l'ajout d'une face miroir de l'achat de la carte miroir
	@Test
	void effetExploit() {
		miroir.acheter(j1);
		assertEquals(Faces.MIROIR, j1.getDe1().getFace(0));
	}

	// Vérifie que quand la face miroir est appliquée,
	// une face d'un des adversaires est appliquée.
	@Test
	void TestMiroir() {

		assertEquals(0, j1.getVictoire());
		faceMiroir.appliquer(j1);
		assertEquals(4, j1.getVictoire());
		assertEquals(0, j2.getOr());
		faceMiroir.appliquer(j2);
		assertEquals(1, j2.getOr());
	}
}
