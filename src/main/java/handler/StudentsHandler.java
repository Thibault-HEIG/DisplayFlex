package main.java.handler;
import main.java.database.*;

public class StudentsHandler extends BaseApiHandler {

    public String process(String input) {
        // --- LOGIQUE MÉTIER (TA ZONE DE TRAVAIL) ---
        String[] dataStrings = input.split("/"); // stocke les valeurs séparées de "/" dans un tableau
        String responseJava = "";

        if (DatabaseSecurity.checkQuery(dataStrings).equals("ok")) {
            String sqlMessage = DatabaseManager.insertStudent(dataStrings); // Exécute la requête SQL
            if (sqlMessage.equals("Merci")) { // Vérifie la nature du message (succès ou erreur)
                responseJava = sqlMessage + ", l'élève : " + dataStrings[0] + " " + dataStrings[1]
                        + " a bien été enregistré."; // réponse finale
            } else {
                responseJava = sqlMessage;
            }
        } else {
            responseJava = DatabaseSecurity.checkQuery(dataStrings);
        }
        return responseJava;
    }
}