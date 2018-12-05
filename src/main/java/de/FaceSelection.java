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
     * @param J Le joueur sur lequel nous appliquons la Face choisie par le joueur
     */
    public void appliquer(Joueur J)
    {
        allFace[J.choixFace(allFace)].appliquer(J);
    }
    
    public void appliquerX3(Joueur j) {
        allFace[j.choixFace(allFace)].appliquerX3(j);
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(allFace[0].toString());
		for (int i = 1; i < allFace.length; i++)
		{
			sb.append(" ou ");
			sb.append(allFace[i]);
		}
		
		return sb.toString();
	}
	
	@Override
	public void appliquerNegatif(Joueur J)
    {
        allFace[J.choixFaceNegatif(allFace)].appliquerNegatif(J);
    }
    
	@Override
    public void appliquerNegatifX3(Joueur j) {
        allFace[j.choixFaceNegatif(allFace)].appliquerNegatifX3(j);
    }

}