package ui;

import service.ClientService;
import model.Client;
import java.util.Scanner;
import java.util.List;

public class ClientMenu {
    private final ClientService clientService;
    private final Scanner scanner;

    public ClientMenu(ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.scanner = scanner;
    }

    public void showAddClient() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("   AJOUT D'UN NOUVEAU CLIENT");

        System.out.print(ConsoleUI.CYAN + "Nom : " + ConsoleUI.RESET);
        String nom = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Prénom : " + ConsoleUI.RESET);
        String prenom = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Téléphone : " + ConsoleUI.RESET);
        String tel = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Adresse : " + ConsoleUI.RESET);
        String adresse = scanner.nextLine();

        ConsoleUI.showProgressBar("ENREGISTREMENT DU CLIENT");

        try {
            clientService.addClient(nom, prenom, tel, adresse);
            ConsoleUI.printSuccess("Client " + nom + " " + prenom + " enregistré avec succès.");
        } catch (Exception e) {
            ConsoleUI.printError("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }

    public void showListClients() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("      LISTE DES CLIENTS");

        List<Client> clients = clientService.listClients();
        if (clients.isEmpty()) {
            System.out.println(ConsoleUI.YELLOW + "Aucun client enregistré." + ConsoleUI.RESET);
        } else {
            System.out.println(ConsoleUI.BOLD_GREEN +
                String.format("%-5s | %-15s | %-15s | %-12s", "ID", "NOM", "PRÉNOM", "TÉLÉPHONE") +
                ConsoleUI.RESET);
            System.out.println(ConsoleUI.WHITE + "-".repeat(55) + ConsoleUI.RESET);
            for (Client c : clients) {
                System.out.printf("%-5d | %-15s | %-15s | %-12s\n",
                    c.getId(), c.getNom(), c.getPrenom(), c.getTelephone());
            }
        }
    }
}
