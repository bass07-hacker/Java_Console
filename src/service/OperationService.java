package service;

import dao.CompteDAO;
import dao.OperationDAO;
import model.Compte;
import model.Operation;
import exception.SoldeInsuffisantException;
import exception.CompteIntrouvableException;
import java.util.List;
import java.util.Date;

/**
 * Service gérant les opérations financières (Dépôt, Retrait, Transfert, Paiement).
 */
public class OperationService {
    private final CompteDAO compteDAO;
    private final OperationDAO operationDAO;

    public OperationService() {
        this.compteDAO = new CompteDAO();
        this.operationDAO = new OperationDAO();
    }

    /**
     * Effectue un dépôt sur un compte.
     */
    public void depot(String numero, double montant) throws CompteIntrouvableException {
        Compte compte = findCompte(numero);
        compte.setSolde(compte.getSolde() + montant);
        compteDAO.update(compte);
        
        saveOperation(numero, "DEPOT", montant);
    }

    /**
     * Effectue un retrait avec vérification du solde.
     */
    public void retrait(String numero, double montant) throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte compte = findCompte(numero);
        if (compte.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour retirer " + montant + " FCFA.");
        }
        
        compte.setSolde(compte.getSolde() - montant);
        compteDAO.update(compte);
        
        saveOperation(numero, "RETRAIT", montant);
    }

    /**
     * Effectue un transfert entre deux comptes.
     */
    public void transfert(String numSource, String numDest, double montant) 
            throws CompteIntrouvableException, SoldeInsuffisantException {
        
        Compte source = findCompte(numSource);
        Compte dest = findCompte(numDest);
        
        if (source.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour transférer " + montant + " FCFA.");
        }
        
        // Débit source
        source.setSolde(source.getSolde() - montant);
        compteDAO.update(source);
        saveOperation(numSource, "TRANSFERT_SORTANT", montant);
        
        // Crédit destination
        dest.setSolde(dest.getSolde() + montant);
        compteDAO.update(dest);
        saveOperation(numDest, "TRANSFERT_ENTRANT", montant);
    }

    /**
     * Effectue un paiement marchand.
     */
    public void paiementMarchand(String numero, String codeMarchand, double montant) 
            throws CompteIntrouvableException, SoldeInsuffisantException {
        
        retrait(numero, montant); // Réutilise la logique de retrait
        saveOperation(numero, "PAIEMENT_MARCHAND (" + codeMarchand + ")", montant);
    }

    /**
     * Récupère l'historique des opérations d'un compte.
     */
    public List<Operation> listOperations(String numero) throws CompteIntrouvableException {
        findCompte(numero); // Vérifie l'existence
        return operationDAO.findByCompte(numero);
    }

    private Compte findCompte(String numero) throws CompteIntrouvableException {
        Compte c = compteDAO.findByNumero(numero);
        if (c == null) throw new CompteIntrouvableException("Compte " + numero + " introuvable.");
        return c;
    }

    private void saveOperation(String numero, String type, double montant) {
        Operation op = new Operation(type, montant, new Date(), numero);
        operationDAO.save(op);
    }
}
