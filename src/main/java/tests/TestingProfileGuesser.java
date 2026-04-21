package main.java.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import main.java.database.DatabaseManager;
import main.java.handler.ProfileGuesserHandler;
import main.java.model.Profile;
import main.java.model.Vector;

public class TestingProfileGuesser {

    private double[][] matrix;
    private int csvNbOfRows;

    public static Profile[] PROFILES = ProfileGuesserHandler.PROFILES;

    public void loadCSV(String path) {
        List<double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // Skip first line (header)

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println(Arrays.toString(parts));
                double[] row = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Double.parseDouble(parts[i].trim());
                }
                rows.add(row);
            }
        } catch (IOException e) {
            System.out.println("Erreur lecture CSV : " + e.getMessage());
        }
        matrix = rows.toArray(new double[0][]);
        csvNbOfRows = matrix.length;
    }

    public double[] generateRandomValues() {
        double[] values = new double[ProfileGuesserHandler.NB_OF_SKILLS];
        for (int i = 0; i < ProfileGuesserHandler.NB_OF_SKILLS; i++) {
            values[i] = new Random().nextInt(10 + 1);
        }
        return values;
    }

    public double[] generateSemiRandomValues() {

        int row = new Random().nextInt(csvNbOfRows);

        double[] skills = new double[ProfileGuesserHandler.NB_OF_SKILLS];
        for (int i = 0; i < skills.length; i++) {
            skills[i] = Math.max(0, Math.min(10, matrix[row][i] + new Random().nextInt(7) - 3));
        }
        return skills;
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
        ProfileGuesserHandler.setResults(pstmt, false);
    }

    public static void main(String[] args) {

        ProfileGuesserHandler.initProfiles();

        String query = "INSERT INTO resultats_test (id_metier, pourcentage_similitude, rang, timestamp, humain) VALUES (?, ?, ?, NOW(), ?);";
        try (PreparedStatement pstmt = DatabaseManager.getPreparedStatement(query)) {

            TestingProfileGuesser test = new TestingProfileGuesser();
            test.loadCSV("public/data/training-data.csv");

            for (int i = 0; i < 2000; i++) { // 20%
                double[] generatedValues = test.generateRandomValues();
                test.runTest(generatedValues, pstmt);
            }
            for (int i = 0; i < 8000; i++) { // 80%
                double[] generatedValues = test.generateSemiRandomValues();
                test.runTest(generatedValues, pstmt);
            }

            pstmt.executeBatch();

        } catch (Exception e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}