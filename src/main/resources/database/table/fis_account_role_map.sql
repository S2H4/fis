CREATE TABLE fis_account_role_map
(
    id         BIGSERIAL,
    account_id BIGINT NOT NULL REFERENCES fis_account (id),
    role_id    BIGINT NOT NULL REFERENCES fis_role (id),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (account_id, role_id),

    CONSTRAINT pk_fis_account_role_map PRIMARY KEY (id)
);