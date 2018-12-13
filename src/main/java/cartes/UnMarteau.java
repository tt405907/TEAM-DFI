package cartes;

import joueur.Joueur;

/**
 * Un Marteau est obtainable grace à la carte marteau du forgeron et permet de
 * transformer l'or obtenu en potentiels points de victoires
 */
public class UnMarteau {

	/**
	 * Un marteau contient un jeton qui traque l'avancement sur la carte
	 */
	private int jeton;
	private Joueur joueur;

	public UnMarteau(Joueur j) {
		jeton = 0;
		joueur = j;
	}

	/**
	 * effetAddor est déclenchée quand le joueur j veut placer de l'or dans un de
	 * ses marteaux et qu'il en possède au moins un.
	 * 
	 * @param orAPlacer : L'or que le joueur veut placer dans son marteau
	 * @return 0 si l'achat s'est déroulé classiquement ; 1 si la première face se
	 *         complète ; 2 si le marteau est terminé ; 3 si le marteau est terminé
	 *         et qu'un nouveau est commencé.
	 */
	public int effetAddOr(int orAPlacer) {
		// cas où on dépasse ou atteint les 30 et finit le marteau,
		// on supprime alors ce dernier
		if (jeton + orAPlacer >= 30) {
			if (jeton < 15)
				joueur.addVictoire(10);
			joueur.addVictoire(15);
			joueur.delMarteau(this);

			// cas où il y a un autre marteau après le marteau actuel,
			// on recommence alors avec l'or à changer restant sur le marteau suivant
			UnMarteau m = joueur.getNextMarteau();
			if (m != null) {
				m.effetAddOr(orAPlacer + jeton - 30);
				return 3;
			}
			// sinon il faut rendre au joueur l'or non ajouté, cette fois ci le addor ne
			// passera pas par la boucle avec les marteaux car on sait que le joueur n'a
			// plus de marteau
			else
				joueur.addOr(orAPlacer + jeton - 30);
			return 2;

		} // cas où on finit la première face du marteau
		else if (jeton < 15 && jeton + orAPlacer >= 15) {
			joueur.addVictoire(10);
			jeton = jeton + orAPlacer;
			return 1;
		} // cas de base
		else {
			jeton = jeton + orAPlacer;
			return 0;
		}

	}

}
