DO $$
DECLARE
    flat_id int;
    room_id int;
    parent_id_1 int;
    parent_id_2 int;
    parent_id_3 int;
BEGIN


---- Locations


INSERT INTO locations (name, type)
VALUES
    ('My Flat', 'FLAT')
;

flat_id = currval('locations_id_seq');

INSERT INTO locations (name, type, parent_id)
VALUES
    ('Entrance Hall', 'ROOM', flat_id),
    ('Batthroom', 'ROOM', flat_id),
    ('Living Room', 'ROOM', flat_id),
    ('Kitchen', 'ROOM', flat_id)
;

room_id = (SELECT id FROM locations WHERE name = 'Entrance Hall');

INSERT INTO locations (name, type, parent_id)
VALUES
    ('Wardrobe', 'STORAGE', room_id),
    ('Chest', 'STORAGE', room_id)
;

parent_id_1 = (SELECT id FROM locations WHERE parent_id = room_id AND name = 'Chest');

INSERT INTO locations (name, type, parent_id)
VALUES
    ('Drawer-1', 'DRAWER', parent_id_1),
    ('Drawer-2', 'DRAWER', parent_id_1),
    ('Drawer-3', 'DRAWER', parent_id_1),
    ('Drawer-4', 'DRAWER', parent_id_1),
    ('Drawer-5', 'DRAWER', parent_id_1)
;

room_id = (SELECT id FROM locations WHERE name = 'Living Room');

INSERT INTO locations (name, type, parent_id)
VALUES
    ('Wardrobe-1', 'STORAGE', room_id),
    ('Wardrobe-2', 'STORAGE', room_id),
    ('Chest', 'STORAGE', room_id),
    ('Desk', 'STORAGE', room_id)
;

parent_id_1 = (SELECT id FROM locations WHERE parent_id = room_id AND name = 'Desk');

INSERT INTO locations (name, type, parent_id)
VALUES
    ('Drawer-1', 'DRAWER', parent_id_1),
    ('Drawer-2', 'DRAWER', parent_id_1),
    ('Drawer-3', 'DRAWER', parent_id_1),
    ('Drawer-4', 'DRAWER', parent_id_1),
    ('Drawer-5', 'DRAWER', parent_id_1),
    ('Drawer-6', 'DRAWER', parent_id_1),
    ('Drawer-7', 'DRAWER', parent_id_1)
;

parent_id_2 = (SELECT id FROM locations WHERE parent_id = parent_id_1 AND name = 'Drawer-6');

INSERT INTO locations (name, type, parent_id)
VALUES
    ('Shelf-6-1', 'SHELF', parent_id_2),
    ('Shelf-6-2', 'SHELF', parent_id_2),
    ('Shelf-6-3', 'SHELF', parent_id_2)
;


---- Assets


INSERT INTO assets (name, type, comment)
VALUES
    ('Computer', 'DEVICE', ''),
    ('Keyboard-1', 'DEVICE', ''),
    ('Keyboard-2', 'DEVICE', ''),
    ('Gamepad', 'DEVICE', ''),
    ('Multimeter', 'DEVICE', ''),
    ('Type-C Patchcord 1', 'DEVICE', ''),
    ('Jacket 1', 'CLOTHES', ''),
    ('Jacket 2', 'CLOTHES', ''),
    ('T-short 1', 'CLOTHES', ''),
    ('T-short 2', 'CLOTHES', ''),
    ('Paper', 'OFFICE_TOOL', ''),
    ('Receipts', 'DOCUMENT', '')
;


---- Asset Placement

room_id = (SELECT id FROM locations WHERE name = 'Entrance Hall');
parent_id_1 = (SELECT id FROM locations WHERE parent_id = room_id AND name = 'Wardrobe');

INSERT INTO asset_placement (asset_id, location_id, date_placed, removed, date_removed)
VALUES
    (
        (SELECT id FROM assets WHERE name = 'Jacket 1'),
        parent_id_1,
        '2020-09-01',
        true, '2021-10-15'
    ),
    (
        (SELECT id FROM assets WHERE name = 'Jacket 2'),
        parent_id_1,
        '2021-10-02',
        false, NULL
    ),
    (
        (SELECT id FROM assets WHERE name = 'T-short 1'),
        parent_id_1,
        '2021-09-05',
        false, NULL
    ),
    (
        (SELECT id FROM assets WHERE name = 'T-short 2'),
        parent_id_1,
        '2021-09-10',
        true, NULL
    )
;

room_id = (SELECT id FROM locations WHERE name = 'Living Room');
parent_id_1 = (SELECT id FROM locations WHERE parent_id = room_id AND name = 'Desk');

INSERT INTO asset_placement (asset_id, location_id, date_placed, removed, date_removed)
VALUES
    (
        (SELECT id FROM assets WHERE name = 'Computer'),
        parent_id_1,
        '2020-09-01',
        false, NULL
    )
;

parent_id_2 = (SELECT id FROM locations WHERE parent_id = parent_id_1 AND name = 'Drawer-1');

INSERT INTO asset_placement (asset_id, location_id, date_placed, removed, date_removed)
VALUES
    (
        (SELECT id FROM assets WHERE name = 'Paper'),
        parent_id_2,
        '2020-10-15',
        false, NULL
    )
;

parent_id_2 = (SELECT id FROM locations WHERE parent_id = parent_id_1 AND name = 'Drawer-6');
parent_id_3 = (SELECT id FROM locations WHERE parent_id = parent_id_2 AND name = 'Shelf-6-1');

INSERT INTO asset_placement (asset_id, location_id, date_placed, removed, date_removed)
VALUES
    (
        (SELECT id FROM assets WHERE name = 'Keyboard-1'),
        parent_id_3,
        '2020-09-15',
        true, NULL
    ),
    (
        (SELECT id FROM assets WHERE name = 'Keyboard-2'),
        parent_id_3,
        '2020-09-14',
        false, NULL
    )
;

parent_id_2 = (SELECT id FROM locations WHERE parent_id = parent_id_1 AND name = 'Drawer-2');

INSERT INTO asset_placement (asset_id, location_id, date_placed, removed, date_removed)
VALUES
    (
        (SELECT id FROM assets WHERE name = 'Type-C Patchcord 1'),
        parent_id_2,
        '2021-06-01',
        false, NULL
    )
;


END $$;