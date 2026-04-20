# Projet Collaboratif M54/2

Projet pour mettre en pratique les compétences HTML, CSS, Java, SQL, PHP et Git dans un seul et même environnement

---

## 📂 Structure du Projet (Où travailler ?)

⚠️ **ATTENTION** : Certains fichiers sont l'infrastructure du projet. Si vous les cassez, plus personne ne peut travailler.  
**🔴 NE PAS TOUCHER** - sauf si sûr à 100% de son code
**🟠 DANGEREUX** - à modifier avec précaution 
**🟢 OPEN** - modifications autorisées

```
/DisplayFlex
│
├── 📁 src/main/java/                <-- ☕ BACK-END (Architecture Séparée)
│   ├── 📁 server/
│   │   └── ☕ AppServer.java        <-- 🔴 Démarre le serveur et route les URL vers les handlers
│   ├── 📁 handler/                  <-- 🟠 Contrôleurs (Logique des requêtes)
│   │   ├── ☕ BaseApiHandler.java   <-- 🔴 Classe abstraite parent (Gère la structure HTTP commune)
│   │   └── ☕ TrucHandler.java      <-- 🟢 Gère la logique de transformation d'un input en output
│   ├── 📁 database/                 <-- 🟢 Interactions Database
│   │   ├── ☕ DatabaseManager.java  <-- 🟢 Exécution des requêtes SQL
│   │   └── ☕ DatabaseSecurity.java <-- 🟠 Vérifications avant insertion
│   └── 📁 model/                    <-- 🟢 Classes objets
│       ├── ☕ Vector.java           <-- 🟢 Objet vecteur
│       ├── ☕ Profile.java          <-- 🟢 Objet métier
│       └── ☕ Student.java          <-- 🟢 Objet élève
│
├── 📁 public/                       <-- 🎨 FRONT-END
│   ├── 📄 file.html                 <-- 🟢 Structure HTML d'une page statique
│   ├── 📄 file.php                  <-- 🟢 Structure HTML d'une page dynamique
│   ├── 📁 css/                      <-- 🟢 Design et mise en page CSS
│   │   ├── 📄 reset.css             <-- 🟠 Reset pour toute feuille de style
│   │   ├── 📄 theme.css             <-- 🟢 Réglages du thème (charte graphique)
│   │   └── 📄 file.css              <-- 🟢 Feuille de style propre à chaque page
│   ├── 📁 php/                      <-- 🟢 Design et mise en page CSS
│   │   ├── 📄 db-connection.php     <-- 🟠 Script de création de connexion avec la DB
│   │   └── 📄 script-home.php       <-- 🟢 Script pour afficher la liste des tâches sur l'accueil
│   └── 📁 scripts/                  <-- 🟢 Scripts JavaScript
│       ├── 📄 projectCount.js       <-- 🟢 Script pour afficher le nombre de projets
│       ├── 📄 profileGuesser.js     <-- 🟢 Script lié à l'orientateur de métier
│       └── 📄 insertStudent.js      <-- 🟢 Script avec l'appel fetch() vers /api/students
│
├── 📁 sql/                          <-- 🗄️ STRUCTURE DES DONNÉES
│   ├── 📁 initdb/                   <-- 🟠 Scripts exécutés au démarrage du conteneur DB         
│   │   └── 📜 1-init.sql
│   ├── 📜 insert.sql                <-- 🟠 Sauvegarde des données initiales statiques
│   ├── 📜 remove.sql                <-- 🟢 Commandes usuelles pour la réinitialisation de tables
│   └── 📜 queries-dql.sql           <-- 🟢 Requêtes DQL de test (SELECT)
│
├── 📁 DOCUMENTATION/                <-- ℹ️ ZONE INFORMATION
│
├── 📁 lib/
│   └── 📦 postgresql-42.7.10.jar    <-- 🔴 Driver de connexion Java/PostgreSQL
│
├── 🐳 Dockerfile                    <-- 🔴 Instructions de build du serveur Java
├── 🐳 php.Dockerfile                <-- 🔴 Instructions de build du serveur PHP
├── 🐋 docker-compose.yml            <-- 🔴 Orchestration de l'app et de la DB PostgreSQL
├── 🧨 .gitignore                    <-- 🔴 Fichiers ignorés par Git
├── ⚙️ .env                          <-- 🔴 Variables sensible (à garder secrètes)
├── ⚙️ nginx.conf                    <-- 🔴 Gestion des ports entre Java et PHP
└── 📝 README.md                     <-- 👈🏼 Vous êtes ici
```
