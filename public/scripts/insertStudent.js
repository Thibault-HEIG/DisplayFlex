// Ce script fait le pont entre HTML et Java sans recharger la page
async function sendToJava() {
    console.log("sent");
    const prenom = document.getElementById('prenom').value;
    const nom = document.getElementById('nom').value;
    const classe = document.getElementById('classe').value;
    const dateNaissance = document.getElementById('date-naissance').value;
    const output = document.getElementById('serverOutput');

    output.innerText = "Traitement en cours...";
    // const formData = { prenom: prenom, nom: nom, classe: classe, dateNaissance: dateNaissance};
    const formData = prenom + "/" + nom + "/" + classe + "/" + dateNaissance;

    try {
        const response = await fetch('/api/student', {
            method: 'POST',
            body: formData,
        });
        const text = await response.text();
        output.innerText = text;
    } catch (error) {
        output.innerText = "Erreur de connexion au serveur.";
    }
}