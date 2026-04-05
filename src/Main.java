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
        System.out.println(ConsoleUI.CYAN + "MOBILE MONEY TERMINAL v2.0 - INITIALIZING..." + ConsoleUI.RESET);
        ConsoleUI.showProgressBar("CONNECTING TO BANKING SERVER");
        ConsoleUI.showProgressBar("LOADING SECURITY MODULES");
        ConsoleUI.showProgressBar("VERIFYING DATABASE INTEGRITY");
        
        System.out.println(ConsoleUI.BOLD_GREEN + "\n[OK] SYSTEM READY." + ConsoleUI.RESET);
        try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        // Lancement du menu principal
        Menu mainMenu = new Menu();
        mainMenu.start();
    }
}
