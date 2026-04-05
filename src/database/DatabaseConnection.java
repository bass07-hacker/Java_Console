package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/mobilemoney?useSSL=false&serverTimezone=UTC"; // ← remplace "mobilemoney" par ta base de donnees
    private static final String USER     = "root"; ← mets ton username ici
    private static final String PASSWORD = "";  // ← mets ton mot de passe ici

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver MySQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Connexion échouée : " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur fermeture connexion : " + e.getMessage());
        }
    }
}
