package sanctuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.*;
import joueur.*;

public class ListeAchatTest {
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
        listeAchat= sanctuaire.getAchatsPossible(joueur);
    }

    @Test
    void getAvailable()
    {
        joueur.addOr(5);
        assertEquals(joueur.getOr(),5);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        // avec 5 or notre joueur devrait pouvoir acheter face2 mais pas face 3
        assertTrue(listeAchat.getAvailable().contains(face2));
        assertTrue(!listeAchat.getAvailable().contains(face3));
    }
    @Test
    void isEmpty()
    {
        assertEquals(joueur.getOr(),0);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        assertTrue(listeAchat.isEmpty());
        joueur.addOr(2);
        assertEquals(joueur.getOr(),2);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        assertTrue(!listeAchat.isEmpty());
    }
    @Test
    void acheter()
    {
        Joueur joueur2 = new Joueur("joeur2"); // un joueur "sonde" pour tester les sanctuaires
        //cas où l'acheteur veut acheter une face trop chère pour lui

        joueur.addOr(9);
        assertEquals(joueur.getOr(),9);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        listeAchat.acheter(face4); // ici on achète une face normalement non présente dans listeAchat car trop chère, cependant la fonction devrait ne rien faire et ne pas créer d'erreur
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        assertEquals(listeAchat.getAvailable().size(),3); // la liste ne devrait pas avoir perdu d'élément
        assertEquals(joueur.getOr(),9); // le joueur ne devrait pas avoir perdu d'or
        /**on veut savoir si le sanctuaire n'a pas perdu de face pour cela
         * on le teste avec un joueur à 12 or
         * qui pourrait donc virtuellement tout acheter.
         */
        joueur2.addOr(12);
        assertEquals(joueur2.getOr(),12);
        listeAchat = sanctuaire.getAchatsPossible(joueur2);
        assertEquals(listeAchat.getAvailable().size(),4);


        //cas "normal" où l'achat se déroule comme prévu

        assertEquals(joueur.getOr(),9);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        listeAchat.acheter(face3);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        assertTrue(listeAchat.isEmpty());
        assertEquals(joueur.getOr(),1);
        /**même démarche que plus haut,
         * on sonde le sanctuaire avec notre joueur à 12 or
         */
        listeAchat = sanctuaire.getAchatsPossible(joueur2);
        assertEquals(listeAchat.getAvailable().size(),3);
    }
}