package main.java.database;

import java.sql.*;

public class DatabaseSecurity {

    // Méthode qui vérifie que l'élève ne figure pas déjà dans la base de données
    public static boolean checkDupplicate(String[] dataStrings) {
        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL); Statement stmt = conn.createStatement()) {

            // exécute la requête et retourne un ResultSet
            ResultSet result = stmt.executeQuery("SELECT * FROM eleves WHERE prenom = '" + dataStrings[0] + "' AND nom = '" + dataStrings[1] + "';");
            String student = result.getString("prenom") + result.getString("nom");
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
}