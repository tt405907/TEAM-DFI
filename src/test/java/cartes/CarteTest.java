package cartes;

import joueur.BotDefault;
import joueur.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarteTest {
    private Joueur joueur;
    private Carte carte;

    @BeforeEach
    void setBefore()
    {
        joueur = new BotDefault("joeur");
        carte = new Carte("Biloute",5,5,10);
    }

    @Test
    void peutAcheter() {
        // Le joueur à son initialement ne dispose pas d'or , de soleil ou de points de victoires donc le joueur ne peut pas acheter cette carte
        assertEquals(false, carte.peutAcheter(joueur));
        // Nous ajoutons des ressources pour qu'il puisse acheter la carte
        joueur.addSoleil(5);
        joueur.addLune(5);
        // Test d'achat avec les ressources adéquate pour son achat
        assertEquals(true, carte.peutAcheter(joueur));
    }

    @Test
    void acheter()
    {
        // Pour cette méthode on part du principe qu'on a  utilisé la méthode peutAcheter et qu'on dispose donc des ressources adéquate à l'achat de la carte en question
        joueur.addSoleil(5);
        joueur.addLune(5);
        // On effectue l'achat
        carte.acheter(joueur);
        // Vérification du retrait des ressources nécessaire à l'achat et l'ajout des points de victoires associés à la carte au joueur
        assertEquals(0,joueur.getSoleil());
        assertEquals(0,joueur.getLune());
        assertEquals(10,joueur.getVictoire());
    }

    @Test
    void getPrixLune()
    {
        // Test sur l'accés à la ressource Lune de notre carte
        assertEquals(5,carte.getPrixLune());
    }

    @Test
    void getPrixSoleil()
    {
        // Test sur l'accés à la ressource Soleil de notre carte
        assertEquals(5,carte.getPrixSoleil());
    }

    @Test
    void getVictoire()
    {
        // Test sur l'accés à la ressource Victoire de notre carte
        assertEquals(10,carte.getVictoire());
    }

}