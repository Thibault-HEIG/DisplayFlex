package main.java.model;
/* 
* Classe objet : Vector
* Encapsulation : les champs sont privés → on ne peut les lire/modifier qu'via des méthodes contrôlées.
* this : object courant
*/

public class Vector {
    private double[] values; // valeurs représentant les compétences d'un profil. La longeur de la liste
                             // correspond au nombre de dimensions.
    private double[] normalizedValues; // valeurs normalisées pour améliorer l'algorithme

    // Méthode Constructeur (appelée à chaque `new Vector()`)
    public Vector(double values[]) {
        this.values = values;
    }

    // Getters (pour obtenir les variables)
    public double[] getValues() {
        return values;
    }

    // Méthode pour calculer la norme du vecteur (sa longeur)
    public double calculerNorme() {
        // Formule : sqrt((a1)^2) + (a2)^2) + ... + (an)^2))
        double sum = 0.0;
        for (int i = 0; i < normalizedValues.length; i++) {
            double value = normalizedValues[i];
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum);
    }

    // Méthode pour calculer le produit scalaire entre deux vecteurs (le vecteur
    // this et un vecteurB)
    // Le produit scalaire mesure à quel point deux vecteurs "vont dans la même
    // direction".
    public double calculerProduitScalaire(Vector profileVector) { // profileVector est un autre objet Vector

        // Formule : a·b = a1×b1 + a2×b2 + ... + an×bn
        double sommeProduits = 0.0;

        for (int i = 0; i < this.normalizedValues.length; i++) {
            sommeProduits += this.normalizedValues[i] * profileVector.normalizedValues[i];
        }
        return sommeProduits;
    }

    // Méthode pour calculer le cosinus de l'angle entre deux vecteurs (le vecteur
    // this et un vecteurB)
    // Le résultat est donc entre [-1, 1] (fonction cos(x))
    // 1 → même direction (profils identiques)
    // 0 → orthogonaux (aucun lien)
    // -1 → directions opposées (profils inverses)
    public double calculerCosAngle(Vector profileVector) {

        // Formule : cos(𝜃) = (a·b) / (||a|| × ||b||)
        double produitScalaire = calculerProduitScalaire(profileVector); // Appelle une méthode précédente
        double produitNormes = this.calculerNorme() * profileVector.calculerNorme(); // Appelle une méthode précédente
        double divisionResult = produitScalaire / produitNormes;
        return divisionResult;
    }

    // Méthode pour centrer les valeurs par rapport à la moyenne
    public double[] centerValues(double[] rawValues, Vector profileVector) {
        double[] centered = new double[rawValues.length];
        double sum = 0.0;
        int validDimensions = 0;
        for (int i = 0; i < rawValues.length; i++) {
            if (profileVector.values[i] != 0) {
                sum += rawValues[i];
                validDimensions++;
            }
        }
        double average = sum / validDimensions;
        for (int i = 0; i < centered.length; i++) {
            if (profileVector.values[i] != 0) {
                centered[i] = rawValues[i] - average;
            } else {
                centered[i] = 0;
            }
        }
    return centered;

    }

    public double[] centerValues(double[] rawValues) {
        double[] centered = new double[rawValues.length];
        double sum = 0.0;
        int validDimensions = 0;
        for (int i = 0; i < rawValues.length; i++) {
            if (this.values[i] != 0) {
                sum += rawValues[i];
                validDimensions++;
            }
        }
        double average = sum / validDimensions;
        for (int i = 0; i < centered.length; i++) {
            if (rawValues[i] != 0) {
                centered[i] = rawValues[i] - average;
            } else {
                centered[i] = 0;
            }
        }
    return centered;

    }

    // Méthode calculer le score final
    // On calcule le cos(𝜃) et le convertit en score en %
    public double findFinalScore(Profile profile) {
        Vector profileVector = profile.getVector();
        this.normalizedValues = this.centerValues(this.values, profileVector);
        profileVector.normalizedValues = profileVector.centerValues(profileVector.values);

        double cos = calculerCosAngle(profileVector);
        double result = Math.abs(cos + 1) / 2 * 100;
        result = Math.round(result);
        return result;
    }
}
