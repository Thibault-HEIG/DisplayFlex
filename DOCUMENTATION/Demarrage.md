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
Pour communiquer, *le front-end et le backend utilisent HTTP*. Il faut donc lancer le serveur pour pouvoir travailler sur les deux. Si vous modifiez uniquement le front-end ou le SQL, cette étape est *dispensable*.

Prérequis : Avoir Java installé (JDK 11 ou plus récent recommandé).
**À chaque fois que vous voulez travailler, lancer le programme principal :**

-> Lancer `src/main/java/AppServer.java`

-----

## 3. Ouvrir la page
Allez sur http://localhost:8000 proposé dans le terminal.