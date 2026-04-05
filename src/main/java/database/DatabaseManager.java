package main.java.database;

import java.sql.*;
import main.java.model.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DatabaseManager {

    public static final String URL = "jdbc:sqlite:ecole.db";

    public static void initialize() {
        // On demande à Java de lire le fichier SQL
        executeSQLFile("sql/init.sql");
    }

    public static String insertStudent(Student currentStudent) {

        String query = "INSERT INTO eleves (nom, prenom, classe, email, date_naissance) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL)) {
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
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public static String updateWeight(double newWeight, String columnName, String profileName) {

        String query = "UPDATE poids SET ? = ? WHERE id = ?;";

        int profileId = findProfileID(profileName);
        if (profileId == -1) {
            return "Erreur : résultats multiples";

        } else if (profileId == -2) {
            return "Erreur : colonne introuvable";

        } else if (profileId == -3) {
            return "Erreur SQL";

        } else {

            try (Connection conn = DriverManager.getConnection(URL)) {
                PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, columnName);
                pstmt.setDouble(2, newWeight);
                pstmt.setInt(3, profileId);

                pstmt.executeUpdate(); // exécute la requête

                return "SUCCESS";

            } catch (SQLException e) {
                return "Erreur SQL : " + e.getMessage();
            }
        }
    }

    public static int findProfileID(String profileName) {
        int profileID;
        String query = "SELECT id FROM metiers WHERE nom LIKE '" + profileName + "';";
        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
                Statement stmt = conn.createStatement()) {

            // exécute la requête et retourne un ResultSet
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                profileID = result.getInt("id");

                if (result.next()) {
                    // This means there is a second row
                    System.out.println("Error: Multiple profiles found with the same name.");
                    return -1;
                }

                return profileID;
            } else {
                return -2;
            }

        } catch (SQLException e) {
            return -3;
        }
    }

    // Méthode outil : Lit un fichier .sql, coupe les commandes au ";" et les
    // exécute
    private static void executeSQLFile(String filePath) {
        try (Connection conn = DriverManager.getConnection(URL);
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
        return DriverManager.getConnection(URL);
    }
}