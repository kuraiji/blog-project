SELECT * FROM roles;
SELECT * FROM permissions;
SELECT * FROM users;

TRUNCATE TABLE permissions;
TRUNCATE TABLE roles CASCADE;
TRUNCATE TABLE flyway_schema_history;
ALTER SEQUENCE permission_id_seq RESTART WITH 1;
ALTER SEQUENCE role_id_seq RESTART WITH 1;