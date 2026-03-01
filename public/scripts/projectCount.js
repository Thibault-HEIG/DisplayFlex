// Function

function showProjectCount() {
    let spanElement = document.getElementById("project-count"); // Va cherche l'élément avec l'id #project-count

    var projectCount = document.getElementsByClassName("project").length; // Compte le nombre de projets sur la page

    spanElement.textContent = projectCount; // Affiche ce nombre
}

// Action

showProjectCount();