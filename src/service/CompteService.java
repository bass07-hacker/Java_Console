package service;

import dao.CompteDAO;
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

    // Lance une exception si le client n'existe pas
    // → le menu catch l'erreur et affiche le bon message
    public void createAccount(int clientId, String numero, double soldeInitial, String typeCompte)
            throws CompteIntrouvableException {

        Client client = clientDAO.findById(clientId);
        if (client == null)
            throw new CompteIntrouvableException(
                "Aucun client trouvé avec l'ID " + clientId + ". Vérifiez et réessayez.");

        Compte compte = new Compte(numero, soldeInitial, client, typeCompte);
        compteDAO.creerCompte(compte);
    }

    public Compte findByNumero(String numero) throws CompteIntrouvableException {
        Compte c = compteDAO.findByNumero(numero);
        if (c == null)
            throw new CompteIntrouvableException("Le compte n° " + numero + " est introuvable.");
        return c;
    }
}
