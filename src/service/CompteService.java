package service;

// import dao.CompteDAO;
import model.Compte;
import exception.CompteIntrouvableException;

/**
 * Service gérant la logique métier des comptes bancaires.
 */
public class CompteService {
    private final CompteDAO compteDAO;

    public CompteService() {
        this.compteDAO = new CompteDAO();
    }

    /**
     * Crée un nouveau compte pour un client.
     */
    public void createAccount(int clientId, String numero, double soldeInitial) {
        Compte compte = new Compte(numero, soldeInitial, clientId);
        compteDAO.save(compte);
    }

    /**
     * Recherche un compte par son numéro unique.
     */
    public Compte findByNumero(String numero) throws CompteIntrouvableException {
        Compte c = compteDAO.findByNumero(numero);
        if (c == null) {
            throw new CompteIntrouvableException("Le compte n° " + numero + " est introuvable.");
        }
        return c;
    }
}
