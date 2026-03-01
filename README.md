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
├── 📁 src/main/java/                <-- ☕ BACK-END (Architecture Séparée)
│   ├── 📁 server/
│   │   └── ☕ AppServer.java        <-- 🔴 Infrastructure (Démarre le serveur et route les URL vers les handlers)
│   ├── 📁 handler/                  <-- 🟠 Contrôleurs (Logique des requêtes)
│   │   ├── ☕ BaseApiHandler.java   <-- 🔴 Classe abstraite parent (Gère la structure HTTP commune)
│   │   └── ☕ TrucHandler.java      <-- 🟢 Gère la logique de transformation d'un input en output
│   ├── 📁 database/                 <-- 🟠 Interactions Database
│   │   ├── ☕ DatabaseManager.java  <-- 🟠 Exécution des requêtes SQL
│   │   └── ☕ DatabaseSecurity.java <-- 🟠 Vérifications avant insertion
│   └── 📁 model/                    <-- 🟢 Classes objets
│       └── ☕ Student.java          <-- 🟢 Objet élève
│
├── 📁 public/                       <-- 🎨 FRONT-END
│   ├── 📄 file.html                 <-- 🟢 Structure HTML d'une des pages (index, sql, websites)
│   ├── 📁 css/                      <-- 🟢 Design et mise en page CSS
│   │   ├── 📄 reset.css             <-- 🟠 Reset pour toute feuille de style
│   │   ├── 📄 theme.css             <-- 🟢 Réglages du thème (charte graphique)
│   │   └── 📄 file.css              <-- 🟢 Feuille de style propre à chaque page
│   └── 📁 scripts/                  <-- 🟢 Scripts JavaScript
│       ├── 📄 projectCount.js       <-- 🟢 Script pour afficher le nombre de projets
│       └── 📄 insertStudent.js      <-- 🟢 Script avec l'appel fetch() vers /api/students
│
├── 📁 sql/                          <-- 🗄️ STRUCTURE DES DONNÉES
│   ├── 📜 init.sql                  <-- 🟠 Création des tables
│   ├── 📜 queries-dml.sql           <-- 🟢 Requêtes DML (INSERT, DELETE, UPDATE)
│   └── 📜 queries-dql.sql           <-- 🟢 Requêtes DQL (SELECT)
│
├── 📁 DOCUMENTATION/                <-- ℹ️ ZONE INFORMATION
│   ├── 🏁 Demarrage.md              <-- Initialisation et tutoriel Git
│   ├── 🛠️ Fonctionnement.md         <-- Fonctionnement global du repo
│   ├── 🤖 VibeCoding.md             <-- Guidelines et astuces pour l'IA
│   ├── 🎋 UtiliserGit.md            <-- Guide détaillé pour Git
│   └── 📖 UtiliserSQL.md            <-- Guide de configuration SQLite
│
├── 📁 lib/
│   └── 📦 sqlite-jdbc-3.42.0.0.jar <-- 🔴 Driver de connexion Java/SQL
│
├── 🗃️ ecole.db                  <-- 🔴 Fichier de base de données SQLite généré
├── 🧨 .gitignore                <-- 🔴 Paramétrages Git
└── 📝 README.md                 <-- Documentation principale
```
