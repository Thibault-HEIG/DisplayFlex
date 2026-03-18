package main.java.model;

public class Student {
    private int id;
    private String prenom;
    private String nom;
    private String classe;
    private String dateNaissance;
    private String email;

    public Student(String[] dataStrings) {
        this.prenom = dataStrings[0];
        this.nom = dataStrings[1];
        this.classe = dataStrings[2];
        this.email = dataStrings[3];
        if (dataStrings.length > 4) {
            this.dateNaissance = dataStrings[4];
        } else {
            this.dateNaissance = "";
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public String getClasse() {
        return this.classe;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }
}
