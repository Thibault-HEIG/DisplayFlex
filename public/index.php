<?php require_once 'php/script-home.php'; ?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projet Collaboratif - DisplayFlex</title>
    <link rel="stylesheet" href="css/index.css">
</head>

<body>
    <div class="page">
        <nav>
            <div class="nav-menu">
                <a href="websites.html">Projets Web</a>
                <a href="sql.html">DataBase</a>
                <a href="profile-guesser.php">Orientation</a>
                <a href="mini-games.html">Mini-Jeux</a>
            </div>
            <fieldset>
                <legend>Thème</legend>
                <div>
                    <input type="radio" id="light" name="drone" value="light" checked />
                    <label for="dewey">Light</label>
                </div>
                <div>
                    <input type="radio" id="dark" name="drone" value="dark" />
                    <label for="huey">Dark</label>
                </div>
            </fieldset>
        </nav>
        <section id="hero">
            <h1>Projet Collaboratif M54/2</h1>
            <h2>Mettre en pratique les compétences HTML, CSS, Java, SQL, PHP et Git dans un seul et même environnement</h2>
            <p>Bienvenue sur la page d'accueil du projet ! Le site est en construction continue, n'hésite pas à faire
                part
                de tes idées pour les implémenter seul ou en équipe.<br>Bonne visite !</p>
        </section>
        <section id="tasks">
            <h2>Listes des idées</h2>
            <ul>
                <?php
                // On boucle sur le tableau défini dans script-home.php
                foreach ($tasks as $task): ?>
                    <li><?php echo $task; ?></li>
                <?php endforeach; ?>
            </ul>
        </section>
        <footer>
            <img src="img/statue.webp">
        </footer>
    </div>
</body>

</html>