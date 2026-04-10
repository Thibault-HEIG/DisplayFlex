# 📘 Comprendre le projet (Version Simplifiée)

Ce projet est un site web "tout-en-un". Il contient à la fois la partie visible (HTML/CSS) et la partie invisible qui réfléchit (Java), le tout relié à une mémoire (SQL).

## 🏗️ Qui fait quoi ? (Les 4 Piliers)

### 1. Le Frontend (La Vitrine)
* **C'est quoi ?** Les fichiers `index.html` et `style.css` dans le dossier `public/`.
* **Son rôle :** C'est l'interface que l'utilisateur voit.
* **Comment il parle au Java ?** Il utilise du JavaScript (une fonction `fetch`) pour envoyer des messages (requêtes POST) au serveur.

### 2. L'Infrastructure (Le Moteur)
* **C'est quoi ?** Le fichier `AppServer.java`.
* **⚠️ Zone Danger :** Ne modifiez pas ce fichier.
* **Son rôle :** C'est lui qui démarre le serveur web sur le port 8000. Il agit comme un aiguilleur :
    * Si on demande une page (ex: `/`), il envoie le HTML.
    * Si on demande un calcul (ex: `/api/process`), il passe le relais à votre code Java (`ApiHandler`).

### 3. Le Backend (Votre Cerveau)
* **C'est quoi ?** Les fichiers `Handler.java`.
* **✅ Zone de Code :** C'est ici que vous travaillez.
* **Leur rôle :** Il reçoit les données du site, réfléchit, et décide quoi faire (calculer, vérifier un mot de passe, enregistrer dans la base...).

### 4. La Base de Données (La Mémoire)
* **C'est quoi ?** Une image Docker où sont contenues les données du projet.
* **Son rôle :** Le Java oublie tout quand on l'éteint. La base de données, elle, n'oublie jamais. `DatabaseManager.java` est l'outil qui permet à votre code Java d'envoyer des ordres SQL (SELECT, INSERT) à la base.

---

## 🔄 Le Voyage d'une Donnée (Étape par Étape)

Imaginez qu'un utilisateur clique sur le bouton "Enregistrer" du site. Voici ce qui se passe techniquement :

1.  **Départ (JS) :** Le navigateur envoie une enveloppe (requête HTTP) contenant les données vers l'adresse `/api/process`.
2.  **Réception (AppServer) :** Le serveur voit l'adresse `/api/process` et se dit : *"Ah, c'est pour la partie logique !"*. Il donne l'enveloppe au bon `Handler.java`.
3.  **Traitement (Java) :**
    * Votre code ouvre l'enveloppe et lit le message.
    * Il demande à `DatabaseManager.java` : *"Note ça dans la base de données s'il te plaît"*.
    * La base confirme que c'est noté.
4.  **Réponse :** Votre code écrit une réponse (ex: *"C'est tout bon !"*) et la renvoie au navigateur.
5.  **Arrivée :** Le site web reçoit la réponse et affiche un message vert à l'écran.