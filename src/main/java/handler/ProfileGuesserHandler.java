package main.java.handler;

import java.util.Arrays;
import java.util.Comparator;

import main.java.model.Profile;
import main.java.model.Vector;

public class ProfileGuesserHandler extends BaseApiHandler {
    // Scores (compétences) de l'utilisateur - à modifier pour tester le programme.
    // Format : {marketing, graphic design, programming, writing, interface, data,
    // media, maths, english, economy}
    public static double[] userScores;
    final static int NB_OF_PROFILES = 13;
    final static int NB_OF_SKILLS = 10;
    public static final Profile[] PROFILES = new Profile[NB_OF_PROFILES];

    public static Profile dataAnalyst;
    public static Profile webDesigner;
    public static Profile communicationSpecialist;
    public static Profile marketingManager;
    public static Profile uxUiDesigner;
    public static Profile webDeveloper;
    public static Profile motionDesigner;
    public static Profile graphicDesigner;
    public static Profile contentStrategist;
    public static Profile productManager;
    public static Profile creativeDirector;
    public static Profile seoSpecialist;
    public static Profile fullStackDeveloper;

    public static Profile userProfile;

    public static void initProfiles() {
        webDesigner = new Profile("Web Designer", new double[] { 8, 10, 9, 8, 15, 4, 6, 2, 7, 1 });
        communicationSpecialist = new Profile("Communication Specialist",
                new double[] { 10, 7, 0, 15, 5, 5, 8, 1, 13, 4 });
        marketingManager = new Profile("Marketing Manager", new double[] { 12, 2, 3, 8, 3, 6, 4, 4, 15, 7 });
        uxUiDesigner = new Profile("UX-UI Designer", new double[] { 4, 10, 4, 7, 15, 7, 4, 2, 7, 1 });
        webDeveloper = new Profile("Web Developer", new double[] { 2, 3, 15, 3, 7, 6, 2, 8, 5, 1 });
        dataAnalyst = new Profile("Data Analyst", new double[] { 5, 2, 10, 3, 4, 15, 1, 12, 7, 6 });
        motionDesigner = new Profile("Motion Designer", new double[] { 5, 10, 4, 4, 7, 3, 15, 2, 6, 1 });
        graphicDesigner = new Profile("Graphic Designer", new double[] { 6, 15, 2, 5, 8, 2, 8, 1, 5, 1 });
        contentStrategist = new Profile("Content Strategist", new double[] { 10, 3, 1, 15, 3, 5, 7, 1, 13, 3 });
        productManager = new Profile("Product Manager", new double[] { 10, 3, 5, 8, 10, 8, 3, 5, 10, 7 });
        creativeDirector = new Profile("Creative Director", new double[] { 12, 12, 2, 8, 8, 4, 10, 1, 8, 5 });
        seoSpecialist = new Profile("SEO Specialist", new double[] { 10, 2, 7, 10, 5, 12, 3, 6, 10, 5 });
        fullStackDeveloper = new Profile("Full-Stack Developer", new double[] { 1, 1, 15, 2, 5, 8, 2, 10, 8, 1 });

        userProfile = new Profile("User", userScores);

        PROFILES[0] = dataAnalyst;
        PROFILES[1] = webDesigner;
        PROFILES[2] = communicationSpecialist;
        PROFILES[3] = marketingManager;
        PROFILES[4] = uxUiDesigner;
        PROFILES[5] = webDeveloper;
        PROFILES[6] = motionDesigner;
        PROFILES[7] = graphicDesigner;
        PROFILES[8] = contentStrategist;
        PROFILES[9] = productManager;
        PROFILES[10] = creativeDirector;
        PROFILES[11] = seoSpecialist;
        PROFILES[12] = fullStackDeveloper;
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

        String output = "SUCCESS/" + PROFILES[0].getName() + "_" + PROFILES[0].getScore() + "/"
                + PROFILES[1].getName() + "_" + PROFILES[1].getScore() + "/"
                + PROFILES[2].getName() + "_" + PROFILES[2].getScore();
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