import os
import psycopg2
import pandas as pd
from dotenv import load_dotenv

# 1. Load the secrets from the .env file into Python's memory
load_dotenv()

HOST = os.environ.get("DB_HOST", "127.0.0.1")
PORT = os.environ.get("DB_PORT")
NAME = os.environ.get("DB_NAME")
USER = os.environ.get("DB_USER")
PASSWORD = os.environ.get("DB_PASSWORD")

connection = None
# 2. Establish the connection (The Translator)
try:
    connection = psycopg2.connect(
        host=HOST,
        port=PORT,
        dbname=NAME,
        user=USER,
        password=PASSWORD
    )
    
    print("PostgreSQL connection successful")

    # 3. Write the aggregated SQL query we discussed earlier
    query = """
    SELECT id_metier, COUNT(*) AS total_recommendations, AVG(pourcentage_similitude) AS average_score, MIN(pourcentage_similitude) AS min_score, MAX(pourcentage_similitude) AS max_score, AVG(rang) AS average_rank
    FROM resultats_test
    WHERE rang <= 5
    GROUP BY id_metier
    ORDER BY total_recommendations DESC;
    """

    # 4. Let Pandas run the query and store the result in a DataFrame
    dataFrame = pd.read_sql(query, connection)

    # 5. Print the text table to the console
    print(dataFrame)

except Exception as error:
    print(f"Error connecting to the database: {error}")

finally:
    # Always close the door when you are done
    if connection:
        connection.close()
        print("Database connection closed.")