package main.java;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:ecole.db";

    public static void initialize() {
        // On demande à Java de lire le fichier SQL
        executeSQLFile("sql/init.sql");
    }

    public static String insertStudent(String[] dataStrings) {
        String nom = dataStrings[1];
        String prenom = dataStrings[0];
        String classe = dataStrings[2];
        String dateNaissance = dataStrings[3];

        String query = "INSERT INTO eleves (nom, prenom, classe, date_naissance) VALUES ('" + nom + "', '" + prenom
                + "', '" + classe + "', '" + dateNaissance + "' );";
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
            stmt.execute(query.trim()); // exécute la requête
            return "Merci";
        } catch (SQLException e) {
            return "Erreur SQL : " + e.getMessage();
        }
    }

    public static boolean checkDupplicate(String[] dataStrings) {
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {

            ResultSet result = stmt.executeQuery("SELECT * FROM eleves WHERE prenom = '" + dataStrings[0] + "' AND nom = '" + dataStrings[1] + "';");
            String student = result.getString("prenom") + result.getString("nom") ;
            if (student.equals(dataStrings[0] + dataStrings[1])) {
                return true; // l'élève figure déja dans la DB
            } else {
                return false;
            }
        } catch (SQLException e) {
            return true;
        }
    }

    public static String checkQuery(String[] dataStrings) { // Gère les inputs invalides
        String message = "";

        String date[] = dataStrings[3].split("-"); // trouver l'année de naissance
        int birthYear = Integer.parseInt(date[0]); // trouver l'année de naissance

        if (dataStrings[2].length() != 5) {
            message = "Erreur : La classe doit être sous la forme 'M54-2'";
        } else if (birthYear > 2010) {
            message = "Erreur : il semble que " + birthYear + " soit trop récent pour une date de naissance...";
        } else if (checkDupplicate(dataStrings) == true) {
            message = "L'élève figure déjà dans la base de données. L'opération a été annulée.";
        } else {
            message = "ok";
        }
        return message;
    }

    // Méthode outil : Lit un fichier .sql, coupe les commandes au ";" et les exécute
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