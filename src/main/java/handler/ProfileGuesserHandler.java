package main.java.handler;

import java.util.Arrays;
import java.util.Comparator;

import main.java.model.Profile;
import main.java.model.Vector;

public class ProfileGuesserHandler extends BaseApiHandler {
    // Scores (compétences) de l'utilisateur - à modifier pour tester le programme.
    // Format : {marketing, design, coding, leadership}
    public static double[] userScores;
    final static int NB_OF_SKILLS = 4;
    public static final Profile[] PROFILES = new Profile[NB_OF_SKILLS];

    public static Profile dataAnalyst;
    public static Profile webDesigner;
    public static Profile communicationSpecialist;
    public static Profile marketingManager;

    public static Profile userProfile;

    public static void initProfiles() {
        dataAnalyst = new Profile("Data Analyst", new double[] { 8, 3, 8, 4 });
        webDesigner = new Profile("Web Designer", new double[] { 4, 8, 7, 1 });
        communicationSpecialist = new Profile("Communication Specialist", new double[] { 9, 6, 1, 3 });
        marketingManager = new Profile("Marketing Manager", new double[] { 8, 2, 4, 9 });

        userProfile = new Profile("User", userScores);

        PROFILES[0] = dataAnalyst;
        PROFILES[1] = webDesigner;
        PROFILES[2] = communicationSpecialist;
        PROFILES[3] = marketingManager;
    }

    @Override
    public String process(String input) {
        System.out.println(input); // CHECKPOINT

        // input = marketing/design/coding/leadership
        userScores = toDouble(input.split("/"));

        initProfiles();
        Vector userVector = userProfile.getVector();

        // findFinalScore() appelle en cascade : calculerCosAngle →
        // calculerProduitScalaire + calculerNorme
        for (int i = 0; i < PROFILES.length; i++) {
            Vector profileVector = PROFILES[i].getVector();
            int score = (int) Math.round(userVector.findFinalScore(profileVector));
            PROFILES[i].setScore(score);
        }

        Arrays.sort(PROFILES, Comparator.comparingInt(Profile::getScore).reversed());

        for (int i = 0; i < PROFILES.length; i++) {
            System.out.println(PROFILES[i].getName() + " : " + PROFILES[i].getScore() + "%");
        }

        String output = "SUCCESS/" + PROFILES[0].getName() + "-" + PROFILES[0].getScore() + "/"
                + PROFILES[1].getName() + "-" + PROFILES[1].getScore() + "/"
                + PROFILES[2].getName() + "-" + PROFILES[2].getScore();
        return output;
    }

    private double[] toDouble(String[] input) {
        double[] scores = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            scores[i] = Double.parseDouble(input[i]);
        }
        return scores;
    }
}