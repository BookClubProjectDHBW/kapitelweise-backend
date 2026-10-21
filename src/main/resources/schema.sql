

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
                                 suggestion_status            VARCHAR(45) NOT NULL DEFAULT 'PENDING' CHECK (suggestion_status IN ('PENDING', 'READY', 'FAILED')),
                                 title             VARCHAR(200) NOT NULL,
                                 isbn              VARCHAR(45) NOT NULL,
                                 pages             INT,
                                 author            VARCHAR(75),
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
                                 author              VARCHAR(75),
                                 first_publish_year  INT,
                                 started_at          TIMESTAMP NOT NULL DEFAULT now(),
                                 finished_at         TIMESTAMP
);


-- pro Club darf nur ein Buch CURRENT sein, ein zweites lehnt die DB ab
-- Idee von KI, kannten diese Vorgehensweise nicht - hat für uns aber Sinn gemacht.
CREATE UNIQUE INDEX IF NOT EXISTS club_book_one_current
    ON club_book (bookclub_id) WHERE book_status = 'CURRENT';



GRANT USAGE ON SCHEMA detailpage TO detailService;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA detailpage TO detailService;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA detailpage TO detailService;

