<?php

require_once 'db-connection.php';

$pdo = getConnection();

$stmt = $pdo->prepare('SELECT tache FROM liste_taches');
$stmt->execute();

$tasks = $stmt->fetchAll(PDO::FETCH_COLUMN); // Retourne un tableau de lignes