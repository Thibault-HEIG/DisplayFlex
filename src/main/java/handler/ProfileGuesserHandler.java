package main.java.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.database.DatabaseManager;
import main.java.model.Profile;
import main.java.model.Vector;

public class ProfileGuesserHandler extends BaseApiHandler {
    // Scores (compétences) de l'utilisateur - à modifier pour tester le programme.
    public static double[] userScores;
    public static HashMap<String, Integer> jobHashMap = new HashMap<>();

    final public static int NB_OF_PROFILES = 13;
    final public static int NB_OF_SKILLS = 16;

    public static final Profile[] PROFILES = new Profile[NB_OF_PROFILES];

    public static Profile userProfile;

    public static void initProfiles() {

        String query = "SELECT m.nom, p.* FROM metiers m JOIN poids p ON m.id_poids = p.id ORDER BY m.id ASC;";

        try (PreparedStatement pstmt = DatabaseManager.getPreparedStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            int i = 0;
            while (rs.next()) {
                String jobName = rs.getString("nom");
                double[] weights = new double[ProfileGuesserHandler.NB_OF_SKILLS];

                weights[0] = rs.getDouble("marketing");
                weights[1] = rs.getDouble("design_graphique");
                weights[2] = rs.getDouble("programmation");
                weights[3] = rs.getDouble("ecriture");
                weights[4] = rs.getDouble("design_interface");
                weights[5] = rs.getDouble("data");
                weights[6] = rs.getDouble("media");
                weights[7] = rs.getDouble("maths");
                weights[8] = rs.getDouble("english");
                weights[9] = rs.getDouble("economie");
                weights[10] = rs.getDouble("leadership");
                weights[11] = rs.getDouble("communication_orale");
                weights[12] = rs.getDouble("creativite");
                weights[13] = rs.getDouble("pensee_analytique");
                weights[14] = rs.getDouble("gestion_projet");
                weights[15] = rs.getDouble("storytelling");

                PROFILES[i] = new Profile(jobName, weights);
                i++;
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

        userProfile = new Profile("User", userScores);

        completeJobHashMap();
    }

    public static void setResults(PreparedStatement pstmt, int compId, boolean human) {

        for (int i = 0; i < PROFILES.length; i++) {
            int rank = i + 1;
            int jobId = jobHashMap.get(PROFILES[i].getName());
            int percentage = PROFILES[i].getScore();

            // Update the database
            try {
                DatabaseManager.setProfileGuesserResult(pstmt, compId, jobId, percentage, human, rank);
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }
    }

    public static void completeJobHashMap() {
        String query = "SELECT id FROM metiers WHERE nom = ?";
        try (java.sql.Connection conn = DatabaseManager.getConnection();
                PreparedStatement lookupStmt = conn.prepareStatement(query)) {
            for (int i = 0; i < ProfileGuesserHandler.PROFILES.length; i++) {
                String jobName = ProfileGuesserHandler.PROFILES[i].getName();
                lookupStmt.setString(1, jobName);
                try (ResultSet resultSet = lookupStmt.executeQuery()) {
                    if (resultSet.next()) {
                        int jobId = resultSet.getInt("id");
                        jobHashMap.put(jobName, jobId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'initialisation du jobHashMap : " + e.getMessage());
        }
    }

    @Override
    public String process(String input) {
        System.out.println("input : " + input); // CHECKPOINT

        JsonObject inputData = JsonParser.parseString(input).getAsJsonObject();

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

        boolean saveResult = inputData.get("save_result").getAsBoolean();
        if (saveResult) {
            int compId = inputData.get("id_competences").getAsInt();
            String query = "INSERT INTO resultats_test (id_competences, id_metier, pourcentage_similitude, rang, timestamp, humain) VALUES (?, ?, ?, ?, NOW(), ?);";
            try (PreparedStatement pstmt = DatabaseManager.getPreparedStatement(query)) {
                setResults(pstmt, compId, true);
                pstmt.executeBatch();
            } catch (Exception e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
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