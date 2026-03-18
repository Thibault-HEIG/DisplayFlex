package main.java.handler;

import main.java.model.Profile;
import main.java.model.Vector;

public class ProfileGuesserHandler {
    // Scores (compétences) de l'utilisateur - à modifier pour tester le programme.
    // Format : {marketing, design, coding, leadership}
    public static double[] userScores = { 2, 1, 8, 3 };
    public static final Profile[] PROFILES = new Profile[userScores.length];

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

    public static void main(String[] args) {
        initProfiles();
        Vector userVector = userProfile.getVector();

        // findFinalScore() appelle en cascade : calculerCosAngle →
        // calculerProduitScalaire + calculerNorme
        for (int i = 0; i < PROFILES.length; i++) {
            Vector profileVector = PROFILES[i].getVector();
            int score = (int) Math.round(userVector.findFinalScore(profileVector));
            PROFILES[i].setScore(score);
        }

        for (int i = 0; i < PROFILES.length; i++) {
            System.out.println(PROFILES[i].getName() + " : " + PROFILES[i].getScore() + "%");
        }
    }
}