package sanctuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.*;
import joueur.*;

public class SanctuaireTest {

    private Sanctuaire sanctuaire;
    private Joueur joueur;
    private Face face1;
    private Face face2;
    private Face face3;
    private Face face4;
    private ListeAchat listeAchat;

    /**
     * On initialise chacun de nos tests avec cette methode
     */
    @BeforeEach
    void setBefore()
    {
        joueur = new Joueur("joeur");
        face1 = new FaceOr(3,2);
        face2 = new FaceLune(2,5);
        face3 = new FaceSoleil(3,8);
        face4 = new FaceVictoire(10,10);
        sanctuaire = new Sanctuaire(face1,face2,face3,face4);
    }

    /**
     * Test de notre methode remove
     */
    @Test
    void remove()
    {   joueur.addOr(9);
        assertEquals(joueur.getOr(),9);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        assertEquals(3,listeAchat.getAvailable().size());
        sanctuaire.remove(face3);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        assertEquals(2,listeAchat.getAvailable().size());
    }
}