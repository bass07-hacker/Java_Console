import ui.ConsoleUI;
import ui.Menu;

/**
 * Point d'entrée de l'application Mobile Money Premium.
 * Initialise l'interface utilisateur et lance le système avec des animations.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleUI.clearScreen();
        
        // Animation de démarrage premium
        System.out.println(ConsoleUI.CYAN + "MOBILE MONEY TERMINAL v2.0 - INITIALISATION..." + ConsoleUI.RESET);
        ConsoleUI.showProgressBar("CONNECTION AU SERVEUR");
        ConsoleUI.showProgressBar("CHARGEMENT DES MODULES DE SECURITE");
        ConsoleUI.showProgressBar("VERIFICATION DE LA BASE DE DONNEES");
        
        System.out.println(ConsoleUI.BOLD_GREEN + "\n[OK] SYSTEM READY." + ConsoleUI.RESET);
        try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        // Lancement du menu principal
        Menu mainMenu = new Menu();
        mainMenu.start();
    }
}
