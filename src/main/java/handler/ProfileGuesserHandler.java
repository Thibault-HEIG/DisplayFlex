package main.java.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.model.Profile;
import main.java.model.Vector;

public class ProfileGuesserHandler extends BaseApiHandler {
    // Scores (compétences) de l'utilisateur - à modifier pour tester le programme.
    public static double[] userScores;

    final public static int NB_OF_PROFILES = 13;
    final public static int NB_OF_SKILLS = 16;

    // Index des lignes Profiles
    final static int dataAnalystIndex = 0;
    final static int webDesignerIndex = 1;
    final static int communicationSpecialistIndex = 2;
    final static int marketingManagerIndex = 3;
    final static int uxUiDesignerIndex = 4;
    final static int webDeveloperIndex = 5;
    final static int motionDesignerIndex = 6;
    final static int graphicDesignerIndex = 7;
    final static int contentStrategistIndex = 8;
    final static int productManagerIndex = 9;
    final static int creativeDirectorIndex = 10;
    final static int seoSpecialistIndex = 11;
    final static int fullStackDeveloperIndex = 12;

    // Index des colonnes skills
    // Hard Skills
    final static int marketingIndex = 0;
    final static int graphicDesignIndex = 1;
    final static int programmingIndex = 2;
    final static int writingIndex = 3;
    final static int interfaceIndex = 4;
    final static int dataIndex = 5;
    final static int mediaIndex = 6;
    final static int mathsIndex = 7;
    final static int englishIndex = 8;
    final static int economyIndex = 9;

    // Soft Skills
    final static int leadershipIndex = 10;
    final static int oralCommunicationIndex = 11;
    final static int creativityIndex = 12;
    final static int analyticalThinkingIndex = 13;
    final static int projectManagementIndex = 14;
    final static int storyTellingIndex = 15;

    public static double[][] WEIGHTS_MATRIX = {
            // dataAnalystIndex
            { 7, 3, 14, 11, 5, 20, 3, 17, 13, 10, 6, 12, 0, 20, 0, 0 },
            // webDesignerIndex (web/digital interface designer)
            { 10, 18, 11, 7, 19, 8, 9, 4, 11, 5, 5, 11, 18, 12, 0, 10 },
            // communicationSpecialistIndex (PR / comms)
            { 18, 6, 2, 19, 6, 7, 18, 5, 17, 11, 10, 19, 15, 0, 14, 19 },
            // marketingManagerIndex
            { 20, 7, 3, 14, 7, 16, 8, 12, 17, 18, 18, 16, 15, 17, 19, 14 },
            // uxUiDesignerIndex
            { 9, 14, 7, 10, 20, 13, 9, 6, 13, 8, 8, 15, 18, 16, 11, 13 },
            // webDeveloperIndex
            { 5, 4, 20, 8, 10, 11, 7, 12, 13, 6, 7, 0, 12, 19, 0, 0 },
            // motionDesignerIndex
            { 8, 20, 4, 6, 9, 4, 17, 7, 10, 5, 5, 0, 20, 0, 9, 18 },
            // graphicDesignerIndex
            { 11, 20, 3, 7, 11, 4, 15, 5, 11, 7, 6, 12, 20, 0, 12, 16 },
            // contentStrategistIndex
            { 17, 5, 4, 20, 9, 14, 15, 7, 18, 11, 9, 15, 16, 17, 16, 20 },
            // productManagerIndex
            { 15, 4, 8, 14, 11, 16, 8, 13, 15, 18, 19, 17, 14, 18, 20, 16 },
            // creativeDirectorIndex
            { 18, 17, 2, 14, 10, 9, 17, 6, 16, 15, 20, 18, 20, 13, 17, 19 },
            // seoSpecialistIndex
            { 19, 4, 12, 15, 11, 18, 12, 11, 17, 10, 0, 14, 13, 19, 15, 0 },
            // fullStackDeveloperIndex
            { 4, 3, 20, 9, 12, 17, 7, 14, 14, 7, 10, 0, 13, 19, 0, 0 }
    };

    public static final String[] jobNames = new String[NB_OF_PROFILES];

    public static final Profile[] PROFILES = new Profile[NB_OF_PROFILES];

    public static Profile userProfile;

    public static void initProfiles() {
        for (int i = 0; i < NB_OF_PROFILES; i++) {
            PROFILES[i] = new Profile(jobNames[i], WEIGHTS_MATRIX[i]);
        }

        userProfile = new Profile("User", userScores);
    }

    @Override
    public String process(String input) {
        System.out.println("input : " + input); // CHECKPOINT

        JsonObject inputData = JsonParser.parseString(input).getAsJsonObject();

        String jsonContent = null;
        try {
            jsonContent = Files.readString(Paths.get("public/data/job-profiles.json"));
            JsonObject jsonJobs = JsonParser.parseString(jsonContent).getAsJsonObject();
            for (int i = 0; i < NB_OF_PROFILES; i++) {
                String name = jsonJobs.get("jobs").getAsJsonArray().get(i)
                        .getAsJsonObject().get("name").getAsString();
                jobNames[i] = name;
            }

        } catch (Exception e) {
            System.out.println("Erreur de lecture du fichier job-profiles.json : " + e);
            return "{\"status\":\"error\", \"message\":\"Erreur de lecture du fichier job-profiles.json : " + e + "\"}";
        }

        userScores = new double[NB_OF_SKILLS];

        for (int i = 0; i < NB_OF_SKILLS; i++) {
            userScores[i] = inputData.get("skills").getAsJsonArray().get(i)
                    .getAsJsonObject().get("value").getAsDouble();
        }

        initProfiles();
        Vector userVector = userProfile.getVector();

        // findFinalScore() appelle en cascade : calculerCosAngle →
        // calculerProduitScalaire + calculerNorme
        for (int i = 0; i < PROFILES.length; i++) {
            int score = (int) Math.round(userVector.findFinalScore(PROFILES[i]));
            PROFILES[i].setScore(score);
        }

        Arrays.sort(PROFILES, Comparator.comparingInt(Profile::getScore).reversed()); // Trie la liste = classement

        for (int i = 0; i < PROFILES.length; i++) { // Just to get an update in the terminal
            System.out.println(PROFILES[i].getName() + " : " + PROFILES[i].getScore() + "%");
        }

        JsonObject output = new JsonObject();
        output.addProperty("status", "success");

        JsonArray results = new JsonArray(); // Liste des métiers
        for (int i = 0; i < PROFILES.length; i++) {
            JsonObject result = new JsonObject();
            result.addProperty("name", PROFILES[i].getName());
            result.addProperty("rank", i + 1);
            result.addProperty("score", PROFILES[i].getScore());
            results.add(result);
        }
        output.add("results", results);

        System.out.println(output.toString()); // CHECKPOINT

        return output.toString();
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            // 1. Lire ce que le site web a envoyé
            InputStream is = t.getRequestBody();
            String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String output = process(input);

            // 2. Renvoyer la réponse au site web
            byte[] bytes = output.getBytes(StandardCharsets.UTF_8);
            t.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}