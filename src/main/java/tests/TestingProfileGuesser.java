package main.java.tests;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import main.java.database.DatabaseManager;
import main.java.handler.ProfileGuesserHandler;
import main.java.model.Profile;
import main.java.model.Vector;

public class TestingProfileGuesser {

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
            matrixLineCopy[j] += (ProfileGuesserHandler.WEIGHTS_MATRIX[i][j]) + new Random().nextInt(6 + 1) - 3;
        }

        return matrixLineCopy;
    }

    public void runTest(double[] generatedValues, PreparedStatement pstmt) {

        // Recreate envrionnement
        Profile userProfile = new Profile("Bot", generatedValues);
        Profile[] PROFILES = ProfileGuesserHandler.PROFILES;

        ProfileGuesserHandler.initProfiles();
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
            String jobName = ProfileGuesserHandler.PROFILES[i].getName();
            int percentage = PROFILES[i].getScore();

            // Update to the database
            try {
                DatabaseManager.setProfileGuesserResult(pstmt, jobName, percentage, false, rank);
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        String query = "INSERT INTO resultats_test (metier, pourcentage, rang, timestamp, humain) VALUES (?, ?, ?, NOW(), ?);";
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