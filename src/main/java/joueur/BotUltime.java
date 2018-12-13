package joueur;

import cartes.Carte;
import cartes.CarteRenfort;
import cartes.Cartes;
import de.De;
import de.Face;
import de.Faces;
import sanctuaire.ListeAchat;

import java.util.List;

import static joueur.BotUtils.*;

public class BotUltime extends Bot {

    /**
     * L'Ordre d'importance
     * 1 . Dé 1 valeur Or > VALEUR_OR_MINIMUM_DE1
     * 2 . Dé 1 valeur Lune > VALEUR_LUNE_MINIMUM_DE1
     * 3 . Dé 2 valeur Soleil > VALEUR_SOLEIL_MINIMUM_DE2
     * 4 . Dé 2 valeur Lune > 1
     * 5 . Dé 1 Echange (Face Or_1 par Face Victoire_3 & 4) ou (Si plus de Marteau échange Face Or min par Face Victoire dispo Max)
     * 6 . Dé 2 Echange Face Or-1 par Face OR_2_OU_LUNE_2_OU_SOLEIL_2
     * 7 . A voir
     */
    private static final int VALEUR_OR_MINIMUM_DE1 = 12;
    private static final int VALEUR_MAX_RESERVE_OR_INIT = 12;
    private static final int VALEUR_LUNE_MINIMUM_DE1 = 2;
    private static final int VALEUR_LUNE_MINIMUM_DE2 = 1;
    private static final int VALEUR_SOLEIL_MINIMUM_DE2 = 2 ;
    private static final int PRIX_CASQUE_CARTE = 5;
    private static final int PRIX_HYDRE_CARTE = 5;



    /**
     * Donnent le nombres de ressources en plus de l'Or que l'ont peut avoir en même temps sur cette face
     */
    private int valeurAccessoireOr(Face face) {
        if (face == Faces.OR_2_LUNE_1) {
            return 1;
        }
        if (face == Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
            return 3;
        } else {
            return 0;
        }
    }

    /**
     * Donnent le nombres de ressources en plus de la Lune que l'ont peut avoir en meme temps sur cette face
     */
    private int valeurAccessoireLune(Face face) {
        if (face == Faces.LUNE_2_VICTOIRE_2 || face == Faces.OR_2_LUNE_1) {
            return 2;
        }
        if (face == Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
            return 3;
        } else {
            return 0;
        }
    }

    /**
     * Donnent le nombres de ressources en plus des Pdv que l'ont peut avoir en meme temps sur cette face
     */
    private int valeurAccessoireVictoire(Face face) {
        if (face == Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
            return 3;
        }
        if (face == Faces.LUNE_2_VICTOIRE_2) {
            return 2;
        }
        if (face == Faces.SOLEIL_1_VICTOIRE_1) {
            return 1;
        }
        return 0;
    }

    /**
     * Donnent le nombres de ressources en plus des Soleils que l'ont peut avoir en meme temps sur cette face
     */
    private int valeurAccessoireSoleil(Face face) {
        if (face == Faces.SOLEIL_1_VICTOIRE_1) {
            return 1;
        }
        if (face == Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
            return 3;
        }
        return 0;
    }


    @Override
    public int choixFace(Face... faces) {
        // Choisit la face qui rapporte le plus de points d'Or
        int choix = 0;
        int max = 0;
        for (int i = 0; i < faces.length; i++) {
            if (BotUtils.valeurOr(faces[i]) > max) {
                choix = i;
                max = valeurOr(faces[i]);
            }
        }
        return choix;
    }

    /**
     * donne l'indice de la Face qui va être appliquer de manière négatif sur mon joueur
     */
    @Override
    public int choixFaceNegatif(Face... faces) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < faces.length; indice++) {
            int valeurVic = BotUtils.valeurVictoire(faces[indice]);
            int valeurSol = BotUtils.valeurSoleil(faces[indice]);
            int valeurOr = valeurOr(faces[indice]);
            int valeurLun = BotUtils.valeurLune(faces[indice]);
            boolean boolOr = (valeurOr == 0);
            boolean boolLun = (valeurLun == 0);
            boolean boolSol = (valeurSol == 0);
            boolean boolVic = (valeurVic == 0);

            // si on a pas de points de victoires et que la face donne uniquement des Pdv
            if (valeurVic >= 0 && boolOr && boolLun && boolSol && this.getJoueur().getVictoire() == 0) {
                return indice;
            }
            // Si on a pas de Soleil et que la face donne uniquement du Soleil
            if (valeurSol >= 0 && boolLun && boolOr && boolVic && this.getJoueur().getSoleil() == 0) {
                return indice;
            }
            // Si on a pas d'Or et que la face donne uniquement de l'Or
            if (valeurOr >= 0 && boolVic && boolLun && boolSol && this.getJoueur().getOr() == 0) {
                return indice;
            }
            // Si on a pas de Lune et que la face donne uniquement de la Lune
            if (valeurLun >= 0 && boolOr && boolSol && boolVic && this.getJoueur().getLune() == 0) {
                return indice;
            }
            // On choisit alors la face qui nous perde le moins d'Or
            if (valeurOr > 0 && valeurOr <= poidFace && valeurAccessoireOr(faces[indice]) <= bonus) {
                choix = indice;
                poidFace = valeurOr;
                bonus = valeurAccessoireOr(faces[indice]);
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face ayant le moins de Victoire
     */
    private int getMinIndiceVictoire(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueVictoire = BotUtils.valeurVictoire(de.getFace(indice));
            int valueBonus = valeurAccessoireVictoire(de.getFace(indice));
            // Si la Face à moins de Victoire
            if (valueVictoire < poidFace && valueVictoire >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueVictoire;
                bonus = valueBonus;
            }
            // Si la Face à  autant de Victoire mais moins de Ressources Annexes
            if (valueVictoire == poidFace && valueBonus < bonus) {
                choix = indice;
                poidFace = valueVictoire;
                bonus = valueBonus;
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face ayant le moins de Soleil
     */
    private int getMinIndiceSoleil(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueSoleil = BotUtils.valeurSoleil(de.getFace(indice));
            int valueBonus = valeurAccessoireSoleil(de.getFace(indice));
            // Si la Face à moins de Soelil
            if (valueSoleil < poidFace && valueSoleil >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueSoleil;
                bonus = valueBonus;
            }
            // Si la Face à  autant de Soleil mais moins de Ressources Annexes
            if (valueSoleil == poidFace && valueBonus < bonus ) {
                choix = indice;
                poidFace = valueSoleil;
                bonus = valueBonus;
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face ayant le moins d'Or
     */
    private int getMinIndiceOr(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueOr = valeurOr(de.getFace(indice));
            int valueBonus = valeurAccessoireOr(de.getFace(indice));
            // Si la Face à moins d'Or
            if (valueOr < poidFace && valueOr >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueOr;
                bonus = valueBonus;
            }
            // Si la Face à autant D'Or mais moins de Ressources Annexes
            if (valueOr == poidFace && valueBonus < bonus) {
                choix = indice;
                poidFace = valueOr;
                bonus = valueBonus;
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face ayant le moins de Lune
     */
    private int getMinIndiceLune(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueLune = BotUtils.valeurLune(de.getFace(indice));
            int valueBonus = valeurAccessoireLune(de.getFace(indice));
            // Si la Face à moins de Lune
            if (valueLune < poidFace && valueLune >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueLune;
                bonus = valueBonus;
            }
            // Si la Fce à  autant de Lune mais moins de REssources Annexes
            if (valueLune == poidFace && valueBonus < bonus) {
                choix = indice;
                poidFace = valueLune;
                bonus = valueBonus;
            }
        }
        return choix;
    }


    @Override
    public void forge(Face face) {
        int valeurVic = BotUtils.valeurVictoire(face);
        int valeurSol = BotUtils.valeurSoleil(face);
        int valeurOr = valeurOr(face);
        int valeurLun = BotUtils.valeurLune(face);

        int minIndiceLuneDe1 = getMinIndiceLune(getJoueur().de1);
        int minIndiceLuneDe2 = getMinIndiceLune(getJoueur().de2);
        int minIndiceOrDe1 = getMinIndiceOr(getJoueur().de1);
        int minIndiceOrDe2 = getMinIndiceOr(getJoueur().de2);
        int minIndiceVictoireDe1 = getMinIndiceVictoire(getJoueur().de1);
        int minIndiceVictoireDe2 = getMinIndiceVictoire(getJoueur().de2);
        int minIndiceSoleilDe1 = getMinIndiceSoleil(getJoueur().de1);
        int minIndiceSoleilDe2 = getMinIndiceSoleil(getJoueur().de2);
        int action = 0;

        De tempDe1 = getJoueur().de1;
        De tempDe2 = getJoueur().de2;

        Face faceMinOrDe1 = tempDe1.getFace(minIndiceOrDe1);
        Face faceMinOrDe2 = tempDe2.getFace(minIndiceOrDe2);
        Face faceMinLuneDe1 = tempDe1.getFace(minIndiceLuneDe1);
        Face faceMinLuneDe2 = tempDe2.getFace(minIndiceLuneDe2);
        Face faceMinVictoireDe1 = tempDe1.getFace(minIndiceVictoireDe1);
        Face faceMinVictoireDe2 = tempDe2.getFace(minIndiceVictoireDe2);
        Face faceMinSoleilDe1 = tempDe1.getFace(minIndiceSoleilDe1);
        Face faceMinSoleilDe2 = tempDe2.getFace(minIndiceSoleilDe2);

        //  On Traite la X3
        if (face == Faces.X3 && action == 0) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            action++;
        }

        // On traite MIROIR
        // Normalement impossible qu'il y en est mais bon on traite tous les au cas ou
        // Si y'en a un je le met à la place de ma face qui me rapporte de la victoire
        if (face == Faces.MIROIR && action == 0) {
            getJoueur().de2.forge(face, minIndiceVictoireDe2);
            action++;
        }

        // On Traite OR_1 - OR_2 - OR_3 - OR_4 - OR_6
        if (valeurAccessoireOr(face) == 0 && valeurOr(face) > 0 && action == 0) {
            if (BotUtils.valeurDe(VALEUR_OR,tempDe1) < VALEUR_OR_MINIMUM_DE1) {
                getJoueur().de1.forge(face, minIndiceOrDe1);
                action++;
            }
            if (BotUtils.valeurDe(VALEUR_OR,tempDe1) >= VALEUR_OR_MINIMUM_DE1 && action == 0) {
                getJoueur().de2.forge(face, minIndiceOrDe2);
                action++;
            }
        }

        // On Traite OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1
        if (valeurOr == 1 && valeurAccessoireOr(face) == 3 && action == 0) {
            getJoueur().de1.forge(face, minIndiceSoleilDe1);
            action++;
        }

        // On Traite LUNE_1  - LUNE_2
        if (valeurLun > 0 && valeurAccessoireLune(face) == 0 && BotUtils.valeurDe(VALEUR_LUNE,tempDe2) <= 2 && action == 0) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            action++;
        }

        // On Traite VICTOIRE_1 - VICTOIRE_2 - VICTOIRE_3 -  VICTOIRE_4
        if (valeurVic == 0 && valeurAccessoireVictoire(face) == 0 && action == 0) {
            getJoueur().de1.forge(face, minIndiceOrDe1);
            action++;
        }

        // On Traite OR_2_OU_LUNE_2_OU_SOLEIL_2  - OR_1_OU_LUNE_1_OU_SOLEIL_1
        if (valeurOr >= 1 && valeurLun >= 1 && valeurSol >= 1 && action == 0) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            action++;
        }

        // On Traite OR_3_OU_VICTOIRE_2
        if (valeurOr == 3 && valeurVic == 2 && action == 0) {
            if (BotUtils.valeurDe(VALEUR_OR,tempDe1) < VALEUR_OR_MINIMUM_DE1) {
                getJoueur().de1.getFace(getMinIndiceOr(getJoueur().de1));
                action++;
            }
            if (BotUtils.valeurDe(VALEUR_OR,tempDe1) >= VALEUR_OR_MINIMUM_DE1 && action == 0) {
                getJoueur().de2.forge(face, getMinIndiceOr(getJoueur().de2));
                action++;
            }
        }

        // On Traite OR_2_LUNE_1
        if (valeurOr == 2 && valeurLun == 1 && action == 0) {
            if (BotUtils.valeurDe(VALEUR_LUNE,tempDe1) < VALEUR_LUNE_MINIMUM_DE1) {
                getJoueur().de1.forge(face, minIndiceOrDe1);
                action++;
            }
            if (BotUtils.valeurDe(VALEUR_LUNE,tempDe2) < VALEUR_LUNE_MINIMUM_DE2 && action == 0) {
                getJoueur().de2.forge(face, minIndiceOrDe2);
                action++;
            }
        }

        // On Traite LUNE_2_VICTOIRE_2
        if (valeurLun == 2 && valeurVic == 2 && action == 0) {
            if (BotUtils.valeurDe(VALEUR_LUNE,tempDe1) < VALEUR_LUNE_MINIMUM_DE1) {
                if (BotUtils.valeurLune(faceMinLuneDe1) == 1 && valeurAccessoireLune(faceMinLuneDe1)== 0) {
                    getJoueur().de1.forge(face, minIndiceLuneDe1);
                    action++;
                }
                if (action == 0) ;
                {
                    getJoueur().de1.forge(face, minIndiceOrDe1);
                    action++;
                }
            }
            if (BotUtils.valeurDe(VALEUR_LUNE,tempDe2) < VALEUR_LUNE_MINIMUM_DE2 && action == 0) {
                getJoueur().de2.forge(face, minIndiceVictoireDe2);
                action++;
            }
        }

        // On traite SOLEIL_1 - SOLEIL_2
        if ((valeurSol == 2 && valeurSol == 1) && valeurAccessoireSoleil(face) == 0 && action == 0) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            action++;
        }

        // On Traite enfin le dernier SOLEIL_1_VICTOIRE_1
        if (valeurSol == 1 && valeurVic == 1 && valeurAccessoireSoleil(face) == 1 && action == 0) {
            if (BotUtils.valeurVictoire(faceMinVictoireDe2) == 2 && valeurAccessoireVictoire(faceMinVictoireDe2) == 0) {
                getJoueur().de2.forge(face, minIndiceVictoireDe2);
                action++;
            }
            if (valeurOr(faceMinOrDe2) == 1 && valeurAccessoireOr(faceMinOrDe2) == 0 && action == 0) {
                getJoueur().de2.forge(face, minIndiceOrDe2);
            }
            if (BotUtils.valeurSoleil(faceMinSoleilDe2) == 1 && valeurAccessoireSoleil(faceMinSoleilDe2) == 0 && action == 0) {
                getJoueur().de2.forge(face, minIndiceSoleilDe2);
                action++;
            }
        }


    }


    /**
     * Donne si la FAce est dans la liste
     */
    private boolean checkFace(List<Face> liste,Face face)
    {
        for (Face faces : liste) {
            if (faces == face) {
                return true;
            }
        }
        return false;
    }
    /**
     * Donnent le nombre de Cartes disponnible de la carte donné en paramètre
     */
    private int nombreCartes(List<Carte> liste , Carte carte)
    {
        int somme = 0;
        for (Carte cartes : liste) {
            if (carte == cartes) {
                somme++;
            }
        }
        return somme;
    }

    @Override
    public void faireAchatsFace(ListeAchat liste) {
        boolean action;
        boolean checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1;
        boolean checkOR_6;
        boolean checkOR_3_OU_VICTOIRE_2;
        boolean checkOR_4;
        boolean checkOR_3;
        boolean checkOR_2_LUNE_1;
        boolean checkLUNE_2_VICTOIRE_2;
        boolean checkLUNE_2;
        boolean checkLUNE_1;
        boolean checkOR_2_OU_LUNE_2_OU_SOLEIL_2;
        boolean checkSOLEIL_2;
        boolean checkSOLEIL_1_VICTOIRE_1;
        boolean checkSOLEIL_1;
        boolean checkVICTOIRE_3;
        boolean checkVICTOIRE_4;

        while (!liste.isEmpty()) {
            action = false;
            checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 = checkFace(liste.getAvailable(),Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
            checkOR_6 = checkFace(liste.getAvailable(),Faces.OR_6);
            checkOR_3_OU_VICTOIRE_2 = checkFace(liste.getAvailable(),Faces.OR_3_OU_VICTOIRE_2);
            checkOR_4 = checkFace(liste.getAvailable(),Faces.OR_4);
            checkOR_3 = checkFace(liste.getAvailable(),Faces.OR_3);
            checkOR_2_LUNE_1 = checkFace(liste.getAvailable(),Faces.OR_2_LUNE_1);
            checkLUNE_2_VICTOIRE_2 = checkFace(liste.getAvailable(),Faces.LUNE_2_VICTOIRE_2);
            checkLUNE_2 = checkFace(liste.getAvailable(),Faces.LUNE_2);
            checkLUNE_1 = checkFace(liste.getAvailable(),Faces.LUNE_1);
            checkOR_2_OU_LUNE_2_OU_SOLEIL_2 = checkFace(liste.getAvailable(),Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
            checkSOLEIL_2 = checkFace(liste.getAvailable(),Faces.SOLEIL_2);
            checkSOLEIL_1_VICTOIRE_1 = checkFace(liste.getAvailable(),Faces.SOLEIL_1_VICTOIRE_1);
            checkSOLEIL_1 = checkFace(liste.getAvailable(),Faces.SOLEIL_1);
            checkVICTOIRE_3 = checkFace(liste.getAvailable(),Faces.VICTOIRE_3);
            checkVICTOIRE_4 = checkFace(liste.getAvailable(),Faces.VICTOIRE_4);

            // Dé 1 < 11 Or
            if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) < VALEUR_OR_MINIMUM_DE1) {
                if (checkOR_6) {
                    liste.acheter(Faces.OR_6);
                    action = true;

                }
                if (checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
                    liste.acheter(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
                    action = true;
                }
                if (checkOR_4) {
                    liste.acheter(Faces.OR_4);
                    action = true;
                }
                if (checkOR_3) {
                    liste.acheter(Faces.OR_3);
                    action = true;
                }
                if (checkOR_3_OU_VICTOIRE_2) {
                    liste.acheter(Faces.OR_3_OU_VICTOIRE_2);
                    action = true;
                }
                if (checkOR_2_LUNE_1) {
                    liste.acheter(Faces.OR_2_LUNE_1);
                    action = true;
                }
            }

            // Dé 1 valeur Lune < 2
            if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) < VALEUR_LUNE_MINIMUM_DE1) {
                if (checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
                    liste.acheter(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
                    action = true;
                }
                if (checkLUNE_2_VICTOIRE_2) {
                    liste.acheter(Faces.LUNE_2_VICTOIRE_2);
                    action = true;
                }
                if (checkOR_2_LUNE_1) {
                    liste.acheter(Faces.OR_2_LUNE_1);
                    action = true;
                }
                if (checkLUNE_2) {
                    liste.acheter(Faces.LUNE_2);
                    action = true;
                }
                if (checkLUNE_1) {
                    liste.acheter(Faces.LUNE_1);
                    action = true;
                }
            }

            //Dé 2 valeur Soleil < VALEUR_SOLEIL_MINIMUM_DE2
            if (BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) < VALEUR_SOLEIL_MINIMUM_DE2) {
                if (checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
                    liste.acheter(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
                    action = true;
                }
                if (checkOR_2_OU_LUNE_2_OU_SOLEIL_2) {
                    liste.acheter(Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
                    action = true;
                }
                if (checkSOLEIL_2) {
                    liste.acheter(Faces.SOLEIL_2);
                    action = true;
                }
                if (checkSOLEIL_1_VICTOIRE_1) {
                    liste.acheter(Faces.SOLEIL_1_VICTOIRE_1);
                    action = true;
                }
                if (checkSOLEIL_1) {
                    liste.acheter(Faces.SOLEIL_1);
                    action = true;
                }
            }

            // Dé 2 valeur Lune < VALEUR_LUNE_MINIMUM_DE2
            if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2) {
                if (checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
                    liste.acheter(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
                    action = true;
                }
                if (checkOR_2_OU_LUNE_2_OU_SOLEIL_2) {
                    liste.acheter(Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
                    action = true;
                }
                if (checkLUNE_2_VICTOIRE_2) {
                    liste.acheter(Faces.LUNE_2_VICTOIRE_2);
                    action = true;
                }
                if (checkLUNE_2) {
                    liste.acheter(Faces.LUNE_2);
                    action = true;
                }
                if (checkLUNE_1) {
                    liste.acheter(Faces.LUNE_1);
                    action = true;
                }
            }
            // Si il n'a pas fait d'action on stop, car ca veut dire qu'il y a rien d'interressant pour le joueur a acheter
            if (!action) {
                break;
            }

        }
    }

    /**
     *  Renvoie si le Dé 2 dispose de Face x3
     */
    private boolean didWeGotx3()
    {
        for(int indice = 0 ; indice < 6; indice++)
        {
            if(getJoueur().de2.getFace(indice) == Faces.X3)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tourSanctuaire() {
        List<Face> listAchetable = this.getJoueur().getPartie().getSanctuaire().getFaces();
        // Dé 1 valeur Or < VALEUR_OR_MINIMUM_DE1 && qu'on peut avoir une Face >= OR_3 on va la prendre
        if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) < VALEUR_OR_MINIMUM_DE1 && checkFace(listAchetable,Faces.OR_3) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 1 valeur Lune <= 2 && qu'on peut avoir une Face >= Lune 1 on va la prendre
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) <= 2 && checkFace(listAchetable,Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Marteau du Forgeron en avoir toujours 1 si il y en a encore
        if (this.getJoueur().getNextMarteau() == null && nombreCartes(this.getJoueur().getPartie().getPlateau().getCartes(),Cartes.MARTEAU) > 0) {
            return false;
        }
        // Si on a toujours pas augmenter notre stock max il faut le faire car on risque d'être full donc
        // si on peut avoir le Coffre du forgeron et qu'on en a pas déjà,  un on le prend
        if (this.getJoueur().getOrMax() == VALEUR_MAX_RESERVE_OR_INIT && this.getJoueur().getLune() >= 1) {
            return false;
        }
        // Si on peut avoir le Casque d'invisibilité , la carte qui donne la face x3
        if (this.getJoueur().getLune() >= PRIX_CASQUE_CARTE) {
            return false;
        }
        // Dé 2 valeur Soleil <= 2 && qu'on peut avoir une Face >= Soleil 1  on va la prendre
        if (BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) <= VALEUR_SOLEIL_MINIMUM_DE2 && checkFace(listAchetable,Faces.SOLEIL_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 2 valeur Lune < 1 && qu'on peut avoir une Face >= Lune_1 on va la prendre
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2 && checkFace(listAchetable,Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Si on a un marteau en cours on va voir si on peut acheter une carte intérréssante
        if (this.getJoueur().getNextMarteau() != null) {
            return false;
        }
        // Aussi non on va essayer d'optimiser nos pièces déjà présentes sur nos Dé
        else {
            return true;
        }
    }

    @Override
    public boolean faireTourSupplementaire() {
        List<Face> listAchetable = this.getJoueur().getPartie().getSanctuaire().getFaces();
        // Dé 1 valeur Or < VALEUR_OR_MINIMUM_DE1
        if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) < VALEUR_OR_MINIMUM_DE1 && checkFace(listAchetable,Faces.OR_3) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 1 valeur Lune <= 2
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) <= VALEUR_LUNE_MINIMUM_DE1 && checkFace(listAchetable,Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Marteau du Forgeron en avoir toujours 1 si possible
        if (this.getJoueur().getNextMarteau() == null && nombreCartes(this.getJoueur().getPartie().getPlateau().getCartes(),Cartes.MARTEAU) > 0) {
            return true;
        }
        // 1 Coffre Forgeron
        if (this.getJoueur().getOrMax() == VALEUR_MAX_RESERVE_OR_INIT && this.getJoueur().getLune() >= 1 && nombreCartes(getJoueur().getPartie().getPlateau().getCartes(),Cartes.COFFRE) > 0) {
            return true;
        }
        // Achete HYDRE EN MASSE
        if( getJoueur().getLune()>= PRIX_HYDRE_CARTE & getJoueur().getSoleil() >= PRIX_HYDRE_CARTE && nombreCartes(getJoueur().getPartie().getPlateau().getCartes(),Cartes.HYDRE) > 0)
        {
            return true;
        }
        // Casque D'invisbilité dés qu'on peut
        //if (this.getJoueur().getLune() >= PRIX_CASQUE_CARTE && nombreCartes(getJoueur().getPartie().getPlateau().getCartes(),Cartes.CASQUE) > 0) {
        //    return true;
        //}
        // Dé 2 valeur Soleil <= 2
        if (BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) <= VALEUR_SOLEIL_MINIMUM_DE2 && checkFace(listAchetable,Faces.SOLEIL_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 2 valeur Lune < 1
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2 && checkFace(listAchetable,Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        return false;

    }


    @Override
    public Carte faireAchatCartes(List<Carte> cartes) {
        //N'achete pas si il ne peut rien acheter
        if (cartes.isEmpty()) {
            return null;
        }
        // Achete HYDRE EN MASSE
        if( nombreCartes(cartes,Cartes.HYDRE) > 0)
        {
            return Cartes.HYDRE;
        }
        // Si le joueur n'a pas de Marteau Forgeron et qu'il y en a encore
        if (this.getJoueur().getNextMarteau() == null && nombreCartes(cartes,Cartes.MARTEAU) > 0) {
            return Cartes.MARTEAU;
        }
        // Si le joueur il n'a pas encore acheter son coffre du forgeron
        if (this.getJoueur().getOrMax() == VALEUR_MAX_RESERVE_OR_INIT && nombreCartes(cartes,Cartes.COFFRE) > 0) {
            return Cartes.COFFRE;
        }
        // Achete MEDUSE EN MASSE
        if( nombreCartes(cartes,Cartes.MEDUSE) > 0)
        {
            return Cartes.MEDUSE;
        }
        // Si le joueur peut acheter le Casque d'invisbilité
        if (nombreCartes(cartes,Cartes.CASQUE) > 0 && !didWeGotx3()) {
            return Cartes.CASQUE;
        }
        if(nombreCartes(cartes,Cartes.PINCE) > 0)
        {
            return Cartes.PINCE;
        }
        // Si y'a plus de marteau de disponible à acheter il commence à faire une transition avec la carte Ancien
        if (nombreCartes(this.getJoueur().getPartie().getPlateau().getCartes(),Cartes.ANCIEN) == 0) {
            return Cartes.ANCIEN;
        }
        // Aussi non il économise et il fait aucun choix
        return null;
    }

    @Override
    public De choixFaveurMineure() {
        // Le Dé 1 reste notre Dé avec les plus grosses Face
        return getJoueur().de1;
    }

    private boolean peutUtiliser(List<CarteRenfort> disponibles, CarteRenfort tente) {
        return disponibles.contains(tente) && tente.peutActiver(getJoueur());
    }

    @Override
    public CarteRenfort choixRenfort(List<CarteRenfort> liste) {
        // Il commence par les Sabots pour optimiser son échange d'or avec l'ancien
        // Même si normalement il va jamais prendre la carte SABOTS
        if (this.peutUtiliser(liste, Cartes.SABOTS)) {
            return Cartes.SABOTS;
        } else if (peutUtiliser(liste, Cartes.AILES)) {
            return Cartes.AILES;
        } else if (peutUtiliser(liste, Cartes.ANCIEN)) {
            return Cartes.ANCIEN;
        }
        return null;
    }

    @Override
    public int changeOrEnMarteau(int orChangeable) {
        // Si il a le mimimnum de value par Dé et qu'il a un marteau
        if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) <= VALEUR_OR_MINIMUM_DE1 && BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) < VALEUR_LUNE_MINIMUM_DE1 && BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) < VALEUR_SOLEIL_MINIMUM_DE2 && BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2 && this.getJoueur().getNextMarteau() != null) {
            return orChangeable/2;
        }
        return orChangeable;
    }
}