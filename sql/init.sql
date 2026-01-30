-- Active: 1764341854783@@127.0.0.1@5437


--CRÉER DES TABLES (💡 Ajouter IF NOT EXTISTS éviter les doublons)
CREATE TABLE IF NOT EXISTS eleves (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    classe TEXT NOT NULL,
    date_naissance DATE
);




--INSÉRER DES DONNÉES
INSERT INTO eleves (nom, prenom, classe, date_naissance)
VALUES ('Moret', 'Thibault', 'M54-2', '2005-07-05');