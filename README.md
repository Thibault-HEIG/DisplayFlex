# Projet Collaboratif M54/2

Projet pour mettre en pratique les compétences HTML, CSS, Java et Git dans un seul et même projet

---

## 📂 Structure du Projet (Où travailler ?)

⚠️ **ATTENTION** : Certains fichiers sont l'infrastructure du projet. Si vous les cassez, plus personne ne peut travailler.


/DisplayFlex
│
├── 📁 src/main/java/
│   └── ☕ AppServer.java        <-- ⚠️ ZONE DANGER : Ne touchez pas aux imports ni au "main".
│                                    ✅ ZONE SAFE : Cherchez la classe "ApiHandler" (lignes ~50+).
│                                    C'est ICI que vous codez votre logique Java.
│
├── 📁 public/                   <-- 🎨 ZONE CRÉATIVE (Frontend)
│   ├── 📄 index.html            <-- Modifiez la structure de la page ici.
│   └── 🎨 style.css             <-- Changez les couleurs et le design ici.
│
│
│
├── 📁 DOCUMENTATION/             <-- ℹ️ ZONE INFORMATION (Doc)
│   ├── 🏁 demarrage.md           <-- Intitialisation et tuto Git
│   └── 🛠️ fonctionnement.md      <-- Comment le repository fonctionne (analogie)
│
│
├── 🧨 .gitignore                 <-- ⚠️ NE PAS TOUCHER (paramétrages de git)
│
└── 📝 README.md                  <-- Ce fichier.