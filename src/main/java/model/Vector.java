package main.java.model;
/* 
* Classe objet : Vector
* Encapsulation : les champs sont privés → on ne peut les lire/modifier qu'via des méthodes contrôlées.
* this : object courant
*/

public class Vector {
    private double[] values; // valeurs représentant les compétences d'un profil. La longeur de la liste
                             // correspond au nombre de dimensions.
    private double[] centeredValues; // valeurs normalisées pour améliorer l'algorithme (à ignorer)

    // Méthode Constructeur (appelée à chaque `new Vector()`)
    public Vector(double values[]) {
        this.values = values;
        this.centeredValues = centrerValeurs(this.values);
    }

    // Getters (pour obtenir les variables)
    public double[] getValues() {
        return values;
    }

    // Méthode pour calculer la norme du vecteur (sa longeur)
    public double calculerNorme() {
        // Formule : sqrt((a1)^2) + (a2)^2) + ... + (an)^2))
        double sum = 0.0;
        for (int i = 0; i < centeredValues.length; i++) {
            double value = centeredValues[i];
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum);
    }

    // Méthode pour calculer le produit scalaire entre deux vecteurs (le vecteur
    // this et un vecteurB)
    // Le produit scalaire mesure à quel point deux vecteurs "vont dans la même
    // direction".
    public double calculerProduitScalaire(Vector vectorB) { // vectorB est un autre objet Vector

        // Formule : a·b = a1×b1 + a2×b2 + ... + an×bn
        double sommeProduits = 0.0;

        for (int i = 0; i < this.centeredValues.length; i++) {
            sommeProduits += this.centeredValues[i] * vectorB.centeredValues[i];
        }
        return sommeProduits;
    }

    // Méthode pour calculer le cosinus de l'angle entre deux vecteurs (le vecteur
    // this et un vecteurB)
    // Le résultat est donc entre [-1, 1] (fonction cos(x))
    // 1 → même direction (profils identiques)
    // 0 → orthogonaux (aucun lien)
    // -1 → directions opposées (profils inverses)
    public double calculerCosAngle(Vector vectorB) {

        // Formule : cos(𝜃) = (a·b) / (||a|| × ||b||)
        double produitScalaire = calculerProduitScalaire(vectorB); // Appelle une méthode précédente
        double produitNormes = this.calculerNorme() * vectorB.calculerNorme(); // Appelle une méthode précédente
        double divisionResult = produitScalaire / produitNormes;
        return divisionResult;
    }

    // Méthode pour centrer les valeurs par rapport à la moyenne
    public double[] centrerValeurs(double[] values) {
        double[] centered = new double[values.length];
        double sum = 0.0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        double average = sum / values.length;
        for (int i = 0; i < centered.length; i++) {
            centered[i] -= values[i] - average;
        }
        return centered;
    }

    // Méthode calculer le score final
    // On calcule le cos(𝜃) et le convertit en score en %
    public double findFinalScore(Vector vectorB) {
        double cos = calculerCosAngle(vectorB);
        double result = Math.abs(cos + 1) / 2 * 100;
        result = Math.round(result);
        return result;
    }
}
