package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // -------------------------------------------------------
    //  Paramètres de connexion — à modifier selon votre config
    // -------------------------------------------------------
    private static final String URL      = "jdbc:mysql://localhost:3306/Mobile_Money";
    private static final String USER     = "root";
    private static final String PASSWORD = "";      

    private static Connection connection = null;

    // -------------------------------------------------------
    //  Retourne la connexion (singleton)
    // -------------------------------------------------------
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion à la base de données réussie.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC introuvable. Vérifiez mysql-connector-java.jar");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("❌ Erreur de connexion MySQL : " + e.getMessage());
            System.out.println("   → Vérifiez que MySQL est lancé et que les identifiants sont corrects.");
            throw new RuntimeException(e);
        }
        return connection;
    }

    // -------------------------------------------------------
    //  Fermer la connexion proprement
    // -------------------------------------------------------
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("🔒 Connexion fermée.");
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}