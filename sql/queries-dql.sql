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

DROP TABLE eleves; -- /!\ DANGER - utiliser pour hard reset les élèves et leur id/!\

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