package service;

import dao.CompteDAO;
import dao.OperationDAO;
import model.Compte;
import model.Operation;
import model.Depot;
import model.Retrait;
import model.Transfert;
import model.Paiement;
import exception.SoldeInsuffisantException;
import exception.CompteIntrouvableException;
import java.util.List;
import java.time.LocalDate;

public class OperationService {
    private final CompteDAO    compteDAO;
    private final OperationDAO operationDAO;

    public OperationService() {
        this.compteDAO    = new CompteDAO();
        this.operationDAO = new OperationDAO();
    }

    public void depot(String numero, double montant) throws CompteIntrouvableException {
        Compte compte = findCompte(numero);
        compte.setSolde(compte.getSolde() + montant);
        compteDAO.updateSolde(compte);
        operationDAO.enregistrer(new Depot(montant, compte));
    }

    public void retrait(String numero, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte compte = findCompte(numero);
        if (compte.getSolde() < montant)
            throw new SoldeInsuffisantException("Solde insuffisant pour retirer " + montant + " FCFA.");
        compte.setSolde(compte.getSolde() - montant);
        compteDAO.updateSolde(compte);
        operationDAO.enregistrer(new Retrait(montant, compte));
    }

    public void transfert(String numSource, String numDest, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte source = findCompte(numSource);
        Compte dest   = findCompte(numDest);

        if (source.getSolde() < montant)
            throw new SoldeInsuffisantException("Solde insuffisant pour transférer " + montant + " FCFA.");

        source.setSolde(source.getSolde() - montant);
        compteDAO.updateSolde(source);
        dest.setSolde(dest.getSolde() + montant);
        compteDAO.updateSolde(dest);
        operationDAO.enregistrer(new Transfert(montant, source, dest));
    }

    // Vérifie que le marchand existe ET est de type MARCHAND
    public void paiementMarchand(String numeroClient, String codeMarchand, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {

        Compte compteClient = findCompte(numeroClient);

        Compte compteMarchand = compteDAO.findByNumero(codeMarchand);
        if (compteMarchand == null)
            throw new CompteIntrouvableException(
                "Le marchand '" + codeMarchand + "' n'existe pas dans le système.");

        if (!compteMarchand.isMarchand())
            throw new CompteIntrouvableException(
                "Le compte '" + codeMarchand + "' n'est pas un compte marchand.");

        if (compteClient.getSolde() < montant)
            throw new SoldeInsuffisantException("Solde insuffisant pour le paiement de " + montant + " FCFA.");

        compteClient.setSolde(compteClient.getSolde() - montant);
        compteDAO.updateSolde(compteClient);

        compteMarchand.setSolde(compteMarchand.getSolde() + montant);
        compteDAO.updateSolde(compteMarchand);

        operationDAO.enregistrer(new Paiement(montant, compteClient, codeMarchand));
    }

    public List<Operation> listOperations(String numero) throws CompteIntrouvableException {
        findCompte(numero);
        return operationDAO.findByCompte(numero);
    }

    public List<Operation> rechercherParDate(String numero, LocalDate date)
            throws CompteIntrouvableException {
        findCompte(numero);
        return operationDAO.findByDate(numero, date);
    }

    private Compte findCompte(String numero) throws CompteIntrouvableException {
        Compte c = compteDAO.findByNumero(numero);
        if (c == null)
            throw new CompteIntrouvableException("Compte " + numero + " introuvable.");
        return c;
    }
}
