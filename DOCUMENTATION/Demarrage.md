# 🛠️ Installation & Démarrage**
`Les lignes en vert sont des commandes à faire dans le terminal`

## 1. Cloner le projet (Première fois seulement)**

Allez dans un endroit pratique de votre machine:
exemple : `cd ~/Desktop`

Ouvrez votre terminal et récupérez le code sur votre machine :
`git clone lien-du-repo` (quelques chose comme https://github.com/votre-org/displayflex.git)

Allez dans le dossier :
`cd /displayflex`

-----

### 2. Lancer le serveur**
Prérequis : Avoir Java installé (JDK 11 ou plus récent recommandé).
**À chaque fois que vous voulez travailler, compiler le code java :**

*Mac*
-> `javac -cp "lib/*:src" src/main/java/*.java`

*Windows*
-> `javac -cp "lib/*;src" src/main/java/*.java`

**Puis lancer le programme principal :**

*Mac*
-> `java -cp "lib/*:src" main.java.AppServer`

**Windows*
-> `java -cp "lib/*;src" main.java.AppServer`

-----

#### 3. Ouvrir la page
Allez sur http://localhost:8000