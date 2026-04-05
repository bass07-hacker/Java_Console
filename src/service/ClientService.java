package service;

import dao.ClientDAO;
import model.Client;
import java.util.List;

/**
 * Service gérant la logique métier des clients.
 */
public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Ajoute un nouveau client dans le système.
     */
    public void addClient(String nom, String prenom, String telephone) {
        Client client = new Client(nom, prenom, telephone);
        clientDAO.save(client);
    }

    /**
     * Récupère la liste de tous les clients.
     */
    public List<Client> listClients() {
        return clientDAO.findAll();
    }
}
