-- Active: 1764341854783@@127.0.0.1@5437

--CRÉER DES TABLES (💡 Ajouter IF NOT EXTISTS éviter les doublons)
CREATE TABLE IF NOT EXISTS eleves (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    classe TEXT NOT NULL,
    date_naissance DATE
);

CREATE TABLE IF NOT EXISTS branches (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL, -- nom abrégé comme DocWeb
    sujet TEXT NOT NULL, -- description du cours comme Développement Web (HTML/CSS)
    id_module INT REFERENCES modules(id),
    duree_semestre INT -- durée en demi semestre (1 à 4)
);

CREATE TABLE IF NOT EXISTS modules (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    secteur TEXT
);