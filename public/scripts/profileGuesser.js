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

function getDescription(jobs, name) {
  const job = jobs.find(j => j.name === name);
  return job.description;
}

function parseAndDisplay(data, jobs) {
  output.innerHTML = data.results.map((r) => `
    <div class="result-item rank-${r.rank}">
      <div>
        <div class="title">
          <span class="result-rank">#${r.rank}</span>
          <h3 class="result-name">${r.name}</h3>
        </div>
        <p class="result-desc">${getDescription(jobs, r.name) || '...'}</p>
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
      ${Array(10).fill(`
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

  const jsonResponse = await fetch('/data/job-profiles.json');
  const jobJsonFile = await jsonResponse.json();
  const jobs = jobJsonFile.jobs;

  output.scrollIntoView({ behavior: 'smooth', block: 'start' }); // Scroll a bit

  const delay = new Promise(resolve => setTimeout(resolve, 1000));

  const payload = { // Créé le fichier JSON
    "skills": [
      {
        "skill": "Marketing",
        "value": parseInt(document.getElementById('s0').value)
      },
      {
        "skill": "Graphic Design",
        "value": parseInt(document.getElementById('s1').value)
      },
      {
        "skill": "Coding",
        "value": parseInt(document.getElementById('s2').value)
      },
      {
        "skill": "Writing",
        "value": parseInt(document.getElementById('s3').value)
      },
      {
        "skill": "Interface",
        "value": parseInt(document.getElementById('s4').value)
      },
      {
        "skill": "Data",
        "value": parseInt(document.getElementById('s5').value)
      },
      {
        "skill": "Media",
        "value": parseInt(document.getElementById('s6').value)
      },
      {
        "skill": "Maths",
        "value": parseInt(document.getElementById('s7').value)
      },
      {
        "skill": "English",
        "value": parseInt(document.getElementById('s8').value)
      },
      {
        "skill": "Economy",
        "value": parseInt(document.getElementById('s9').value)
      },
      {
        "skill": "Leadership",
        "value": parseInt(document.getElementById('s10').value)
      },
      {
        "skill": "Oral Communication",
        "value": parseInt(document.getElementById('s11').value)
      },
      {
        "skill": "Creativity",
        "value": parseInt(document.getElementById('s12').value)
      },
      {
        "skill": "Analytical Thinking",
        "value": parseInt(document.getElementById('s13').value)
      },
      {
        "skill": "Project Management",
        "value": parseInt(document.getElementById('s14').value)
      },
      {
        "skill": "Storytelling",
        "value": parseInt(document.getElementById('s15').value)
      }
    ],
    "student": null
  };

  console.log(payload);

  try {
    // fetch + delay run in parallel
    const [response] = await Promise.all([
      fetch('/api/algorithm/job', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      }),
      delay
    ]);
    const data = await response.json();
    if (data.status === "success") parseAndDisplay(data, jobs);
    else output.innerText = data.message;
  } catch (error) {
    output.innerText = error.message;
  }
}

showInputValues();