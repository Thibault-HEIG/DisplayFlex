package main.java;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// C'est ICI que tu codes ta logique Java
public class ApiHandler implements HttpHandler {

    public static String checkQuery(String[] dataStrings) { // Gère les inputs invalides
        String message = "";

        String date[] = dataStrings[3].split("-");
        int birthYear = Integer.parseInt(date[0]);

        if (dataStrings[2].length() != 5) {
            message = "Erreur : La classe doit être sous la forme 'M54-2'";
        } else if (birthYear > 2010) {
            message = "Erreur : il semble que " + birthYear + " soit trop récent pour une date de naissance...";
        } else {
            message = "ok";
        }
        return message;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            // 1. Lire ce que le site web a envoyé
            InputStream is = t.getRequestBody();
            String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // --- LOGIQUE MÉTIER (TA ZONE DE TRAVAIL) ---
            String[] dataStrings = input.split("/"); // stocke les valeurs séparées de "/" dans un tableau
            String reponseJava = "";

            if (checkQuery(dataStrings).equals("ok")) {
                String sqlMessage = DatabaseManager.insertStudent(dataStrings[0], dataStrings[1], dataStrings[2],
                        dataStrings[3]); // Exécute la requête SQL
                if (sqlMessage.equals("Merci")) { // Vérifie la nature du message (succès ou erreur)
                    reponseJava = sqlMessage + ", l'élève : " + dataStrings[0] + " " + dataStrings[1]
                            + " a bien été enregistré."; // réponse finale
                } else {
                    reponseJava = sqlMessage;
                }
            } else reponseJava = checkQuery(dataStrings);
            // -------------------------------------------

            // 2. Renvoyer la réponse au site web
            byte[] bytes = reponseJava.getBytes(StandardCharsets.UTF_8);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}