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
        compteDAO.updateSolde(compte);   // updateSolde(), pas update()

        operationDAO.enregistrer(new Depot(montant, compte));  // enregistrer(), pas save()
    }

    public void retrait(String numero, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte compte = findCompte(numero);
        if (compte.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour retirer " + montant + " FCFA.");
        }
        compte.setSolde(compte.getSolde() - montant);
        compteDAO.updateSolde(compte);

        operationDAO.enregistrer(new Retrait(montant, compte));
    }

    public void transfert(String numSource, String numDest, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte source = findCompte(numSource);
        Compte dest   = findCompte(numDest);

        if (source.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour transférer " + montant + " FCFA.");
        }

        source.setSolde(source.getSolde() - montant);
        compteDAO.updateSolde(source);

        dest.setSolde(dest.getSolde() + montant);
        compteDAO.updateSolde(dest);

        operationDAO.enregistrer(new Transfert(montant, source, dest));
    }

    public void paiementMarchand(String numero, String codeMarchand, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte compte = findCompte(numero);
        if (compte.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour le paiement de " + montant + " FCFA.");
        }
        compte.setSolde(compte.getSolde() - montant);
        compteDAO.updateSolde(compte);

        operationDAO.enregistrer(new Paiement(montant, compte, codeMarchand));
    }

    public List<Operation> listOperations(String numero) throws CompteIntrouvableException {
        findCompte(numero);
        return operationDAO.findByCompte(numero);
    }

    private Compte findCompte(String numero) throws CompteIntrouvableException {
        Compte c = compteDAO.findByNumero(numero);
        if (c == null) throw new CompteIntrouvableException("Compte " + numero + " introuvable.");
        return c;
    }
}
