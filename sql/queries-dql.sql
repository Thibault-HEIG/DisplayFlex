-- Active: 1775506739659@@localhost@5432@displayflex
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

INSERT INTO eleves (
        nom,
        prenom,
        classe,
        email,
        date_naissance
    )
VALUES (
        'Moret',
        'Thibault',
        'M54-2',
        'thibault.moret@heig-vd.ch',
        '2005-07-05'
    );

SELECT COUNT(*), metier FROM resultats_test
WHERE rang <= 3
GROUP BY metier
ORDER BY COUNT(*) DESC;

DROP TABLE resultats_test;