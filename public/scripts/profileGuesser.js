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

showInputValues();