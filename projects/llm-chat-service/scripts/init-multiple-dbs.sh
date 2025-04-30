#!/bin/bash
# Script to initialize PostgreSQL databases
# Exits immediately if a command exits with a non-zero status
set -e

# Define databases to create
DATABASES=("mydatabase" "keycloak")

echo "Starting database initialization process..."

# Loop through each database
for DB in "${DATABASES[@]}"; do
  echo "Checking if $DB exists..."
  
  # Check if database exists, create if it doesn't
  if ! psql -U "$PG_USER" -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname = '$DB'" | grep -q 1; then
    echo "Creating database: $DB"
    psql -U "$PG_USER" -d postgres -c "CREATE DATABASE $DB"
  else
    echo "Database $DB already exists"
  fi
done

echo "Database initialization complete. All required databases are ready."