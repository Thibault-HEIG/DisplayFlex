# Utilisation d'une image PHP avec Apache pré-installé
FROM php:8.2-apache

# Installation des dépendances système nécessaires pour PostgreSQL et Python
# On combine pour optimiser les couches du cache Docker
RUN apt-get update && apt-get install -y \
    libpq-dev \
    python3 \
    python3-pip \
    && rm -rf /var/lib/apt/lists/*

# Installation et activation de l'extension pdo_pgsql (le "pont" PHP -> Postgres)
RUN docker-php-ext-install pdo_pgsql

# Copie et installation des dépendances Python
# Note : sqlite3 est inclus dans la bibliothèque standard de Python
COPY src/api/music-recommendation-algorithm/requirements.txt /tmp/requirements.txt
RUN pip3 install --no-cache-dir --break-system-packages -r /tmp/requirements.txt

# On indique à Apache que la racine du site se trouve dans ton dossier 'public'
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
