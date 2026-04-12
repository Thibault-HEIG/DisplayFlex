# Aide-Mémoire Docker : Fonctionnement et Cycle de Vie

Ce document synthétise la mécanique interne de Docker et les commandes essentielles pour manipuler une infrastructure conteneurisée.

## 1. Les Concepts Fondamentaux

Il est crucial de séparer l'état statique de l'état actif. Par analogie avec la programmation orientée objet :

* **Image (La Classe) :** Un modèle statique, immuable et en lecture seule. Elle contient tout le nécessaire (OS, environnement d'exécution, code compilé) pour faire tourner une application, mais n'exécute rien par elle-même.
* **Conteneur (L'Objet) :** L'environnement actif, isolé et en cours d'exécution. Il est instancié à partir d'une image. Vous pouvez faire tourner simultanément plusieurs conteneurs identiques basés sur la même image.

---

## 2. L'Architecture des Fichiers

Ces trois fichiers dictent la construction et l'orchestration de votre environnement.

* **`Dockerfile` :** La recette de construction. Il indique à Docker comment assembler l'image étape par étape (ex: partir d'un OS Java, copier le code source, le compiler, et définir la commande de lancement).
* **`docker-compose.yml` :** Le plan d'infrastructure. Il orchestre plusieurs conteneurs (services) pour qu'ils fonctionnent ensemble (ex: relier un conteneur "App Java" à un conteneur "Base de données Postgres"), gère les ports réseau et définit la persistance des données (volumes).

---

## 3. Commandes Principales (Cycle de Vie)

Ces commandes s'exécutent depuis le dossier contenant votre fichier `docker-compose.yml`.

### Démarrage (Boot)
* `docker compose up` : Construit (si nécessaire) et démarre tous les conteneurs. Les logs s'affichent en direct. Si vous fermez le terminal, les conteneurs s'arrêtent.
* `docker compose up -d` : Démarre les conteneurs en **mode détaché** (arrière-plan). Vous récupérez le contrôle du terminal.
* `docker compose up --build` : Force Docker à ignorer son cache, à recompiler le `Dockerfile` de zéro, puis à démarrer les conteneurs. Indispensable après une modification du code source ou du Dockerfile.

### Arrêt et Destruction (Teardown)
* `docker compose down` : Arrête proprement et détruit les conteneurs et réseaux virtuels. **Les volumes de données sont conservés** (votre base de données reste intacte pour le prochain redémarrage).
* `docker compose down -v` : **Destruction totale** (Hard reset). Détruit les conteneurs ET les volumes de persistance. Toutes les données en base sont définitivement effacées.

### Supervision et Interaction
* `docker compose logs -f` : S'attache au flux des logs en temps réel. Essentiel pour déboguer des conteneurs lancés en arrière-plan avec `-d`.