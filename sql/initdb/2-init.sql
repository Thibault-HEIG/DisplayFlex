-- Active: 1775506739659@@localhost@5432@displayflex

--CRÉER DES TABLES (💡 Ajouter IF NOT EXTISTS éviter les doublons)

CREATE TABLE IF NOT EXISTS eleves (
    id SERIAL PRIMARY KEY,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    classe TEXT NOT NULL,
    email TEXT,
    date_naissance DATE
);

CREATE TABLE IF NOT EXISTS modules (
    id SERIAL PRIMARY KEY,
    nom TEXT NOT NULL,
    secteur TEXT
);

CREATE TABLE IF NOT EXISTS branches (
    id SERIAL PRIMARY KEY,
    nom TEXT NOT NULL, -- nom abrégé comme DocWeb
    sujet TEXT NOT NULL, -- description du cours comme Développement Web (HTML/CSS)
    id_module INT REFERENCES modules (id),
    duree_semestre INT -- durée en demi semestre (1 à 4)
);