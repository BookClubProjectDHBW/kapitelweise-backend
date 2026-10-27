

-- Schema nutzen um asynchrone Kommunikation per Queue zu ermöglichen.
-- Aktuell nutzen wir die selbe user tabelle für auth und dieses backend, dadurch haben wir keine getrennten services --> Schema nutzung!
Create SCHEMA  if not exists  detailpage;
SET search_path TO detailpage, public;

create table if not exists club_member(
                                        user_id BIGINT not null,
                                        username varchar(50) not null,
                                        bookclub_id BIGINT not null,
                                        role varchar(10) not null,
                                        primary key (user_id, bookclub_id)
);


--speichern der Vorschläge
CREATE TABLE IF NOT EXISTS club_suggestions (
                                 id      BIGSERIAL PRIMARY KEY,
                                 bookclub_id       BIGINT NOT NULL,
                                 suggestion_status            VARCHAR(45) NOT NULL DEFAULT 'PENDING' CHECK (suggestion_status IN ('PENDING', 'READY', 'REJECTED')),
                                 title             VARCHAR(200) NOT NULL,
                                 isbn              VARCHAR(45) NOT NULL,
                                 pages             INT,
                                 author            VARCHAR(255),
                                 first_publish_year  INT,
                                suggested_by varchar(50) not null,
                                created_at Timestamp not null default now()

);


--ausgewählte Bücher: CURRENT = wird gerade gelesen, FINISHED = abgeschlossen
CREATE TABLE IF NOT EXISTS club_book (
                                 id                  BIGSERIAL PRIMARY KEY,
                                 bookclub_id         BIGINT NOT NULL,
                                 book_status         VARCHAR(45) NOT NULL DEFAULT 'CURRENT' CHECK (book_status IN ('CURRENT', 'FINISHED')),
                                 title               VARCHAR(200) NOT NULL,
                                 isbn                VARCHAR(45) NOT NULL,
                                 pages               INT,
                                 author              VARCHAR(255),
                                 first_publish_year  INT,
                                 started_at          TIMESTAMP NOT NULL DEFAULT now(),
                                 finished_at         TIMESTAMP
);


-- pro Club darf nur ein Buch CURRENT sein, ein zweites lehnt die DB ab
-- Idee von KI, kannten diese Vorgehensweise nicht - hat für uns aber Sinn gemacht.
CREATE UNIQUE INDEX IF NOT EXISTS club_book_one_current
    ON club_book (bookclub_id) WHERE book_status = 'CURRENT';


ALTER TABLE club_suggestions ADD COLUMN IF NOT EXISTS suggested_by VARCHAR(50);
ALTER TABLE club_suggestions ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE club_suggestions ALTER COLUMN author TYPE VARCHAR(255);
ALTER TABLE club_book ALTER COLUMN author TYPE VARCHAR(255);

ALTER TABLE club_suggestions DROP CONSTRAINT IF EXISTS club_suggestions_suggestion_status_check;
UPDATE club_suggestions SET suggestion_status = 'REJECTED' WHERE suggestion_status = 'FAILED';
ALTER TABLE club_suggestions ADD CONSTRAINT club_suggestions_suggestion_status_check CHECK (suggestion_status IN ('PENDING', 'READY', 'REJECTED'));


DELETE FROM club_suggestions older
    USING club_suggestions newer
    WHERE older.bookclub_id = newer.bookclub_id
      AND older.suggested_by = newer.suggested_by
      AND older.id < newer.id;

CREATE UNIQUE INDEX IF NOT EXISTS club_suggestions_one_per_member
    ON club_suggestions (bookclub_id, suggested_by);

CREATE UNIQUE INDEX IF NOT EXISTS invitation_one_pending
    ON invitation (bookclub_id, invited_user_id) WHERE invitation_status = 'PENDING';



-- Termin eines Buchclubs: pro Club höchstens einer, der Admin ersetzt ihn durch einen neuen

CREATE TABLE IF NOT EXISTS club_meeting (
                                            id            BIGSERIAL PRIMARY KEY,
                                            bookclub_id   BIGINT NOT NULL UNIQUE,
                                            meeting_date  TIMESTAMP NOT NULL,

    -- wird für den zusammengesetzten Fremdschlüssel in meeting_response gebraucht
                                            CONSTRAINT uq_club_meeting_id_bookclub UNIQUE (id, bookclub_id)
);

-- Zu-/Absagen der Mitglieder zum Termin
CREATE TABLE IF NOT EXISTS meeting_response (
                                                id            BIGSERIAL PRIMARY KEY,
                                                meeting_id    BIGINT NOT NULL,
                                                user_id       BIGINT NOT NULL,
                                                bookclub_id   BIGINT NOT NULL,
                                                response_status VARCHAR(45) NOT NULL DEFAULT 'DECLINED' CHECK (response_status IN ('ACCEPTED', 'DECLINED')),
                                                responded_at  TIMESTAMP NOT NULL DEFAULT now(),


    -- Termin gehört zu diesem Club; beim Löschen des Termins verschwinden die Antworten
                                                CONSTRAINT fk_response_meeting
                                                    FOREIGN KEY (meeting_id, bookclub_id)
                                                        REFERENCES club_meeting (id, bookclub_id)
                                                        ON DELETE CASCADE,

    -- Antwortender ist Mitglied in genau diesem Club
                                                CONSTRAINT fk_response_member
                                                    FOREIGN KEY (user_id, bookclub_id)
                                                        REFERENCES club_member (user_id, bookclub_id)
                                                        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_response_member
    ON meeting_response (user_id, bookclub_id);

CREATE UNIQUE INDEX IF NOT EXISTS meeting_response_one_per_member
    ON meeting_response (meeting_id, user_id);