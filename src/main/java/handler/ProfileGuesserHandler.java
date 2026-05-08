package main.java.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.database.DatabaseManager;
import main.java.model.Profile;
import main.java.model.Vector;

public class ProfileGuesserHandler extends BaseApiHandler {
    
    public static final int NB_OF_SKILLS = 16;

    /**
     * Cette méthode contient le coeur de l'algorithme.
     * Elle prend les scores de l'utilisateur et les compare à chaque métier.
     * Ensuite, elle trie la liste pour mettre les meilleurs scores en premier.
     */
    public static List<Profile> calculateRanking(double[] userScores, List<Profile> profiles) {
        // On crée un profil virtuel pour l'utilisateur
        Profile userProfile = new Profile("User", userScores);
        Vector userVector = userProfile.getVector();

        // Pour chaque métier, on calcule la similitude (le score)
        for (Profile profile : profiles) {
            int score = (int) Math.round(userVector.findFinalScore(profile));
            profile.setScore(score);
        }

        // On trie la liste : les métiers les plus ressemblants arrivent en haut (index 0)
        profiles.sort(Comparator.comparingInt(Profile::getScore).reversed());
        return profiles;
    }

    @Override
    public String process(String input) {
        System.out.println("input : " + input);

        // On transforme le texte JSON reçu en objet manipulable par Java
        JsonObject inputData = JsonParser.parseString(input).getAsJsonObject();
        double[] userScores = new double[NB_OF_SKILLS];

        // On extrait les valeurs du tableau de compétences envoyé par le site
        JsonArray skillsArray = inputData.get("skills").getAsJsonArray();
        for (int i = 0; i < NB_OF_SKILLS; i++) {
            userScores[i] = skillsArray.get(i).getAsJsonObject().get("value").getAsDouble();
        }

        // On récupère les profils métiers depuis la base de données
        List<Profile> profiles = DatabaseManager.getJobProfiles(NB_OF_SKILLS);
        
        // On lance le calcul du classement
        calculateRanking(userScores, profiles);

        // On regarde si le JSON dit de sauver le résultat
        boolean saveResult = inputData.get("save_result").getAsBoolean();
        if (saveResult) {
            try {
                int compId = DatabaseManager.insertCompetences(userScores);
                Map<String, Integer> jobHashMap = DatabaseManager.getJobHashMap();
                DatabaseManager.saveTestResults(compId, profiles, jobHashMap, true);
            } catch (SQLException e) {
                System.err.println("Database error during result saving: " + e.getMessage());
            }
        }

        // On créé le JSON de sortie
        JsonObject output = new JsonObject();
        output.addProperty("status", "success");

        JsonArray results = new JsonArray();
        for (int i = 0; i < profiles.size(); i++) {
            Profile p = profiles.get(i);
            JsonObject result = new JsonObject();
            result.addProperty("name", p.getName());
            result.addProperty("rank", i + 1);
            result.addProperty("score", p.getScore());
            results.add(result);
        }
        output.add("results", results);

        System.out.println(output.toString()); // check dans le terminal

        return output.toString();
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            InputStream is = t.getRequestBody();
            String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String output = process(input);

            byte[] bytes = output.getBytes(StandardCharsets.UTF_8);
            t.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}