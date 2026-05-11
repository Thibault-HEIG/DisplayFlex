package main.java.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.java.database.DatabaseManager;
import main.java.handler.ProfileGuesserHandler;
import main.java.model.Profile;

public class TestingProfileGuesser {

    private double[][] matrix;
    private int csvNbOfRows;

    // On vient importer le CSV dans 'public/data/training-data.csv'
    // Ce sont des données humaines qu'on va amplifier
    public void loadCSV(String path) {
        List<double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // Skip first line (header)

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
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
        Random rand = new Random();
        for (int i = 0; i < ProfileGuesserHandler.NB_OF_SKILLS; i++) {
            values[i] = rand.nextInt(11);
        }
        return values;
    }

    // En partant des données humaines, on les altère légèrement
    public double[] generateSemiRandomValues() {
        int row = new Random().nextInt(csvNbOfRows);
        double[] skills = new double[ProfileGuesserHandler.NB_OF_SKILLS];
        Random rand = new Random();
        for (int i = 0; i < skills.length; i++) {
            // Incrémentation ou décrémentation légère
            skills[i] = Math.max(0, Math.min(10, matrix[row][i] + rand.nextInt(7) - 3));
        }
        return skills;
    }

    /**
     * Cette méthode fait le travail pour un groupe de tests.
     * On calcule les classements pour chaque test, puis on envoie tout à la DB d'un seul coup.
     */
    public void runTestsInBatch(Connection conn, List<double[]> generatedValuesList, List<Profile> baseProfiles, Map<String, Integer> jobHashMap) throws SQLException {
        // Cette liste va contenir tous les classements calculés dans ce groupe
        List<List<Profile>> allRankings = new ArrayList<>();
        
        for (double[] generatedValues : generatedValuesList) {
            // On doit "cloner" les profils de base pour que chaque test ait ses propres scores
            List<Profile> testProfiles = new ArrayList<>();
            for (Profile p : baseProfiles) {
                testProfiles.add(new Profile(p.getName(), p.getVector().getValues()));
            }
            // On calcule le classement pour ce test précis (on ne garde que le top 5)
            List<Profile> topProfiles = ProfileGuesserHandler.calculateRanking(generatedValues, testProfiles);
            allRankings.add(topProfiles);
        }
        
        // On envoie d'abord les compétences (en groupe)
        List<Integer> compIds = DatabaseManager.insertCompetencesBatch(conn, generatedValuesList);
        // Puis on envoie les résultats liés à ces compétences (en groupe aussi)
        DatabaseManager.saveTestResultsBatch(conn, compIds, allRankings, jobHashMap, false);
    }

    public static void main(String[] args) {
        TestingProfileGuesser test = new TestingProfileGuesser();
        test.loadCSV("public/data/training-data.csv");

        // On prépare les données nécessaires avant de lancer les boucles
        List<Profile> baseProfiles = DatabaseManager.getJobProfiles(ProfileGuesserHandler.NB_OF_SKILLS);
        Map<String, Integer> jobHashMap = DatabaseManager.getJobHashMap();

        int totalTests = 10000;
        int chunkSize = 1000; // On traite les tests par paquets de 1000 pour aller plus vite

        // On ouvre UNE SEULE connexion pour tout le programme
        try (Connection conn = DatabaseManager.getConnection()) {
            for (int i = 0; i < totalTests; i += chunkSize) {
                List<double[]> chunkValues = new ArrayList<>();
                // On remplit notre paquet de 1000 tests
                for (int j = 0; j < chunkSize && (i + j) < totalTests; j++) {
                    if (i + j < 2000) {
                        chunkValues.add(test.generateRandomValues());
                    } else {
                        chunkValues.add(test.generateSemiRandomValues());
                    }
                }
                // On lance le traitement du paquet
                test.runTestsInBatch(conn, chunkValues, baseProfiles, jobHashMap);
                System.out.println("Progress: " + Math.min(i + chunkSize, totalTests) + "/" + totalTests);
            }
            System.out.println("Tests completed successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error during tests: " + e.getMessage());
        }
    }
}