// Ce script fait le pont entre HTML et Java sans recharger la page
async function sendToJava() {
    const input = document.getElementById('userInput').value;
    const output = document.getElementById('serverOutput');

    output.innerText = "Traitement en cours...";

    try {
        const response = await fetch('/api/process', {
            method: 'POST',
            body: input
        });
        const text = await response.text();
        output.innerText = text;
    } catch (error) {
        output.innerText = "Erreur de connexion au serveur.";
    }
}