package joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class testant toutes les methodes de notre class Joueur
 */
public class JoueurTest {

    private Joueur joueur;

    /**
     * On initialise chacun de nos tests avec  cette methode
     */
    @BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
    }

    /**
     * Test de notre methode addOr de notre class Joueur
     */
    @Test
    void addOr()
    {
        // test borne max
        joueur.addOr(51);
        assertEquals(12,joueur.getOr());

        // test borne min
        joueur.addOr(-54);
        assertEquals(0,joueur.getOr());

        // test simple add
        joueur.addOr(8);
        assertEquals(8,joueur.getOr());
    }

    /**
     * Test de notre methode addSoleil de notre class Joueur
     */
    @Test
    void addSoleil()
    {
        // test borne max
        joueur.addSoleil(51);
        assertEquals(6,joueur.getSoleil());

        // test borne min
        joueur.addSoleil(-54);
        assertEquals(0,joueur.getSoleil());

        // test simple add
        joueur.addSoleil(4);
        assertEquals(4,joueur.getSoleil());
    }

    /**
     * Test de notre methode addLune de notre class Joueur
     */
    @Test
    void addLune()
    {
        // test borne max
        joueur.addLune(51);
        assertEquals(6,joueur.getLune());

        // test borne min
        joueur.addLune(-54);
        assertEquals(0,joueur.getLune());

        // test simple add
        joueur.addLune(4);
        assertEquals(4,joueur.getLune());
    }

    /**
     * Test de notre methode addVictoire de notre class Joueur
     */
    @Test
    void addVictoire()
    {
        // test simple add
        joueur.addVictoire(3);
        assertEquals(3,joueur.getVictoire());

        // test simple add
        joueur.addVictoire(5);
        assertEquals(8,joueur.getVictoire());
    }
}