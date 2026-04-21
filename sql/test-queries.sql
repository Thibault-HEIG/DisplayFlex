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
    nom = 'Moret'
    AND prenom = 'Thibault';

INSERT INTO
    utilisateurs (
        nom,
        prenom,
        classe,
        est_eleve,
        email,
        date_naissance
    )
VALUES (
        'Moret',
        'Thibault',
        'M54-2',
        TRUE,
        'thibault.moret@heig-vd.ch',
        '2005-07-05'
    );

SELECT
    m.nom,
    COUNT(*) AS total_recommendations,
    AVG(pourcentage_similitude) AS average_score,
    MIN(pourcentage_similitude) AS min_score,
    MAX(pourcentage_similitude) AS max_score,
    AVG(rang) AS average_rank
FROM resultats_test LEFT JOIN metiers m ON m.id = resultats_test.id_metier
WHERE
    rang <= 3 AND humain = true
GROUP BY
    m.nom
ORDER BY total_recommendations DESC;

SELECT m.nom, COUNT(*) AS total_top_1
FROM resultats_test LEFT JOIN metiers m ON m.id = resultats_test.id_metier
WHERE
    rang = 1
GROUP BY
    m.nom
ORDER BY total_top_1 DESC;

SELECT * FROM liste_taches;
SELECT * FROM resultats_test WHERE humain = false;

SELECT * FROM metiers LEFT JOIN poids ON metiers.id_poids = poids.id;

SELECT c.* FROM competences c LEFT JOIN resultats_test rt ON c.id = rt.id_competences;