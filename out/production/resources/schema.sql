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