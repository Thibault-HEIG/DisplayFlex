
--INSERT ZONE--
INSERT INTO eleves (
        nom,
        prenom,
        classe,
        date_naissance
    )
VALUES (
        'Moret',
        'Thibault',
        'M54-2',
        '2005-07-05'
    );

INSERT INTO branches (
        sujet,
        nom,
        id_module,
        duree_semestre
    )
VALUES (
        'Mathematiques',
        'MathlM',
        1,
        4
    ),
    (
        'Modelisation des donnees',
        'ModelDon',
        1,
        2
    ),
    (
        'Programmation',
        'ProglM',
        1,
        4
    ),
    (
        'Marketing digital',
        'MarDig',
        2,
        4
    ),
    (
        'Marketing et developpement de produit',
        'MarDevProd',
        2,
        1
    ),
    (
        'Recherche et analyse clients et publics cibles',
        'RechAnPub',
        2,
        1
    ),
    (
        'Auditer une communication numerique',
        'AuditCom',
        3,
        1
    ),
    (
        'Communication Serious Game',
        'ComGame',
        3,
        1
    ),
    (
        'Bases des medias numeriques',
        'BasMedNum',
        1,
        1
    ),
    (
        'Bases des neurosciences',
        'BasNeuro',
        1,
        1
    ),
    (
        'Durabilite',
        'Dura',
        4,
        1
    ),
    (
        'Sociologie des medias',
        'Socio',
        4,
        1
    ),
    (
        'Document web',
        'DocuWeb',
        5,
        2
    ),
    (
        'Outils de developpement',
        'OutDev',
        5,
        1
    ),
    (
        'Reseaux et environnement internet',
        'ResInt',
        5,
        1
    ),
    (
        'English for Engineers',
        'Ang',
        6,
        4
    ),
    (
        'Creation images',
        'Crealm',
        7,
        2
    ),
    (
        'Production de contenu media',
        'ProdCont',
        7,
        1
    ),
    (
        'Redaction et strategie de contenu',
        'RedCont',
        7,
        1
    ),
    (
        'Droit',
        'Droit',
        8,
        1
    ),
    (
        'Metiers des medias',
        'MetMed',
        8,
        1
    ),
    (
        'Infrastructure de donnees',
        'InfraDon',
        9,
        2
    ),
    (
        'Programmation serveur',
        'ProgServ',
        9,
        1
    ),
    (
        'Analyse de marche',
        'AnalysMar',
        2,
        1
    ),
    (
        'Developper une application web simple',
        'DevAppliS',
        3,
        1
    ),
    (
        'Mettre en place un ecosysteme digital',
        'MEPEcosys',
        3,
        1
    ),
    (
        'Conception orientee objet',
        'ConcepOb',
        1,
        1
    ),
    (
        'Outils methodologiques',
        'OutMetho',
        1,
        1
    ),
    (
        'Identite visuelle et systeme graphique',
        'IdentVis',
        10,
        1
    ),
    (
        'Interface utilisateur',
        'IntUtil',
        10,
        1
    ),
    (
        'Representation graphique des donnees',
        'RepGraph',
        10,
        1
    );

INSERT INTO modules (nom, secteur)
VALUES (
        'Sciences ingenierie des medias',
        'Technique'
    ),
    ('Marketing', 'Strategique'),
    ('SAE', 'Pratique'),
    (
        'Sociologie et durabilite',
        'Complementaire'
    ),
    (
        'Technologies web',
        'Technique'
    ),
    ('Anglais', 'Complementaire'),
    ('Contenus medias', 'Creatif'),
    (
        'Droit et metiers des medias',
        'Complementaire'
    ),
    (
        'Developpement web',
        'Technique'
    ),
    (
        'Utilisateurs et interfaces',
        'Strategique'
    );



--SELECT ZONE--
SELECT * FROM eleves;

SELECT * FROM branches;

SELECT * FROM modules;

SELECT b.nom, m.nom
FROM branches b
    LEFT JOIN modules m ON m.id = b.id_module;