package service;

import dao.ClientDAO;
import model.Client;
import java.util.List;

public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    // Client(nom, prenom, telephone) → il faut 4 args (nom, prenom, telephone, adresse)
    public void addClient(String nom, String prenom, String telephone, String adresse) {
        Client client = new Client(nom, prenom, telephone, adresse);
        clientDAO.ajouter(client);  // la méthode s'appelle ajouter(), pas save()
    }

    public List<Client> listClients() {
        return clientDAO.findAll();
    }
}
