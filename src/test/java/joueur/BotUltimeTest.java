package joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cartes.Carte;
import cartes.CarteRenfort;
import cartes.Cartes;
import cartes.UnMarteau;
import sanctuaire.*;
import de.De;
import de.Face;
import de.Faces;
import partie.*;

class BotUltimeTest {

	private Joueur joueur;
	private BotUltime bot;
	private Face face1;
	private Face face2;
	private Face face3;
	private Face face4;
	private Face face5;
	private Face face6;
	private Face face7;
	private Face face8;
	private De de1;
	private De de2;
	private ListeAchat liste;
	private Sanctuaire sanctuaire;
	@SuppressWarnings("unused")
	private PartieDeTest partie;
	private ArrayList<Carte> cartes;
	private ArrayList<CarteRenfort> cartesRenfort;

	class PartieDeTest extends Partie {
		public PartieDeTest(Joueur... joueurs) {
			super(joueurs);
		}

		@Override
		public Sanctuaire getSanctuaire() {
			return sanctuaire;
		}
	};

	/**
	 * On initialise chacun de nos tests avec cette methode
	 */
	@BeforeEach
	void setBefore() {
		bot = new BotUltime();
		joueur = new Joueur("joeur").setBot(bot);
		face1 = Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1;
		face2 = Faces.LUNE_2_VICTOIRE_2;
		face3 = Faces.OR_3;
		face4 = Faces.SOLEIL_2;
		face5 = Faces.LUNE_1;
		face6 = Faces.VICTOIRE_2;
		face7 = Faces.OR_6;
		face8 = Faces.OR_2_LUNE_1;
		de1 = joueur.getDe1();
		de2 = joueur.getDe2();
		cartes = new ArrayList<>();
		cartesRenfort = new ArrayList<>();
		partie = new PartieDeTest(joueur, new Joueur("joueur2"));
		sanctuaire = new Sanctuaire(face1, face2, face3, face4, face5, face6, face7, face8);
	}

	@Test
	void choixFace() {
		int i1;
		// notre bot choisit-il bien toujours la face rapportant le plus d'or ?
		i1 = joueur.choixFace(face1, face2, face3, face4, face5);
		assertEquals(2, i1);
		// si les faces ont le même nombre d'or, il choisit bien une face,
		// arbitairement la première :
		i1 = joueur.choixFace(face4, face5);
		assertEquals(0, i1);
	}

	@Test
	void choixFaceNegatif() {
		// si on n'a pas de points de victoires et que la face rapporte uniquement des
		// Pdv
		joueur.addOr(1);
		int indice1 = joueur.choixFaceNegatif(face3, face6);
		// il devrait nous renvoyer face6 car son impact est nul, alors que face3 réduit
		// l'or de notre joueur.
		assertEquals(1, indice1);
		// Si on n'a pas de Soleil et que la face rapporte uniquement du Soleil
		joueur.addOr(-1);
		joueur.addVictoire(1);
		int indice2 = joueur.choixFaceNegatif(face6, face4);
		assertEquals(1, indice2);
		// Si on n'a pas d'Or et que la face rapporte uniquement de l'Or
		int indice3 = joueur.choixFaceNegatif(face6, face3);
		assertEquals(1, indice3);
		// Si on n'a pas de Lune et que la face rapporte uniquement de la Lune
		int indice4 = joueur.choixFaceNegatif(face6, face5);
		assertEquals(1, indice4);
		// On choisit alors la face qui nous fait perdre le moins d'Or et de valeur
		// accesoireOr parmi celles rapportant de l'or
		joueur.addLune(2);
		joueur.addOr(4);
		joueur.addSoleil(6);
		int indice5 = joueur.choixFaceNegatif(face1, face3, face4, face7, face8);
		assertEquals(4, indice5);
	}

	@Test
	void forge() {

		assertEquals(Faces.OR_1, de2.getFace(0));
		assertEquals(Faces.OR_1, de1.getFace(0));
		// Forge un X3
		joueur.forge(Faces.X3);
		assertEquals(de2.getFace(0), Faces.X3);
		de2.forge(Faces.OR_1, 0);
		// Forge miroir
		joueur.forge(Faces.MIROIR);
		assertEquals(de2.getFace(5), Faces.MIROIR);
		de2.forge(Faces.VICTOIRE_2, 5);
		// si on forge de l'or ce sera sur le dé1 jusqu'à dépasser VALEUR_OR_MINIMUM_DE1
		// qui vaut 12 ici
		joueur.forge(Faces.OR_4);
		assertEquals(de1.getFace(0), Faces.OR_4);
		joueur.forge(Faces.OR_4);
		joueur.forge(Faces.OR_4);
		assertEquals(de1.getFace(2), Faces.OR_4);
		// on a assez d'or sur le dé 1, on passe au second dé.
		joueur.forge(Faces.OR_4);
		assertEquals(de2.getFace(0), Faces.OR_4);
		de2.forge(Faces.OR_1, 0);
		de1.forge(Faces.OR_1, 0);
		de1.forge(Faces.OR_1, 1);
		de1.forge(Faces.OR_1, 2);
		// or 1 lune 1 soleil 1 victoire 1
		joueur.forge(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
		assertEquals(de1.getFace(5), Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
		de1.forge(Faces.SOLEIL_1, 5);
		// test de lune 1 et 2
		joueur.forge(Faces.LUNE_1);
		assertEquals(de2.getFace(0), Faces.LUNE_1);
		joueur.forge(Faces.LUNE_2);
		assertEquals(de2.getFace(1), Faces.LUNE_2);
		de2.forge(Faces.OR_1, 0);
		de2.forge(Faces.OR_1, 1);
		// test de victoire 4
		joueur.forge(Faces.VICTOIRE_4);
		assertEquals(de1.getFace(0), Faces.VICTOIRE_4);
		de1.forge(Faces.OR_1, 0);
		// test de OR_2_OU_LUNE_2_OU_SOLEIL_2
		joueur.forge(Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
		assertEquals(de2.getFace(0), Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
		de2.forge(Faces.OR_1, 0);
		// test de OR_3_OU_VICTOIRE_2
		joueur.forge(Faces.OR_3_OU_VICTOIRE_2);
		assertEquals(de1.getFace(0), Faces.OR_3_OU_VICTOIRE_2);
		de1.forge(Faces.OR_1, 0);
		// test de OR_2_LUNE_1
		joueur.forge(Faces.OR_2_LUNE_1);
		assertEquals(de1.getFace(0), Faces.OR_2_LUNE_1);
		de1.forge(Faces.OR_1, 0);
		// test de LUNE_2_VICTOIRE_2
		joueur.forge(Faces.LUNE_2_VICTOIRE_2);
		assertEquals(de1.getFace(0), Faces.LUNE_2_VICTOIRE_2);
		de1.forge(Faces.OR_1, 0);
		// test de SOLEIL_2
		joueur.forge(Faces.SOLEIL_2);
		assertEquals(de2.getFace(0), Faces.SOLEIL_2);
		de2.forge(Faces.OR_1, 0);
		// test de SOLEIL_1_VICTOIRE_1
		joueur.forge(Faces.SOLEIL_1_VICTOIRE_1);
		assertEquals(de2.getFace(5), Faces.SOLEIL_1_VICTOIRE_1);
		de2.forge(Faces.VICTOIRE_2, 5);
	}

	@Test
	void faireAchatsFace() {
		// vérifions que le bot achète en priorité le 6 or.
		joueur.addOr(12);
		liste = sanctuaire.getAchatsPossible(joueur);
		joueur.faireAchatsFace(liste);
		assertEquals(de1.getFace(0), Faces.OR_6);
		assertEquals(de1.getFace(1), Faces.OR_3);
		assertEquals(de1.getFace(2), Faces.OR_2_LUNE_1);
		assertEquals(de2.getFace(0), Faces.LUNE_1);
		// vérifions que le bot n'achète plus
		assertEquals(de1.getFace(3), Faces.OR_1);
		assertEquals(de2.getFace(1), Faces.OR_1);
		// achète-til maintenant du soleil pour son dé 2 ?
		joueur.addOr(12);
		sanctuaire = new Sanctuaire(Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2, Faces.SOLEIL_1, Faces.SOLEIL_1_VICTOIRE_1);
		liste = sanctuaire.getAchatsPossible(joueur);
		joueur.faireAchatsFace(liste);
		assertEquals(de2.getFace(1), Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);

		// plus assez d'argent pour le reste normalement
		assertEquals(de2.getFace(2), Faces.OR_1);

		de2.forge(Faces.OR_1, 0);
		de2.forge(Faces.OR_1, 1);
		// achète-t-il maintenant de la lune pour son dé 1 ?
		joueur.addOr(12);
		sanctuaire = new Sanctuaire(Faces.LUNE_2, Faces.LUNE_2_VICTOIRE_2, Faces.LUNE_1);
		liste = sanctuaire.getAchatsPossible(joueur);
		joueur.faireAchatsFace(liste);
		assertEquals(de1.getFace(3), Faces.LUNE_2_VICTOIRE_2);
		assertEquals(de1.getFace(4), Faces.OR_1);
		// plus assez d'argent pour le reste normalement
		assertEquals(de2.getFace(0), Faces.OR_1);
		// achète-t-il maintenant de la lune pour son dé 2 ?
		joueur.addOr(12);
		sanctuaire = new Sanctuaire(Faces.LUNE_2, Faces.LUNE_1);
		liste = sanctuaire.getAchatsPossible(joueur);
		joueur.faireAchatsFace(liste);
		assertEquals(de2.getFace(0), Faces.LUNE_2);
		assertEquals(de2.getFace(1), Faces.LUNE_1);
	}

	@Test
	void tourSanctuaire() {
		// Première phase: on veut toujours acheter des faces en or si possible
		sanctuaire = new Sanctuaire(Faces.OR_4);
		joueur.addOr(2);
		assertEquals(joueur.tourSanctuaire(), false);
		joueur.addOr(1);
		assertEquals(joueur.tourSanctuaire(), true);
		de1.forge(Faces.OR_6, 0);
		de1.forge(Faces.OR_6, 1);
		// Deuxième phase : on veut avoir un marteau
		assertEquals(joueur.tourSanctuaire(), false);
		UnMarteau m = new UnMarteau(joueur);
		joueur.addMarteau(m);
		// Troisième phase : on veut avoir un coffre du forgeron
		joueur.addLune(1);
		assertEquals(joueur.tourSanctuaire(), false);
		joueur.setOrMax(16);
		// Quatrième phase : on veut ensuite le x3
		joueur.addLune(4);
		assertEquals(joueur.tourSanctuaire(), false);
		// Cinquième phase : on veut du soleil sur notre dé 2
		sanctuaire = new Sanctuaire(Faces.SOLEIL_1);
		joueur.addLune(-5);
		joueur.addOr(2);
		assertEquals(joueur.tourSanctuaire(), true);
		// Sixième phase : on veut de la lune sur notre dé 2
		sanctuaire = new Sanctuaire(Faces.LUNE_1);
		assertEquals(joueur.tourSanctuaire(), true);
		// Septième phase : Si on a un marteau en cours on va voir si on peut acheter
		// une carte intéressante
		joueur.addOr(-2);
		assertEquals(joueur.tourSanctuaire(), false);
	}

	@Test
	void faireTourSupplementaire() {
		// Première phase: on veut toujours acheter des faces en or si possible
		sanctuaire = new Sanctuaire(Faces.OR_6);
		joueur.addOr(4);
		assertEquals(joueur.faireTourSupplementaire(), true);
		de1.forge(Faces.OR_6, 0);
		de1.forge(Faces.OR_6, 1);

		// Deuxième phase : on veut avoir un marteau
		assertEquals(joueur.faireTourSupplementaire(), true);
		UnMarteau m = new UnMarteau(joueur);
		joueur.addMarteau(m);
		// Troisième phase : on veut avoir un coffre du forgeron
		joueur.addLune(1);
		assertEquals(joueur.faireTourSupplementaire(), true);
		joueur.setOrMax(16);
		// Quatrième phase : on veut ensuite le x3
		joueur.addLune(4);
		assertEquals(joueur.faireTourSupplementaire(), false);
		// Cinquième phase : on veut du soleil sur notre dé 2
		sanctuaire = new Sanctuaire(Faces.SOLEIL_1);
		joueur.addLune(-5);
		joueur.addOr(2);
		assertEquals(joueur.faireTourSupplementaire(), true);
		// Sixième phase : on veut de la lune sur notre dé 2
		sanctuaire = new Sanctuaire(Faces.LUNE_1);
		assertEquals(joueur.faireTourSupplementaire(), true);
		// Septième phase : Si on a un marteau en cours on va voir si on peut acheter
		// une carte intéressante
		joueur.addOr(-2);
		assertEquals(joueur.faireTourSupplementaire(), true);
	}

	@Test
	void faireAchatCartes() {
		// n'achète pas si il ne peut rien acheter
		assertEquals(joueur.faireAchatCartes(cartes), null);
		// notre bot privilégie toujours les hydres, puis les marteaux si il n'en a pas
		// puis le coffre la méduse le casque et les pinces.
		cartes.add(Cartes.HYDRE);
		cartes.add(Cartes.PINCE);
		cartes.add(Cartes.MEDUSE);
		cartes.add(Cartes.CASQUE);
		cartes.add(Cartes.MARTEAU);
		cartes.add(Cartes.COFFRE);
		cartes.add(Cartes.ANCIEN);
		cartes.add(Cartes.MINOTAURE);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.HYDRE);
		cartes.remove(Cartes.HYDRE);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.MARTEAU);
		cartes.remove(Cartes.MARTEAU);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.COFFRE);
		cartes.remove(Cartes.COFFRE);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.MEDUSE);
		cartes.remove(Cartes.MEDUSE);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.CASQUE);
		cartes.remove(Cartes.CASQUE);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.PINCE);
		cartes.remove(Cartes.PINCE);
		assertEquals(joueur.faireAchatCartes(cartes), Cartes.ANCIEN);
		cartes.remove(Cartes.ANCIEN);
		// il ne reste plus que le minotaure, mais on ne veut pas l'acheter
		assertEquals(joueur.faireAchatCartes(cartes), null);
	}

	@Test
	void choixFaveurMineure() {
		assertEquals(joueur.choixFaveurMineure(), de1);
	}

	@Test
	void choixRenfort() {
		cartesRenfort.add(Cartes.SABOTS);
		cartesRenfort.add(Cartes.AILES);
		cartesRenfort.add(Cartes.ANCIEN);
		joueur.addOr(3);
		assertEquals(joueur.choixRenfort(cartesRenfort), Cartes.SABOTS);
		cartesRenfort.remove(Cartes.SABOTS);
		assertEquals(joueur.choixRenfort(cartesRenfort), Cartes.AILES);
		cartesRenfort.remove(Cartes.AILES);
		assertEquals(joueur.choixRenfort(cartesRenfort), Cartes.ANCIEN);
		joueur.addOr(-3);
		assertEquals(joueur.choixRenfort(cartesRenfort), null);
	}

	@Test
	void changeOrEnMarteau() {
		assertEquals(joueur.changeOrEnMarteau(12), 4);
		de1.forge(Faces.OR_6, 0);
		de1.forge(Faces.OR_6, 1);

		assertEquals(joueur.changeOrEnMarteau(4), 4);
	}
}
