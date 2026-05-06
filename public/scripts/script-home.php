<?php

require_once __DIR__ . '/db-connection.php';

$tasks = [];

try {
    $pdo = getConnection();
    $stmt = $pdo->prepare('SELECT tache FROM liste_taches');
    $stmt->execute();
    $tasks = $stmt->fetchAll(PDO::FETCH_COLUMN); // Retourne un tableau de lignes
} catch (Exception $e) {
    error_log("Erreur lors de la récupération des tâches : " . $e->getMessage());
}