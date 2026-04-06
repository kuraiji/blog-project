DO $$
DECLARE
    admin_variable INT;
    user_variable INT;
BEGIN
    SELECT id INTO admin_variable FROM roles WHERE roles.name = 'admin';
    SELECT id INTO user_variable FROM roles WHERE roles.name = 'user';

    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'edit_user', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'delete_user', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'get_user', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'edit_role', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'delete_role', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'get_role', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'edit_permission', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'delete_permission', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'get_permission', 'ALL');

    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'edit_user', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'delete_user', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'get_user', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'edit_role', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'delete_role', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'get_role', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'edit_permission', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'delete_permission', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'get_permission', 'NONE');
END $$;