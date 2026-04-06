-- Active: 1775506739659@@localhost@5432
--SELECT ZONE--
SELECT * FROM eleves;

SELECT * FROM branches ORDER BY duree_semestre DESC;

SELECT * FROM modules;

SELECT b.nom, m.nom
FROM branches b
    LEFT JOIN modules m ON m.id = b.id_module;

SELECT *
FROM eleves
WHERE
    nom = 'Moret' AND prenom = 'Thibault';