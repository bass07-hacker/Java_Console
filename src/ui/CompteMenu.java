package ui;

import service.CompteService;
import service.OperationService;
import model.Operation;
import exception.SoldeInsuffisantException;
import exception.CompteIntrouvableException;
import java.util.Scanner;
import java.util.List;

public class CompteMenu {
    private final CompteService    compteService;
    private final OperationService operationService;
    private final Scanner          scanner;

    public CompteMenu(CompteService compteService, OperationService operationService, Scanner scanner) {
        this.compteService    = compteService;
        this.operationService = operationService;
        this.scanner          = scanner;
    }

    // -------------------------------------------------------
    //  Helpers saisie sécurisée
    // -------------------------------------------------------
    private int saisiEntier(String label) {
        while (true) {
            System.out.print(ConsoleUI.CYAN + label + ConsoleUI.RESET);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                ConsoleUI.printError("Valeur invalide. Veuillez saisir un nombre entier.");
            }
        }
    }

    private double saisiDecimal(String label) {
        while (true) {
            System.out.print(ConsoleUI.CYAN + label + ConsoleUI.RESET);
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                if (v <= 0) { ConsoleUI.printError("Le montant doit être supérieur à 0."); continue; }
                return v;
            } catch (NumberFormatException e) {
                ConsoleUI.printError("Valeur invalide. Veuillez saisir un montant (ex: 5000).");
            }
        }
    }

    private String formatCurrency(double amount) {
        return ConsoleUI.BOLD_GREEN + String.format("%,.0f FCFA", amount).replace(",", " ") + ConsoleUI.RESET;
    }

    // -------------------------------------------------------
    //  Menu visuel type de compte
    // -------------------------------------------------------
    private String choisirTypeCompte() {
        while (true) {
            System.out.println();
            System.out.println(ConsoleUI.WHITE + "  ┌─────────────────────────────┐");
            System.out.println("  │     TYPE DE COMPTE          │");
            System.out.println("  ├─────────────────────────────┤");
            System.out.println("  │  " + ConsoleUI.GREEN + "1" + ConsoleUI.WHITE + "  ➜  Compte CLIENT        │");
            System.out.println("  │  " + ConsoleUI.GREEN + "2" + ConsoleUI.WHITE + "  ➜  Compte MARCHAND      │");
            System.out.println("  └─────────────────────────────┘" + ConsoleUI.RESET);
            System.out.print(ConsoleUI.CYAN + "  Votre choix : " + ConsoleUI.RESET);
            switch (scanner.nextLine().trim()) {
                case "1": return "CLIENT";
                case "2": return "MARCHAND";
                default:  ConsoleUI.printError("Choix invalide. Tapez 1 ou 2.");
            }
        }
    }

    // -------------------------------------------------------
    //  Création de compte
    // -------------------------------------------------------
    public void showCreateAccount() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     CRÉATION DE COMPTE");

        int    clientId   = saisiEntier("ID du propriétaire : ");
        System.out.print(ConsoleUI.CYAN + "Numéro de compte (ex: ACC-001) : " + ConsoleUI.RESET);
        String numero     = scanner.nextLine().trim();
        double solde      = saisiDecimal("Solde initial : ");
        String typeCompte = choisirTypeCompte();

        ConsoleUI.showProgressBar("GÉNÉRATION DU COMPTE");

        try {
            compteService.createAccount(clientId, numero, solde, typeCompte);
            ConsoleUI.printSuccess("Compte " + typeCompte + " n° " + numero + " créé avec succès.");
        } catch (CompteIntrouvableException e) {
            // Client introuvable → affiche l'erreur, PAS de succès
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Dépôt
    // -------------------------------------------------------
    public void showDepot() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("      DÉPÔT D'ARGENT");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num     = scanner.nextLine().trim();
        double montant = saisiDecimal("Montant à déposer : ");

        ConsoleUI.showProgressBar("TRAITEMENT DU DÉPÔT");

        try {
            operationService.depot(num, montant);
            ConsoleUI.printSuccess("Dépôt réussi de " + formatCurrency(montant));
        } catch (CompteIntrouvableException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Retrait
    // -------------------------------------------------------
    public void showRetrait() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     RETRAIT D'ARGENT");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num     = scanner.nextLine().trim();
        double montant = saisiDecimal("Montant à retirer : ");

        ConsoleUI.showProgressBar("VÉRIFICATION DU SOLDE ET RETRAIT");

        try {
            operationService.retrait(num, montant);
            ConsoleUI.printSuccess("Retrait réussi de " + formatCurrency(montant));
        } catch (CompteIntrouvableException | SoldeInsuffisantException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Transfert
    // -------------------------------------------------------
    public void showTransfert() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     TRANSFERT DE FONDS");

        System.out.print(ConsoleUI.CYAN + "Numéro compte source : " + ConsoleUI.RESET);
        String src     = scanner.nextLine().trim();
        System.out.print(ConsoleUI.CYAN + "Numéro compte destination : " + ConsoleUI.RESET);
        String dst     = scanner.nextLine().trim();
        double montant = saisiDecimal("Montant à transférer : ");

        ConsoleUI.showProgressBar("SÉCURISATION DU TRANSFERT");

        try {
            operationService.transfert(src, dst, montant);
            ConsoleUI.printSuccess("Transfert de " + formatCurrency(montant) + " effectué avec succès.");
        } catch (CompteIntrouvableException | SoldeInsuffisantException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Paiement marchand
    // -------------------------------------------------------
    public void showPaiementMarchand() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     PAIEMENT MARCHAND");

        System.out.print(ConsoleUI.CYAN + "Numéro de votre compte : " + ConsoleUI.RESET);
        String num     = scanner.nextLine().trim();
        System.out.print(ConsoleUI.CYAN + "Numéro du compte marchand : " + ConsoleUI.RESET);
        String code    = scanner.nextLine().trim();
        double montant = saisiDecimal("Montant : ");

        ConsoleUI.showProgressBar("VALIDATION DU PAIEMENT");

        try {
            operationService.paiementMarchand(num, code, montant);
            ConsoleUI.printSuccess("Paiement de " + formatCurrency(montant) + " au marchand " + code + " réussi.");
        } catch (CompteIntrouvableException | SoldeInsuffisantException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Historique
    // -------------------------------------------------------
    public void showHistory() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("   HISTORIQUE DES OPÉRATIONS");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num = scanner.nextLine().trim();

        ConsoleUI.showProgressBar("EXTRACTION DES DONNÉES");

        try {
            afficherOperations(operationService.listOperations(num));
        } catch (CompteIntrouvableException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Recherche par date
    // -------------------------------------------------------
    public void showRechercheParDate() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("   RECHERCHE PAR DATE");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num = scanner.nextLine().trim();

        java.time.LocalDate date = null;
        while (date == null) {
            System.out.print(ConsoleUI.CYAN + "Date (AAAA-MM-JJ) : " + ConsoleUI.RESET);
            try {
                date = java.time.LocalDate.parse(scanner.nextLine().trim());
            } catch (java.time.format.DateTimeParseException e) {
                ConsoleUI.printError("Format invalide. Utilisez AAAA-MM-JJ (ex: 2026-04-01).");
            }
        }

        ConsoleUI.showProgressBar("RECHERCHE EN COURS");

        try {
            afficherOperations(operationService.rechercherParDate(num, date));
        } catch (CompteIntrouvableException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Affichage tableau opérations
    // -------------------------------------------------------
    private void afficherOperations(List<Operation> operations) {
        if (operations.isEmpty()) {
            System.out.println(ConsoleUI.YELLOW + "Aucune opération trouvée." + ConsoleUI.RESET);
        } else {
            System.out.println(ConsoleUI.BOLD_GREEN +
                String.format("%-15s | %-25s | %-15s", "DATE", "TYPE", "MONTANT") + ConsoleUI.RESET);
            System.out.println(ConsoleUI.WHITE + "-".repeat(60) + ConsoleUI.RESET);
            for (Operation op : operations) {
                System.out.printf("%-15s | %-25s | %-15s\n",
                    op.getDateOperation().toString(),
                    op.getTypeOperation(),
                    formatCurrency(op.getMontant()));
            }
        }
    }
}
