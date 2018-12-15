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
     * Donne le nombres de ressources en plus de l'Or que l'on peut avoir en même temps sur cette face
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
     * Donne le nombres de ressources en plus de la Lune que l'on peut avoir en même temps sur cette face
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
     * Donne le nombres de ressources en plus des Pdv que l'on peut avoir en même temps sur cette face
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
     * Donne le nombres de ressources en plus des Soleils que l'on peut avoir en même temps sur cette face
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
     * donne l'indice de la Face qui va être appliquée de manière négatif sur mon joueur
     */
    @Override
    public int choixFaceNegatif(Face... faces) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < faces.length; indice++) {
            int valeurVic = BotUtils.valeurVictoire(faces[indice]);
            int valeurSol = BotUtils.valeurSoleil(faces[indice]);
            int valeurOr = BotUtils.valeurOr(faces[indice]);
            int valeurLun = BotUtils.valeurLune(faces[indice]);
            boolean boolOr = (valeurOr == 0);
            boolean boolLun = (valeurLun == 0);
            boolean boolSol = (valeurSol == 0);
            boolean boolVic = (valeurVic == 0);

            // si on n'a pas de points de victoires et que la face rapporte uniquement des Pdv
            if (valeurVic >= 0 && boolOr && boolLun && boolSol && this.getJoueur().getVictoire() == 0) {
                return indice;
            }
            // Si on n'a pas de Soleil et que la face rapporte uniquement du Soleil
            if (valeurSol >= 0 && boolLun && boolOr && boolVic && this.getJoueur().getSoleil() == 0) {
                return indice;
            }
            // Si on n'a pas d'Or et que la face rapporte uniquement de l'Or
            if (valeurOr >= 0 && boolVic && boolLun && boolSol && this.getJoueur().getOr() == 0) {
                return indice;
            }
            // Si on n'a pas de Lune et que la face rapporte uniquement de la Lune
            if (valeurLun >= 0 && boolOr && boolSol && boolVic && this.getJoueur().getLune() == 0) {
                return indice;
            }
            // On choisit alors la face qui nous fait perdre le moins d'Or et de valeurAccesoire
            if (valeurOr > 0 && valeurOr+valeurAccessoireOr(faces[indice]) <= poidFace + bonus) {
                choix = indice;
                poidFace = valeurOr;
                bonus = valeurAccessoireOr(faces[indice]);
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face rapportant le moins de victoire
     */
    private int getMinIndiceVictoire(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueVictoire = BotUtils.valeurVictoire(de.getFace(indice));
            int valueBonus = valeurAccessoireVictoire(de.getFace(indice));
            // Si la Face rappporte moins de Victoire
            if (valueVictoire < poidFace && valueVictoire >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueVictoire;
                bonus = valueBonus;
            }
            // Si la Face rapporte autant de Victoire mais moins de Ressources Annexes
            if (valueVictoire == poidFace && valueBonus < bonus) {
                choix = indice;
                poidFace = valueVictoire;
                bonus = valueBonus;
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face rapportant le moins de Soleil
     */
    private int getMinIndiceSoleil(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueSoleil = BotUtils.valeurSoleil(de.getFace(indice));
            int valueBonus = valeurAccessoireSoleil(de.getFace(indice));
            // Si la Face rapporte moins de Soelil
            if (valueSoleil < poidFace && valueSoleil >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueSoleil;
                bonus = valueBonus;
            }
            // Si la Face rapporte autant de Soleil mais moins de Ressources Annexes
            if (valueSoleil == poidFace && valueBonus < bonus ) {
                choix = indice;
                poidFace = valueSoleil;
                bonus = valueBonus;
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face rapportant le moins d'Or
     */
    private int getMinIndiceOr(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueOr = valeurOr(de.getFace(indice));
            int valueBonus = valeurAccessoireOr(de.getFace(indice));
            // Si la Face rapporte moins d'Or
            if (valueOr < poidFace && valueOr >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueOr;
                bonus = valueBonus;
            }
            // Si la Face rapporte autant d'Or mais moins de Ressources Annexes
            if (valueOr == poidFace && valueBonus < bonus) {
                choix = indice;
                poidFace = valueOr;
                bonus = valueBonus;
            }
        }
        return choix;
    }

    /**
     * Donne l'indice de la Face rapportant le moins de Lune
     */
    private int getMinIndiceLune(De de) {
        int choix = 0;
        int poidFace = 6;
        int bonus = 0;
        for (int indice = 0; indice < 6; indice++) {
            int valueLune = BotUtils.valeurLune(de.getFace(indice));
            int valueBonus = valeurAccessoireLune(de.getFace(indice));
            // Si la Face rapporte moins de Lune
            if (valueLune < poidFace && valueLune >= 1 && de.getFace(indice) != Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 ) {
                choix = indice;
                poidFace = valueLune;
                bonus = valueBonus;
            }
            // Si la Face rapporte autant de Lune mais moins de Ressources Annexes
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
        int minIndiceLuneDe1 = getMinIndiceLune(getJoueur().de1);
        int minIndiceLuneDe2 = getMinIndiceLune(getJoueur().de2);
        int minIndiceOrDe1 = getMinIndiceOr(getJoueur().de1);
        int minIndiceOrDe2 = getMinIndiceOr(getJoueur().de2);
        int minIndiceVictoireDe1 = getMinIndiceVictoire(getJoueur().de1);
        int minIndiceVictoireDe2 = getMinIndiceVictoire(getJoueur().de2);
        int minIndiceSoleilDe1 = getMinIndiceSoleil(getJoueur().de1);
        int minIndiceSoleilDe2 = getMinIndiceSoleil(getJoueur().de2);

        De tempDe1 = getJoueur().de1;
        De tempDe2 = getJoueur().de2;

        tempDe1.getFace(minIndiceOrDe1);
        Face faceMinOrDe2 = tempDe2.getFace(minIndiceOrDe2);
        Face faceMinLuneDe1 = tempDe1.getFace(minIndiceLuneDe1);
        tempDe2.getFace(minIndiceLuneDe2);
        tempDe1.getFace(minIndiceVictoireDe1);
        Face faceMinVictoireDe2 = tempDe2.getFace(minIndiceVictoireDe2);
        tempDe1.getFace(minIndiceSoleilDe1);
        //  On Traite la X3
        if (face == Faces.X3 ) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            return;
        }

        // On traite MIROIR
        // Normalement impossible que notre joueur en achète, mais on traite ce cas quand même.
        // Si il y en a un, il est placé à la place de la face rapportant le moins de victoire.
        if (face == Faces.MIROIR) {
            getJoueur().de2.forge(face, minIndiceVictoireDe2);
            return;
        }

        // On Traite OR_1 - OR_2 - OR_3 - OR_4 - OR_6 - OR_3_OU_VICTOIRE_2
        if (face == Faces.OR_1 || face == Faces.OR_2 || face == Faces.OR_3 || face == Faces.OR_4 || face == Faces.OR_6 || face == Faces.OR_3_OU_VICTOIRE_2) {
        	if (BotUtils.valeurDe(VALEUR_OR,tempDe1) < VALEUR_OR_MINIMUM_DE1)
        		getJoueur().de1.forge(face, minIndiceOrDe1);
        	else 
        		getJoueur().de2.forge(face, minIndiceOrDe2);
            return;
        }

        // On Traite OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1
        if (face == Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1) {
            getJoueur().de1.forge(face, minIndiceSoleilDe1);
            return;
        }

        // On Traite LUNE_1  - LUNE_2
        if (face == Faces.LUNE_1 || face == Faces.LUNE_2) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            return;
        }

        // On Traite VICTOIRE_1 - VICTOIRE_2 - VICTOIRE_3 -  VICTOIRE_4
        if (face==Faces.VICTOIRE_1 || face==Faces.VICTOIRE_2 || face==Faces.VICTOIRE_3 || face==Faces.VICTOIRE_4) {
            getJoueur().de1.forge(face, minIndiceOrDe1);
            return;
        }

        // On Traite OR_2_OU_LUNE_2_OU_SOLEIL_2  - OR_1_OU_LUNE_1_OU_SOLEIL_1
        if (face == Faces.OR_1_OU_LUNE_1_OU_SOLEIL_1 || face == Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            return;
        }


        // On Traite OR_2_LUNE_1
        if (face == Faces.OR_2_LUNE_1) {
            if (BotUtils.valeurDe(VALEUR_LUNE,tempDe1) < VALEUR_LUNE_MINIMUM_DE1) 
                getJoueur().de1.forge(face, minIndiceOrDe1);
            else
                getJoueur().de2.forge(face, minIndiceOrDe2);
            return;
            
        }

        // On Traite LUNE_2_VICTOIRE_2
        if (face == Faces.LUNE_2_VICTOIRE_2) {
            if (BotUtils.valeurDe(VALEUR_LUNE,tempDe1) < VALEUR_LUNE_MINIMUM_DE1) {
                if (BotUtils.valeurLune(faceMinLuneDe1) == 1 && valeurAccessoireLune(faceMinLuneDe1)== 0) 
                    getJoueur().de1.forge(face, minIndiceLuneDe1);
                else
                    getJoueur().de1.forge(face, minIndiceOrDe1);
            }
            else
                getJoueur().de2.forge(face, minIndiceVictoireDe2);
            return;
            }
        
        // On traite SOLEIL_1 - SOLEIL_2
        if (face == Faces.SOLEIL_1 || face == Faces.SOLEIL_2) {
            getJoueur().de2.forge(face, minIndiceOrDe2);
            return;
        }

        // On Traite enfin le dernier SOLEIL_1_VICTOIRE_1
        if (face == Faces.SOLEIL_1_VICTOIRE_1) {
            if (BotUtils.valeurVictoire(faceMinVictoireDe2) == 2 && valeurAccessoireVictoire(faceMinVictoireDe2) == 0) 
                getJoueur().de2.forge(face, minIndiceVictoireDe2);
            else if (valeurOr(faceMinOrDe2) == 1 && valeurAccessoireOr(faceMinOrDe2) == 0) 
                getJoueur().de2.forge(face, minIndiceOrDe2);
            else
                getJoueur().de2.forge(face, minIndiceSoleilDe2);
            return;
        }
        getJoueur().getDe1().forge(face, minIndiceOrDe1);
        return;
    }


    
    /**
     * Donne le nombre de Cartes disponibles de la carte donnée en paramètre
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
        while (!liste.isEmpty()) {
            action = false;
            List<Face> facesDisponibles = liste.getAvailable();
            checkOR_1_LUNE_1_SOLEIL_1_VICTOIRE_1 = facesDisponibles.contains(Faces.OR_1_LUNE_1_SOLEIL_1_VICTOIRE_1);
            checkOR_6 = facesDisponibles.contains(Faces.OR_6);
            checkOR_3_OU_VICTOIRE_2 = facesDisponibles.contains(Faces.OR_3_OU_VICTOIRE_2);
            checkOR_4 = facesDisponibles.contains(Faces.OR_4);
            checkOR_3 = facesDisponibles.contains(Faces.OR_3);
            checkOR_2_LUNE_1 = facesDisponibles.contains(Faces.OR_2_LUNE_1);
            checkLUNE_2_VICTOIRE_2 = facesDisponibles.contains(Faces.LUNE_2_VICTOIRE_2);
            checkLUNE_2 = facesDisponibles.contains(Faces.LUNE_2);
            checkLUNE_1 = facesDisponibles.contains(Faces.LUNE_1);
            checkOR_2_OU_LUNE_2_OU_SOLEIL_2 = facesDisponibles.contains(Faces.OR_2_OU_LUNE_2_OU_SOLEIL_2);
            checkSOLEIL_2 = facesDisponibles.contains(Faces.SOLEIL_2);
            checkSOLEIL_1_VICTOIRE_1 = facesDisponibles.contains(Faces.SOLEIL_1_VICTOIRE_1);
            checkSOLEIL_1 = facesDisponibles.contains(Faces.SOLEIL_1);
            facesDisponibles.contains(Faces.VICTOIRE_3);
            facesDisponibles.contains(Faces.VICTOIRE_4);

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
            // Si il n'a pas fait d'action on s'arrête, car cela veut dire qu'il n'y a plus rien d'intéressant pour le joueur à acheter
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
        if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) < VALEUR_OR_MINIMUM_DE1 && listAchetable.contains(Faces.OR_3) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 1 valeur Lune <= 2 && qu'on peut avoir une Face >= Lune 1 on va la prendre
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) <= 2 && listAchetable.contains(Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Marteau du Forgeron : en avoir toujours 1 si il en reste.
        if (this.getJoueur().getNextMarteau() == null && nombreCartes(this.getJoueur().getPartie().getPlateau().getCartes(),Cartes.MARTEAU) > 0) {
            return false;
        }
        // Si on n'a toujours pas augmenté notre stock max il faut le faire car on risque d'être limité donc
        // si on peut acheter le Coffre du forgeron et qu'on n'en a pas encore, on le prend.
        if (this.getJoueur().getOrMax() == VALEUR_MAX_RESERVE_OR_INIT && this.getJoueur().getLune() >= 1) {
            return false;
        }
        // Si on peut avoir le Casque d'invisibilité, la carte qui donne la face x3
        if (this.getJoueur().getLune() >= PRIX_CASQUE_CARTE) {
            return false;
        }
        // Dé 2 valeur Soleil <= 2 && qu'on peut avoir une Face >= Soleil 1  on va la prendre
        if (BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) <= VALEUR_SOLEIL_MINIMUM_DE2 && listAchetable.contains(Faces.SOLEIL_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 2 valeur Lune < 1 && qu'on peut avoir une Face >= Lune_1 on va la prendre
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2 && listAchetable.contains(Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Si on a un marteau en cours on va voir si on peut acheter une carte intérréssante
        if (this.getJoueur().getNextMarteau() != null) {
            return false;
        }
        // Autrement on va essayer d'optimiser nos pièces déjà présentes sur nos Dé
        else {
            return true;
        }
    }

    @Override
    public boolean faireTourSupplementaire() {
        List<Face> listAchetable = this.getJoueur().getPartie().getSanctuaire().getFaces();
        // Dé 1 valeur Or < VALEUR_OR_MINIMUM_DE1
        if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) < VALEUR_OR_MINIMUM_DE1 && listAchetable.contains(Faces.OR_3) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 1 valeur Lune <= 2
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) <= VALEUR_LUNE_MINIMUM_DE1 && listAchetable.contains(Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
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
        // Casque D'invisbilité dès que possible
        //if (this.getJoueur().getLune() >= PRIX_CASQUE_CARTE && nombreCartes(getJoueur().getPartie().getPlateau().getCartes(),Cartes.CASQUE) > 0) {
        //    return true;
        //}
        // Dé 2 valeur Soleil <= 2
        if (BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) <= VALEUR_SOLEIL_MINIMUM_DE2 && listAchetable.contains(Faces.SOLEIL_1) && this.getJoueur().getOr() >= 2) {
            return true;
        }
        // Dé 2 valeur Lune < 1
        if (BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2 && listAchetable.contains(Faces.LUNE_1) && this.getJoueur().getOr() >= 2) {
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
        // Si le joueur n'a pas de Marteau Forgeron et qu'il en reste
        if (this.getJoueur().getNextMarteau() == null && nombreCartes(cartes,Cartes.MARTEAU) > 0) {
            return Cartes.MARTEAU;
        }
        // Si le joueur n'a pas encore acheté son coffre du forgeron
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
        // Si il n'y a plus de marteau achetable il commence à faire une transition avec la carte Ancien
        if (nombreCartes(this.getJoueur().getPartie().getPlateau().getCartes(),Cartes.ANCIEN) == 0) {
            return Cartes.ANCIEN;
        }
        // Autrement, il économise et ne fait aucun choix
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
        // Même si normalement il ne va jamais prendre la carte SABOTS
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
        // Si il a le minimum de valeur par Dé et qu'il a un marteau
        if (BotUtils.valeurDe(VALEUR_OR,getJoueur().de1) <= VALEUR_OR_MINIMUM_DE1 && BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de1) < VALEUR_LUNE_MINIMUM_DE1 && BotUtils.valeurDe(VALEUR_SOLEIL,getJoueur().de2) < VALEUR_SOLEIL_MINIMUM_DE2 && BotUtils.valeurDe(VALEUR_LUNE,getJoueur().de2) < VALEUR_LUNE_MINIMUM_DE2 && this.getJoueur().getNextMarteau() != null) {
            return orChangeable/2;
        }
        return orChangeable;
    }
}