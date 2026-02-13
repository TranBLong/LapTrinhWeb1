-- Tạo 10 roles
INSERT INTO roles (role_code, role_name)
SELECT 
    'ROLE_' || i,
    'Role ' || i
FROM generate_series(1,10) AS s(i);

-- Tạo 20 users
INSERT INTO users (username, password_hash, user_type)
SELECT
    'user' || i,
    'hashed_pw_' || i,
    'STAFF'
FROM generate_series(1,20) AS s(i);

-- Gán role cho user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
CROSS JOIN roles r
LIMIT 20;

-- Tạo permission
INSERT INTO permissions (permission_code, permission_name, module)
SELECT
    'PERM_' || i,
    'Permission ' || i,
    'USER'
FROM generate_series(1,15) AS s(i);

-- Gán permission cho role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
LIMIT 50;
