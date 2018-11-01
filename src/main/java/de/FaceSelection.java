package de;

import joueur.Joueur;

public class FaceSelection extends Face {

    private Face[] allFace;
    public FaceSelection(int prix, Face...faces)
    {
        super(prix);
        allFace = faces;
    }

    /**
     * Demande au robot quel Face il veut choisir et applique la Face à l'indice que le robot à donne
     * @param J Le joueur sur lequel nous apppliquons la Face choisit pas le joueur
     */
    public void appliquer(Joueur J)
    {
        allFace[J.choixFace(allFace)].appliquer(J);
    }

}