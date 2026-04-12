# 🛠️ Installation & Démarrage

## 1. Cloner le projet (Première fois seulement)

Allez dans un endroit pratique de votre machine:
exemple : `cd ~/Desktop`

Ouvrez votre terminal et récupérez le code sur votre machine :
`git clone lien-du-repo` (quelques chose comme git@github.com:Thibault-HEIG/DisplayFlex.git)

Allez dans le dossier :
`cd /displayflex`

-----

## 2. Lancer le serveur
L'application et la base de données (PostgreSQL) sont conteneurisées.

Prérequis : Avoir [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé et lancé.

**À chaque fois que vous voulez travailler, lancez cette commande à la racine du projet :**
`docker compose up --build`
Cela reconstruit le programme avec la dernière version des fichiers.

**Pour arrêter le conteneur :**
`docker compose down`
⚠️ NE PAS FAIRE `docker compose down -v`, ça supprimera la base de données.

*Note : Si vous modifiez uniquement le front-end (HTML/CSS/JS), cette étape est dispensable.*

-----

## 3. Ouvrir l'application
Allez sur http://localhost:8000 dans un navigateur.