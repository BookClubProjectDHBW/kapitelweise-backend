-- Nutzer
CREATE TABLE IF NOT EXISTS users (
                                     id          BIGSERIAL    PRIMARY KEY,
                                     username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- Buchclubs
CREATE TABLE IF NOT EXISTS bookclub (
                                     id          BIGSERIAL    PRIMARY KEY,
                                     name        VARCHAR(20)  NOT NULL,
    description VARCHAR(200),
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

--Mitgliederverwaltung

CREATE TABLE IF NOT EXISTS clubmember (
                                           id            BIGSERIAL   PRIMARY KEY,
                                           role          VARCHAR(10) NOT NULL,
    join_date     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    userid       BIGINT      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    bookclub_id  BIGINT      NOT NULL REFERENCES bookclub (id) ON DELETE CASCADE
    );

-- ein User kann pro Club nur einmal Mitglied sein
-- (als Index statt im CREATE TABLE, damit es auch auf bereits bestehende Tabellen angewendet wird)
CREATE UNIQUE INDEX IF NOT EXISTS clubmember_user_club_unique ON clubmember (userid, bookclub_id);
