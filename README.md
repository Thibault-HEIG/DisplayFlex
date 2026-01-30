# Projet Collaboratif M54/2

Projet pour mettre en pratique les compétences HTML, CSS, Java, SQL et Git dans un seul et même projet

---

## 📂 Structure du Projet (Où travailler ?)

⚠️ **ATTENTION** : Certains fichiers sont l'infrastructure du projet. Si vous les cassez, plus personne ne peut travailler.


/DisplayFlex
│
├── 📁 src/main/java/            <-- ☕ BACK-END
│   ├── ☕ AppServer.java         <-- ⚠️ ZONE DANGER : Infrastructure serveur (NE PAS TOUCHER).
│   ├── ☕ ApiHandler.java        <-- ✏️ ZONE OK : ApiHandler (modifier le code ici).
│   └── ☕ DatabaseManager.java   <-- ⚙️ Gestionnaire de connexion SQLite et exécution SQL.
│
├── 📁 public/                   <-- 🎨 FRONT-END
│   ├── 📄 script.js             <-- ⚠️ ZONE DANGER : Communication avec Java (NE PAS TOUCHER)
│   ├── 📄 index.html            <-- Structure HTML de l'interface.
│   └── 🎨 style.css             <-- Design et mise en page CSS.
│
├── 📁 sql/                      <-- 🗄️ STRUCTURE DES DONNÉES
│   ├── 📜 init.sql              <-- Création des tables et insertion initiale.
│   └── 📜 requetes.sql          <-- Fichier de tests pour vos requêtes SELECT/INSERT.
│
├── 📁 DOCUMENTATION/            <-- ℹ️ ZONE INFORMATION
│   ├── 🏁 Demarrage.md          <-- Initialisation et tutoriel Git.
│   ├── 🛠️ Fonctionnement.md     <-- Fonctionnement global du repo.
│   ├── 🤖 VibeCoding.md         <-- Guidelines et astuces pour l'IA.
│   ├── 🎋 UtiliserGit.md        <-- Guide détaillé pour Git.
│   └── 📖 UtiliserSQL.md        <-- Guide de configuration de l'extension SQLite.
│
├── 📁 lib/
│   └── 📦 sqlite-jdbc-3.42.0.0.jar <-- Driver nécessaire à la connexion Java/SQL.
│
├── 🗃️ ecole.db                  <-- Fichier de base de données SQLite généré.
├── 🧨 .gitignore                <-- ⚠️ Paramétrages Git (NE PAS TOUCHER).
└── 📝 README.md                 <-- Documentation principale.


### Idées de features à implémenter
- Créer un design CSS accueillant
- Créer et remplir une table 'eleves' complète
- Créer et remplir une table 'matieres'
- Créer un bouton qui permet d'afficher la liste des matières
- Créer un input qui demande un prénom et affiche toutes les infos de l'étudiant
- Implémenter le mini-jeu de Thibault dans une nouvelle page du site