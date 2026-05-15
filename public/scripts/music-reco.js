/**
 * SCRIPT : music-reco.js
 * ROLE : Gestion dynamique des lignes de musique et autocomplétion serveur
 */

document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('songs-container');
    const addButton = document.getElementById('add-row');
    const timers = new Map(); // Stocke les "délais" (debouncers) par champ input

    /**
     * FONCTION : fetchSuggestions
     * Appelle le serveur (PHP) pour obtenir les suggestions SQLite
     */
    const fetchSuggestions = async (query, type, context = '') => {
        if (query.length < 2) return [];
        
        try {
            // On envoie la requête à auto-complete.php avec les paramètres q (texte), type (artiste/morceau) et context (filtre optionnel)
            let url = `scripts/auto-complete.php?q=${encodeURIComponent(query)}&type=${type}`;
            if (context) url += `&context=${encodeURIComponent(context)}`;
            
            const response = await fetch(url);
            if (!response.ok) throw new Error('Erreur réseau');
            return await response.json();
        } catch (err) {
            console.error("Erreur d'autocomplétion:", err);
            return [];
        }
    };

    /**
     * FONCTION : displaySuggestions
     * Génère visuellement la liste déroulante sous l'input
     */
    const displaySuggestions = (input, matches) => {
        closeAllLists(); // On nettoie les listes existantes
        
        if (matches.length === 0) return;

        // Création de la structure <ul>
        const list = document.createElement('ul');
        list.className = 'autocomplete-list';

        matches.forEach(item => {
            const li = document.createElement('li');
            li.className = 'autocomplete-item';
            li.textContent = `${item.track_name} — ${item.artists}`;
            
            // Évènement clic sur une suggestion
            li.addEventListener('click', () => {
                const row = input.closest('.song-row');
                const trackInput = row.querySelector('input[name="track_names[]"]');
                const artistInput = row.querySelector('input[name="artists[]"]');
                const idInput = row.querySelector('input[name="track_ids[]"]');
                
                // On remplit les champs visibles
                trackInput.value = item.track_name;
                artistInput.value = item.artists;

                // On stocke l'ID technique uniquement dans le champ caché pour le POST
                idInput.value = item.track_id;

                // UX : Marquer les champs comme valides (fond vert)
                trackInput.classList.add('is-valid');
                artistInput.classList.add('is-valid');
                
                closeAllLists();
            });
            list.appendChild(li);
        });

        // On attache la liste au parent du champ actuel (div.field)
        input.parentNode.appendChild(list);
    };

    /**
     * GESTIONNAIRES D'ÉVÈNEMENTS (DOM)
     */

    // 1. Fermer l'autocomplétion (nettoyage)
    const closeAllLists = () => {
        document.querySelectorAll('.autocomplete-list').forEach(list => list.remove());
    };

    // 2. Ajouter une nouvelle ligne
    const addNewRow = () => {
        const firstRow = container.querySelector('.song-row');
        const newRow = firstRow.cloneNode(true);
        
        // Réinitialisation complète de tous les inputs de la nouvelle ligne
        newRow.querySelectorAll('input').forEach(input => {
            input.value = '';
            input.classList.remove('is-valid');
        });
        
        container.appendChild(newRow);
        updateRemoveButtons();
    };

    // 3. Délégation d'évènement pour l'input (Autocomplete + Debounce)
    container.addEventListener('input', (e) => {
        const input = e.target;
        
        // On ne cible que les inputs de musique
        if (input.tagName === 'INPUT' && (input.name === 'artists[]' || input.name === 'track_names[]')) {
            
            // Si l'utilisateur modifie manuellement le texte, l'ID technique n'est plus valide
            const row = input.closest('.song-row');
            const idInput = row.querySelector('input[name="track_ids[]"]');
            idInput.value = '';

            // UX : Retirer la classe de validité sur toute la ligne si modification
            row.querySelectorAll('input[name="track_names[]"], input[name="artists[]"]').forEach(i => i.classList.remove('is-valid'));

            // Récupérer la valeur de l'autre champ pour le filtrage contextuel
            const otherInputName = (input.name === 'artists[]') ? 'track_names[]' : 'artists[]';
            const otherInput = row.querySelector(`input[name="${otherInputName}"]`);
            const contextValue = otherInput.value.trim();

            /**
             * LOGIQUE DE DEBOUNCE :
             * On attend 500ms après la dernière touche tapée avant d'appeler le serveur.
             * Cela évite de saturer le serveur de requêtes si l'utilisateur tape vite.
             */
            if (timers.has(input)) clearTimeout(timers.get(input));
            
            const timer = setTimeout(async () => {
                const matches = await fetchSuggestions(input.value, input.name, contextValue);
                displaySuggestions(input, matches);
            }, 500);
            
            timers.set(input, timer);
        }
    });

    // 4. Gestion des boutons d'ajout/suppression
    if (addButton) addButton.addEventListener('click', addNewRow);

    container.addEventListener('click', (e) => {
        if (e.target.classList.contains('remove-row')) {
            const rows = container.querySelectorAll('.song-row');
            if (rows.length > 1) {
                e.target.closest('.song-row').remove();
                updateRemoveButtons();
            }
        }
    });

    const updateRemoveButtons = () => {
        const rows = container.querySelectorAll('.song-row');
        rows.forEach(row => {
            const btn = row.querySelector('.remove-row');
            if (btn) btn.style.visibility = rows.length > 1 ? 'visible' : 'hidden';
        });
    };

    // 5. Fermer si clic à l'extérieur ou Echap
    document.addEventListener('click', (e) => {
        if (!e.target.closest('.field')) closeAllLists();
    });

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeAllLists();
    });

    // Initialisation au démarrage
    updateRemoveButtons();
});
