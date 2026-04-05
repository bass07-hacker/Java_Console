package ui;

import service.CompteService;
import service.OperationService;
import model.Compte;
import model.Operation;
import exception.SoldeInsuffisantException;
import exception.CompteIntrouvableException;
import java.util.Scanner;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class CompteMenu {
    private final CompteService    compteService;
    private final OperationService operationService;
    private final Scanner          scanner;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public CompteMenu(CompteService compteService, OperationService operationService, Scanner scanner) {
        this.compteService    = compteService;
        this.operationService = operationService;
        this.scanner          = scanner;
    }

    private String formatCurrency(double amount) {
        return ConsoleUI.BOLD_GREEN + String.format("%,.0f FCFA", amount).replace(",", " ") + ConsoleUI.RESET;
    }

    public void showCreateAccount() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     CRÉATION DE COMPTE");

        System.out.print(ConsoleUI.CYAN + "ID Client propriétaire : " + ConsoleUI.RESET);
        int clientId = Integer.parseInt(scanner.nextLine());
        System.out.print(ConsoleUI.CYAN + "Numéro de compte (ex: ACC-001) : " + ConsoleUI.RESET);
        String numero = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Solde initial : " + ConsoleUI.RESET);
        double solde = Double.parseDouble(scanner.nextLine());

        ConsoleUI.showProgressBar("GÉNÉRATION DU COMPTE");

        try {
            compteService.createAccount(clientId, numero, solde);
            ConsoleUI.printSuccess("Compte n° " + numero + " créé avec succès.");
        } catch (Exception e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    public void showDepot() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("      DÉPÔT D'ARGENT");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Montant à déposer : " + ConsoleUI.RESET);
        double montant = Double.parseDouble(scanner.nextLine());

        ConsoleUI.showProgressBar("TRAITEMENT DU DÉPÔT");

        try {
            operationService.depot(num, montant);
            ConsoleUI.printSuccess("Dépôt réussi de " + formatCurrency(montant));
        } catch (CompteIntrouvableException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    public void showRetrait() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     RETRAIT D'ARGENT");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Montant à retirer : " + ConsoleUI.RESET);
        double montant = Double.parseDouble(scanner.nextLine());

        ConsoleUI.showProgressBar("VÉRIFICATION DU SOLDE ET RETRAIT");

        try {
            operationService.retrait(num, montant);
            ConsoleUI.printSuccess("Retrait réussi de " + formatCurrency(montant));
        } catch (CompteIntrouvableException | SoldeInsuffisantException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    public void showTransfert() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     TRANSFERT DE FONDS");

        System.out.print(ConsoleUI.CYAN + "Numéro compte source : " + ConsoleUI.RESET);
        String src = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Numéro compte destination : " + ConsoleUI.RESET);
        String dst = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Montant à transférer : " + ConsoleUI.RESET);
        double montant = Double.parseDouble(scanner.nextLine());

        ConsoleUI.showProgressBar("SÉCURISATION DU TRANSFERT");

        try {
            operationService.transfert(src, dst, montant);
            ConsoleUI.printSuccess("Transfert de " + formatCurrency(montant) + " effectué avec succès.");
        } catch (CompteIntrouvableException | SoldeInsuffisantException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    public void showPaiementMarchand() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("     PAIEMENT MARCHAND");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Code Marchand : " + ConsoleUI.RESET);
        String code = scanner.nextLine();
        System.out.print(ConsoleUI.CYAN + "Montant : " + ConsoleUI.RESET);
        double montant = Double.parseDouble(scanner.nextLine());

        ConsoleUI.showProgressBar("VALIDATION DU PAIEMENT");

        try {
            operationService.paiementMarchand(num, code, montant);
            ConsoleUI.printSuccess("Paiement de " + formatCurrency(montant) + " au marchand " + code + " réussi.");
        } catch (CompteIntrouvableException | SoldeInsuffisantException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    public void showHistory() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("   HISTORIQUE DES OPÉRATIONS");

        System.out.print(ConsoleUI.CYAN + "Numéro de compte : " + ConsoleUI.RESET);
        String num = scanner.nextLine();

        ConsoleUI.showProgressBar("EXTRACTION DES DONNÉES");

        try {
            List<Operation> operations = operationService.listOperations(num);
            if (operations.isEmpty()) {
                System.out.println(ConsoleUI.YELLOW + "Aucune opération trouvée pour ce compte." + ConsoleUI.RESET);
            } else {
                System.out.println(ConsoleUI.BOLD_GREEN +
                    String.format("%-15s | %-25s | %-15s", "DATE", "TYPE", "MONTANT") + ConsoleUI.RESET);
                System.out.println(ConsoleUI.WHITE + "-".repeat(60) + ConsoleUI.RESET);
                for (Operation op : operations) {
                    // getDateOperation() → LocalDate → on formate avec toString()
                    // getTypeOperation() au lieu de getType()
                    // getMontant() est correct
                    System.out.printf("%-15s | %-25s | %-15s\n",
                        op.getDateOperation().toString(),
                        op.getTypeOperation(),
                        formatCurrency(op.getMontant()));
                }
            }
        } catch (CompteIntrouvableException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }
}
