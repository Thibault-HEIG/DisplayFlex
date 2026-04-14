<?php

function getConnection(): PDO {
    $host     = getenv('DB_HOST');
    $dbname   = getenv('DB_NAME');
    $user     = getenv('DB_USER');
    $password = getenv('DB_PASSWORD');
    $port     = getenv('DB_PORT');

    // DSN = Data Source Name : indique à PDO le driver + où se connecter
    $dsn = "pgsql:host=$host;port=$port;dbname=$dbname";

    try {
        $pdo = new PDO($dsn, $user, $password, [
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION, // Active les exceptions SQL
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,       // Résultats en tableau associatif
        ]);
        return $pdo;
    } catch (PDOException $e) {
        // En prod : logger l'erreur, ne JAMAIS afficher le message brut
        http_response_code(500);
        die(json_encode(['error' => 'Connexion échouée']));
    }
}