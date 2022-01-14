CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    authority VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_permissions_permission_name UNIQUE (authority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_roles_role_name UNIQUE (role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    account_non_expired BIT NOT NULL,
    account_non_locked BIT NOT NULL,
    credentials_non_expired BIT NOT NULL,
    email VARCHAR(254) NOT NULL,
    enabled BIT NOT NULL,
    mobile VARCHAR(11) NOT NULL,
    password VARCHAR(80) NOT NULL,
    username VARCHAR(50) NOT NULL,
    using_mfa BIT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_mobile UNIQUE (mobile),
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS roles_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_roles_permissions_role_id_roles_id FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_roles_permissions_permission_id_permissions_id FOREIGN KEY (permission_id) REFERENCES permissions (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user_id_users_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_users_roles_role_id_roles_id FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
