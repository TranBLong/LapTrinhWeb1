CREATE DATABASE usermanage
COLLATE Vietnamese_CI_AS;
GO

USE usermanage;
GO
CREATE TABLE users (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),

    username NVARCHAR(50) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(100),
    email NVARCHAR(100),
    phone NVARCHAR(20),

    user_type NVARCHAR(20) NOT NULL, -- ADMIN, GIAOVU, GIANGVIEN, SINHVIEN

    last_login_at DATETIME2,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2,

    created_by UNIQUEIDENTIFIER,
    updated_by UNIQUEIDENTIFIER,

    deleted_at DATETIME2,
    deleted_by UNIQUEIDENTIFIER,

    is_active BIT NOT NULL DEFAULT 1
);
GO
CREATE TABLE roles (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),

    role_code NVARCHAR(50) NOT NULL UNIQUE, -- ADMIN, GIAOVU, ...
    role_name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255),

    is_system BIT NOT NULL DEFAULT 0,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2,

    created_by UNIQUEIDENTIFIER,
    updated_by UNIQUEIDENTIFIER,

    deleted_at DATETIME2,
    deleted_by UNIQUEIDENTIFIER,

    is_active BIT NOT NULL DEFAULT 1
);
GO
CREATE TABLE user_roles (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),

    user_id UNIQUEIDENTIFIER NOT NULL,
    role_id UNIQUEIDENTIFIER NOT NULL,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2,

    created_by UNIQUEIDENTIFIER,
    updated_by UNIQUEIDENTIFIER,

    deleted_at DATETIME2,
    deleted_by UNIQUEIDENTIFIER,

    is_active BIT NOT NULL DEFAULT 1,

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id) REFERENCES roles(id),

    CONSTRAINT uq_user_role UNIQUE (user_id, role_id)
);
GO
CREATE TABLE permissions (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),

    permission_code NVARCHAR(100) NOT NULL UNIQUE,
    permission_name NVARCHAR(150) NOT NULL,
    module NVARCHAR(100),
    description NVARCHAR(255),

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2,

    created_by UNIQUEIDENTIFIER,
    updated_by UNIQUEIDENTIFIER,

    deleted_at DATETIME2,
    deleted_by UNIQUEIDENTIFIER,

    is_active BIT NOT NULL DEFAULT 1
);
GO
CREATE TABLE role_permissions (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),

    role_id UNIQUEIDENTIFIER NOT NULL,
    permission_id UNIQUEIDENTIFIER NOT NULL,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2,

    created_by UNIQUEIDENTIFIER,
    updated_by UNIQUEIDENTIFIER,

    deleted_at DATETIME2,
    deleted_by UNIQUEIDENTIFIER,

    is_active BIT NOT NULL DEFAULT 1,

    CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (role_id) REFERENCES roles(id),

    CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id),

    CONSTRAINT uq_role_permission UNIQUE (role_id, permission_id)
);
GO
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_user_type ON users(user_type);

CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);

CREATE INDEX idx_role_permissions_role_id ON role_permissions(role_id);
CREATE INDEX idx_role_permissions_permission_id ON role_permissions(permission_id);
GO
INSERT INTO roles (id, role_code, role_name, description, is_system)
VALUES
(NEWID(), N'ADMIN', N'Quản trị hệ thống', N'Toàn quyền hệ thống', 1),
(NEWID(), N'GIAOVU', N'Giáo vụ', N'Quản lý đào tạo', 1),
(NEWID(), N'GIANGVIEN', N'Giảng viên', N'Giảng dạy và chấm điểm', 1),
(NEWID(), N'SINHVIEN', N'Sinh viên', N'Học tập và xem điểm', 1),

(NEWID(), N'MANAGER', N'Quản lý', N'Quản lý chung', 0),
(NEWID(), N'STAFF', N'Nhân viên', N'Nhân viên hành chính', 0),
(NEWID(), N'VIEWER', N'Người xem', N'Chỉ xem dữ liệu', 0),
(NEWID(), N'EDITOR', N'Biên tập', N'Chỉnh sửa nội dung', 0),
(NEWID(), N'AUDITOR', N'Kiểm toán', N'Kiểm tra hệ thống', 0),
(NEWID(), N'SUPPORT', N'Hỗ trợ', N'Hỗ trợ người dùng', 0),

(NEWID(), N'LIBRARY', N'Thư viện', N'Quản lý thư viện', 0),
(NEWID(), N'FINANCE', N'Tài chính', N'Quản lý tài chính', 0),
(NEWID(), N'HR', N'Nhân sự', N'Quản lý nhân sự', 0),
(NEWID(), N'IT', N'Công nghệ thông tin', N'Hỗ trợ CNTT', 0),
(NEWID(), N'SECURITY', N'Bảo mật', N'Quản lý an ninh', 0),

(NEWID(), N'RESEARCH', N'Nghiên cứu', N'Nghiên cứu khoa học', 0),
(NEWID(), N'ASSISTANT', N'Trợ giảng', N'Hỗ trợ giảng viên', 0),
(NEWID(), N'LEADER', N'Trưởng nhóm', N'Quản lý nhóm', 0),
(NEWID(), N'COORDINATOR', N'Điều phối', N'Điều phối hoạt động', 0),
(NEWID(), N'GUEST', N'Khách', N'Tài khoản khách', 0);
GO
INSERT INTO users
(id, username, password_hash, full_name, email, phone, user_type)
VALUES
(NEWID(), N'admin01', N'hashed_pw_01', N'Nguyễn Văn Admin', N'admin01@dtu.edu.vn', N'0900000001', N'ADMIN'),
(NEWID(), N'giaovu01', N'hashed_pw_02', N'Trần Thị Giáo Vụ', N'giaovu01@dtu.edu.vn', N'0900000002', N'GIAOVU'),
(NEWID(), N'gv01', N'hashed_pw_03', N'Lê Văn Giảng Viên', N'gv01@dtu.edu.vn', N'0900000003', N'GIANGVIEN'),
(NEWID(), N'sv01', N'hashed_pw_04', N'Phạm Thị Sinh Viên', N'sv01@dtu.edu.vn', N'0900000004', N'SINHVIEN'),

(NEWID(), N'sv02', N'hashed_pw_05', N'Sinh Viên 02', N'sv02@dtu.edu.vn', N'0900000005', N'SINHVIEN'),
(NEWID(), N'sv03', N'hashed_pw_06', N'Sinh Viên 03', N'sv03@dtu.edu.vn', N'0900000006', N'SINHVIEN'),
(NEWID(), N'sv04', N'hashed_pw_07', N'Sinh Viên 04', N'sv04@dtu.edu.vn', N'0900000007', N'SINHVIEN'),
(NEWID(), N'sv05', N'hashed_pw_08', N'Sinh Viên 05', N'sv05@dtu.edu.vn', N'0900000008', N'SINHVIEN'),

(NEWID(), N'gv02', N'hashed_pw_09', N'Giảng viên 02', N'gv02@dtu.edu.vn', N'0900000009', N'GIANGVIEN'),
(NEWID(), N'gv03', N'hashed_pw_10', N'Giảng viên 03', N'gv03@dtu.edu.vn', N'0900000010', N'GIANGVIEN'),

(NEWID(), N'user11', N'hashed_pw_11', N'Người dùng 11', N'user11@mail.com', N'0900000011', N'STAFF'),
(NEWID(), N'user12', N'hashed_pw_12', N'Người dùng 12', N'user12@mail.com', N'0900000012', N'STAFF'),
(NEWID(), N'user13', N'hashed_pw_13', N'Người dùng 13', N'user13@mail.com', N'0900000013', N'STAFF'),
(NEWID(), N'user14', N'hashed_pw_14', N'Người dùng 14', N'user14@mail.com', N'0900000014', N'STAFF'),
(NEWID(), N'user15', N'hashed_pw_15', N'Người dùng 15', N'user15@mail.com', N'0900000015', N'STAFF'),

(NEWID(), N'user16', N'hashed_pw_16', N'Người dùng 16', N'user16@mail.com', N'0900000016', N'STAFF'),
(NEWID(), N'user17', N'hashed_pw_17', N'Người dùng 17', N'user17@mail.com', N'0900000017', N'STAFF'),
(NEWID(), N'user18', N'hashed_pw_18', N'Người dùng 18', N'user18@mail.com', N'0900000018', N'STAFF'),
(NEWID(), N'user19', N'hashed_pw_19', N'Người dùng 19', N'user19@mail.com', N'0900000019', N'STAFF'),
(NEWID(), N'user20', N'hashed_pw_20', N'Người dùng 20', N'user20@mail.com', N'0900000020', N'STAFF');
GO
INSERT INTO permissions
(id, permission_code, permission_name, module, description)
VALUES
(NEWID(), N'USER_VIEW', N'Xem người dùng', N'USER', N'Xem danh sách người dùng'),
(NEWID(), N'USER_CREATE', N'Tạo người dùng', N'USER', N'Tạo mới người dùng'),
(NEWID(), N'USER_UPDATE', N'Cập nhật người dùng', N'USER', N'Sửa thông tin người dùng'),
(NEWID(), N'USER_DELETE', N'Xóa người dùng', N'USER', N'Xóa người dùng'),

(NEWID(), N'ROLE_VIEW', N'Xem vai trò', N'ROLE', N'Xem danh sách vai trò'),
(NEWID(), N'ROLE_CREATE', N'Tạo vai trò', N'ROLE', N'Tạo vai trò'),
(NEWID(), N'ROLE_UPDATE', N'Cập nhật vai trò', N'ROLE', N'Sửa vai trò'),
(NEWID(), N'ROLE_DELETE', N'Xóa vai trò', N'ROLE', N'Xóa vai trò'),

(NEWID(), N'PERM_VIEW', N'Xem quyền', N'PERMISSION', N'Xem quyền'),
(NEWID(), N'PERM_ASSIGN', N'Gán quyền', N'PERMISSION', N'Gán quyền cho vai trò'),

(NEWID(), N'COURSE_VIEW', N'Xem môn học', N'COURSE', N'Xem môn học'),
(NEWID(), N'COURSE_CREATE', N'Tạo môn học', N'COURSE', N'Tạo môn học'),
(NEWID(), N'COURSE_UPDATE', N'Cập nhật môn học', N'COURSE', N'Sửa môn học'),
(NEWID(), N'COURSE_DELETE', N'Xóa môn học', N'COURSE', N'Xóa môn học'),

(NEWID(), N'GRADE_VIEW', N'Xem điểm', N'GRADE', N'Xem điểm'),
(NEWID(), N'GRADE_UPDATE', N'Cập nhật điểm', N'GRADE', N'Sửa điểm'),
(NEWID(), N'STUDENT_VIEW', N'Xem sinh viên', N'STUDENT', N'Xem sinh viên'),
(NEWID(), N'STUDENT_UPDATE', N'Cập nhật sinh viên', N'STUDENT', N'Sửa sinh viên'),
(NEWID(), N'LOGIN', N'Đăng nhập', N'AUTH', N'Quyền đăng nhập'),
(NEWID(), N'LOGOUT', N'Đăng xuất', N'AUTH', N'Quyền đăng xuất');
GO
INSERT INTO user_roles (id, user_id, role_id)
SELECT TOP 20 NEWID(), u.id, r.id
FROM users u
CROSS JOIN roles r;
GO
INSERT INTO role_permissions (id, role_id, permission_id)
SELECT TOP 20 NEWID(), r.id, p.id
FROM roles r
CROSS JOIN permissions p;
