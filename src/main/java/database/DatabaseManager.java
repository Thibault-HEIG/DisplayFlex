package main.java.database;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.model.Profile;

public class DatabaseManager {

    public static final String HOST = System.getenv("DB_HOST");
    public static final String PORT = System.getenv("DB_PORT");
    public static final String NAME = System.getenv("DB_NAME");
    public static final String USER = System.getenv("DB_USER");
    public static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + NAME;

    public static void initialize() {
        executeSQLFile("sql/initdb/1-init.sql");
    }

    private static void executeSQLFile(String filePath) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            String contenu = Files.readString(Paths.get(filePath));
            String[] requetes = contenu.split(";");

            for (String sql : requetes) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql.trim());
                }
            }
            System.out.println("Fichier SQL exécuté avec succès : " + filePath);

        } catch (IOException | SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Un Prepeared Statement permet de prévenir les injections SQL
    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public static List<Profile> getJobProfiles(int nbSkills) {
        List<Profile> profiles = new ArrayList<>();
        String query = "SELECT m.nom, p.* FROM metiers m JOIN poids p ON m.id_poids = p.id ORDER BY m.id ASC;";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String jobName = rs.getString("nom");
                double[] weights = new double[nbSkills];

                // Les skills doivent avoir cet ordre spécifique dans la table 'poids' :
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

                profiles.add(new Profile(jobName, weights));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching job profiles: " + e.getMessage());
        }
        return profiles;
    }

    // Cette méthode récupère tous les métiers et leur id de la DB et les transforme
    // en
    // "HashMap" pour faciliter la recherche
    public static Map<String, Integer> getJobHashMap() {
        Map<String, Integer> jobHashMap = new HashMap<>();
        String query = "SELECT id, nom FROM metiers";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                jobHashMap.put(rs.getString("nom"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching job map: " + e.getMessage());
        }
        return jobHashMap;
    }

    // On insère les compétences dans la table 'competences'
    public static int insertCompetences(double[] scores) throws SQLException {
        try (Connection conn = getConnection()) {
            List<Integer> ids = insertCompetencesBatch(conn, List.of(scores));
            if (!ids.isEmpty())
                return ids.get(0);
        }
        throw new SQLException("Creating competences failed, no ID obtained.");
    }

    public static void saveTestResults(int compId, List<Profile> profiles, Map<String, Integer> jobHashMap,
            boolean human) throws SQLException {
        try (Connection conn = getConnection()) {
            saveTestResultsBatch(conn, List.of(compId), List.of(profiles), jobHashMap, human);
        }
    }

    /**
     * Cette méthode permet d'insérer PLUSIEURS lignes de compétences d'un coup.
     * C'est ce qu'on appelle le "BATCHING". Au lieu d'envoyer 1000 lettres séparées
     * à la poste,
     * on met les 1000 lettres dans un seul gros carton. C'est beaucoup plus rapide
     */
    public static List<Integer> insertCompetencesBatch(Connection conn, List<double[]> scoresList) throws SQLException {
        String query = "INSERT INTO competences (marketing, design_graphique, programmation, ecriture, design_interface, data, media, maths, english, economie, leadership, communication_orale, creativite, pensee_analytique, gestion_projet, storytelling) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        List<Integer> ids = new ArrayList<>();
        // Statement.RETURN_GENERATED_KEYS : On demande à la DB de nous renvoyer les IDs
        // qu'elle vient de créer
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (double[] scores : scoresList) {
                for (int i = 0; i < scores.length; i++) {
                    pstmt.setDouble(i + 1, scores[i]);
                }
                // addBatch() : On prépare la ligne mais on ne l'envoie pas encore
                pstmt.addBatch();
            }
            // executeBatch() : On envoie tout le "carton" d'un coup
            pstmt.executeBatch();

            // On récupère tous les nouveaux IDs générés par la DB
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
        }
        return ids;
    }

    /**
     * Cette méthode permet d'insérer PLUSIEURS lignes de résultats d'un coup.
     * Elle utilise aussi le BATCHING.
     */
    public static void saveTestResultsBatch(Connection conn, List<Integer> compIds, List<List<Profile>> allRankings,
            Map<String, Integer> jobHashMap, boolean human) throws SQLException {
        String query = "INSERT INTO resultats_test (id_competences, id_metier, pourcentage_similitude, rang, timestamp, humain) VALUES (?, ?, ?, ?, NOW(), ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int k = 0; k < compIds.size(); k++) {
                int compId = compIds.get(k);
                List<Profile> profiles = allRankings.get(k);

                for (int i = 0; i < profiles.size(); i++) {
                    Profile p = profiles.get(i);
                    Integer jobId = jobHashMap.get(p.getName());
                    if (jobId == null)
                        continue;

                    // On remplit les valeurs '?' dans la query
                    pstmt.setInt(1, compId);
                    pstmt.setInt(2, jobId);
                    pstmt.setDouble(3, p.getScore());
                    pstmt.setInt(4, i + 1);
                    pstmt.setBoolean(5, human);
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
        }
    }
}