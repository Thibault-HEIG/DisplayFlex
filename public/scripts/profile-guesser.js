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