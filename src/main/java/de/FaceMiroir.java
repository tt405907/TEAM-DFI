package de;

import java.util.ArrayList;
import java.util.List;

import joueur.Joueur;

public class FaceMiroir extends Face{
	public FaceMiroir() {
		super(0);
	}
	
	public Face getProxiedFace(Joueur j) {
		Face[] faces = getFacesEnnemis(j);
		if (faces.length > 0) return faces[j.choixFace(faces)];
		//Si aucune face n'est copiable, elle n'aura pas d'effet
		else return this;
	}

	public void appliquer(Joueur j) {
		Face[] faces = getFacesEnnemis(j);
		if (faces.length > 0) faces[j.choixFace(faces)].appliquer(j);
	}
	public void appliquerX3(Joueur j) {
		Face[] faces = getFacesEnnemis(j);
		if (faces.length > 0) faces[j.choixFace(faces)].appliquerX3(j);
	}
	
	private Face[] getFacesEnnemis(Joueur j) {
		Joueur[] ennemis = j.getPartie().getEnnemis(j);
		List<Face> facesList = new ArrayList<>();
		
		for (int i = 0; i < ennemis.length; i++) {
			Joueur e = ennemis[i];
			Face de1 = e.getDe1().getLastFace();
			Face de2 = e.getDe2().getLastFace();
			//On ne copie pas de Mirroir
			if (de1 != this) facesList.add(de1);
			if (de2 != this) facesList.add(de2);
		}
		
		return facesList.toArray(new Face[0]);
	}

	@Override
	public String toString() {
		return "Miroir";
	}

}
