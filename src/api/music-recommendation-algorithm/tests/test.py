import pandas as pd
from pathlib import Path

# Get directory where the main algorithm folder is located
BASE_DIR = Path(__file__).resolve().parent.parent

df = pd.read_csv(BASE_DIR / 'data' / 'dataset_cleaned.csv')

# Filtrer et afficher les lignes où 'artists' OU 'track_name' est vide (NaN)
lignes_problematiques = df[df['artists'].isna() | df['track_name'].isna()]
if not lignes_problematiques.empty:
    print("--- Lignes avec des valeurs nulles détectées ---")
    print(lignes_problematiques[['track_id', 'artists', 'track_name']])
    print(f"Total de lignes à problème : {len(lignes_problematiques)}")
else:
    print("Aucune valeur nulle trouvée dans 'artists' ou 'track_name'.")