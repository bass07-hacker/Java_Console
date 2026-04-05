package service;

import dao.CompteDAO;       // l'import était commenté !
import dao.ClientDAO;
import model.Compte;
import model.Client;
import exception.CompteIntrouvableException;

public class CompteService {
    private final CompteDAO compteDAO;
    private final ClientDAO clientDAO;

    public CompteService() {
        this.compteDAO = new CompteDAO();
        this.clientDAO = new ClientDAO();
    }

    // clientId (int) → on charge le Client depuis la BD pour construire Compte
    public void createAccount(int clientId, String numero, double soldeInitial) {
        Client client = clientDAO.findById(clientId);
        if (client == null) {
            System.out.println("❌ Client introuvable avec l'id : " + clientId);
            return;
        }
        Compte compte = new Compte(numero, soldeInitial, client);
        compteDAO.creerCompte(compte);  // la méthode s'appelle creerCompte(), pas save()
    }

    public Compte findByNumero(String numero) throws CompteIntrouvableException {
        Compte c = compteDAO.findByNumero(numero);
        if (c == null) {
            throw new CompteIntrouvableException("Le compte n° " + numero + " est introuvable.");
        }
        return c;
    }
}
