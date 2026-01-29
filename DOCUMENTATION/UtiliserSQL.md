**Configurer VS Code pour SQLite (Sans Docker)**

Pour que le bouton "Run" ou tes raccourcis fonctionnent depuis ton fichier .sql, tu as besoin de l'extension qui fait le pont.

----

1. Installer l'extension Va dans l'onglet Extensions de VS Code et installe *SQLite* (l'auteur est alexcvzz). C'est la référence.

----

2. Lier SQL à ta base de données :

Ouvre la "Palette de Commandes" (Cmd + Maj + P).
*Tape SQLite: Open Database.*

Choisis ton fichier ecole.db dans la liste. (Maintenant, VS Code sait que c'est la base active).

----

3. Exécuter tes requêtes

Va dans ton fichier sql/requetes.sql.

Sélectionne le code SQL que tu veux lancer.

Fais Cmd + Maj + P > *SQLite: Run Selected Query.*