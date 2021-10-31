DROP DATABASE IF EXISTS test;

CREATE DATABASE test;

\c test

BEGIN;


CREATE SEQUENCE locations_id_seq;

CREATE TABLE locations (
    id int
        PRIMARY KEY
        DEFAULT nextval('locations_id_seq'),
    name text,
    type text,
    parent_id int
        REFERENCES locations (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);


CREATE SEQUENCE assets_id_seq;

CREATE TABLE assets (
    id int
        PRIMARY KEY
        DEFAULT nextval('assets_id_seq'),
    name text,
    type text,
    comment text
);


CREATE TABLE asset_placement (
    location_id int
        REFERENCES locations (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    asset_id int
        REFERENCES assets (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    date_placed date,
    removed boolean DEFAULT false,
    date_removed date DEFAULT null,

    PRIMARY KEY (location_id, asset_id, date_placed)
);


END;
