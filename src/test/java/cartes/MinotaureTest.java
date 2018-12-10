package cartes;

import de.*;
import joueur.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import partie.Partie;
import static org.junit.jupiter.api.Assertions.*;

public class MinotaureTest {
    private Joueur joueur1;
    private Joueur joueur2;
    private Carte carte;

    @BeforeEach
    void setUp() {
        joueur1 = new Joueur("MerryChristmas");
        joueur2 = new Joueur("LoveChristmas");
        carte = new Minotaure();
        new Partie(joueur1,joueur2);
        for(int indice = 0; indice < 6 ; indice++)
        {
            joueur2.getDe1().forge(new FaceVictoire(1,0),indice);
            joueur2.getDe2().forge(new FaceVictoire(1,0),indice);
        }
    }

    @Test
    void effetExploit()
    {
        // J'initialise le nombre de point Victoire à 10
        joueur2.addVictoire(10);
        assertEquals(10,joueur2.getVictoire());
        // Je vais donc appliquer le minotaure
        carte.effetExploit(joueur1);
        assertEquals(8,joueur2.getVictoire());
    }
}