package ui;

import service.ClientService;
import service.CompteService;
import service.OperationService;
import java.util.Scanner;

/**
 * Menu principal de l'application Mobile Money Premium.
 * Gère la navigation et l'affichage stylé avec des animations.
 */
public class Menu {
    private final Scanner scanner;
    private final ClientMenu clientMenu;
    private final CompteMenu compteMenu;

    public Menu() {
        this.scanner = new Scanner(System.in);
        
        // Initialisation des services
        ClientService clientService = new ClientService();
        CompteService compteService = new CompteService();
        OperationService operationService = new OperationService();
        
        // Initialisation des menus spécifiques
        this.clientMenu = new ClientMenu(clientService, scanner);
        this.compteMenu = new CompteMenu(compteService, operationService, scanner);
    }

    private void displayHeader() {
        ConsoleUI.clearScreen();
        System.out.println(ConsoleUI.BOLD_GREEN);
        System.out.println("╔" + "═".repeat(108) + "╗");
        System.out.println("║ ███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███╗   ███╗ ██████╗ ███╗   ██╗███████╗██╗   ██╗ ║");
        System.out.println("║ ████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ████╗ ████║██╔═══██╗████╗  ██║██╔════╝╚██╗ ██╔╝ ║");
        System.out.println("║ ██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ██╔████╔██║██║   ██║██╔██╗ ██║█████╗   ╚████╔╝  ║");
        System.out.println("║ ██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ██║╚██╔╝██║██║   ██║██║╚██╗██║██╔══╝    ╚██╔╝   ║");
        System.out.println("║ ██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ██║ ╚═╝ ██║╚██████╔╝██║ ╚████║███████╗   ██║    ║");
        System.out.println("║ ╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   ╚═╝    ║");
        System.out.println("║                                                                                                    ║");
        System.out.println("║                             SÉCURISÉ • RAPIDE • FIABLE • v2.0                                      ║");
        System.out.println("╚" + "═".repeat(108) + "╝");
        System.out.println(ConsoleUI.RESET);
    }

    public void start() {
        boolean running = true;
        displayHeader();

        while (running) {
            printOptions();
            System.out.print("\n" + ConsoleUI.BLUE + "SÉLECTIONNEZ UNE OPTION > " + ConsoleUI.RESET);
            
            String input = scanner.nextLine();
            int choice;
            
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ConsoleUI.printError("Veuillez saisir un nombre entre 1 et 9.");
                continue;
            }

            switch (choice) {
                case 1 -> clientMenu.showAddClient();
                case 2 -> clientMenu.showListClients();
                case 3 -> compteMenu.showCreateAccount();
                case 4 -> compteMenu.showDepot();
                case 5 -> compteMenu.showRetrait();
                case 6 -> compteMenu.showTransfert();
                case 7 -> compteMenu.showPaiementMarchand();
                case 8 -> compteMenu.showHistory();
                case 9 -> {
                    ConsoleUI.showProgressBar("SHUTTING DOWN SYSTEM");
                    System.out.println(ConsoleUI.YELLOW + "\n[INFO] Déconnexion sécurisée...");
                    System.out.println("Merci d'avoir utilisé Mobile Money Premium. À bientôt !");
                    running = false;
                }
                default -> ConsoleUI.printError("Option invalide.");
            }
            
            if (running) {
                System.out.println("\n" + ConsoleUI.CYAN + "Appuyez sur Entrée pour revenir au menu principal..." + ConsoleUI.RESET);
                scanner.nextLine();
                displayHeader();
            }
        }
    }

    private void printOptions() {
        System.out.println(ConsoleUI.WHITE + "  " + "─".repeat(45));
        System.out.println("  TABLEAU DE BORD MOBILE MONEY");
        System.out.println("  " + "─".repeat(45) + ConsoleUI.RESET);
        System.out.println("  " + ConsoleUI.GREEN + "1" + ConsoleUI.RESET + " ➜ Ajouter un client");
        System.out.println("  " + ConsoleUI.GREEN + "2" + ConsoleUI.RESET + " ➜ Afficher les clients");
        System.out.println("  " + ConsoleUI.GREEN + "3" + ConsoleUI.RESET + " ➜ Créer un compte");
        System.out.println("  " + ConsoleUI.GREEN + "4" + ConsoleUI.RESET + " ➜ Dépôt d'argent");
        System.out.println("  " + ConsoleUI.GREEN + "5" + ConsoleUI.RESET + " ➜ Retrait d'argent");
        System.out.println("  " + ConsoleUI.GREEN + "6" + ConsoleUI.RESET + " ➜ Transfert de fonds");
        System.out.println("  " + ConsoleUI.GREEN + "7" + ConsoleUI.RESET + " ➜ Paiement marchand");
        System.out.println("  " + ConsoleUI.GREEN + "8" + ConsoleUI.RESET + " ➜ Historique des opérations");
        System.out.println("  " + ConsoleUI.RED + "9" + ConsoleUI.RESET + " ➜ Quitter");
        System.out.println(ConsoleUI.WHITE + "  " + "─".repeat(45) + ConsoleUI.RESET);
    }
}
