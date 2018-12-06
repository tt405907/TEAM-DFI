package cartes;

import de.FaceLune;
import de.FaceOr;
import de.FaceSoleil;
import de.FaceVictoire;
import joueur.BotDefault;
import joueur.Joueur;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import partie.Partie;

import static org.junit.jupiter.api.Assertions.*;

public class SatyresTest {
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueur3;
    private Partie partie;
    private Carte carte;

    @BeforeEach
    void setUp() {
        joueur1 = new BotDefault("MerryChristmas");
        joueur2 = new BotDefault("LoveChristmas");
        joueur3 = new BotDefault("SexyMotherChristmas");
        carte = new Satyres();
        partie = new Partie(joueur1, joueur2,joueur3);
        for (int indice = 0; indice < 6; indice++) {
            joueur2.getDe1().forge(new FaceVictoire(1,0),indice);
            joueur2.getDe2().forge(new FaceVictoire(1,0),indice);
            joueur3.getDe1().forge(new FaceVictoire(1, 0), indice);
            joueur3.getDe2().forge(new FaceVictoire(1, 0), indice);
        }
    }
    @Test
    void effetExploit() {
        // Je vérifie que notre joueur 1 n'a pas de points de victoires
        assertEquals(0,joueur1.getVictoire());
        carte.effetExploit(joueur1);
        // Il devra donc avoir 2 points de victoires car il va choisir 2 dés parmi les 4 dés proposés et tous les dés sont set avec une face à 1 victoire
        assertEquals(2,joueur1.getVictoire());

    }
}