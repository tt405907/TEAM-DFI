package plateau;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.Carte;
import joueur.BotDefault;
import joueur.Joueur;

class PlateauTest {
	
	private Joueur joueur;
	
	@BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
    }

	@Test
	void testGetCartesAchetables() 
	{
		joueur.addLune(5);
		joueur.addSoleil(3);
		assertEquals(5,joueur.getLune());
		assertEquals(3,joueur.getSoleil());
		
		Carte carte1 = new Carte("Peut payer",3 ,2, 0);
		Carte carte2 = new Carte("Pas assez de soleil",3 ,4, 0);
		Carte carte3 = new Carte("Pas assez de lune",6 ,2, 0);
		Carte carte4 = new Carte("Pas assez de lune et de soleil",6 ,4, 0);
		Carte carte5 = new Carte("Juste assez de lune et de soleil",5 ,3, 0);
		
		Plateau plateau = new Plateau(carte1,carte2,carte3,carte4,carte5) ;
		
		List<Carte> liste = plateau.getCartesAchetables(joueur);
		assertTrue(liste.contains(carte1));
		assertTrue(liste.contains(carte5));
		
		assertFalse(liste.contains(carte2));
		assertFalse(liste.contains(carte3));
		assertFalse(liste.contains(carte4));
	
	}
	
	@Test
	void testAcheter()
	{
		joueur.addLune(5);
		joueur.addSoleil(3);
		assertEquals(5,joueur.getLune());
		assertEquals(3,joueur.getSoleil());
		assertEquals(0,joueur.getVictoire());
		
		Carte carte = new Carte("Geoffroy",1 ,1, 3);
		assertTrue(carte.peutAcheter(joueur));
		
		Plateau plateau = new Plateau(carte);
		assertFalse(plateau.getCartesAchetables(joueur).isEmpty());
		
		plateau.acheter(carte, joueur);
		assertEquals(4,joueur.getLune());
		assertEquals(2,joueur.getSoleil());
		assertEquals(3,joueur.getVictoire());
		
		assertTrue(carte.peutAcheter(joueur));
		assertTrue(plateau.getCartesAchetables(joueur).isEmpty());
	}
	
	@Test
	void testDeplacementPlateau() 
	{
		Plateau plateau = new Plateau(2);
		Joueur joueur2 = new BotDefault("joeur");
		
		joueur.addSoleil(4);
		assertEquals(0,joueur.getLune());
		assertEquals(4,joueur.getSoleil());
		assertEquals(0,joueur.getOr());
		assertEquals(0,joueur.getVictoire());
		
		joueur2.addSoleil(4);
		assertEquals(0,joueur2.getLune());
		assertEquals(4,joueur2.getSoleil());
		assertEquals(0,joueur2.getOr());
		assertEquals(0,joueur2.getVictoire());
		
		List<Carte> liste = plateau.getCartesAchetables(joueur);
		Carte meduse = null;
		for(Carte c : liste)
		{
			if(c.getNom().equals("La Méduse")) 
			{
				meduse = c;
				break;
			}
		}
		assertNotNull(meduse);
		
		assertTrue(meduse.peutAcheter(joueur));
		assertTrue(meduse.peutAcheter(joueur2));
		
		plateau.acheter(meduse, joueur);
		assertEquals(0,joueur.getSoleil());
		assertEquals(14,joueur.getVictoire());
		
		plateau.acheter(meduse, joueur2);
		assertEquals(0,joueur.getSoleil());
		assertEquals(14,joueur.getVictoire());
		
		assertTrue(joueur.getOr() > 0 ||
				joueur.getLune() > 0 ||
				joueur.getSoleil() > 0 ||
				joueur.getVictoire() > 14);
	}
}
