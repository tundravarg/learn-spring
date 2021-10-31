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


---- SELECT Assets

SELECT a.*, ap.date_placed, ap.removed, l1.name, l2.name, l3.name
FROM assets AS a
LEFT JOIN asset_placement AS ap ON ap.asset_id = a.id
LEFT JOIN locations AS l1 ON l1.id = ap.location_id
LEFT JOIN locations AS l2 ON l2.id = l1.parent_id
LEFT JOIN locations AS l3 ON l3.id = l2.parent_id
ORDER BY a.id
;
