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
    //Dernière face lancée, pour l'affichage
    private int lastIndex = 0;
    
    private boolean printing = false;

	public void setPrinting(boolean printing) {
		this.printing = printing;
	}

    // Nos Des initaux
    public static final Face[] de1 = {Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.SOLEIL_1};
    public static final Face[] de2 = {Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.OR_1, Faces.LUNE_1, Faces.VICTOIRE_2};

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
     * Donne la dernière face que ce dé à appliqué.
     * @return la dernière Face appliquée par ce dé
     */
    public Face getLastFace()
    {
    	return getFace(lastIndex);
    }

    /**
     * Nous appliquons une Face de manière aléatoire sur les deux De
     * @param j Le propietaire du De
     */
    public void appliquerDe(Joueur j)
    {
        int temp = rand.nextInt(6);
        this.getFace(temp).appliquer(j);
        lastIndex = temp;
    }
    
    /**
     * Remplace la Face à l'indice indice par face.
     * @param face Face à poser
     * @param indice Emplacement où face sera posée
     */
    public void forge(Face face, int indice)
    {
    	Face old = allFaces[indice];
    	allFaces[indice] = face;
    	if (printing) 
    	{
    		System.out.println("Forge: " + old + " -> " + face + " (pour " + face.getPrix() + " or)");
    	}
    }
    
    @Override
    public String toString()
    {
    	return Arrays.toString(allFaces);
    }
    
    
}
