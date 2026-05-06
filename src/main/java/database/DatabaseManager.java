package main.java.database;

import java.sql.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DatabaseManager {

    public static final String HOST = System.getenv("DB_HOST");
    public static final String PORT = System.getenv("DB_PORT");
    public static final String NAME = System.getenv("DB_NAME");
    public static final String USER = System.getenv("DB_USER");
    public static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + NAME;
    public static void initialize() {
        // On demande à Java de lire le fichier SQL
        executeSQLFile("sql/initdb/1-init.sql");
    }

    // Méthode outil : Lit un fichier .sql, coupe les commandes au ";" et les
    // exécute
    private static void executeSQLFile(String filePath) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            // 1. Lire le fichier complet
            String contenu = Files.readString(Paths.get(filePath));

            // 2. Couper le texte à chaque ";" pour avoir des requêtes individuelles
            // (SQLite aime bien qu'on lui donne les ordres un par un via JDBC)
            String[] requetes = contenu.split(";");

            // 3. Exécuter chaque morceau
            for (String sql : requetes) {
                if (!sql.trim().isEmpty()) { // On ignore les lignes vides
                    stmt.execute(sql.trim());
                }
            }
            System.out.println("Fichier SQL exécuté avec succès : " + filePath);

        } catch (IOException e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public static void setProfileGuesserResult(PreparedStatement pstmt, int compId, int jobId, int percentage, boolean human,
            int rank) throws SQLException {

        pstmt.setInt(1, compId);
        pstmt.setInt(2, jobId);
        pstmt.setInt(3, percentage);
        pstmt.setInt(4, rank);
        pstmt.setBoolean(5, human);

        pstmt.addBatch();
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        return pstmt;
    }
}