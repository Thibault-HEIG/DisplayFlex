package main.java.model;

/*
 * Classe objet Profile : représente un profil métier ou utilisateur.
 * Chaque profil est défini par plusieurs compétences et son vecteur associé.
 *
 * Un Profile "possède" un objet Vector. C'est une relation "has-a" : un profil A un vecteur (pas "est" un vecteur).
 */
public class Profile {

    // Variables
    private String name;
    private Vector vector;
    private int score;

    // Méthode Constructeur (appelée à chaque `new Profile()`)
    // Initialise un profil à partir d'un nom et d'un tableau de scores.
    public Profile(String name, double[] scores) {
        this.name = name;
        this.vector = createVector(scores); // délègue la création du Vector à une méthode dédiée
    }

    // Getters
    public String getName() {
        return name;
    }

    public Vector getVector() {
        return vector;
    }

    public int getScore() {
        return score;
    }

    // Setters — accès en écriture contrôlé.
    // On pourrait y ajouter une validation (ex: score entre 0 et 10)
    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Méthode pour créer un vecteur de représentation à partir d'un tableau de
    // scores
    public Vector createVector(double[] skills) {
        Vector profileVector = new Vector(skills);

        return profileVector;
    }
}