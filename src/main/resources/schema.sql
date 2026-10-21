

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
                                 first_publish_year  INT

);

CREATE INDEX IF NOT EXISTS club_suggestions_bookclub_id
    ON club_suggestions (bookclub_id);



GRANT USAGE ON SCHEMA detailpage TO detailService;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA detailpage TO detailService;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA detailpage TO detailService;

