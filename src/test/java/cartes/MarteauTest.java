package cartes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.BotDefault;
import joueur.Joueur;

public class MarteauTest {
	private Joueur joueur;
	UnMarteau marteau;
	UnMarteau marteau2;
	
	@BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
        marteau = new UnMarteau(joueur);
        marteau2 = new UnMarteau(joueur);
    }
	
	@Test
	void testAddMarteau() {
		//on regarde si il n'y a pas de marteau
		assertNull(joueur.getNextMarteau());
		//on ajoute un marteau
		joueur.addMarteau(marteau);
		//regarde de nouveau si on a un marteau
		assertEquals(marteau,joueur.getNextMarteau());
	}
	
	@Test
	void testDelMarteau() {
		joueur.addMarteau(marteau);
		assertEquals(marteau,joueur.getNextMarteau());
		//on supprime le marteau
		joueur.delMarteau(marteau);
		//on regarde que le marteau est bien supprimer
		assertNull(joueur.getNextMarteau());
	}
	
	@Test
	void testgetNextMarteau() {
		//on ajoute 2 marteaux
		joueur.addMarteau(marteau);
		joueur.addMarteau(marteau2);
		assertEquals(marteau,joueur.getNextMarteau());
		//on enleve le premier et on regarde que marteau2 est bien le suivant
		joueur.delMarteau(marteau);
		assertEquals(marteau2,joueur.getNextMarteau());
	}
	
	@Test
	void testEffetAddOr() {
		joueur.addMarteau(marteau);
		assertEquals(0,joueur.getVictoire());
		//on ajoute 14 or il ce passe rien
		assertEquals(0,marteau.effetAddOr(14));
		assertEquals(0,joueur.getVictoire());
		//on passe à 15, premiere face terminer
		assertEquals(1,marteau.effetAddOr(1));
		assertEquals(10,joueur.getVictoire());
		//on passe à 29 il ce passe rien
		assertEquals(0,marteau.effetAddOr(14));
		assertEquals(10,joueur.getVictoire());
		//on passe à 30 deuxieme face termine et on supprime le marteau
		assertEquals(2,marteau.effetAddOr(1));
		assertEquals(25,joueur.getVictoire());
		assertNull(joueur.getNextMarteau());	
	}
	
	@Test
	void testDebordement() {
		//on finit le premier marteau en une seule fois et on regarde que le 1 est bien débordé
		joueur.addMarteau(marteau);
		joueur.addMarteau(marteau2);
		assertEquals(0,joueur.getVictoire());
		assertEquals(3,marteau.effetAddOr(31));
		assertEquals(25,joueur.getVictoire());
		assertEquals(marteau2,joueur.getNextMarteau());
		//on rajoute 14 pour verifier que le deuxieme marteau se termine
		assertEquals(1,marteau2.effetAddOr(14));
		assertEquals(35,joueur.getVictoire());
	}
	
	@Test
	void testEffetExploit() {
		//quand on achete la carte il y a bien un nouveau marteau
		assertNull(joueur.getNextMarteau());
		new MarteauDuForgeron().acheter(joueur);
		assertNotNull(joueur.getNextMarteau());
	}
	
}
