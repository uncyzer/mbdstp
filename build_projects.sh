#!/bin/bash

# Répertoire contenant les microservices
BASE_DIR=$(pwd) # Par défaut, le répertoire courant
BUILD_COMMAND="./gradlew clean build -x test" # Commande de build (modifiable selon vos besoins)

echo "==================================="
echo "Build des microservices"
echo "Dossier de base: $BASE_DIR"
echo "Commande de build: $BUILD_COMMAND"
echo "==================================="

# Vérification que le répertoire de base existe
if [ ! -d "$BASE_DIR" ]; then
  echo "Erreur : Le répertoire $BASE_DIR n'existe pas."
  exit 1
fi

# Parcourir les sous-dossiers
for dir in "$BASE_DIR"/*/; do

 # Ignorer les dossiers .idea et .git
  if [[ "$folder_name" == ".idea" || "$folder_name" == ".git" || "$folder_name" == "config-repo" ]]; then
    echo "Ignoré : $folder_name"
    continue
  fi

  if [ -d "$dir" ]; then
    echo "-----------------------------------"
    echo "Entrée dans le dossier: $dir"
    cd "$dir" || continue

    # Exécution de la commande de build
    echo "Lancement du build..."
    if $BUILD_COMMAND; then
      echo "Build réussi pour: $dir"
    else
      echo "Erreur lors du build pour: $dir"
    fi

    # Retour au répertoire de base
    cd "$BASE_DIR" || exit
  fi
done

echo "==================================="
echo "Build terminé pour tous les services."
echo "==================================="
