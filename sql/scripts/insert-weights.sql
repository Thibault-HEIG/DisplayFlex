-- Active: 1775506739659@@localhost@5432@displayflex
INSERT INTO
    poids (
        marketing,
        design_graphique,
        programmation,
        ecriture,
        design_interface,
        data,
        media,
        maths,
        english,
        economie,
        leadership,
        communication_orale,
        creativite,
        pensee_analytique,
        gestion_projet,
        storytelling
    )
VALUES (
        7,
        3,
        14,
        11,
        5,
        20,
        3,
        17,
        13,
        10,
        6,
        12,
        0,
        20,
        0,
        0
    ), -- 1: Data Analyst
    (
        10,
        18,
        11,
        7,
        19,
        8,
        9,
        4,
        11,
        5,
        5,
        11,
        18,
        12,
        0,
        10
    ), -- 2: Web Designer
    (
        18,
        6,
        2,
        19,
        6,
        7,
        18,
        5,
        17,
        11,
        10,
        19,
        15,
        0,
        14,
        19
    ), -- 3: Communication Specialist
    (
        20,
        7,
        3,
        14,
        7,
        16,
        8,
        12,
        17,
        18,
        18,
        16,
        15,
        17,
        19,
        14
    ), -- 4: Marketing Manager
    (
        9,
        14,
        7,
        10,
        20,
        13,
        9,
        6,
        13,
        8,
        8,
        15,
        18,
        16,
        11,
        13
    ), -- 5: UX/UI Designer
    (
        5,
        4,
        20,
        8,
        10,
        11,
        7,
        12,
        13,
        6,
        7,
        0,
        12,
        19,
        0,
        0
    ), -- 6: Web Developer
    (
        8,
        20,
        4,
        6,
        9,
        4,
        17,
        7,
        10,
        5,
        5,
        0,
        20,
        0,
        9,
        18
    ), -- 7: Motion Designer
    (
        11,
        20,
        3,
        7,
        11,
        4,
        15,
        5,
        11,
        7,
        6,
        12,
        20,
        0,
        12,
        16
    ), -- 8: Graphic Designer
    (
        17,
        5,
        4,
        20,
        9,
        14,
        15,
        7,
        18,
        11,
        9,
        15,
        16,
        17,
        16,
        20
    ), -- 9: Content Strategist
    (
        15,
        4,
        8,
        14,
        11,
        16,
        8,
        13,
        15,
        18,
        19,
        17,
        14,
        18,
        20,
        16
    ), -- 10: Product Manager
    (
        18,
        17,
        2,
        14,
        10,
        9,
        17,
        6,
        16,
        15,
        20,
        18,
        20,
        13,
        17,
        19
    ), -- 11: Creative Director
    (
        19,
        4,
        12,
        15,
        11,
        18,
        12,
        11,
        17,
        10,
        0,
        14,
        13,
        19,
        15,
        0
    ), -- 12: SEO Specialist
    (
        4,
        3,
        20,
        9,
        12,
        17,
        7,
        14,
        14,
        7,
        10,
        0,
        13,
        19,
        0,
        0
    );
-- 13: Full Stack Developer

INSERT INTO
    metiers (nom, description, id_poids)
VALUES (
        'Data Analyst',
        'Collecte, nettoie et analyse des données pour dégager des insights, construit tableaux de bord et rapports, formule des recommandations métier basées sur les indicateurs clés pour aider à la décision.',
        1
    ),
    (
        'Web Designer',
        'Crée l''identité visuelle et la mise en page de sites web, définit la charte graphique digitale, conçoit maquettes responsives et éléments graphiques en veillant à la cohérence de marque et à l''accessibilité.',
        2
    ),
    (
        'Communication Specialist',
        'Conçoit et met en œuvre des stratégies de communication 360° (médias, réseaux sociaux, événements, RP), rédige contenus et messages clés, gère la relation avec les parties prenantes et suit la réputation de la marque.',
        3
    ),
    (
        'Marketing Manager',
        'Pilote les stratégies marketing digitales (SEO/SEA, e-mail, réseaux sociaux, display), planifie et exécute les campagnes, suit les KPIs, optimise les budgets médias et coordonne agences et équipes internes.',
        4
    ),
    (
        'UX/UI Designer',
        'Conçoit des expériences et interfaces centrées utilisateur, mène la recherche UX, crée wireframes, prototypes et maquettes UI, puis teste et itère les parcours pour améliorer l''ergonomie sur web et mobile.',
        5
    ),
    (
        'Web Developer',
        'Développe et maintient des sites et applications web, écrit du code propre et testable (front et/ou back), intègre maquettes, optimise les performances et corrige les bugs en collaboration avec les designers et chefs de projet.',
        6
    ),
    (
        'Motion Designer',
        'Crée des animations 2D/3D et visuels en mouvement pour le web, la publicité ou le produit, participe à la conception créative, prépare storyboards et moodboards, puis anime typographie, illustration et vidéo.',
        7
    ),
    (
        'Graphic Designer',
        'Conçoit des identités visuelles et supports print/digitaux (logos, affiches, bannières, interfaces), met en forme messages et concepts de marque en images claires et impactantes, en respectant charte et contraintes techniques.',
        8
    ),
    (
        'Content Strategist',
        'Définit la stratégie éditoriale multicanale, planifie et coordonne la production de contenus, structure les messages selon les objectifs business et SEO, mesure la performance et ajuste les formats et sujets.',
        9
    ),
    (
        'Product Manager',
        'Porter la vision du produit, analyser besoins utilisateurs et marché, prioriser la roadmap, rédiger user stories et coordonner équipes design, tech et business pour livrer des fonctionnalités à forte valeur.',
        10
    ),
    (
        'Creative Director',
        'Définit la vision créative globale d''une marque ou d''un projet, supervise les concepts visuels et narratifs, encadre les équipes créatives, valide les pistes et garantit la cohérence esthétique et stratégique.',
        11
    ),
    (
        'SEO Specialist',
        'Optimise la visibilité d''un site sur les moteurs de recherche via audit technique, recherche de mots-clés, optimisation on-page, netlinking et analyse de performances pour augmenter trafic organique qualifié.',
        12
    ),
    (
        'Full-Stack Developer',
        'Développe le front-end et le back-end d''applications web, conçoit APIs et modèles de données, intègre interfaces, gère la logique serveur et veille à la performance, la sécurité et la maintenabilité de l''ensemble.',
        13
    );