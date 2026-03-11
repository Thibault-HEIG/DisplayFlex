const output = document.getElementById('serverOutput');
const undoButton = document.getElementById("undo-button");

// Ce script fait le pont entre HTML et Java sans recharger la page
async function insertStudent() {
    console.log("sent");
    const prenom = document.getElementById('prenom').value;
    const nom = document.getElementById('nom').value;
    const classe = document.getElementById('classe').value;
    const email = document.getElementById('email').value;
    const dateNaissance = document.getElementById('date-naissance').value;

    output.innerText = "Traitement en cours...";
    // const formData = { prenom: prenom, nom: nom, classe: classe, dateNaissance: dateNaissance};
    const formData = prenom + "/" + nom + "/" + classe + "/" + email + "/" + dateNaissance;

    try {
        const response = await fetch('/api/student/insert', {
            method: 'POST',
            body: formData,
        });
        var text = await response.text();
        if (text.startsWith("SUCCESS")) {
            let outputArray = text.split("/");
            document.getElementById("undo-button").dataset.studentId = outputArray[1];
            text = outputArray[2];
            showButton();
        }
        output.innerText = text;
    } catch (error) {
        output.innerText = "Erreur de connexion au serveur.";
    }
}

async function undoInsertStudent() {
    const extractedId = undoButton.dataset.studentId;

    try {
        const response = await fetch('/api/student/undo', {
            method: 'POST',
            body: "undo/" + extractedId,
        });
        let text = await response.text();
        output.innerText = text;
    } catch (error) {
        output.innerText = "L'annulation n'a pas pu être effectuée.";
    }
}

function showButton() {
    undoButton.classList.remove('hidden');
}