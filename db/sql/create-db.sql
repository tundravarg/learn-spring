DROP DATABASE IF EXISTS test;

CREATE DATABASE test;

\c test

BEGIN;


CREATE SEQUENCE test_id_seq;

CREATE TABLE test (
    id int
        PRIMARY KEY
        DEFAULT nextval('test_id_seq'),
    value text
);


END;
