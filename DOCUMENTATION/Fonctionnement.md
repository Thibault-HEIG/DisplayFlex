## 🧠 Comment ça marche ? (Vulgarisation)
Imaginez un **Restaurant** :

1.  **Le Client (Navigateur / HTML)** : C'est ce que vous voyez à l'écran. Il ne cuisine pas, il passe juste la commande.
    * *Exemple : Vous tapez "Bonjour" et cliquez sur "Envoyer".*
2.  **Le Serveur (Java / AppServer)** : C'est la cuisine. Il reçoit la commande, fait le travail (calculs, logique), et prépare le plat.
    * *Exemple : Java reçoit "Bonjour", le met en majuscules ("BONJOUR"), et le renvoie.*
3.  **Le Protocole (HTTP)** : C'est le serveur (garçon de café) qui fait les allers-retours entre la table et la cuisine.

**Flux technique :**
`HTML (Input)` --> `JavaScript (Fetch)` --> `Réseau (HTTP)` --> `Java (InputStream)` --> **VOTRE LOGIQUE** --> `Java (OutputStream)` --> `HTML (Affichage)`