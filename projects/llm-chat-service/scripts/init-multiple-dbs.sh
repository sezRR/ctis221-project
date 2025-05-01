#!/bin/bash
set -e

echo "Initializing databases..."

DB_USER="${POSTGRES_USER:-postgres}"

DATABASES=("llm_chat_wrapper_db" "keycloak")

for db in "${DATABASES[@]}"; do
  echo "Checking if database '$db' exists..."
  DB_EXISTS=$(psql -U "$DB_USER" -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname = '$db'")

  if [ "$DB_EXISTS" != "1" ]; then
    echo "Creating database '$db'..."
    psql -U "$DB_USER" -d postgres -c "CREATE DATABASE \"$db\";"
  else
    echo "Database '$db' already exists, skipping."
  fi
done

echo "Done initializing databases."