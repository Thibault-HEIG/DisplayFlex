# Projet Collaboratif M54/2

Projet pour mettre en pratique les compétences HTML, CSS, Java, SQL et Git dans un seul et même environnement

---

## 📂 Structure du Projet (Où travailler ?)

⚠️ **ATTENTION** : Certains fichiers sont l'infrastructure du projet. Si vous les cassez, plus personne ne peut travailler.  
**🔴 NE PAS TOUCHER** - sauf si sûr à 100% de son code - *nouvelle branche git obligatoire*  
**🟠 DANGEREUX** - à modifier avec précaution - *nouvelle branche git obligatoire*  
**🟢 OPEN** - modifications autorisées - *nouvelle branche git recommandée*  

```
/DisplayFlex
│
├── 📁 src/main/java/            <-- ☕ BACK-END
│   ├── ☕ AppServer.java        <-- 🔴 Infrastructure serveur
│   ├── ☕ ApiHandler.java       <-- 🟢 ApiHandler (modifier le code ici)
│   └── ☕ DatabaseManager.java  <-- 🟠 Gestionnaire de connexion avec SQLite
│
├── 📁 public/                   <-- 🎨 FRONT-END
│   ├── 📄 connection.js         <-- 🟠 Communication avec Java
│   ├── 📄 file.html             <-- 🟢 Structure HTML d'une des pages
│   └── 📁 css/                  <-- 🟢 Design et mise en page CSS
│       ├── 📄 reset.css         <-- 🟠 Reset pour toute feuille de style
│       ├── 📄 theme.css         <-- 🟢 Réglages du thème (charte graphique)
│       └── 📄 file.css          <-- 🟢 Feuille de style propre à chaque page
│   └── 📁 other/                <-- 🟢 Fichiers d'expérimentation
│       ├── 📄 script.js         <-- 🟢 Script pour afficher le nombre de projets
│       └── 📄 insertStudent.js  <-- 🟢 Script pour envoyer le formulaire à Java
│
├── 📁 sql/                      <-- 🗄️ STRUCTURE DES DONNÉES
│   ├── 📜 init.sql              <-- 🟠 Création des tables
│   └── 📜 requetes.sql          <-- 🟢 Fichier de tests pour vos requêtes SELECT/INSERT
│
├── 📁 DOCUMENTATION/            <-- ℹ️ ZONE INFORMATION
│   ├── 🏁 Demarrage.md          <-- Initialisation et tutoriel Git
│   ├── 🛠️ Fonctionnement.md     <-- Fonctionnement global du repo
│   ├── 🤖 VibeCoding.md         <-- Guidelines et astuces pour l'IA
│   ├── 🎋 UtiliserGit.md        <-- Guide détaillé pour Git
│   └── 📖 UtiliserSQL.md        <-- Guide de configuration de l'extension SQLite
│
├── 📁 lib/
│   └── 📦 sqlite-jdbc-3.42.0.0.jar <-- 🔴 Driver nécessaire à la connexion Java/SQL
│
├── 🗃️ ecole.db                  <-- 🔴 Fichier de base de données SQLite généré
├── 🧨 .gitignore                <-- 🔴 Paramétrages Git
└── 📝 README.md                 <-- Documentation principale
```

## Idées de features à implémenter
- Créer un design CSS accueillant
- Créer et remplir une table 'eleves' complète
- Créer et remplir une table 'matieres'
- Créer un bouton qui permet d'afficher la liste des matières
- Créer un input qui demande un prénom et affiche toutes les infos de l'étudiant
- Implémenter le mini-jeu de Thibault et Christian dans une nouvelle page du site
- Automatiser le lancement du programme Java (compilation, exécution)
- Créer une page pour accéder à nos sites web
- Améliorer la documentation
- Créer une charte graphique (```theme.css```) avec des variables de typo, couleurs, style global
- Ajouter un mode sombre
