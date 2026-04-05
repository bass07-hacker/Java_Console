package dao;

import database.DatabaseConnection;
import model.Client;
import model.Compte;

import java.sql.*;

public class CompteDAO {

    // -------------------------------------------------------
    //  Créer un compte en base
    // -------------------------------------------------------
    public void creerCompte(Compte compte) {
        String sql = "INSERT INTO COMPTE (numero_compte, solde, client_id) VALUES (?, ?, ?)";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setString(1, compte.getNumeroCompte());
            P.setDouble(2, compte.getSolde());
            P.setInt(3, compte.getClient().getId());
            P.executeUpdate();
            System.out.println("✅ Compte créé avec succès. Numéro : " + compte.getNumeroCompte());
        } catch (SQLException e) {
            System.out.println("❌ Erreur lor de la création du compte : " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Rechercher un compte par numéro
    // -------------------------------------------------------
    public Compte findByNumero(String numeroCompte) {
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.telephone, cl.adresse " +
                     "FROM COMPTE c " +
                     "JOIN CLIENT cl ON c.client_id = cl.id " +
                     "WHERE c.numero_compte = ?";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setString(1, numeroCompte);
            Result r = P.executeQuery();
            if (r.next()) {
                return mapToCompte(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lor de la recherche : " + e.getMessage());
        }
        return null;
    }

    // -------------------------------------------------------
    //  Rechercher le compte d'un client par son id
    // -------------------------------------------------------
    public Compte findByClientId(int clientId) {
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.telephone, cl.adresse " +
                     "FROM COMPTE c " +
                     "JOIN CLIENT cl ON c.client_id = cl.id " +
                     "WHERE c.client_id = ?";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setInt(1, clientId);
            Result r = P.executeQuery();
            if (r.next()) {
                return mapToCompte(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lor de la recherche : " + e.getMessage());
        }
        return null;
    }

    // -------------------------------------------------------
    //  Mettre à jour le solde d'un compte
    // -------------------------------------------------------
    public void updateSolde(Compte compte) {
        String sql = "UPDATE COMPTE SET solde = ? WHERE numero_compte = ?";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setDouble(1, compte.getSolde());
            P.setString(2, compte.getNumeroCompte());
            P.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lor de la mise à jour du solde : " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Convertir une ligne SQL en objet Compte
    // -------------------------------------------------------
    private Compte mapToCompte(Result r) throws SQLException {
        Client client = new Client(
            r.getString("nom"),
            r.getString("prenom"),
            r.getString("telephone"),
            r.getString("adresse")
        );
        client.setId(r.getInt("client_id"));

        Compte compte = new Compte(
            r.getString("numero_compte"),
            r.getDouble("solde"),
            client
        );
        compte.setId(r.getInt("id"));
        return compte;
    }
}