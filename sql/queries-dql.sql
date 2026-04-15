-- Active: 1775506739659@@localhost@5432@displayflex
--SELECT ZONE--
SELECT * FROM utilisateurs;

SELECT * FROM branches ORDER BY duree_semestre DESC;

SELECT * FROM modules;

SELECT b.nom, m.nom
FROM branches b
    LEFT JOIN modules m ON m.id = b.id_module;

SELECT *
FROM utilisateurs
WHERE
    nom = 'Moret' AND prenom = 'Thibault';

INSERT INTO utilisateurs (
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

SELECT metier, COUNT(*) AS total_recommendations, AVG(pourcentage) AS average_score, MIN(pourcentage) AS min_score, MAX(pourcentage) AS max_score, AVG(rang) AS average_rank
    FROM resultats_test
    WHERE rang <= 3
    GROUP BY metier
    ORDER BY total_recommendations DESC;

SELECT metier, COUNT(*) AS total_top_1
    FROM resultats_test
    WHERE rang = 1
    GROUP BY metier
    ORDER BY total_top_1 DESC;