package de;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import joueur.BotDefault;
import joueur.Joueur;

public class DeTest {
    private Joueur joueur;

    @BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
    }

    /**
     * Test si les faces s'appliquent bien au lancé
     */
    @Test
    void testAppliquerDe() {
        De d = new De(new Face[]{
                new FaceLune(1,0),
                new FaceLune(1,1),
                new FaceLune(1,0),
                new FaceLune(1,0),
                new FaceLune(1,0),
                new FaceLune(1,0)
        });

        assertEquals(0, joueur.getLune());
        d.appliquerDe(joueur);
        assertEquals(1, joueur.getLune());
        d.appliquerDe(joueur);
        assertEquals(2, joueur.getLune());
    }

    /**
     * Test si des faces différentes peuvent s'appliquer
     */
    @Test
    void testAppliquerFaces() {
        De d = new De(new Face[]{
                new FaceLune(1,0),
                new FaceLune(1,0),
                new FaceLune(1,0),
                new FaceSoleil(1,0),
                new FaceSoleil(1,0),
                new FaceSoleil(1,0)
        });

        assertEquals(0, joueur.getLune());
        assertEquals(0, joueur.getSoleil());
        d.appliquerDe(joueur);
        assertTrue(joueur.getLune() > 0 || joueur.getSoleil() > 0);
    }
}