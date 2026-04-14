<?php

require_once 'db-connection.php';

$pdo = getConnection();

$stmt = $pdo->prepare('SELECT task FROM task_list');
$stmt->execute();

$tasks = $stmt->fetchAll(PDO::FETCH_COLUMN); // Retourne un tableau de lignes