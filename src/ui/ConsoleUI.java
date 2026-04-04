package ui;

/**
 * Utilitaire pour les effets visuels de la console (Couleurs, Animations, Mise en forme).
 */
public class ConsoleUI {
    // Couleurs ANSI
    public static final String RESET = "\033[0m";
    public static final String GREEN = "\033[0;32m";
    public static final String BOLD_GREEN = "\033[1;32m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";
    public static final String YELLOW = "\033[0;33m";
    public static final String WHITE = "\033[1;37m";
    public static final String PURPLE = "\033[0;35m";

    /**
     * Efface l'écran de la console.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Affiche un texte avec un effet de machine à écrire.
     */
    public static void typeWrite(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(delay); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    /**
     * Affiche une barre de chargement réaliste.
     */
    public static void showProgressBar(String message) {
        System.out.print(CYAN + message + " [");
        for (int i = 0; i <= 20; i++) {
            System.out.print("■");
            try { Thread.sleep(50 + (int)(Math.random() * 100)); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println("] 100%" + RESET);
    }

    /**
     * Affiche un en-tête de section stylé.
     */
    public static void printHeader(String title) {
        System.out.println(WHITE + "\n" + "═".repeat(50));
        System.out.println("  " + title.toUpperCase());
        System.out.println("═".repeat(50) + RESET);
    }

    /**
     * Affiche un message de succès premium.
     */
    public static void printSuccess(String message) {
        System.out.println(BOLD_GREEN + "\n[✔] SUCCESS: " + message + RESET);
    }

    /**
     * Affiche un message d'erreur premium.
     */
    public static void printError(String message) {
        System.out.println(RED + "\n[✘] ERROR: " + message + RESET);
    }
}
