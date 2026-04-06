const output = document.getElementById('server-output');

function showInputValues() { // Synchronise le chiffre avec le curseur
  // For each .skill container, find the range input and the .skill-value span
  const skills = document.querySelectorAll('#input-group .skill');

  skills.forEach(skill => {
    const input = skill.querySelector('input[type="range"]');
    const valueSpan = skill.querySelector('.skill-value');

    // initialize displayed value
    valueSpan.textContent = input.value;

    // update on input
    input.addEventListener('input', (event) => {
      valueSpan.textContent = event.target.value;
    });
  });
}

const JOB_DESCRIPTIONS = {
  "UX-UI Designer":
    "Conçoit des expériences et interfaces centrées utilisateur, mène la recherche UX, crée wireframes, prototypes et maquettes UI, puis teste et itère les parcours pour améliorer l’ergonomie sur web et mobile.",

  "Web Designer":
    "Crée l’identité visuelle et la mise en page de sites web, définit la charte graphique digitale, conçoit maquettes responsives et éléments graphiques en veillant à la cohérence de marque et à l’accessibilité.",

  "Web Developer":
    "Développe et maintient des sites et applications web, écrit du code propre et testable (front et/ou back), intègre maquettes, optimise les performances et corrige les bugs en collaboration avec les designers et chefs de projet.",

  "Data Analyst":
    "Collecte, nettoie et analyse des données pour dégager des insights, construit tableaux de bord et rapports, formule des recommandations métier basées sur les indicateurs clés pour aider à la décision.",

  "Digital Marketing Manager":
    "Pilote les stratégies marketing digitales (SEO/SEA, e-mail, réseaux sociaux, display), planifie et exécute les campagnes, suit les KPIs, optimise les budgets médias et coordonne agences et équipes internes.",

  "Motion Designer":
    "Crée des animations 2D/3D et visuels en mouvement pour le web, la publicité ou le produit, participe à la conception créative, prépare storyboards et moodboards, puis anime typographie, illustration et vidéo.",

  "Graphic Designer":
    "Conçoit des identités visuelles et supports print/digitaux (logos, affiches, bannières, interfaces), met en forme messages et concepts de marque en images claires et impactantes, en respectant charte et contraintes techniques.",

  "Content Strategist":
    "Définit la stratégie éditoriale multicanale, planifie et coordonne la production de contenus, structure les messages selon les objectifs business et SEO, mesure la performance et ajuste les formats et sujets.",

  "Product Manager":
    "Porter la vision du produit, analyser besoins utilisateurs et marché, prioriser la roadmap, rédiger user stories et coordonner équipes design, tech et business pour livrer des fonctionnalités à forte valeur.",

  "Creative Director":
    "Définit la vision créative globale d’une marque ou d’un projet, supervise les concepts visuels et narratifs, encadre les équipes créatives, valide les pistes et garantit la cohérence esthétique et stratégique.",

  "SEO Specialist":
    "Optimise la visibilité d’un site sur les moteurs de recherche via audit technique, recherche de mots-clés, optimisation on-page, netlinking et analyse de performances pour augmenter trafic organique qualifié.",

  "Full-Stack Developer":
    "Développe le front-end et le back-end d’applications web, conçoit APIs et modèles de données, intègre interfaces, gère la logique serveur et veille à la performance, la sécurité et la maintenabilité de l’ensemble.",

  "Communication Specialist":
    "Conçoit et met en œuvre des stratégies de communication 360° (médias, réseaux sociaux, événements, RP), rédige contenus et messages clés, gère la relation avec les parties prenantes et suit la réputation de la marque."
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
  console.log("data sent");
  // Show skeleton immediately
  output.innerHTML = `
    <div class="skeleton-loader">
      ${Array(3).fill(`
        <div class="skeleton-item">
          <div class="skeleton-rank"></div>
          <div class="skeleton-text">
            <div class="skeleton-title"></div>
            <div class="skeleton-desc"></div>
          </div>
          <div class="skeleton-score"></div>
        </div>
      `).join('')}
    </div>
  `;

  output.scrollIntoView({ behavior: 'smooth', block: 'start' }); // Scroll a bit

  const delay = new Promise(resolve => setTimeout(resolve, 2000));

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
  const leadershipScore = document.getElementById('s10').value; // Leadership
  const oralCommunicationScore = document.getElementById('s11').value; // Oral Communication
  const creativityScore = document.getElementById('s12').value; // Creativity
  const analyticalThinkingScore = document.getElementById('s13').value; // Analytical Thinking
  const projectManagementScore = document.getElementById('s14').value; // Project Management
  const storytellingScore = document.getElementById('s15').value; // Storytelling



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
    economyScore,
    leadershipScore,
    oralCommunicationScore,
    creativityScore,
    analyticalThinkingScore,
    projectManagementScore,
    storytellingScore
  ].join('/');

  console.log(valuesData);

  try {
    // fetch + delay run in parallel
    const [response] = await Promise.all([
      fetch('/api/algorithm/job', { method: 'POST', body: valuesData }),
      delay
    ]);
    const text = await response.text();
    if (text.startsWith("SUCCESS")) parseAndDisplay(text);
  } catch (error) {
    output.innerText = "Thibault a fait de la merde.";
  }
}

showInputValues();