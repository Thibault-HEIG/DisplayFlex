-- Active: 1775506739659@@localhost@5432@displayflex
DROP TABLE branches;
DROP TABLE modules;
DROP TABLE poids;
TRUNCATE TABLE poids RESTART IDENTITY CASCADE;
DROP TABLE metiers;
TRUNCATE TABLE metiers RESTART IDENTITY CASCADE;
TRUNCATE TABLE competences RESTART IDENTITY CASCADE;
DROP TABLE utilisateurs_branches;

DROP TABLE utilisateurs; -- /!\ DANGER - utiliser pour hard reset les élèves et leur id /!\

DROP TABLE resultats_test; -- /!\ DANGER - utiliser pour hard reset les résultats et leur id /!\

DELETE FROM resultats_test WHERE humain = false; -- /!\ Supprimer les tests de simulation /!\
