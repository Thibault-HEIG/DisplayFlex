const output = document.getElementById('server-output');

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

const JOB_DESCRIPTIONS = {
  "UX/UI Designer": "Conçoit des interfaces centrées sur l'utilisateur, entre wireframe et prototype.",
  "Web Designer": "Crée l'identité visuelle et la mise en page de sites web.",
  "Web Developer": "Développe et maintient des sites et applications web.",
  "Data Analyst": "Analyse des données pour aider à la prise de décision.",
  "Digital Marketing Manager": "Pilote les stratégies marketing sur les canaux digitaux.",
  "Motion Designer": "Crée des animations et visuels en mouvement.",
  "Graphic Designer": "Produit des visuels print et digitaux à forte identité.",
  "Content Strategist": "Planifie et produit du contenu aligné sur les objectifs de marque.",
  "Product Manager": "Coordonne le développement d'un produit entre équipes techniques et métier.",
  "Creative Director": "Définit la vision créative globale d'un projet ou d'une marque.",
  "SEO Specialist": "Optimise la visibilité d'un site dans les moteurs de recherche.",
  "Full-stack Developer": "Développe aussi bien le front-end que le back-end d'une application."
};

function parseAndDisplay(text) {
  const parts = text.split('/');
  parts.shift(); // remove "SUCCESS"

  const results = parts.map(part => {
    const [name, score] = part.split('_');
    return { name, score: parseInt(score) };
  });

  output.innerHTML = results.map((r, i) => `
    <div class="result-item rank-${i + 1}">
      <div>
        <div class="title">
          <span class="result-rank">#${i + 1}</span>
          <h3 class="result-name">${r.name}</h3>
        </div>
        <p class="result-desc">${JOB_DESCRIPTIONS[r.name] || '...'}</p>
      </div>
      <span class="result-score">${r.score}%</span>
    </div>
  `).join('');
}

async function runAlgorithm() {
  console.log("sent");
  output.innerText = "Calcul en cours...";
  // Read each range input by its id (s0..s9) to match the HTML order
  const marketingScore = document.getElementById('s0').value; // Marketing
  const graphicDesignScore = document.getElementById('s1').value; // Design
  const codingScore = document.getElementById('s2').value; // Code
  const writingScore = document.getElementById('s3').value; // Writing
  const interfaceScore = document.getElementById('s4').value; // Interface / Design Thinking
  const dataScore = document.getElementById('s5').value; // Data / SQL
  const mediaScore = document.getElementById('s6').value; // Media
  const mathsScore = document.getElementById('s7').value; // Maths
  const englishScore = document.getElementById('s8').value; // English
  const economyScore = document.getElementById('s9').value; // Economy

  const valuesData = [
    marketingScore,
    graphicDesignScore,
    codingScore,
    writingScore,
    interfaceScore,
    dataScore,
    mediaScore,
    mathsScore,
    englishScore,
    economyScore
  ].join('/');

  console.log(valuesData);

  try {
    const response = await fetch('/api/algorithm/job', {
      method: 'POST',
      body: valuesData,
    });
    var text = await response.text();
    console.log(text);
    if (text.startsWith("SUCCESS")) {
      parseAndDisplay(text);
    }
  } catch (error) {
    output.innerText = "Thibault a fait de la merde.";
  }
}

showInputValues();