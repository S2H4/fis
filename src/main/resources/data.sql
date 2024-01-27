INSERT INTO fis_account
       (name, username, email, mobile_phone, password, enabled, created_on, created_by, approved, mobile_verified, expired, locked, x, credentials_expired)
VALUES
       ('Admin', 'baligcoup8@gmail.com', 'baligcoup8@gmail.com', '9343352734', '$2a$10$9NhAigH0vdTPk1M45AVSYO1UpA0ZQSl1ce6drvP4KdPzlBHnnHGm2', TRUE, NOW(), 1, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE);


INSERT INTO fis_account
       (name, username, email, mobile_phone, password, enabled, created_on, created_by, approved, mobile_verified, expired, locked, x, credentials_expired)
VALUES
       ('CO', 'amir.choudhary@gmail.com', 'amir.choudhary@gmail.com', '9343645334', '$2a$10$9NhAigH0vdTPk1M45AVSYO1UpA0ZQSl1ce6drvP4KdPzlBHnnHGm2', TRUE, NOW(), 1, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE);


INSERT INTO fis_account
       (name, username, email, mobile_phone, password, enabled, created_on, created_by, approved, mobile_verified, expired, locked, x, credentials_expired)
VALUES
       ('JCO', 'shabana@gmail.com', 'shabana@gmail.com', '9343645335', '$2a$10$9NhAigH0vdTPk1M45AVSYO1UpA0ZQSl1ce6drvP4KdPzlBHnnHGm2', TRUE, NOW(), 1, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE);


INSERT INTO fis_role (role) VALUES ('ROLE_ADMIN'); -- Admin
INSERT INTO fis_role (role) VALUES ('ROLE_CO'); -- COMPLIANCE OFFICER
INSERT INTO fis_role (role) VALUES ('ROLE_JCO'); -- JUNIOR COMPLIANCE OFFICER


INSERT INTO fis_account_role_map (account_id, role_id)
VALUES
    (1, 1);

INSERT INTO fis_account_role_map (account_id, role_id)
VALUES
    (2, 2);

INSERT INTO fis_account_role_map (account_id, role_id)
VALUES
    (3, 3);