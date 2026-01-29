-- Active: 1764341854783@@127.0.0.1@5437
--CRÉER DES TABLES

CREATE TABLE eleves (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    classe TEXT NOT NULL
);




--INSÉRER DES DONNÉES
INSERT INTO eleves (nom, prenom, classe) VALUES ('Moret', 'Thibault', 'M54-2');
INSERT INTO eleves (nom, prenom, classe) VALUES ('Loup', 'Robin', 'M54-2');