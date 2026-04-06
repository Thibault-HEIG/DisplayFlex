package main.java.database;

import java.sql.*;
import java.util.List;

import main.java.model.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DatabaseManager {

    private static final String HOST = System.getenv("DB_HOST");
    private static final String PORT = System.getenv("DB_PORT");
    private static final String NAME = System.getenv("DB_NAME");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + NAME;

    public static void initialize() {
        // On demande à Java de lire le fichier SQL
        executeSQLFile("sql/initdb/1-remove.sql");
        executeSQLFile("sql/initdb/2-init.sql");
        executeSQLFile("sql/initdb/3-insert.sql");
    }

    public static String insertStudent(Student currentStudent) {

        String query = "INSERT INTO eleves (nom, prenom, classe, email, date_naissance) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, currentStudent.getNom());
            pstmt.setString(2, currentStudent.getPrenom());
            pstmt.setString(3, currentStudent.getClasse());
            pstmt.setString(4, currentStudent.getEmail());
            pstmt.setString(5, currentStudent.getDateNaissance());

            pstmt.executeUpdate(); // exécute la requête et retourne true/false
            ResultSet result = pstmt.getGeneratedKeys();

            int id = result.getInt(1);
            currentStudent.setId(id);

            return "SUCCESS/" + id;

        } catch (SQLException e) {
            return "Erreur SQL : " + e.getMessage();
        }
    }

    public static boolean undoInsertStudent(int id) {
        String query = "DELETE FROM eleves WHERE id = " + id + ";";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public static String updateWeight(double newWeight, String columnName, String profileName) {

        final List<String> ALLOWED_COLUMNS = List.of(
        // ✏️ Skills List TO BE COMPLETED
        );
        if (!ALLOWED_COLUMNS.contains(columnName))
            return "Erreur : colonne invalide";

        String query = "UPDATE poids SET " + columnName + " = ? WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, columnName);
            pstmt.setDouble(2, newWeight);
            // ✏️ Profile ID TO BE COMPLETED with a JOIN statement

            pstmt.executeUpdate();

            return "SUCCESS";

        } catch (SQLException e) {
            return "Erreur SQL : " + e.getMessage();
        }
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

    // Garde tes méthodes d'accès (ajouterEleve, etc.) ici pour l'interaction
    // dynamique
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}