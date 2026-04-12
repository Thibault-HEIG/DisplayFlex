package main.java.database;

import java.sql.*;

import main.java.model.*;

public class DatabaseSecurity {

    public static int getYearFromDate(String date) {
        int year;
        if (date.isEmpty()) {
            year = 0;
        } else {
            String dateTable[] = date.split("-");
            year = Integer.parseInt(dateTable[0]); // trouver l'année de naissance
        }
        return year;
    }

    public static boolean checkDupplicate(Student currentStudent) throws SQLException {

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL, DatabaseManager.USER,
                DatabaseManager.PASSWORD);
                Statement stmt = conn.createStatement()) {

            // exécute la requête et retourne un ResultSet
            ResultSet result = stmt.executeQuery("SELECT * FROM eleves WHERE prenom = '" + currentStudent.getPrenom()
                    + "' AND nom = '" + currentStudent.getNom() + "';");
            if (result.next()) { // check if a row exists first
                return true; // duplicate found
            } else {
                return false; // no duplicate
            }
        }
    }

    public static String checkQuery(Student currentStudent) { // Gère les inputs invalides

        String message = "";

        int birthYear = getYearFromDate(currentStudent.getDateNaissance());

        if (currentStudent.getClasse().length() != 5) {
            message = "Erreur : La classe doit être sous la forme 'M54-2'";
        } else if (birthYear > 2010) {
            message = "Erreur : il semble que " + birthYear + " soit trop récent pour une date de naissance...";
        } else if (!currentStudent.getEmail().contains("@heig-vd.ch")) {
            message = "Merci d'utiliser un email professionnel (@heig-vd.ch)";
        } else
        try {
            if (checkDupplicate(currentStudent) == true) {
                message = "L'élève figure déjà dans la base de données. L'opération a été annulée.";
            } else {
                message = "ok";
            }
        } catch (SQLException e) {
            message = "Erreur lors de la vérification des doublons : " + e.getMessage();
        }
        return message;
    }
}