-- ALTER TABLE locations
-- ADD COLUMN nsm_l int;
-- ALTER TABLE locations
-- ADD COLUMN nsm_r int;

CREATE OR REPLACE FUNCTION build_location_node_nsm (id int, nsm_l int)
RETURNS int
LANGUAGE plpgsql
AS $$
    #variable_conflict use_variable
DECLARE
    nsm_r int;
    child record;
BEGIN

    nsm_r = nsm_l;

    FOR child IN
        SELECT c.id FROM locations AS c
        WHERE c.parent_id = id
        ORDER BY c.id
    LOOP
        nsm_r := build_location_node_nsm(child.id, nsm_r + 1);
    END LOOP;

    nsm_r = nsm_r + 1;

    UPDATE locations AS l
    SET nsm_l = nsm_l, nsm_r = nsm_r
    WHERE l.id = id;

    RETURN nsm_r;

END; $$;

CREATE OR REPLACE FUNCTION build_locations_nsm ()
RETURNS int
LANGUAGE plpgsql
AS $$
DECLARE
    nsm_l int;
    root record;
BEGIN

    nsm_l = 1;

    FOR root IN
        SELECT r.id FROM locations AS r
        WHERE r.parent_id IS NULL
        ORDER BY r.id
    LOOP
        nsm_l := build_location_node_nsm(root.id, nsm_l);
        nsm_l := nsm_l + 1;
    END LOOP;

    RETURN NULL;

END; $$;


SELECT build_locations_nsm();



CREATE OR REPLACE FUNCTION locations_nsm_trigger_func ()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF (TG_OP = 'UPDATE')
    THEN
        IF (OLD.id != NEW.id OR OLD.parent_id != NEW.parent_id)
        THEN
            PERFORM build_locations_nsm();
        END IF;
    ELSE
        PERFORM build_locations_nsm();
    END IF;
    RETURN NULL;
END; $$;

DROP TRIGGER IF EXISTS locations_nsm_trigger_id ON locations;
DROP TRIGGER IF EXISTS locations_nsm_trigger_u ON locations;

CREATE TRIGGER locations_nsm_trigger_id
AFTER INSERT OR DELETE
ON locations
EXECUTE PROCEDURE locations_nsm_trigger_func();

CREATE TRIGGER locations_nsm_trigger_u
AFTER UPDATE
ON locations
FOR EACH ROW EXECUTE PROCEDURE locations_nsm_trigger_func();


-- SELECT * FROM locations
-- ORDER BY nsm_l, nsm_r;
