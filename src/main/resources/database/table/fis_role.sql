CREATE TABLE fis_role
(
    id         BIGSERIAL,
    role       VARCHAR(256) NOT NULL UNIQUE,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_fis_role PRIMARY KEY (id)
);
