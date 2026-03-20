const output = document.getElementById('serverOutput');

function showInputValues() { // Synchronise le chiffre avec le curseur
  const inputs = document.querySelectorAll('#input-group input[type="range"]');

  inputs.forEach((input) => {
    // the HTML places the value span right after the input
    const valueSpan = input.nextElementSibling;
    if (valueSpan && valueSpan.classList.contains('skill-value')) {
      // initialize displayed value
      valueSpan.textContent = input.value;
      // update on input
      input.addEventListener('input', (event) => {
        valueSpan.textContent = event.target.value;
      });
    }
  });
}

async function runAlgorithm() {
  console.log("sent");
  output.innerText = "Calcul en cours...";

  const marketingScore = document.querySelector('div#marketing input[type="range"]').value;
  const designScore = document.querySelector('div#design input[type="range"]').value;
  const codingScore = document.querySelector('div#coding input[type="range"]').value;
  const managementScore = document.querySelector('div#management input[type="range"]').value;


  const valuesData = marketingScore + "/" + designScore + "/" + codingScore + "/" + managementScore;

  console.log(valuesData);

  try {
    const response = await fetch('/api/algorithm/job', {
      method: 'POST',
      body: valuesData,
    });
    var text = await response.text();
    if (text.startsWith("SUCCESS")) {
      output.innerText = text;
    }
  } catch (error) {
    output.innerText = "Erreur de connexion au serveur.";
  }
}

showInputValues();