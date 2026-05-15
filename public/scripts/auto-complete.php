<?php

/**
 * SCRIPT : auto-complete.php
 * ROLE : Recherche dynamique dans la base de données SQLite (89'000 lignes)
 * 
 * Pourquoi côté serveur ? 
 * Charger 89'000 lignes en JS (10-15 Mo) ralentirait la page. 
 * SQLite, avec des index, peut fouiller ces lignes en moins de 1ms.
 */

// 1. Définition du chemin vers la base de données
const DATABASE_FILE = __DIR__ . '/../../src/api/music-recommendation-algorithm/data/music_database.db';

// Vérification de l'existence du fichier
if (!file_exists(DATABASE_FILE)) {
    header('Content-Type: application/json', true, 500);
    echo json_encode(['error' => 'Base de données introuvable à : ' . DATABASE_FILE]);
    exit;
}

// 2. Récupération des paramètres envoyés par le JavaScript (fetch)
// 'q' est le texte tapé par l'utilisateur
// 'type' est soit 'artists' soit 'track_name' (le nom de l'input)
$searchTerm = $_GET['q'] ?? '';
$searchType = str_replace('[]', '', $_GET['type'] ?? 'track_name');
$contextValue = $_GET['context'] ?? '';

// 3. Sécurité : On ne lance pas de recherche si moins de 2 caractères
if (strlen($searchTerm) < 2) {
    header('Content-Type: application/json');
    echo json_encode([]);
    exit;
}

try {
    // 4. Connexion à SQLite avec PDO
    $pdo = new PDO("sqlite:" . DATABASE_FILE, null, null, [
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,        // Active les erreurs détaillées
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC    // Retourne des tableaux associatifs
    ]);

    // 5. Choix de la colonne SQL en fonction du champ input utilisé
    $column = ($searchType === 'artists') ? 'artists' : 'track_name';
    $otherColumn = ($column === 'artists') ? 'track_name' : 'artists';

    /**
     * 6. La requête SQL optimisée sur la table 'songs'
     * - WHERE $column LIKE :q : Recherche le texte n'importe où dans la colonne
     * - context : si présent, on filtre aussi sur l'autre colonne
     * - ORDER BY popularity DESC : Les morceaux les plus connus sortent en premier
     * - LIMIT 5 : On ne renvoie que les 5 meilleurs pour ne pas encombrer le réseau
     */
    $sql = "SELECT track_id, track_name, artists FROM songs 
            WHERE $column LIKE :q";
    
    $params = [':q' => "%$searchTerm%"];

    if (!empty($contextValue)) {
        $sql .= " AND $otherColumn LIKE :context";
        $params[':context'] = "%$contextValue%";
    }

    $sql .= " ORDER BY popularity DESC LIMIT 5";

    $stmt = $pdo->prepare($sql);
    $stmt->execute($params);
    $results = $stmt->fetchAll();

    // 7. Envoi du résultat au format JSON au navigateur
    header('Content-Type: application/json');
    echo json_encode($results);

} catch (PDOException $e) {
    header('Content-Type: application/json', true, 500);
    echo json_encode(['error' => 'Erreur SQL : ' . $e->getMessage()]);
}
