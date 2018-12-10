package partie;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.Joueur;

public class PartieTest {
	private Partie partie;
	private Joueur j1;
	private Joueur j2;
	private Joueur j3;
	private Joueur j4;

	    @BeforeEach
	    void setBefore()
	    {
	        j1= new Joueur("joeur1");
	        j2= new Joueur("joeur2");
	        j3= new Joueur("joeur3");
	        j4= new Joueur("joeur4");
	        partie= new Partie(j1,j2,j3,j4);
	    }

	    
	    @Test
	    void faireTours()
	    {	
	    
	        // test borne max
	        partie.faireTour();
	        int i1= j1.getOr()+j1.getLune()+j1.getSoleil()+j1.getLune();
	        int i2= j2.getOr()+j2.getLune()+j2.getSoleil()+j2.getLune();
	        int i3= j3.getOr()+j3.getLune()+j3.getSoleil()+j3.getLune();
	        int i4= j4.getOr()+j4.getLune()+j4.getSoleil()+j4.getLune();
	        assertTrue(i1>0);
	        assertTrue(i2>0);
	        assertTrue(i3>0);
	        assertTrue(i4>0);
	    }

	    @Test
	    
	    void game()
	    {
	    	List<Joueur> ganiants;
	    	ganiants=partie.game();
	    	assertTrue(!ganiants.isEmpty());
	    }
}
