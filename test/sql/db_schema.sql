---- Show table FKs

-- SELECT tc.table_name, tc.constraint_name, kcu.column_name, ccu.table_name, ccu.column_name
-- FROM information_schema.table_constraints AS tc
-- JOIN information_schema.key_column_usage AS kcu ON kcu.constraint_name = tc.constraint_name
-- JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name
-- WHERE TRUE
--     AND tc.table_name='asset_placement'
--     AND tc.constraint_type='FOREIGN KEY'
-- ;


---- Table FK references

SELECT
    tc.table_name AS "Contr. table", tc.constraint_name AS "Constr. name"
    , kcu.table_name AS "From table", kcu.column_name AS "From field"
    , ccu.table_name AS "To table", ccu.column_name AS "To field"
FROM information_schema.table_constraints AS tc
JOIN information_schema.key_column_usage AS kcu ON kcu.constraint_name = tc.constraint_name
JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name
WHERE TRUE
    AND (kcu.table_name='locations' OR ccu.table_name='locations')
    AND tc.constraint_type='FOREIGN KEY'
ORDER BY
    kcu.table_name != ccu.table_name -- This table first
    , tc.table_name, tc.constraint_name
;
