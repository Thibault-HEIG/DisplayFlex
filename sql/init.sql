-- Active: 1764341854783@@127.0.0.1@5437

--CRÉER DES TABLES (💡 Ajouter IF NOT EXTISTS éviter les doublons)

CREATE TABLE IF NOT EXISTS metiers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS eleves (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    classe TEXT NOT NULL,
    email TEXT,
    date_naissance DATE
);

CREATE TABLE IF NOT EXISTS branches (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL, -- nom abrégé comme DocWeb
    sujet TEXT NOT NULL, -- description du cours comme Développement Web (HTML/CSS)
    id_module INT REFERENCES modules (id),
    duree_semestre INT -- durée en demi semestre (1 à 4)
);

CREATE TABLE IF NOT EXISTS modules (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    secteur TEXT
);

CREATE TABLE IF NOT EXISTS scores (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_eleve INTEGER REFERENCES eleves(id) NOT NULL,

-- Hard skills (0-10)
    digital_marketing INTEGER DEFAULT 0, -- email, social media
    content_writing INTEGER DEFAULT 0,   -- writing
    graphic_design INTEGER DEFAULT 0,    -- image editing
    programming INTEGER DEFAULT 0,       -- dev
    design_thinking INTEGER DEFAULT 0,   -- interface, wireframe
    data_skill INTEGER DEFAULT 0,        -- SQL, analytics
    marketing_thinking INTEGER DEFAULT 0,-- brand strategy
    media INTEGER DEFAULT 0,             -- photo, video, audio
    maths INTEGER DEFAULT 0,
    english INTEGER DEFAULT 0,
    rights INTEGER DEFAULT 0,
    economy INTEGER DEFAULT 0,

-- Soft skills (0-10)
    leadership INTEGER DEFAULT 0,            -- team management
    creativity INTEGER DEFAULT 0,            -- originality
    analytical_thinking INTEGER DEFAULT 0,   -- decision, synthesize
    oral_communication INTEGER DEFAULT 0,    -- presentation
    project_management INTEGER DEFAULT 0,    -- organization
    storytelling INTEGER DEFAULT 0,

    id_attributed_profile INTEGER REFERENCES metiers(id)

);