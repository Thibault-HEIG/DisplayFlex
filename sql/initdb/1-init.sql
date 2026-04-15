-- Active: 1776260232183@@localhost@5432@displayflex

--CRÉER DES TABLES (💡 Ajouter IF NOT EXTISTS éviter les doublons)

CREATE TABLE IF NOT EXISTS liste_taches (
    id SERIAL PRIMARY KEY,
    tache TEXT NOT NULL,
    fait BOOLEAN DEFAULT FALSE,
    responsable VARCHAR(50),
    duree_h INT
);

CREATE TABLE IF NOT EXISTS utilisateurs (
    id SERIAL PRIMARY KEY,
    nom VARCHAR (50) NOT NULL,
    prenom VARCHAR (50) NOT NULL,
    classe VARCHAR(8),
    est_eleve BOOLEAN NOT NULL,
    email VARCHAR (30),
    date_naissance VARCHAR(10),
    id_competences INT REFERENCES competences (id)
);

CREATE TABLE IF NOT EXISTS modules (
    id SERIAL PRIMARY KEY,
    nom VARCHAR (50) NOT NULL,
    secteur VARCHAR (50)
);

CREATE TABLE IF NOT EXISTS competences (
    id SERIAL PRIMARY KEY,
    marketing FLOAT NOT NULL DEFAULT 5,
    design_graphique FLOAT NOT NULL DEFAULT 5
    programmation FLOAT NOT NULL DEFAULT 5,
    ecriture FLOAT NOT NULL DEFAULT 5,
    design_interface FLOAT NOT NULL DEFAULT 5,
    data FLOAT NOT NULL DEFAULT 5,
    media FLOAT NOT NULL DEFAULT 5,
    maths FLOAT NOT NULL DEFAULT 5,
    english FLOAT NOT NULL DEFAULT 5,
    economie FLOAT NOT NULL DEFAULT 5,
    leadership FLOAT NOT NULL DEFAULT 5,
    communication_orale FLOAT NOT NULL DEFAULT 5,
    creativite FLOAT NOT NULL DEFAULT 5,
    pensee_analytique FLOAT NOT NULL DEFAULT 5,
    gestion_projet FLOAT NOT NULL DEFAULT 5,
    storytelling FLOAT NOT NULL DEFAULT 5
);

CREATE TABLE IF NOT EXISTS poids (
    id SERIAL PRIMARY KEY,
    marketing FLOAT NOT NULL,
    design_graphique FLOAT NOT NULL,
    programmation FLOAT NOT NULL,
    ecriture FLOAT NOT NULL,
    design_interface FLOAT NOT NULL,
    data FLOAT NOT NULL,
    media FLOAT NOT NULL,
    maths FLOAT NOT NULL,
    english FLOAT NOT NULL,
    economie FLOAT NOT NULL,
    leadership FLOAT NOT NULL,
    communication_orale FLOAT NOT NULL,
    creativite FLOAT NOT NULL,
    pensee_analytique FLOAT NOT NULL,
    gestion_projet FLOAT NOT NULL,
    storytelling FLOAT NOT NULL
);
CREATE TABLE IF NOT EXISTS branches (
    id SERIAL PRIMARY KEY,
    nom VARCHAR (10) NOT NULL, -- nom abrégé comme DocWeb
    sujet VARCHAR(50) NOT NULL, -- description du cours comme Développement Web (HTML/CSS)
    id_module INT REFERENCES modules (id) NOT NULL,
    duree_semestre INT -- durée en demi semestre (1 à 4)
);

CREATE TABLE IF NOT EXISTS resultats_test (
    id SERIAL PRIMARY KEY,
    id_utilisateur INT REFERENCES utilisateurs (id),
    id_metier INT REFERENCES metiers (id) NOT NULL,
    pourcentage_similitude FLOAT NOT NULL,
    rang SMALLINT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    humain BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS utilisateurs_branches (
    id SERIAL PRIMARY KEY,
    id_utilisateur INT REFERENCES utilisateurs (id),
    id_branches INT REFERENCES branches (id),
    moyenne DECIMAL(2,1)
);

CREATE TABLE IF NOT EXISTS metiers(
    nom VARCHAR (50),
    description VARCHAR (200),
    id_poids INT REFERENCES poids (id)
);

