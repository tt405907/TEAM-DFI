package de;

/**
 * Contient les instances des différentes Faces, permettant de les comparer avec ==.
 */
public final class Faces {
	private Faces() {}
	
	//Faces de base
	public static final Face OR_1 = new FaceOr(1,0);
	public static final Face LUNE_1 = new FaceLune(1,2);
	public static final Face SOLEIL_1 = new FaceSoleil(1,3);
	public static final Face VICTOIRE_2 = new FaceVictoire(2,0);
	
	//Faces utilisées par les faces composites mais non obtainable
	public static final Face OR_2 = new FaceOr(2,0);
	public static final Face VICTOIRE_1 = new FaceVictoire(1,0);
	
	//2 Or
	public static final Face OR_3 = new FaceOr(3,2);
	
	//3 Or
	public static final Face OR_4 = new FaceOr(4,3);
	
	//4 Or
	public static final Face OR_6 = new FaceOr(6,4);
	public static final Face OR_2_LUNE_1 = new FaceMultiple(4, OR_2, LUNE_1);
	public static final Face SOLEIL_1_VICTOIRE_1 = new FaceMultiple(4, SOLEIL_1, VICTOIRE_1);
	public static final Face OR_1_OU_LUNE_1_OU_SOLEIL_1 = new FaceSelection(4, OR_1, LUNE_1, SOLEIL_1);
	
	//5 Or
	public static final Face OR_3_OU_VICTOIRE_2 = new FaceSelection(5, OR_3, VICTOIRE_2);
	
	//6 Or
	public static final Face LUNE_2 = new FaceLune(2,6);
	
	//8 Or
	public static final Face SOLEIL_2 = new FaceSoleil(2,8);
	public static final Face VICTOIRE_3 = new FaceVictoire(3,8);
	
	//12 Or
	public static final Face VICTOIRE_4 = new FaceVictoire(4,12);
	public static final Face LUNE_2_VICTOIRE_2 = new FaceMultiple(12, LUNE_2, VICTOIRE_2);
	public static final Face OR_2_OU_LUNE_2_OU_SOLEIL_2 = new FaceSelection(12, OR_2, LUNE_2, SOLEIL_2);
	public static final Face OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 = new FaceMultiple(12, OR_1, LUNE_1, SOLEIL_1, VICTOIRE_1);
	
	//Faces spéciales
	public static final Face X3 = new FaceX3();
}
