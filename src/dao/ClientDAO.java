package dao;

import database.DatabaseConnection;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    // -------------------------------------------------------
    //  Ajouter un client dans la base
    // -------------------------------------------------------
    public void ajouter(Client client) {
        String sql = "INSERT INTO CLIENT (nom, prenom, telephone, adresse) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());
            ps.setString(4, client.getAdresse());
            ps.executeUpdate();
            System.out.println("✅ Client ajouté avec succès.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Ce numéro de téléphone est déjà utilisé.");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Récupérer tous les clients
    // -------------------------------------------------------
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM CLIENT ORDER BY nom, prenom";

        try (Statement st = DatabaseConnection.getConnection().createStatement();
             ResultSet r  = st.executeQuery(sql)) {

            while (r.next()) {
                clients.add(mapToClient(r));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération : " + e.getMessage());
        }
        return clients;
    }

    // -------------------------------------------------------
    //  Rechercher un client par son numéro de téléphone
    // -------------------------------------------------------
    public Client findByTelephone(String telephone) {
        String sql = "SELECT * FROM CLIENT WHERE telephone = ?";

        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, telephone);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                return mapToClient(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la recherche : " + e.getMessage());
        }
        return null;
    }

    // -------------------------------------------------------
    //  Rechercher un client par son id
    // -------------------------------------------------------
    public Client findById(int id) {
        String sql = "SELECT * FROM CLIENT WHERE id = ?";

        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                return mapToClient(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la recherche : " + e.getMessage());
        }
        return null;
    }

    // -------------------------------------------------------
    //  Convertir une ligne SQL en objet Client
    // -------------------------------------------------------
    private Client mapToClient(ResultSet r) throws SQLException {
        Client client = new Client(
            r.getString("nom"),
            r.getString("prenom"),
            r.getString("telephone"),
            r.getString("adresse")
        );
        client.setId(r.getInt("id"));
        return client;
    }
}
