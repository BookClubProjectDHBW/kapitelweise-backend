BEGIN;


INSERT INTO club.club_user (id, username) VALUES
    (1, 'niklas'),
    (3, 'testadmin'),
    (4, 'testmember'),
    (5, 'niko11'),
    (6, 'niko')
ON CONFLICT (id) DO NOTHING;

INSERT INTO club.bookclub (id, name, description) VALUES
    (1, 'Testclub', 'Krimis'),
    (2, 'Queueclub', 'Test'),
    (3, 'Dystopien', 'Aktuelles Buch läuft'),
    (4, 'Hesse-Runde', 'Ein Buch abgeschlossen, eins läuft')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description;

DELETE FROM club.invitation WHERE bookclub_id IN (1, 2, 3, 4);
DELETE FROM club.clubmember WHERE bookclub_id IN (1, 2, 3, 4);
INSERT INTO club.clubmember (role, userid, bookclub_id) VALUES
    ('ADMIN',  3, 1),
    ('MEMBER', 4, 1),
    ('ADMIN',  3, 2),
    ('MEMBER', 4, 2),
    ('MEMBER', 5, 2),
    ('ADMIN',  6, 2),
    ('ADMIN',  5, 3),
    ('MEMBER', 6, 3),
    ('MEMBER', 4, 3),
    ('ADMIN',  5, 4),
    ('MEMBER', 3, 4);

SELECT setval(pg_get_serial_sequence('club.bookclub', 'id'), (SELECT MAX(id) FROM club.bookclub));


DELETE FROM detailpage.club_member WHERE bookclub_id IN (1, 2, 3, 4);
INSERT INTO detailpage.club_member (user_id, username, bookclub_id, role) VALUES
    (3, 'testadmin',  1, 'ADMIN'),
    (4, 'testmember', 1, 'MEMBER'),
    (3, 'testadmin',  2, 'ADMIN'),
    (4, 'testmember', 2, 'MEMBER'),
    (5, 'niko11',     2, 'MEMBER'),
    (6, 'niko',       2, 'ADMIN'),
    (5, 'niko11',     3, 'ADMIN'),
    (6, 'niko',       3, 'MEMBER'),
    (4, 'testmember', 3, 'MEMBER'),
    (5, 'niko11',     4, 'ADMIN'),
    (3, 'testadmin',  4, 'MEMBER');

DELETE FROM detailpage.club_suggestions WHERE bookclub_id IN (1, 2, 3, 4);
INSERT INTO detailpage.club_suggestions
    (bookclub_id, suggestion_status, title, isbn, pages, author, first_publish_year, suggested_by) VALUES
    (2, 'READY',   'Die Verwandlung',       '080442957X',    96,   'Franz Kafka',      1915, 'testadmin'),
    (2, 'PENDING', 'Unsichtbar für niko11', '9783442311231', 400,  'Unbekannt',        2020, 'testmember'),
    (2, 'FAILED',  'Kaputter Vorschlag',    '0000000000',    NULL, NULL,               NULL, 'niko11'),
    (2, 'PENDING', 'Alter Vorschlag von niko', '9783423214124', 320, 'Unbekannt',      2010, 'niko'),
    (3, 'READY',   'Schöne neue Welt',      '9783596905850', 368,  'Aldous Huxley',    1932, 'niko11'),
    (3, 'PENDING', 'Fahrenheit 451',        '9783453527867', 256,  'Ray Bradbury',     1953, 'testmember'),
    (4, 'READY',   'Der Steppenwolf',       '9783518366752', 288,  'Hermann Hesse',    1927, 'testadmin');

DELETE FROM detailpage.club_book WHERE bookclub_id IN (1, 2, 3, 4);
INSERT INTO detailpage.club_book
    (bookclub_id, book_status, title, isbn, pages, author, first_publish_year, started_at, finished_at) VALUES
    (3, 'CURRENT',  '1984',       '9780451524935', 328, 'George Orwell', 1949, now() - interval '2 days',  NULL),
    (4, 'FINISHED', 'Siddhartha', '9783518366820', 128, 'Hermann Hesse', 1922, now() - interval '60 days', now() - interval '21 days'),
    (4, 'CURRENT',  'Demian',     '9783518367063', 192, 'Hermann Hesse', 1919, now() - interval '20 days', NULL);

COMMIT;
