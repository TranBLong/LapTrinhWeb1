INSERT INTO roles (role_code, role_name, is_active)
SELECT 
    'ROLE_' || i,
    'Role ' || i,
    TRUE
FROM generate_series(1,10) AS s(i);
INSERT INTO users (username, password_hash, user_type, is_active)
SELECT
    'user' || i,
    'hashed_pw_' || i,
    'STAFF',
    TRUE
FROM generate_series(1,20) AS s(i);
INSERT INTO user_roles (user_id, role_id, is_active)
SELECT u.id,
       (SELECT id FROM roles ORDER BY random() LIMIT 1),
       TRUE
FROM users u
ON CONFLICT DO NOTHING;
INSERT INTO permissions (permission_code, permission_name, module, is_active)
SELECT
    'PERM_' || i,
    'Permission ' || i,
    'USER',
    TRUE
FROM generate_series(1,15) AS s(i);
INSERT INTO role_permissions (role_id, permission_id, is_active)
SELECT r.id,
       p.id,
       TRUE
FROM roles r
CROSS JOIN permissions p
LIMIT 50
ON CONFLICT DO NOTHING;
