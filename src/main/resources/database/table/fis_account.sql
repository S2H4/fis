CREATE TABLE fis_account
(
    id                  BIGSERIAL,
    name                VARCHAR(256),
    username            VARCHAR(256) NOT NULL UNIQUE,
    email               VARCHAR(256) NULL UNIQUE,
    mobile_phone        VARCHAR(25) NOT NULL UNIQUE,
    password            VARCHAR(256) NOT NULL,
    enabled             BOOLEAN   DEFAULT FALSE,
    mobile_verified     BOOLEAN   DEFAULT FALSE,
    expired             BOOLEAN   DEFAULT FALSE,
    locked              BOOLEAN   DEFAULT FALSE,
    credentials_expired BOOLEAN   DEFAULT FALSE,
    last_login          TIMESTAMP,
    created_on          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by          BIGINT,
    updated_on          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by          BIGINT,
    approved            BOOLEAN DEFAULT FALSE,
    x                   BOOLEAN DEFAULT FALSE,

    CONSTRAINT pk_fis_account PRIMARY KEY (id)
);