import pandas as pd
from pathlib import Path

# Get directory where the main algorithm folder is located
BASE_DIR = Path(__file__).resolve().parent.parent

# Importer le CSV comme Data Frame (df)
df = pd.read_csv(BASE_DIR / 'data' / 'dataset.csv')

# Normalisation du score de popularité
if df["popularity"].max() > 1:
    df["popularity"] = df["popularity"] / 100

# print(df["popularity"].head())


# Préparation du remapping des genres
genre_mapping = {
    'alt-rock': 'Rock', 'alternative': 'Rock', 'british': 'Rock', 'emo': 'Rock', 'grunge': 'Rock', 
    'hard-rock': 'Rock', 'j-rock': 'Rock', 'psych-rock': 'Rock', 'punk': 'Rock', 'punk-rock': 'Rock', 
    'rock': 'Rock', 'rock-n-roll': 'Rock', 'rockabilly': 'Rock', 'indie': 'Rock',
    'cantopop': 'Pop', 'indie-pop': 'Pop', 'j-idol': 'Pop', 'j-pop': 'Pop', 'k-pop': 'Pop', 
    'mandopop': 'Pop', 'pop': 'Pop', 'pop-film': 'Pop', 'power-pop': 'Pop', 'synth-pop': 'Pop',
    'breakbeat': 'Electronic', 'chicago-house': 'Electronic', 'club': 'Electronic', 'dance': 'Electronic', 
    'dancehall': 'Electronic', 'deep-house': 'Electronic', 'detroit-techno': 'Electronic', 'disco': 'Electronic', 
    'drum-and-bass': 'Electronic', 'dubstep': 'Electronic', 'edm': 'Electronic', 'electro': 'Electronic', 
    'electronic': 'Electronic', 'hardstyle': 'Electronic', 'house': 'Electronic', 'idm': 'Electronic', 
    'j-dance': 'Electronic', 'minimal-techno': 'Electronic', 'progressive-house': 'Electronic', 
    'techno': 'Electronic', 'trance': 'Electronic', 'trip-hop': 'Electronic', 'hardcore': 'Electronic',
    'black-metal': 'Metal', 'death-metal': 'Metal', 'grindcore': 'Metal', 'heavy-metal': 'Metal', 
    'industrial': 'Metal', 'metal': 'Metal', 'metalcore': 'Metal',
    'jazz': 'Jazz',
    'classical': 'Classical', 'opera': 'Classical', 'piano': 'Classical', 'show-tunes': 'Classical',
    'hip-hop': 'Hip-Hop',
    'dub': 'Reggae', 'reggae': 'Reggae', 'reggaeton': 'Reggae', 'ska': 'Reggae',
    'brazil': 'Latin', 'forro': 'Latin', 'latin': 'Latin', 'latino': 'Latin', 'mpb': 'Latin', 
    'pagode': 'Latin', 'salsa': 'Latin', 'samba': 'Latin', 'sertanejo': 'Latin', 'tango': 'Latin',
    'funk': 'Soul/R&B', 'groove': 'Soul/R&B', 'r-n-b': 'Soul/R&B', 'soul': 'Soul/R&B',
    'bluegrass': 'Folk/Country', 'country': 'Folk/Country', 'folk': 'Folk/Country', 
    'honky-tonk': 'Folk/Country', 'singer-songwriter': 'Folk/Country', 'songwriter': 'Folk/Country',
    'blues': 'Blues',
    'afrobeat': 'World', 'french': 'World', 'german': 'World', 'indian': 'World', 'iranian': 'World', 
    'malay': 'World', 'spanish': 'World', 'swedish': 'World', 'turkish': 'World', 'world-music': 'World',
    'anime': 'Kids', 'children': 'Kids', 'disney': 'Kids', 'kids': 'Kids',
    'acoustic': 'Other', 'ambient': 'Other', 'chill': 'Other', 'comedy': 'Other', 'garage': 'Other', 
    'gospel': 'Other', 'goth': 'Other', 'guitar': 'Other', 'happy': 'Other', 'new-age': 'Other', 
    'party': 'Other', 'romance': 'Other', 'sad': 'Other', 'sleep': 'Other', 'study': 'Other'
}

# On applique le mapping
df["meta_genre"] = df["track_genre"].map(genre_mapping)
print("Les genres ont été normalisés en 15 méta genres.")

# On remplit les trous (NaN) par "Other" et on jette les doublons
df["meta_genre"] = df["meta_genre"].fillna("Other")
df.drop_duplicates(subset=['track_id'], inplace=True)

# Supprimer l'ancienne colonne de genre ('inplace' pour remplacer)
df.drop(columns=["track_genre"], inplace=True)
# Supprimer l'id obsolète
df.drop(columns=["Unnamed: 0"], inplace=True)

# On créé une copie du CSV pour le script 02
df.to_csv(BASE_DIR / 'data' / 'dataset_cleaned.csv', index=False)
print("Une copie du CSV a été créée : data/dataset_cleaned.csv")