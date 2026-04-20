package main.java.tests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import main.java.database.DatabaseManager;
import main.java.handler.ProfileGuesserHandler;
import main.java.model.Profile;
import main.java.model.Vector;

public class TestingProfileGuesser {

    public static HashMap<String, Integer> jobHashMap = new HashMap<>();
    public static Profile[] PROFILES = ProfileGuesserHandler.PROFILES;

    public double[] generateRandomValues() {
        double[] values = new double[ProfileGuesserHandler.NB_OF_SKILLS];
        for (int i = 0; i < ProfileGuesserHandler.NB_OF_SKILLS; i++) {
            values[i] = new Random().nextInt(10 + 1);
        }
        return values;
    }

    public double[] generateSemiRandomValues() {

        int i = new Random().nextInt((ProfileGuesserHandler.NB_OF_PROFILES - 1) - 0 + 1);

        double[] matrixLineCopy = new double[ProfileGuesserHandler.NB_OF_SKILLS];
        for (int j = 0; j < matrixLineCopy.length; j++) {
            matrixLineCopy[j] += (0) + new Random().nextInt(6 + 1) - 3; // Temporarily set to 0
        }

        return matrixLineCopy;
    }

    public void runTest(double[] generatedValues, PreparedStatement pstmt) {

        // Recreate envrionnement
        Profile userProfile = new Profile("Bot", generatedValues);

        Vector userVector = userProfile.getVector();

        // findFinalScore() appelle en cascade : calculerCosAngle →
        // calculerProduitScalaire + calculerNorme
        for (int i = 0; i < PROFILES.length; i++) {
            int score = (int) Math.round(userVector.findFinalScore(PROFILES[i]));
            PROFILES[i].setScore(score);
        }

        Arrays.sort(PROFILES, Comparator.comparingInt(Profile::getScore).reversed());

        for (int i = 0; i < PROFILES.length; i++) {
            int rank = i + 1;
            int jobId = jobHashMap.get(PROFILES[i].getName());
            int percentage = PROFILES[i].getScore();

            // Update to the database
            try {
                DatabaseManager.setProfileGuesserResult(pstmt, jobId, percentage, false, rank);
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }
    }

    public static void completeJobHashMap() {
        ProfileGuesserHandler.initProfiles();
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
        System.out.println(jobHashMap.toString());
    }

    public static void main(String[] args) {

        ProfileGuesserHandler.initProfiles();
        completeJobHashMap();

        String query = "INSERT INTO resultats_test (id_metier, pourcentage_similitude, rang, timestamp, humain) VALUES (?, ?, ?, NOW(), ?);";
        try (PreparedStatement pstmt = DatabaseManager.getPreparedStatement(query)) {

            TestingProfileGuesser test = new TestingProfileGuesser();
            for (int i = 0; i < 8000; i++) { // 20%
                double[] generatedValues = test.generateRandomValues();
                test.runTest(generatedValues, pstmt);
            }
            for (int i = 0; i < 2000; i++) { // 80%
                double[] generatedValues = test.generateSemiRandomValues();
                test.runTest(generatedValues, pstmt);
            }

            pstmt.executeBatch();

        } catch (Exception e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}