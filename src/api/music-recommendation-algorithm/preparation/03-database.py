import sqlite3
import pandas as pd
import os
from pathlib import Path

# Get directory where the main algorithm folder is located
BASE_DIR = Path(__file__).resolve().parent.parent

def initialize_database():
    # 1. Chargement des données métier
    dataset_path = BASE_DIR / 'data' / 'dataset_cleaned.csv'
    if not dataset_path.exists():
        print("Erreur : data/dataset_cleaned.csv non trouvé. Lancez 01-cleaning.py d'abord.")
        return
    
    df = pd.read_csv(dataset_path)
    
    # Nettoyage préventif des doublons pour respecter la Clé Primaire SQL
    initial_count = len(df)
    df.drop_duplicates(subset=['track_id'], inplace=True)
    if len(df) < initial_count:
        print(f"INFO : {initial_count - len(df)} doublons d'ID supprimés.")
        
    # Supprime les lignes où les colonnes critiques sont vides
    df.dropna(subset=['artists', 'track_name'], inplace=True)

    # 2. Connexion à SQLite
    conn = sqlite3.connect(BASE_DIR / 'data' / 'music_database.db')
    cursor = conn.cursor()

    # 3. Lecture et exécution du fichier .sql
    try:
        with open(BASE_DIR / 'schema.sql', 'r', encoding='utf-8') as f:
            sql_script = f.read()
        
        # exécute toutes les instructions du script SQL
        cursor.executescript(sql_script)
        print("Table 'songs' créée avec succès via schema.sql.")
        
    except FileNotFoundError:
        print("Erreur : Le fichier schema.sql est introuvable.")
        conn.close()
        return
    except sqlite3.Error as e:
        print(f"Erreur SQL : {e}")
        conn.close()
        return

    # 4. Importation des données
    # On utilise 'append' car la table a été créée par le script SQL
    try:
        df.to_sql('songs', conn, if_exists='append', index=False)
        print(f"Importation terminée : {len(df)} morceaux ajoutés à la base.")
    except Exception as e:
        print(f"Erreur : {e}")
        if hasattr(e, '__cause__'):
            print(f"Cause réelle : {e.__cause__}")

    conn.close()


initialize_database()