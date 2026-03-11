
--INSERT ZONE--
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
        96
    ),
    (
        'Modelisation des donnees',
        'ModelDon',
        1,
        48
    ),
    (
        'Programmation',
        'ProglM',
        1,
        96
    ),
    (
        'Marketing digital',
        'MarDig',
        2,
        72
    ),
    (
        'Marketing et developpement de produit',
        'MarDevProd',
        2,
        24
    ),
    (
        'Recherche et analyse clients et publics cibles',
        'RechAnPub',
        2,
        24
    ),
    (
        'Auditer une communication numerique',
        'AuditCom',
        3,
        NULL
    ),
    (
        'Communication Serious Game',
        'ComGame',
        3,
        NULL
    ),
    (
        'Bases des medias numeriques',
        'BasMedNum',
        1,
        24
    ),
    (
        'Bases des neurosciences',
        'BasNeuro',
        1,
        24
    ),
    (
        'Durabilite',
        'Dura',
        4,
        24
    ),
    (
        'Sociologie des medias',
        'Socio',
        4,
        24
    ),
    (
        'Document web',
        'DocuWeb',
        5,
        48
    ),
    (
        'Outils de developpement',
        'OutDev',
        5,
        24
    ),
    (
        'Reseaux et environnement internet',
        'ResInt',
        5,
        24
    ),
    (
        'English for Engineers',
        'Ang',
        6,
        96
    ),
    (
        'Creation images',
        'Crealm',
        7,
        24
    ),
    (
        'Production de contenu media',
        'ProdCont',
        7,
        36
    ),
    (
        'Redaction et strategie de contenu',
        'RedCont',
        7,
        24
    ),
    (
        'Droit',
        'Droit',
        8,
        24
    ),
    (
        'Metiers des medias',
        'MetMed',
        8,
        12
    ),
    (
        'Infrastructure de donnees',
        'InfraDon',
        9,
        48
    ),
    (
        'Programmation serveur',
        'ProgServ',
        9,
        24
    ),
    (
        'Analyse de marche',
        'AnalysMar',
        2,
        24
    ),
    (
        'Developper une application web simple',
        'DevAppliS',
        3,
        NULL
    ),
    (
        'Mettre en place un ecosysteme digital',
        'MEPEcosys',
        3,
        NULL
    ),
    (
        'Conception orientee objet',
        'ConcepOb',
        1,
        24
    ),
    (
        'Outils methodologiques',
        'OutMetho',
        1,
        24
    ),
    (
        'Identite visuelle et systeme graphique',
        'IdentVis',
        10,
        24
    ),
    (
        'Interface utilisateur',
        'IntUtil',
        10,
        12
    ),
    (
        'Representation graphique des donnees',
        'RepGraph',
        10,
        12
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





--DELETE ZONE--
DELETE FROM eleves;

DROP TABLE eleves; -- /!\ DANGER - utiliser pour hard reset les élèves et leur id/!\