package dao;

import database.DatabaseConnection;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO {

    // -------------------------------------------------------
    //  Enregistrer une opération en base
    // -------------------------------------------------------
    public void enregistrer(Operation operation) {
        String sql = "INSERT INTO OPERATION (type_operation, montant, date_operation, " +
                     "compte_source, compte_destination, marchand) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setString(1, operation.getTypeOperation());
            P.setDouble(2, operation.getMontant());
            P.setDate(3, Date.valueOf(operation.getDateOperation()));

            if (operation instanceof Retrait) {
                P.setString(4, ((Retrait) operation).getCompteSource().getNumeroCompte());
                P.setNull(5, Types.VARCHAR);
                P.setNull(6, Types.VARCHAR);

            } else if (operation instanceof Depot) {
                P.setNull(4, Types.VARCHAR);
                P.setString(5, ((Depot) operation).getCompteDestination().getNumeroCompte());
                P.setNull(6, Types.VARCHAR);

            } else if (operation instanceof Transfert) {
                P.setString(4, ((Transfert) operation).getCompteSource().getNumeroCompte());
                P.setString(5, ((Transfert) operation).getCompteDestination().getNumeroCompte());
                P.setNull(6, Types.VARCHAR);

            } else if (operation instanceof Paiement) {
                P.setString(4, ((Paiement) operation).getCompteSource().getNumeroCompte());
                P.setNull(5, Types.VARCHAR);
                P.setString(6, ((Paiement) operation).getNomMarchand());
            }

            P.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lor de l'enregistrement : " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    //  Récupérer toutes les opérations
    // -------------------------------------------------------
    public List<Operation> findAll() {
        List<Operation> operation = new ArrayList<>();
        String sql = "SELECT * FROM OPERATION ORDER BY date_operation DESC";

        try (Statement st = DatabaseConnection.getConnection().createStatement();
             Result r = st.executeQuery(sql)) {

            while (r.next()) {
                operation.add(Operation(r));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        return operation;
    }

    // -------------------------------------------------------
    //  Récupérer les opérations d'un compte
    // -------------------------------------------------------
    public List<Operation> findByCompte(String numeroCompte) {
        List<Operation> operation = new ArrayList<>();
        String sql = "SELECT * FROM OPERATION " +
                     "WHERE compte_source = ? OR compte_destination = ? " +
                     "ORDER BY date_operation DESC";

        try (PreparedStatement P = DatabaseConnection.getConnection().prepareStatement(sql)) {
            P.setString(1, numeroCompte);
            P.setString(2, numeroCompte);
            Result r = P.executeQuery();

            while (r.next()) {
                operation.add(Operation(r));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        return operation;
    }

    // -------------------------------------------------------
    //  Convertir une ligne SQL en objet Operation
    // -------------------------------------------------------
    private Operation Operation(Result r) throws SQLException {
        String type    = r.getString("type_operation");
        double montant = r.getDouble("montant");
        LocalDate date = r.getDate("date_operation").toLocalDate();
        String src     = r.getString("compte_source");
        String dest    = r.getString("compte_destination");
        String marchand = r.getString("marchand");

        // On crée des comptes léger juste pour l'affichage
        Compte compteSource      = src     != null ? new Compte(src,     0, null) : null;
        Compte compteDestination = dest    != null ? new Compte(dest,    0, null) : null;

        Operation op;
        switch (type) {
            case "DEPOT":
                op = new Depot(montant, compteDestination);
                break;
            case "RETRAIT":
                op = new Retrait(montant, compteSource);
                break;
            case "TRANSFERT":
                op = new Transfert(montant, compteSource, compteDestination);
                break;
            case "PAIEMENT":
                op = new Paiement(montant, compteSource, marchand);
                break;
            default:
                return null;
        }
        op.setId(r.getInt("id"));
        op.setDateOperation(date);
        return op;
    }
}