INSERT INTO roles (id, name) VALUES (nextval('role_id_seq'), 'admin');
INSERT INTO roles (id, name) VALUES (nextval('role_id_seq'), 'user');
DO $$
DECLARE
    admin_variable INT;
    user_variable INT;
BEGIN
    SELECT id INTO admin_variable FROM roles WHERE roles.name = 'admin';
    SELECT id INTO user_variable FROM roles WHERE roles.name = 'user';

    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'admin_panel_access', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'edit_post', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'delete_post', 'ALL');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'edit_comment', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), admin_variable, 'delete_comment', 'ALL');

    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'admin_panel_access', 'NONE');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'edit_post', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'delete_post', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'edit_comment', 'SELF');
    INSERT INTO permissions (id, role_id, name, scope) VALUES (nextval('permission_id_seq'), user_variable, 'delete_comment', 'SELF');
END $$;