package main.java.database;

import java.sql.*;

import main.java.model.*;

public class DatabaseSecurity {

    // Méthode qui vérifie que l'élève ne figure pas déjà dans la base de données
    public static boolean checkDupplicate(Student currentStudent) {

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
                Statement stmt = conn.createStatement()) {

            // exécute la requête et retourne un ResultSet
            ResultSet result = stmt.executeQuery("SELECT * FROM eleves WHERE prenom = '" + currentStudent.getPrenom() + "' AND nom = '" + currentStudent.getNom() + "';");
            String student = result.getString("prenom") + result.getString("nom");
            if (student.equals(currentStudent.getPrenom() + currentStudent.getNom())) {
                    
                return true; // l'élève figure déja dans la DB
            } else {
                return false;
            }
        } catch (SQLException e) {
            return true;
        }
    }

    public static String checkQuery(Student currentStudent) { // Gère les inputs invalides
        
        String message = "";

        String date[] = currentStudent.getDateNaissance().split("-"); // trouver l'année de naissance
        int birthYear = Integer.parseInt(date[0]); // trouver l'année de naissance

        if (currentStudent.getClasse().length() != 5) {
            message = "Erreur : La classe doit être sous la forme 'M54-2'";
        } else if (birthYear > 2010) {
            message = "Erreur : il semble que " + birthYear + " soit trop récent pour une date de naissance...";
        } else if (checkDupplicate(currentStudent) == true) {
            message = "L'élève figure déjà dans la base de données. L'opération a été annulée.";
        } else {
            message = "ok";
        }
        return message;
    }
}