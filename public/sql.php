<?php

require_once "scripts/db-connection.php";

$submitted = false;
$serverResponse = '';
$userId = null;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['action_type']) && $_POST['action_type'] === 'undo' && !empty($userId)) {
        undoInsertStudent($userId);
        $userId = null;
        $submitted = false;
        $serverResponse = "Étudiant supprimé avec succès.";
    } else {
        $userId = insertStudent();
    }
}

function insertStudent() {
    global $submitted, $serverResponse;
    $prenom = $_POST["prenom"];
    $nom = $_POST["nom"];
    $estEleve = filter_input(INPUT_POST, "est-eleve", FILTER_VALIDATE_BOOL) ?? false;
    $email = $_POST["email"];
    $dateNaissance = $_POST["date-naissance"];

    try {
        $pdo = getConnection();
        $query = "INSERT INTO utilisateurs (prenom, nom, est_eleve, email, date_naissance) VALUES (?, ?, ?, ?, ?)";
        $pstmt = $pdo->prepare($query);
        $pstmt->execute([$prenom, $nom, $estEleve ? 1 : 0, $email, $dateNaissance]);

        $submitted = true;
        $serverResponse = "Étudiant ajouté avec succès.";
        return $pdo->lastInsertId();
    } catch (PDOException $e) {
        $serverResponse = "Erreur : " . $e->getMessage();
        return null;
    }
}

function undoInsertStudent($idStudent)
{

    $pdo = getConnection();

    $query = "DELETE FROM utilisateurs WHERE id = ? ;";
    $pstmt = $pdo->prepare($query);
    $pstmt->execute([$idStudent]);

    return true;
}

?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SQL DataBase</title>
    <link rel="stylesheet" href="css/sql.css">
</head>

<body>
    <nav>
        <a href="index.php">← Retour à l'accueil</a>
    </nav>
    <section id="hero">
        <h1>Zone SQL</h1>
        <h2>Pour les passionnés de la data</h2>
    </section>
    <section id="add-student">
        <div class="container">
            <h3>Ajoutez vos données d'élèves.</h3>
            <p>Ces informations seront directement ajoutées à la base de données.</p>

            <form id="input-group" action="sql.php" method="post">
                <div class="field">
                    <label for="prenom">Prénom</label>
                    <input type="text" name="prenom" id="prenom" placeholder="Thibault" required>
                </div>
                <div class="field">
                    <label for="nom">Nom</label>
                    <input type="text" name="nom" id="nom" placeholder="Moret" required>
                </div>
                <div class="field">
                    <label for="classe">Élève</label>
                    <input type="checkbox" name="est-eleve" id="est-eleve">
                </div>
                <div class="field">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" placeholder="prenom.nom@heig-vd.ch">
                </div>
                <div class="field">
                    <label for="date-naissance">Date de naissance</label>
                    <input type="date" name="date-naissance" id="date-naissance">
                </div>
                <button type="submit" name="action_type" value="insert">Valider</button>
                <button type="submit" name="action_type" value="undo" id="undo-button" class="<?php if (!$submitted) {
                                                                                                    echo 'hidden';
                                                                                                } ?>">Annuler</button>
            </form>

            <div id="responseArea">
                <span class="label">Réponse du serveur :</span>
                <p id="serverOutput"><?php if ($submitted == false) {
                                            echo '...';
                                        } else {
                                            echo $serverResponse;
                                        } ?></p>
            </div>
        </div>
    </section>
</body>

</html>