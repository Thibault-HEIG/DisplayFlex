<?php

require_once __DIR__ . '/scripts/db-connection.php';

$results = null;
$errorMsg = null;

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["track_ids"])) {
    // On filtre pour ne garder que les IDs non vides
    $trackIds = array_filter($_POST["track_ids"]);

    if (!empty($trackIds)) {
        // Le script python est lancé automatiquement depuis le terminal
        $rawResults = runPythonScript($trackIds);
        $results = json_decode($rawResults, true);

        if (!$results) {
            $errorMsg = "Erreur de l'algorithme : " . htmlspecialchars($rawResults);
        } else {
            if (!insertResult($results)) {
                $errorMsg = "Erreur base de données, le résultat n'a pas été enregistré.";
            };
        }
    }
}

function runPythonScript($trackIds)
{
    // On définit les arguments du script py
    $args = "";

    foreach ($trackIds as $trackId) {
        // $trackId = escapeshellarg($trackId);
        $args .=  $trackId . " ";
    }

    // On construit la commande python
    $python = "/usr/bin/python3";
    $command = $python . " " . __DIR__ . "/../src/api/music-recommendation-algorithm/algorithm.py " . $args . " 2>&1";

    // On exécute et on capture le print() de Python
    $rawResults = shell_exec($command);

    return $rawResults;
}

function insertResult($results): bool
{
    global $errorMsg;

    try {
        $pdo = getConnection();
        foreach ($results as $result) {
            $query = "INSERT INTO resultats_recommandations 
            (track_id, artists, album_name, track_name, timestamp, rank, distance) 
            VALUES (:track_id, :artists, :album_name, :track_name, :timestamp, :rank, :distance)";
            $pstmt = $pdo->prepare($query);
            $pstmt->execute([
                ':track_id'   => $result['track_id'],
                ':artists'    => $result['artists'],
                ':album_name' => $result['album_name'],
                ':track_name' => $result['track_name'],
                ':timestamp'  => date('Y-m-d H:i:s'),
                ':rank'       => $result['rank'],
                ':distance'   => $result['distance']
            ]);
        }
        return true;
    } catch (PDOException $e) {
        $errorMsg = "Erreur : " . $e->getMessage();
        return false;
    }
}

?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Recommender</title>
    <link rel="stylesheet" href="css/music-reco.css">
    <script src="scripts/music-reco.js" defer></script>
</head>

<body>
    <nav>
        <a href="index.php">← Retour à l'accueil</a>
    </nav>
    <section id="hero">
        <h1>Zone Music</h1>
        <h2>Pour ceux qui veulent découvrir de nouveaux morceaux</h2>
    </section>

    <section id="responseArea">
        <?php if ($errorMsg): ?>
            <div class="error-banner" style="background: rgba(255, 77, 77, 0.1); border: 1px solid #ff4d4d; color: #ff4d4d; padding: 1rem; border-radius: 10px; text-align: center; margin-bottom: 2rem;">
                <?php echo $errorMsg; ?>
            </div>
        <?php endif; ?>

        <?php if ($results): ?>
            <h3 style="text-align: center; color: var(--color-dark-bg); margin-bottom: 2rem; font-size: var(--fs-medium);">Voici vos recommandations personnalisées</h3>
            
            <div class="results-grid">
                <?php foreach ($results as $result): ?>
                    <div class="recommendation-card">
                        <span class="rank">#<?php echo $result['rank']; ?></span>
                        
                        <h4><?php echo htmlspecialchars($result['track_name']); ?></h4>
                        <p class="artist"><?php echo htmlspecialchars($result['artists']); ?></p>

                        <a href="https://open.spotify.com/search/<?php echo urlencode($result['track_name'] . ' ' . $result['artists']); ?>" 
                           target="_blank" 
                           style="margin-top: 1rem; color: #1DB954; text-decoration: none; font-weight: 600; font-size: 0.8rem;">
                           ▶ Écouter sur Spotify
                        </a>
                    </div>
                <?php endforeach; ?>
            </div>
        <?php endif; ?>
    </section>

    <section id="input">
        <div class="container">
            <h3>Ajoutez des musiques que vous aimez</h3>
            <p>L'algorithme proposera des morceaux similaires. Plus la playlist d'entrée est grande, plus les résultats seront précis !</p>

            <form id="music-form" action="music-reco.php" method="post">
                <div id="songs-container">
                    <div class="song-row">
                        <input type="hidden" name="track_ids[]" value="">

                        <div class="field">
                            <label>Artiste(s)</label>
                            <input type="text" name="artists[]" placeholder="Justin Bieber" required>
                        </div>
                        <div class="field">
                            <label>Morceau</label>
                            <input type="text" name="track_names[]" placeholder="Beauty and a Beast" required>
                        </div>
                        <button type="button" class="remove-row" title="Supprimer cette ligne">×</button>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="button" id="add-row">+ Ajouter une musique</button>
                    <button type="submit" name="action_type" value="insert">Obtenir des recommandations</button>
                </div>
            </form>
        </div>
    </section>
</body>

</html>