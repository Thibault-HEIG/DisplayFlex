CREATE TABLE IF NOT EXISTS songs (
    track_id TEXT PRIMARY KEY,
    artists TEXT NOT NULL,
    album_name TEXT,
    track_name TEXT NOT NULL,
    popularity REAL,
    duration_ms REAL,
    explicit BOOLEAN,
    danceability REAL,
    energy REAL,
    key INTEGER,
    loudness REAL,
    mode BOOLEAN, -- 0: mineur, 1: majeur
    speechiness REAL,
    acousticness REAL,
    instrumentalness REAL,
    liveness REAL,
    valence REAL,
    tempo INTEGER,
    time_signature INTEGER,
    meta_genre TEXT
);