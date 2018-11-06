package joueur;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.*;
import sanctuaire.*;

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
	void faireAchats()
	{
		/**
		 * vérifions que le bot n'achète pas la face la plus chère (face2 qui vaut 12 or mais rapporte seulemetnt 2 PdV)
		 * mais qu'il achète bien la face à plus grand nombre de point de victoire possible (face3 à 9 or qui donne 3PdV) 
		 */
		
		joueur.addOr(12);
        assertEquals(joueur.getOr(),12);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        joueur.faireAchats(listeAchat);
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
        joueur.faireAchats(listeAchat);
        de1=joueur.getDe1();
        de2=joueur.getDe2();
        assertEquals(de1.getFace(1),Faces.OR_1);
        assertEquals(de2.getFace(0),Faces.OR_1);
	}

}
