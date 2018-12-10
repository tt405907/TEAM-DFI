package cartes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.FaceVictoire;
import joueur.Joueur;
import partie.Partie;

public class SatyresTest {
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueur3;
    private Carte carte;

    @BeforeEach
    void setUp() {
        joueur1 = new Joueur("MerryChristmas");
        joueur2 = new Joueur("LoveChristmas");
        joueur3 = new Joueur("SexyMotherChristmas");
        carte = new Satyres();
        new Partie(joueur1, joueur2,joueur3);
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