DO $$
DECLARE
    admin_variable INT;
    user_variable INT;
BEGIN
    SELECT id INTO admin_variable FROM roles WHERE roles.name = 'admin';
    SELECT id INTO user_variable FROM roles WHERE roles.name = 'user';

    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'create_user', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'create_role', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'create_permission', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'create_post', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'create_comment', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'get_post', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'get_comment', 'ALL');

    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'create_user', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'create_role', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'create_permission', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'create_post', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'create_comment', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'get_post', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'get_comment', 'ALL');
END $$;