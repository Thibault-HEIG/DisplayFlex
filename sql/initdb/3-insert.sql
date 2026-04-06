-- Active: 1775506739659@@localhost@5432@displayflex

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

INSERT INTO branches (sujet, nom, id_module, duree_semestre)
SELECT 'Mathematiques', 'MathlM', id, 96 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Modelisation des donnees', 'ModelDon', id, 48 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Programmation', 'ProglM', id, 96 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Bases des medias numeriques', 'BasMedNum', id, 24 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Bases des neurosciences', 'BasNeuro', id, 24 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Conception orientee objet', 'ConcepOb', id, 24 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Outils methodologiques', 'OutMetho', id, 24 FROM modules WHERE nom = 'Sciences ingenierie des medias'
UNION ALL
SELECT 'Marketing digital', 'MarDig', id, 72 FROM modules WHERE nom = 'Marketing'
UNION ALL
SELECT 'Marketing et developpement de produit', 'MarDevProd', id, 24 FROM modules WHERE nom = 'Marketing'
UNION ALL
SELECT 'Recherche et analyse clients et publics cibles', 'RechAnPub', id, 24 FROM modules WHERE nom = 'Marketing'
UNION ALL
SELECT 'Analyse de marche', 'AnalysMar', id, 24 FROM modules WHERE nom = 'Marketing'
UNION ALL
SELECT 'Auditer une communication numerique', 'AuditCom', id, NULL FROM modules WHERE nom = 'SAE'
UNION ALL
SELECT 'Communication Serious Game', 'ComGame', id, NULL FROM modules WHERE nom = 'SAE'
UNION ALL
SELECT 'Developper une application web simple', 'DevAppliS', id, NULL FROM modules WHERE nom = 'SAE'
UNION ALL
SELECT 'Mettre en place un ecosysteme digital', 'MEPEcosys', id, NULL FROM modules WHERE nom = 'SAE'
UNION ALL
SELECT 'Durabilite', 'Dura', id, 24 FROM modules WHERE nom = 'Sociologie et durabilite'
UNION ALL
SELECT 'Sociologie des medias', 'Socio', id, 24 FROM modules WHERE nom = 'Sociologie et durabilite'
UNION ALL
SELECT 'Document web', 'DocuWeb', id, 48 FROM modules WHERE nom = 'Technologies web'
UNION ALL
SELECT 'Outils de developpement', 'OutDev', id, 24 FROM modules WHERE nom = 'Technologies web'
UNION ALL
SELECT 'Reseaux et environnement internet', 'ResInt', id, 24 FROM modules WHERE nom = 'Technologies web'
UNION ALL
SELECT 'English for Engineers', 'Ang', id, 96 FROM modules WHERE nom = 'Anglais'
UNION ALL
SELECT 'Creation images', 'Crealm', id, 24 FROM modules WHERE nom = 'Contenus medias'
UNION ALL
SELECT 'Production de contenu media', 'ProdCont', id, 36 FROM modules WHERE nom = 'Contenus medias'
UNION ALL
SELECT 'Redaction et strategie de contenu', 'RedCont', id, 24 FROM modules WHERE nom = 'Contenus medias'
UNION ALL
SELECT 'Droit', 'Droit', id, 24 FROM modules WHERE nom = 'Droit et metiers des medias'
UNION ALL
SELECT 'Metiers des medias', 'MetMed', id, 12 FROM modules WHERE nom = 'Droit et metiers des medias'
UNION ALL
SELECT 'Infrastructure de donnees', 'InfraDon', id, 48 FROM modules WHERE nom = 'Developpement web'
UNION ALL
SELECT 'Programmation serveur', 'ProgServ', id, 24 FROM modules WHERE nom = 'Developpement web'
UNION ALL
SELECT 'Identite visuelle et systeme graphique', 'IdentVis', id, 24 FROM modules WHERE nom = 'Utilisateurs et interfaces'
UNION ALL
SELECT 'Interface utilisateur', 'IntUtil', id, 12 FROM modules WHERE nom = 'Utilisateurs et interfaces'
UNION ALL
SELECT 'Representation graphique des donnees', 'RepGraph', id, 12 FROM modules WHERE nom = 'Utilisateurs et interfaces';