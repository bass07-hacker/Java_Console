package dao;

import database.DatabaseConnection;
import model.Client;
import model.Compte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO {

    public void creerCompte(Compte compte) {
        String sql = "INSERT INTO COMPTE (numero_compte, solde, client_id, type_compte) VALUES (?, ?, ?, ?)";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setString(1, compte.getNumeroCompte());
            P.setDouble(2, compte.getSolde());
            P.setInt(3, compte.getClient().getId());
            P.setString(4, compte.getTypeCompte());
            P.executeUpdate();
            System.out.println("✅ Compte " + compte.getTypeCompte() + " créé. Numéro : " + compte.getNumeroCompte());
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la création du compte : " + e.getMessage());
        }
    }

    public Compte findByNumero(String numeroCompte) {
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.telephone, cl.adresse " +
                     "FROM COMPTE c JOIN CLIENT cl ON c.client_id = cl.id " +
                     "WHERE c.numero_compte = ?";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setString(1, numeroCompte);
            ResultSet r = P.executeQuery();
            if (r.next()) return mapToCompte(r);
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la recherche : " + e.getMessage());
        }
        return null;
    }

    public Compte findByClientId(int clientId) {
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.telephone, cl.adresse " +
                     "FROM COMPTE c JOIN CLIENT cl ON c.client_id = cl.id " +
                     "WHERE c.client_id = ?";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setInt(1, clientId);
            ResultSet r = P.executeQuery();
            if (r.next()) return mapToCompte(r);
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la recherche : " + e.getMessage());
        }
        return null;
    }

    public List<Compte> findAll() {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.telephone, cl.adresse " +
                     "FROM COMPTE c JOIN CLIENT cl ON c.client_id = cl.id";

        try (Statement st = DatabaseConnection.getConnection().createStatement();
             ResultSet r  = st.executeQuery(sql)) {
            while (r.next()) comptes.add(mapToCompte(r));
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération : " + e.getMessage());
        }
        return comptes;
    }

    public void updateSolde(Compte compte) {
        String sql = "UPDATE COMPTE SET solde = ? WHERE numero_compte = ?";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setDouble(1, compte.getSolde());
            P.setString(2, compte.getNumeroCompte());
            P.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du solde : " + e.getMessage());
        }
    }

    private Compte mapToCompte(ResultSet r) throws SQLException {
        Client client = new Client(
            r.getString("nom"),
            r.getString("prenom"),
            r.getString("telephone"),
            r.getString("adresse")
        );
        client.setId(r.getInt("client_id"));

        String type = r.getString("type_compte");
        if (type == null) type = "CLIENT";

        Compte compte = new Compte(
            r.getString("numero_compte"),
            r.getDouble("solde"),
            client,
            type
        );
        compte.setId(r.getInt("id"));
        return compte;
    }
}
