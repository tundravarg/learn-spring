#!/bin/sh

docker run \
    -d \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=pp \
    postgres-t