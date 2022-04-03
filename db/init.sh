#!/bin/sh

function get_pg_password {
    printf "PG password: "
    read -s pgp
    echo
    export PGPASSWORD=$pgp
}

function exec_sql {
    psql -h localhost -p 5432 -U postgres -v ON_ERROR_STOP=1 -f $1 || return 1
}

function exec_sql_in_db {
    psql -h localhost -p 5432 -U postgres -d test -v ON_ERROR_STOP=1 -f $1 || return 1
}

get_pg_password &&
exec_sql sql/create-db.sql &&
exec_sql_in_db sql/locations-nsm.sql &&
exec_sql_in_db sql/fill-db.sql &&
echo 'DONE' || echo 'FAIL'
