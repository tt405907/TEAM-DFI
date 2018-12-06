package joueur;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cartes.UnMarteau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.Carte;
import de.De;
import de.Face;
import de.Faces;
import plateau.Plateau;
import sanctuaire.ListeAchat;
import sanctuaire.Sanctuaire;

public class BotVictoireTest {
	private Joueur joueur;
	private ListeAchat listeAchat;
	private Face face1;
    private Face face2;
    private Face face3;
    private Face face4;
    private Face face5;
    private Sanctuaire sanctuaire;
    private De de1;
	private De de2;
	private UnMarteau marteau;
	
	/**
	  * On initialise chacun de nos tests avec cette methode
	  */
	@BeforeEach
	void setBefore()
	{
		joueur = new BotVictoire("joeur");
		face1 = Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1;
        face2 = Faces.LUNE_2_VICTOIRE_2;
        face3 = Faces.VICTOIRE_3;
        face4 = Faces.SOLEIL_2;
        face5 = Faces.LUNE_1;
        de1=joueur.getDe1();
		de2=joueur.getDe2();
        sanctuaire = new Sanctuaire(face1,face2,face3,face4);
        listeAchat= sanctuaire.getAchatsPossible(joueur);    
	}

	@Test
    void choixFace()
    {
		int i1;
		//notre bot choisit-il bien toujours la face rapportant le plus de points de victoire ?
		i1=joueur.choixFace(face1,face2,face3,face4,face5);
		assertEquals(i1,2);
		//si les faces ont le même nombre de victoire, il choisit bien une face, arbitairement la première :
		i1=joueur.choixFace(face4,face5);
		assertEquals(i1,0);
    }
	@Test
	void forge()
	{
		assertEquals(de1.getFace(0),Faces.OR_1);
		assertEquals(de2.getFace(5),Faces.VICTOIRE_2);
		joueur.forge(face3);
		de1=joueur.getDe1();
		assertEquals(de1.getFace(0),face3);
		joueur.forge(face1);
		de2=joueur.getDe2();
		assertEquals(de2.getFace(0),face1);
		//nos dés ont tous les deux un total de 3 points de victoire, le bot devrait forger la nouvelle face sur le second dé (arbitrairement)
		joueur.forge(face5);
		de2=joueur.getDe2();
		assertEquals(de2.getFace(1),face5);
	}
	
	@Test
	void faireAchatsFace()
	{
		/**
		 * vérifions que le bot n'achète pas la face la plus chère (face2 qui vaut 12 or mais rapporte seulemetnt 2 PdV)
		 * mais qu'il achète bien la face à plus grand nombre de point de victoire possible (face3 à 9 or qui donne 3PdV) 
		 */
		
		joueur.addOr(12);
        assertEquals(joueur.getOr(),12);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        joueur.faireAchatsFace(listeAchat);
        de1=joueur.getDe1();
        assertEquals(de1.getFace(0),face3);
        
        /**
		 * vérifions que si le bot ne peut pas acheter de faces 
		 * augmentant les PdV de ses faces, alors il garde son or
		 * (ici il peut acheter face5 à une lune mais ça ne l'intéresse pas...)
		 */
        
        joueur.addOr(-2);
        assertEquals(joueur.getOr(),2);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        joueur.faireAchatsFace(listeAchat);
        de1=joueur.getDe1();
        de2=joueur.getDe2();
        assertEquals(de1.getFace(1),Faces.OR_1);
        assertEquals(de2.getFace(0),Faces.OR_1);
	}


	@Test
	void tourSanctuaire()
	{
		// Nous vérifions si il a assez de Ressources pour acheter les plus basses cartes qui donnent des points de victoires (false) si doit acheter une carte
		joueur.addLune(5);
		assertEquals(joueur.tourSanctuaire(),false);
		joueur.addSoleil(5);
		assertEquals(joueur.tourSanctuaire(),false);
		// Nous vérifions aussi que dans le cas ou il ne peut acheter une carte il regarde si il peut prendre une face qui donne des points de victoires (true)
		joueur.addSoleil(-5);
		joueur.addLune(-5);
		joueur.addOr(5);
		assertEquals(joueur.tourSanctuaire(),true);
		// Dans le pire des cas il doit comme même faire un choix donc on lui dit d'aller à exploit (false)
		joueur.addOr(-5);
		assertEquals(joueur.tourSanctuaire(),false);
	}

	@Test
	void faireTourSupplementaire()
	{
		// Renvoie un boolean si il peut acheter une face avec l'or nécessaire
		joueur.addOr(5);
		assertEquals(joueur.faireTourSupplementaire(),true);
		// Renvoie un boolean si il peut faire un tour supp avec les soleils necessaire pour acheter la plus basse carte qui donne des points de victoires
		joueur.addOr(-5);
		// Renvoie le boolean d'un joueur qui ne dispose pas d'assez d'Or pour acheter ni une carte qui donne des points de victoires
		assertEquals(joueur.faireTourSupplementaire(),false);
		joueur.addSoleil(5);
		assertEquals(joueur.faireTourSupplementaire(),true);
		// // Renvoie un boolean si il peut faire un tour supp avec les Lunes necessaire pour acheter la plus basse carte qui donne des points de victoires
		joueur.addLune(3);
		assertEquals(joueur.faireTourSupplementaire(),true);

	}

	@Test
	void faireAchatCartes() {
		// Nous allons vérifier que la méthode renvoie null si le joueur ne peut acheter aucune cartes
		Carte carte1 = new Carte("Jadore", 3, 3, 5);
		Carte carte2 = new Carte("Géant", 5, 0, 10);
		Carte carte3 = new Carte("Face", 5, 2, 8);
		Plateau plateau = new Plateau(carte1, carte2, carte3);
		assertEquals(joueur.faireAchatCartes(plateau.getCartesAchetables(joueur)), null);
		// Vérifions qu'il va bien me donner la carte qui me donne le plus de points de victoires
		joueur.addLune(5);
		assertEquals(joueur.faireAchatCartes(plateau.getCartesAchetables(joueur)), carte2);
	}
	@Test
	void changeOrEnMarteau()
	{
		// On vérifie que le bot à 0 or
		assertEquals(0,joueur.getOr());
		// Dans se cas là le bot devai ne pas le mettre dans son marteau il va retourner 0
		assertEquals(0,joueur.changeOrEnMarteau(3));
		// On va mettre notre joueur avec 9 Or et donc la valeur qu'on va mettre dans changeOrEnMarteau devrait etre retourné sur notre prochain test
		joueur.addOr(9);
		assertEquals(3,joueur.changeOrEnMarteau(3));

	}
	@Test
	void choixFaceNegatif()
	{
		// il va donc choisir la face avec le moins le moins de points de victoire
		int indice = joueur.choixFaceNegatif(face4,face2,face3,face1,face5);
		// il devrait donc nous revoyer arbitrairement la face2 qui a 0 point de victoires
		assertEquals(0,indice);
	}



}
