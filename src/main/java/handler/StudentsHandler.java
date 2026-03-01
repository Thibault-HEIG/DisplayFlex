package main.java.handler;

import main.java.database.*;
import main.java.model.Student;

public class StudentsHandler extends BaseApiHandler {

    public String process(String input) {

        String responseJava = "";

        if (input.startsWith("undo/")) {
            int id = Integer.parseInt(input.substring(5)); // Accède à l'id
            if (DatabaseManager.undoInsertStudent(id) == true) {
                responseJava = "L'opération a bien été annulée.";
            } else {
                responseJava = "Erreur lors de l'annulation.";
            }
        } else {

            String[] dataStrings = input.split("/"); // stocke les valeurs séparées de "/" dans un tableau

            Student currentStudent = new Student(dataStrings);

            if (DatabaseSecurity.checkQuery(currentStudent).equals("ok")) {
                String sqlMessage = DatabaseManager.insertStudent(currentStudent); // Exécute la requête SQL
                if (sqlMessage.startsWith("SUCCESS")) { // Vérifie la nature du message (succès ou erreur)
                    responseJava = sqlMessage + "/" + "l'élève : " + currentStudent.getPrenom() + " "
                            + currentStudent.getNom()
                            + " a bien été enregistré."; // réponse finale
                } else {
                    responseJava = sqlMessage;
                }
            } else {
                responseJava = DatabaseSecurity.checkQuery(currentStudent);
            }
        }
        return responseJava;
    }
}