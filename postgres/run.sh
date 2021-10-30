#!/bin/sh

docker run \
    -d \
    -p 5432:5432 \
    -v $(pwd)/data:/var/lib/postgresql/data \
    -e POSTGRES_PASSWORD=pp \
    postgres-t