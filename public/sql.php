<?php

require_once "scripts/db-connection.php";

$submitted = false;
$serverResponse = '';
$userId = null;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $action = $_POST['action_type'] ?? 'insert';

    if ($action === 'undo') {
        $userId = $_POST['user_id'] ?? null;
        if (!empty($userId)) {
            if (undoInsertUser($userId)) {
                $userId = null;
                $submitted = false;
                $serverResponse = "Opération annulée avec succès.";
            } else {
                $serverResponse = "Erreur lors de l'annulation.";
                $submitted = true;
            }
        }
    } else {
        $userId = handleInsert();
    }
}

function handleInsert() {
    global $submitted, $serverResponse;
    $prenom = $_POST["prenom"];
    $nom = $_POST["nom"];
    $estEleve = filter_input(INPUT_POST, "est-eleve", FILTER_VALIDATE_BOOL) ?? false;
    $email = $_POST["email"];
    $dateNaissance = $_POST["date-naissance"];

    $validation = validateUser($prenom, $nom, $email, $dateNaissance);
    if ($validation !== "ok") {
        $serverResponse = $validation;
        $submitted = false;
        return null;
    }

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

function validateUser($prenom, $nom, $email, $dateNaissance) {
    if (!empty($email) && !str_ends_with($email, "@heig-vd.ch")) {
        return "Merci d'utiliser un email professionnel (@heig-vd.ch)";
    }

    if (!empty($dateNaissance)) {
        $year = (int)date('Y', strtotime($dateNaissance));
        if ($year > 2010) {
            return "Erreur : il semble que $year soit trop récent pour une date de naissance...";
        }
    }

    try {
        $pdo = getConnection();
        $query = "SELECT id FROM utilisateurs WHERE prenom = ? AND nom = ?";
        $pstmt = $pdo->prepare($query);
        $pstmt->execute([$prenom, $nom]);
        if ($pstmt->fetch()) {
            return "L'élève figure déjà dans la base de données. L'opération a été annulée.";
        }
    } catch (PDOException $e) {
        return "Erreur lors de la vérification des doublons : " . $e->getMessage();
    }

    return "ok";
}

function undoInsertUser($idUser)
{
    try {
        $pdo = getConnection();
        $query = "DELETE FROM utilisateurs WHERE id = ? ;";
        $pstmt = $pdo->prepare($query);
        $pstmt->execute([$idUser]);
        return true;
    } catch (PDOException $e) {
        return false;
    }
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
                <input type="hidden" name="user_id" value="<?php echo htmlspecialchars($userId ?? ''); ?>">
                <div class="field">
                    <label for="prenom">Prénom</label>
                    <input type="text" name="prenom" id="prenom" placeholder="Thibault" value="<?php echo htmlspecialchars($_POST['prenom'] ?? ''); ?>" required>
                </div>
                <div class="field">
                    <label for="nom">Nom</label>
                    <input type="text" name="nom" id="nom" placeholder="Moret" value="<?php echo htmlspecialchars($_POST['nom'] ?? ''); ?>" required>
                </div>
                <div class="field">
                    <label for="est-eleve">Élève</label>
                    <input type="checkbox" name="est-eleve" id="est-eleve" <?php echo isset($_POST['est-eleve']) ? 'checked' : ''; ?>>
                </div>
                <div class="field">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" placeholder="prenom.nom@heig-vd.ch" value="<?php echo htmlspecialchars($_POST['email'] ?? ''); ?>">
                </div>
                <div class="field">
                    <label for="date-naissance">Date de naissance</label>
                    <input type="date" name="date-naissance" id="date-naissance" value="<?php echo htmlspecialchars($_POST['date-naissance'] ?? ''); ?>">
                </div>
                <button type="submit" name="action_type" value="insert">Valider</button>
                <button type="submit" name="action_type" value="undo" id="undo-button" class="<?php if (!$submitted) {
                                                                                                    echo 'hidden';
                                                                                                } ?>">Annuler</button>
            </form>

            <div id="responseArea">
                <span class="label">Réponse du serveur :</span>
                <p id="serverOutput"><?php if ($submitted == false && empty($serverResponse)) {
                                            echo '...';
                                        } else {
                                            echo $serverResponse;
                                        } ?></p>
            </div>
        </div>
    </section>
</body>

</html>