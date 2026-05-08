-- Active: 1775506739659@@localhost@5432@displayflex
DROP TABLE branches;
DROP TABLE modules;
DROP TABLE poids;
DROP TABLE metiers;
DROP TABLE utilisateurs_branches;

DROP TABLE utilisateurs; -- /!\ DANGER - utiliser pour hard reset les élèves et leur id /!\

DROP TABLE resultats_test; -- /!\ DANGER - utiliser pour hard reset les résultats et leur id /!\

DELETE FROM resultats_test WHERE humain = false; -- /!\ Supprimer les tests de simulation /!\
