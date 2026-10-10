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



Create Table if not exists invitation(
    id Bigserial primary key,
    invitation_status VARCHAR(10) NOT NULL DEFAULT 'PENDING' CHECK (invitation_status IN ('PENDING', 'ACCEPTED', 'DECLINED')),
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    invited_user_id bigint not null references users(id) on delete cascade,
    bookclub_id bigint not null references bookclub(id) on delete cascade,
    invited_by_user_id bigint not null references users(id) on delete cascade
);


CREATE UNIQUE INDEX IF NOT EXISTS invitation_one_pending
    ON invitation (bookclub_id, invited_user_id) WHERE invitation_status = 'PENDING';

ALTER TABLE invitation ADD COLUMN answered_at TIMESTAMP;