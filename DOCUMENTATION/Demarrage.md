**🛠️ Installation & Démarrage**
*Les lignes en italique sont des commandes à faire dans le terminal*

1. Cloner le projet (Première fois seulement)

Allez dans un endroit pratique de votre machine:
exemple : *cd ~/Desktop*

Ouvrez votre terminal et récupérez le code sur votre machine :
*git clone lien-du-repo* (quelques chose comme https://github.com/votre-org/displayflex.git)

Allez dans le dossier :
*cd ~/displayflex*

-----

2. Lancer le serveur
**Prérequis :** Avoir Java installé (JDK 11 ou plus récent recommandé).
À chaque fois que vous voulez travailler, compiler le code java :

*javac src/main/java/AppServer.java*

*java -cp src main.java.AppServer*

Ouvrir : Allez sur http://localhost:8000


----------


**🤝 Workflow Git (Comment travailler à 10 sans s'entretuer)**
Règle d'or : On travaille JAMAIS directement sur la branche main.


Étape 0 : Se mettre à jour
Avant de recommencer une nouvelle tâche, mettez à jour votre local (pour avoir le bon code) :

*git checkout main*
*git pull origin main*

-----

Étape 1 : Créer sa branche
Utilisez ces conventions de nommage pour qu'on s'y retrouve :

modification du CSS : *git checkout -b [style/branche]*

modification du HTML : *git checkout -b [index/branche]*

modification du Java : *git checkout -b [feature/branche]*

-----

Étape 2 : Travailler et Sauvegarder
Faites vos modifications. Testez que ça marche. Puis :

*git add .*
*git commit -m "Verbe d'action + description courte"*

-----

Étape 3 : Partager (Push)
Envoyez votre branche aux autres sur GitHub :

*git push origin nom-de-votre-branche*

-----

Étape 4 : Fusionner (Pull Request)
Allez sur GitHub. Créez une Pull Request (PR) de votre branche vers main.

Important : Demandez à un camarade de relire votre code et de valider la PR avant de cliquer sur "Merge".