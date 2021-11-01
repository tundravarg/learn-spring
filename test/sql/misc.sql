---- Select Locations and theirs parents

SELECT *
FROM locations AS l
LEFT JOIN locations AS p ON p.id = l.parent_id
ORDER BY l.id
;


---- Select Locations and theirs children

SELECT *
FROM locations AS l
LEFT JOIN locations AS c ON c.parent_id = l.id
ORDER BY l.id, c.id
;


---- Select Location and ALL children

WITH RECURSIVE parent_locations AS (
    SELECT id, name, type, parent_id
    FROM locations
    WHERE name = 'Living Room'
    UNION
    SELECT c.id, c.name, c.type, c.parent_id
    FROM locations AS c
    JOIN parent_locations AS p ON p.id = c.parent_id
)
SELECT *
FROM parent_locations AS l
LEFT JOIN locations AS p ON p.id = l.parent_id
;


---- Select Location and ALL parents

WITH RECURSIVE all_locations AS (
    SELECT id, name, type, parent_id
    FROM locations
    WHERE name = 'Shelf-6-2'
    UNION
    SELECT p.id, p.name, p.type, p.parent_id
    FROM locations AS p
    JOIN all_locations AS c ON c.parent_id = p.id
)
SELECT *
FROM all_locations AS l
LEFT JOIN locations AS p ON p.id = l.parent_id
;


---- SELECT Assets

SELECT a.*, ap.date_placed, ap.removed, l1.name, l2.name, l3.name
FROM assets AS a
LEFT JOIN asset_placement AS ap ON ap.asset_id = a.id
LEFT JOIN locations AS l1 ON l1.id = ap.location_id
LEFT JOIN locations AS l2 ON l2.id = l1.parent_id
LEFT JOIN locations AS l3 ON l3.id = l2.parent_id
ORDER BY a.id
;


---- Count Assets in locations

SELECT
    l.name, count(a.name)
FROM locations AS l
LEFT JOIN asset_placement AS ap ON ap.location_id = l.id
LEFT JOIN assets AS a ON a.id = ap.asset_id
GROUP BY l.name
ORDER BY l.name
;
