# 🤝 Workflow Git
**Comment travailler à 10 sans s'entretuer**  
Règle d'or : On travaille *JAMAIS* directement sur la branche main.


## Étape 0 : Se mettre à jour
Avant de recommencer une nouvelle tâche, mettez à jour votre local (pour avoir le bon code) :

`git checkout main`  
`git pull origin main`

-----

## Étape 1 : Créer sa branche
Utilisez ces conventions de nommage pour qu'on s'y retrouve :

modification du CSS : `git checkout -b [style/branche]`

modification du HTML : `git checkout -b [index/branche]`

modification du Java : `git checkout -b [feature/branche]`

-----

## Étape 2 : Travailler et Sauvegarder
Faites vos modifications. Testez que ça marche. Puis :

`git add .`  
`git commit -m "Verbe d'action + description courte"`

-----

## Étape 3 : Partager (Push)
Envoyez votre branche aux autres sur GitHub :

`git push origin nom-de-votre-branche`

-----

## Étape 4 : Fusionner (Pull Request)
Allez sur GitHub. Créez une Pull Request (PR) de votre branche vers main.

Important : Demandez à un camarade de relire votre code et de valider la PR avant de cliquer sur "Merge".