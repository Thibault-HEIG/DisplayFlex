package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:ecole.db";

    public static void initialiser() {
        // On demande à Java de lire le fichier SQL
        executerFichierSql("sql/init.sql");
    }

    public static String insertStudent(String nom, String prenom, String classe, String dateNaissance) {
        String query = "INSERT INTO eleves (nom, prenom, classe, date_naissance) VALUES ('" + nom + "', '" + prenom
                + "', '" + classe + "', '" + dateNaissance + "' );";
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
            stmt.execute(query.trim()); // exécute la requête
            return "Merci";
        } catch (SQLException e) {
            return "Erreur SQL : " + e.getMessage();
        }
    }

    // Méthode outil : Lit un fichier .sql, coupe les commandes au ";" et les
    // exécute
    private static void executerFichierSql(String cheminFichier) {
        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement()) {

            // 1. Lire le fichier complet
            String contenu = Files.readString(Paths.get(cheminFichier));

            // 2. Couper le texte à chaque ";" pour avoir des requêtes individuelles
            // (SQLite aime bien qu'on lui donne les ordres un par un via JDBC)
            String[] requetes = contenu.split(";");

            // 3. Exécuter chaque morceau
            for (String sql : requetes) {
                if (!sql.trim().isEmpty()) { // On ignore les lignes vides
                    stmt.execute(sql.trim());
                }
            }
            System.out.println("Fichier SQL exécuté avec succès : " + cheminFichier);

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