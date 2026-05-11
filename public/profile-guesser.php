<?php

/**
 * Ce fichier PHP fait deux choses :
 * 1. Il demande des infos à la Base de Données (PDO).
 * 2. Il envoie vos réponses à l'algorithme Java (cURL).
 */

$skills = [
    's0'  => 'Marketing',
    's1'  => 'Graphic Design',
    's2'  => 'Coding',
    's3'  => 'Writing',
    's4'  => 'Interface',
    's5'  => 'Data',
    's6'  => 'Media',
    's7'  => 'Maths',
    's8'  => 'English',
    's9'  => 'Economy',
    's10' => 'Leadership',
    's11' => 'Oral Communication',
    's12' => 'Creativity',
    's13' => 'Analytical Thinking',
    's14' => 'Project Management',
    's15' => 'Storytelling',
];

$results    = null; // Ici on stockera les métiers trouvés par l'algorithme
$errorMsg   = null;
$posted     = [];

require_once 'scripts/db-connection.php';

$jobDescriptions = [];
try {
    // On se connecte à la DB pour récupérer les noms et descriptions des métiers
    $pdo  = getConnection();
    $stmt = $pdo->query('SELECT nom, description FROM metiers');

    // On transforme le résultat en un tableau "clé => valeur"
    // Exemple : ['Developpeur' => 'Crée des sites web', ...]
    $jobDescriptions = $stmt->fetchAll(PDO::FETCH_KEY_PAIR);
} catch (PDOException $e) {
    $errorMsg = 'Impossible de charger les métiers : ' . $e->getMessage();
}

// Si l'utilisateur a cliqué sur le bouton "Valider" (méthode POST)
if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    // On récupère les notes (0 à 10) que l'utilisateur a mises sur le site
    foreach ($skills as $id => $name) {
        $val = isset($_POST[$id]) ? (int) $_POST[$id] : 5;
        $posted[$id] = $val;
    }

    $save = isset($_POST['save']); // Est-ce que la case "Sauvegarder" est cochée ?

    $compId = null; // On créé une variable nulle pour stocker le futur id généré lors de l'insertion des compétences

    if ($save) {
        try {
            // On insère d'abord les notes dans la table 'competences'
            $pdo = getConnection();

            $dbCols = [
                'marketing',
                'design_graphique',
                'programmation',
                'ecriture',
                'design_interface',
                'data',
                'media',
                'maths',
                'english',
                'economie',
                'leadership',
                'communication_orale',
                'creativite',
                'pensee_analytique',
                'gestion_projet',
                'storytelling'
            ];
            $placeholders = implode(',', array_fill(0, count($dbCols), '?'));
            $sqlComp = "INSERT INTO competences (" . implode(',', $dbCols) . ") VALUES ($placeholders)";
            $stmtComp = $pdo->prepare($sqlComp);

            $compValues = [];
            foreach ($skills as $id => $name) {
                $compValues[] = $posted[$id];
            }

            $stmtComp->execute($compValues);
            // On récupère l'ID que la DB vient de créer automatiquement
            $compId = (int)$pdo->lastInsertId();
        } catch (PDOException $e) {
            $errorMsg = "Erreur base de données : " . $e->getMessage();
        }
    }

    // On prépare le "paquet" (JSON) à envoyer à notre programme Java
    $skillsPayload = [];
    foreach ($skills as $id => $name) {
        $skillsPayload[] = [
            "skill" => $name,
            "value" => (int)$posted[$id]
        ];
    }

    $payload = [
        "id_competences" => $compId,
        "save_result" => $save,
        "skills" => $skillsPayload,
        "student" => null
    ];

    // La cURL permet de relier PHP et Java, ils communiquent via HTTP (☝🏼Lecorney prime)
    $ch = curl_init('http://app:8000/api/algorithm/job');
    curl_setopt_array($ch, [
        CURLOPT_RETURNTRANSFER => true, // On veut lire la réponse
        CURLOPT_POST           => true, // On envoie des données
        CURLOPT_HTTPHEADER     => ['Content-Type: application/json'], // Le type d'envoi
        CURLOPT_POSTFIELDS     => json_encode($payload), // Le paquet de données
    ]);
    $response = curl_exec($ch);
    $curlError = curl_error($ch);

    if ($curlError) {
        $errorMsg = "L'algorithme Java ne répond pas : " . $curlError;
    } else {
        // On déballe la réponse reçue de Java (JSON) pour l'utiliser en PHP
        $data = json_decode($response, true);
        if (isset($data['status']) && $data['status'] === 'success') {
            $results = $data['results']; // On a nos résultats !
        } else {
            $errorMsg = $data['message'] ?? 'Erreur inconnue de l\'algorithme';
        }
    }
}
?>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conseiller d'orientation</title>
    <link rel="stylesheet" href="css/profile-guesser.css">
</head>

<body>
    <nav>
        <a href="index.php">← Retour à l'accueil</a>
        <div>
            <input type="checkbox" name="save" value="1" form="main-form">
            <label>Sauvegarder le résultat</label>
        </div>
    </nav>

    <section id="hero">
        <h1>Quelles perspectives d'avenir?</h1>
        <h2>Trouver un métier adapté</h2>
    </section>

    <section id="test-form">
        <div class="container">
            <h3>Auto-évaluez vos compétences dans ces domaines.</h3>
            <p>L'algorithme trouvera les métiers adaptés pour vous.</p>
            <div id="server-output">
                <?php if ($errorMsg): ?>
                    <p class="error"><?= htmlspecialchars($errorMsg) ?></p>

                <?php elseif ($results): ?>
                    <?php foreach ($results as $r): ?>
                        <div class="result-item rank-<?= (int) $r['rank'] ?>">
                            <div>
                                <div class="title">
                                    <span class="result-rank">#<?= (int) $r['rank'] ?></span>
                                    <h3 class="result-name"><?= htmlspecialchars($r['name']) ?></h3>
                                </div>
                                <p class="result-desc"><?= htmlspecialchars($jobDescriptions[$r['name']] ?? '...') ?></p>
                            </div>
                            <span class="result-score"><?= htmlspecialchars($r['score']) ?>%</span>
                        </div>
                    <?php endforeach; ?>
                <?php endif; ?>
            </div>

            <form method="post" action="" id="main-form">

                <div id="input-group">
                    <div id="input-group">
                        <div class="skill" id="marketing">
                            <div class="skill-top">
                                <h3 class="skill-name">Marketing</h3>
                                <span class="skill-value" id="v0">5</span>
                            </div>
                            <p class="description">Analyse & Stratégie</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s0" name="s0">
                        </div>

                        <div class="skill" id="graphic-design">
                            <div class="skill-top">
                                <h3 class="skill-name">Design Graphique</h3>
                                <span class="skill-value" id="v1">5</span>
                            </div>
                            <p class="description">Identité visuelle & mise en page</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s1" name="s1">
                        </div>

                        <div class="skill" id="coding">
                            <div class="skill-top">
                                <h3 class="skill-name">Programmation</h3>
                                <span class="skill-value" id="v2">5</span>
                            </div>
                            <p class="description">Développement & code</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s2" name="s2">
                        </div>

                        <div class="skill" id="writing">
                            <div class="skill-top">
                                <h3 class="skill-name">Écriture</h3>
                                <span class="skill-value" id="v3">5</span>
                            </div>
                            <p class="description">Rédaction & storytelling</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s3" name="s3">
                        </div>

                        <div class="skill" id="interface">
                            <div class="skill-top">
                                <h3 class="skill-name">Interface</h3>
                                <span class="skill-value" id="v4">5</span>
                            </div>
                            <p class="description">Expérience Utilisateur & Design d'Interfaces</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s4" name="s4">
                        </div>

                        <div class="skill" id="data">
                            <div class="skill-top">
                                <h3 class="skill-name">Données</h3>
                                <span class="skill-value" id="v5">5</span>
                            </div>
                            <p class="description">SQL & analytics</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s5" name="s5">
                        </div>

                        <div class="skill" id="media">
                            <div class="skill-top">
                                <h3 class="skill-name">Média</h3>
                                <span class="skill-value" id="v6">5</span>
                            </div>
                            <p class="description">Photo, Vidéo & Son</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s6" name="s6">
                        </div>

                        <div class="skill" id="maths">
                            <div class="skill-top">
                                <h3 class="skill-name">Maths</h3>
                                <span class="skill-value" id="v7">5</span>
                            </div>
                            <p class="description">Logique et Calcul</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s7" name="s7">
                        </div>

                        <div class="skill" id="english">
                            <div class="skill-top">
                                <h3 class="skill-name">Anglais</h3>
                                <span class="skill-value" id="v8">5</span>
                            </div>
                            <p class="description">Communication Orale et Écrite</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s8" name="s8">
                        </div>

                        <div class="skill" id="economy">
                            <div class="skill-top">
                                <h3 class="skill-name">Économie</h3>
                                <span class="skill-value" id="v9">5</span>
                            </div>
                            <p class="description">Économie et Gestion</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s9" name="s9">
                        </div>
                        <div class="skill" id="leadership">
                            <div class="skill-top">
                                <h3 class="skill-name">Leadership</h3>
                                <span class="skill-value" id="v10">5</span>
                            </div>
                            <p class="description">Direction & Gestion d'Equipe</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s10" name="s10">
                        </div>
                        <div class="skill" id="oral-communication">
                            <div class="skill-top">
                                <h3 class="skill-name">Communication Orale</h3>
                                <span class="skill-value" id="v11">5</span>
                            </div>
                            <p class="description">Prise de parole & Présentation</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s11" name="s11">
                        </div>
                        <div class="skill" id="creativity">
                            <div class="skill-top">
                                <h3 class="skill-name">Créativité</h3>
                                <span class="skill-value" id="v12">5</span>
                            </div>
                            <p class="description">Imagination & Originalité</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s12" name="s12">
                        </div>
                        <div class="skill" id="analytical-thinking">
                            <div class="skill-top">
                                <h3 class="skill-name">Pensée Analytique</h3>
                                <span class="skill-value" id="v13">5</span>
                            </div>
                            <p class="description">Raisonnement & Synthèse</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s13" name="s13">
                        </div>
                        <div class="skill" id="project-management">
                            <div class="skill-top">
                                <h3 class="skill-name">Gestion de Projets</h3>
                                <span class="skill-value" id="v14">5</span>
                            </div>
                            <p class="description">Organisation & Planification</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s14" name="s14">
                        </div>
                        <div class="skill" id="storytelling">
                            <div class="skill-top">
                                <h3 class="skill-name">StoryTelling</h3>
                                <span class="skill-value" id="v15">5</span>
                            </div>
                            <p class="description">Émotion & Sens</p>
                            <input type="range" min="0" max="10" step="1" value="5" id="s15" name="s15">
                        </div>
                    </div>

                    <button type="submit">Trouver son métier</button>
            </form>
        </div>
    </section>
    <script src="scripts/profileGuesser.js"></script>
</body>

</html>