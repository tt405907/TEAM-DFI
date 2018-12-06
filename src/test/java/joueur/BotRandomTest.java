package joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.Carte;
import de.De;
import de.Face;
import de.Faces;
import sanctuaire.ListeAchat;
import sanctuaire.Sanctuaire;

public class BotRandomTest {
    private Joueur joueur;
    private ListeAchat listeAchat;
    private Face face1;
    private Face face2;
    private Face face3;
    private Face face4;
    private Face face5;
    private Face face6;
    private Face face7;


    @BeforeEach
    void setBefore()
    {
        joueur = new BotRandom("Biloute");
        face1 = Faces.LUNE_2;
        face2 = Faces.LUNE_2_VICTOIRE_2;
        face3 = Faces.VICTOIRE_3;
        face4 = Faces.SOLEIL_2;
        face5 = Faces.OR_3_OU_VICTOIRE_2;
        face6 = Faces.OR_1_OU_LUNE_1_OU_SOLEIL_1;
        face7 = Faces.OR_4;
    }
    @Test
    void choixFace()
    {
        int indice;
        boolean test = false;
        // Le bot va nous donnner de manière aléatoire l'indice d'une face donné en paramètre
        indice = joueur.choixFace(face1,face2,face3,face4,face5,face6,face7);

        // Je vais assigner le boolean true à test si l'indice est bien entre [0,7[ et false si différent
        if ( indice >= 0 && indice < 7)
        {
            test = true;
        }
        assertEquals(test,true);
    }

    @Test
    void forge()
    {
        // Pour tester forge je vais montrer qu'après utilisation de cette méthode un des deux dé à bien recu la face en question à  une position aléatoire
        // Nos dés initiaux
        //de1 = {Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.SOLEIL_1};
        //de2 = {Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.LUNE_1, Faces.VICTOIRE_2};

        boolean test = false;
        // Une Face qui n'est pas sur nos dés initiaux
        Face faceTest = Faces.SOLEIL_1_VICTOIRE_1;
        joueur.forge(faceTest);

        //Je vais donc assigner le boolean true si la face est bien sur un des dés
        for (int i = 0 ; i < 6 ;i++ )
        {
            if(joueur.getDe1().getFace(i) == faceTest)
            {
                test = true;
            }
            if(joueur.getDe2().getFace(i) == faceTest)
            {
                test = true;
            }
        }
        assertEquals(test,true);
    }

    @Test
    void faireAchatsFace()
    {
        // Nos Dés originaux
        De de1 = new De(De.de1);
        De de2 = new De(De.de2);
        // Initialisation de notre sanctuaire et de notre liste d'achat eventuel
        Sanctuaire sanctuaire = new Sanctuaire(face1,face2,face3,face4,face5,face6,face7);
        listeAchat = sanctuaire.getAchatsPossible(joueur);

        // On va mettre son stock d'Or au max pour qu'il puisse faire des achats
        joueur.addOr(12);

        // Le bot va donc faire des achats de Face jusqu'a quand il ne dispose de plus d'assez d'Or pour acheter une Face
        // Pour vérifier cette méthode je vais check d'abord qu'il ne pouvait plus acheter d'Or avec ce qu'il lui restait
        // et voir que au moins un des 2 dés ont été changés
        joueur.faireAchatsFace(listeAchat);
        boolean test = false;

        // Je re fais une listeAchat pour check qu'avec l'Or restant on ne puisse pas acheter une Face dans le sanctuaire
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        if (!listeAchat.isEmpty())
        {
            test = true;
        }

        // On test que un de nos deux dés ont changés
        for (int i = 0 ; i < 6 ;i++ )
        {
            if(joueur.getDe1().getFace(i) != de1.getFace(i))
            {
                test = true;
            }
            if(joueur.getDe2().getFace(i) != de2.getFace(i))
            {
                test = true;
            }
        }
        assertEquals(test,true);

    }

    @Test
    void faireAchatCartes()
    {
        // Nous vérifions si y'a plus de cartes dans la liste il nous retourne bien null
        Carte carte1 = new Carte("Jadore",3,3,5);
        Carte carte2 = new Carte("Géant",5,0,10);
        Carte carte3 = new Carte("Face",5,2,8);
        List<Carte> listeCarte = Arrays.asList();
        assertEquals(joueur.faireAchatCartes(listeCarte),null);

        // Testons qu'il nous renvoit bien une des cartes aléatoires de la liste
        listeCarte = Arrays.asList(carte1,carte2,carte3);
        boolean test = false;
        Carte carteDonne = joueur.faireAchatCartes(listeCarte);
        if ( carteDonne == carte1 || carteDonne == carte2 || carteDonne == carte3)
        {
            test = true;
        }
        assertEquals(test,true);

    }
}