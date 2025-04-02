#!/bin/bash
set -e

echo "Creating databases if they don't exist..."

psql -U "$POSTGRES_USER" -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'mydatabase'" | grep -q 1 || psql -U "myuser" -d postgres -c "CREATE DATABASE mydatabase"
psql -U "$POSTGRES_USER" -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'keycloak'"  | grep -q 1 || psql -U "myuser" -d postgres -c "CREATE DATABASE keycloak"

echo "Databases are ready."
