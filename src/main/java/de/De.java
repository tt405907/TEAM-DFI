package de;

import java.util.Arrays;
import java.util.Random;
import joueur.Joueur;

/**
 * Classe permettant de simuler un De pour un joueur , un De a 6 faces
 */
public class De
{
    //On stock les faces de notre De
    private Face[] allFaces;
    // Permet de generer un random pour notre lancer
    private Random rand;

    // Nos Des initaux
    public static final Face[] de1 = {new FaceOr(1,0),new FaceOr(1,0),new FaceOr(1,0),new FaceOr(1,0),new FaceOr(1,0),new FaceSoleil(1,0)};
    public static final Face[] de2 = {new FaceOr(1,0),new FaceOr(1,0),new FaceOr(1,0),new FaceOr(1,0),new FaceLune(1,0),new FaceVictoire(2,0)};

    /**
     * Notre constructeur pour initialiser nos 2 Des
     * @param allFaces Notre De initial , de1 pour notre premier De  et de2 pour notre deuxieme De
     */
    public De (Face[] allFaces)
    {
        // On init nos Des
        this.allFaces = Arrays.copyOf(allFaces,6);
        rand = new Random();
    }

    /**
     * Permettant la prise d'une face d'un de a l'aide d'un int (indice)
     * @param indice l'indice de la face du De que l'on veut
     * @return Retourne donne la face a l'indice mentionne
     * */
    public Face getFace(int indice)
    {
        return this.allFaces[indice];
    }

    /**
     * Nous appliquons une Face de manière aléatoire sur les deux De
     * @param j Le propietaire du De
     */
    public void appliquerDe(Joueur j)
    {
        int temp = rand.nextInt(6);
        this.getFace(temp).appliquer(j);
    }

}
