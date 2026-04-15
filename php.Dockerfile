# Utilisation d'une image PHP avec Apache pré-installé
FROM php:8.2-apache

# Installation des dépendances système nécessaires pour PostgreSQL
RUN apt-get update && apt-get install -y libpq-dev

# Installation et activation de l'extension pdo_pgsql (le "pont" PHP -> Postgres)
RUN docker-php-ext-install pdo_pgsql

# On indique à Apache que la racine du site se trouve dans ton dossier 'public'
# (Optionnel selon ton organisation, mais souvent utilisé pour la propreté)
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf