package de;
import joueur.Joueur;

public class FaceMultiple extends Face {

    private Face[] allFace;
    public FaceMultiple(int prix, Face...faces)
    {
        super(prix);
        allFace = faces;
    }

    /**
     * Application de toutes les Faces de FaceMultiple
     * @param J Le joueur sur lequel nous apppliquons les Faces
     */
    public void appliquer(Joueur J)
    {
        for(Face face: allFace)
        {
            face.appliquer(J);
        }
    }

}
