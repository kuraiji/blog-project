CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE users
(
    id            BIGINT       NOT NULL,
    handle        VARCHAR(15)  NOT NULL,
    email         VARCHAR(254) NOT NULL,
    password_hash VARCHAR(72)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_handle UNIQUE (handle);