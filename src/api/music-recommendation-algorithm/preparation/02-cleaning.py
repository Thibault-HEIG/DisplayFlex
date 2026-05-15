import pandas as pd
from pathlib import Path

# Get directory where the main algorithm folder is located
BASE_DIR = Path(__file__).resolve().parent.parent

# Le dataset_cleaned.csv est ma nouvelle base
df = pd.read_csv(BASE_DIR / 'data' / 'dataset_cleaned.csv')

# Mapping min-max
def mappingMinMax(column):
    normalized_column = (df[column] - df[column].min()) / (df[column].max() - df[column].min())
    df[column] = normalized_column
    return

columnsToMap = ["tempo", "duration_ms", "loudness", "speechiness", "instrumentalness", "liveness"]

for col in columnsToMap:
    mappingMinMax(col)
print("Les colonnes ont été normalisées avec min-max entre 0 et 1.")
    
# Transformer le booléen en int (0, 1)
df["explicit"] = df["explicit"].astype(int)

# Création des colonnes binaires pour les genres et time_signatures
df = pd.get_dummies(df, columns=['meta_genre', 'time_signature'], prefix=['genre', 'ts'], dtype=int)
print("Les colonnes binaires ont été créées en one-hot encoding.")

# Supprimer la colonne tonalité qui ne donne aucune indication sur le style / vibe
df.drop(columns=["key"], inplace=True, errors='ignore')
print("La colonne 'tonalité' a été supprimée.")

# Sélectionner uniquement les colonnes numériques, booléennes + l'ID
cols_numeriques = df.select_dtypes(include=['number', 'bool']).columns.tolist()
df = df[['track_id'] + cols_numeriques]

# Afficher toutes les colonnes présentes avant l'export
print(df.columns.tolist())

# On créé une copie du CSV pour l'algorithme
df.to_csv(BASE_DIR / 'data' / 'dataset_for_machine.csv', index=False)
print("Le CSV est prêt à être utilisé pour l'algorithme : data/dataset_for_machine.csv")