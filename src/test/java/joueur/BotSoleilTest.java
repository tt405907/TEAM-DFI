package joueur;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.Carte;
import cartes.CarteRenfort;
import cartes.Cartes;
import de.De;
import de.Face;
import de.Faces;
import plateau.Plateau;
import sanctuaire.ListeAchat;
import sanctuaire.Sanctuaire;

public class BotSoleilTest {
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
		joueur = new Joueur("joeur").setBot(new BotSoleil());
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
		//notre bot choisit-il bien toujours la face rapportant le plus de soleil ?
		i1=joueur.choixFace(face1,face2,face3,face4,face5);
		assertEquals(3,i1);
		//si les faces ont le même nombre de soleil, il choisit bien une face, arbitairement la première :
		i1=joueur.choixFace(face4,face5);
		assertEquals(0,i1);
    }
	@Test
	void forge()
	{
		assertEquals(Faces.OR_1,de2.getFace(0));
		assertEquals(Faces.OR_1,de1.getFace(0));
		//Forge un 2 soleil, va sur le dé 2
		joueur.forge(face4);
		assertEquals(face4,de2.getFace(0));
		joueur.forge(face1);
		assertEquals(face1,de1.getFace(0));
		//nos dés ont tous les deux un total de 2 soleils, le bot devrait forger la nouvelle face sur le second dé (arbitrairement)
		joueur.forge(face5);
		assertEquals(face5,de2.getFace(1));
	}
	
	@Test
	void faireAchatsFace()
	{
		//Il va acheter la carte soleil 2 et la mettre sur le dé avec
		//le moins de soleil (de2)
		joueur.addOr(12);
        assertEquals(joueur.getOr(),12);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        joueur.faireAchatsFace(listeAchat);
        assertEquals(face4,de2.getFace(0));
        
        //Il n'y a plus de face de valeur, le bot n'achète rien
        joueur.addOr(-2);
        assertEquals(joueur.getOr(),2);
        listeAchat = sanctuaire.getAchatsPossible(joueur);
        joueur.faireAchatsFace(listeAchat);
        assertEquals(joueur.getOr(),2);
	}


	@Test
	void tourSanctuaire()
	{
		// Nous vérifions si il a assez de Ressources pour acheter les plus basses cartes qui donnent des points de victoires (false) si doit acheter une carte
		joueur.addLune(5);
		assertEquals(joueur.tourSanctuaire(),false);
		joueur.addSoleil(5);
		assertEquals(joueur.tourSanctuaire(),false);
		// Nous vérifions aussi que dans le cas ou il ne peut acheter une carte il regarde si il peut prendre une face qui donne des soleils (true)
		joueur.addSoleil(-5);
		joueur.addLune(-5);
		joueur.addOr(8);
		assertEquals(joueur.tourSanctuaire(),true);
		// Dans le pire des cas il doit comme même faire un choix donc on lui dit d'aller à exploit (false)
		joueur.addOr(-8);
		assertEquals(joueur.tourSanctuaire(),false);
	}

	@Test
	void faireTourSupplementaire()
	{
		// Tour supplémentaire si 8 or ou 3 lune ou 5 soleil
		assertEquals(joueur.faireTourSupplementaire(),false);
		joueur.addOr(8);
		assertEquals(joueur.faireTourSupplementaire(),true);
		joueur.addOr(-8);
		assertEquals(joueur.faireTourSupplementaire(),false);
		joueur.addSoleil(5);
		assertEquals(joueur.faireTourSupplementaire(),true);
		joueur.addSoleil(-5);
		assertEquals(joueur.faireTourSupplementaire(),false);
		joueur.addLune(3);
		assertEquals(joueur.faireTourSupplementaire(),true);

	}

	@Test
	void faireAchatCartes() {
		// Nous allons vérifier que la méthode renvoie null si le joueur ne peut acheter aucune carte
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
		// On vérifie que le bot a 0 or
		assertEquals(0,joueur.getOr());
		// Dans se cas-là le bot ne devrait pas le mettre dans son marteau il va retourner 0
		assertEquals(0,joueur.changeOrEnMarteau(3));
		// On va mettre notre joueur avec 9 Or et donc la valeur qu'on va mettre dans changeOrEnMarteau devrait etre retourné sur notre prochain test
		joueur.addOr(9);
		assertEquals(3,joueur.changeOrEnMarteau(3));

	}
	@Test
	void choixFaceNegatif()
	{
		// il va donc choisir la face avec le moins le moins de points de soleil
		int indice = joueur.choixFaceNegatif(face4,face2,face3,face1,face5);
		// il devrait donc nous revoyer arbitrairement la face2 qui vaut 0 soleil
		assertEquals(1,indice);
	}
	@Test
	void choixFaveurMineure()
	{
		for(int indice = 0 ; indice < 5; indice++)
		{
			de1.forge(Faces.SOLEIL_1,indice);
			de2.forge(Faces.SOLEIL_2,indice);
		}
		// Le Dé 2 va donc avoir plus de valeur en points de victoire que le Dé 1 donc il devrait nous renvoyer le de2
		assertEquals(de2,joueur.choixFaveurMineure());
	}

	@Test
	void choixRenfort()
	{
		CarteRenfort carteSabots = Cartes.SABOTS;
		CarteRenfort carteAile = Cartes.AILES;
		CarteRenfort carteAncien = Cartes.ANCIEN;
		List<CarteRenfort> aymeriqueLaMachineATuer = new ArrayList<CarteRenfort>();
		aymeriqueLaMachineATuer.add(carteAile);
		aymeriqueLaMachineATuer.add(carteSabots);
		aymeriqueLaMachineATuer.add(carteAncien);
		// Notre bot va donc choisir dans l'ordre de priorité SABOTS AILES ANCIEN
		// Dons dans cette list il devrait donc nous retourner SABOTS
		assertEquals(Cartes.SABOTS,joueur.choixRenfort(aymeriqueLaMachineATuer));
		// Si la liste est vide il devra donc nous return null
		assertEquals(null,joueur.choixRenfort(new ArrayList<>()));
}



}
