delete from roles_permissions;
delete from users_roles;
delete from permissions;
delete from users;
delete from roles;
insert into permissions(id, authority, display_name)
    values (1, 'USER_READ', '查询用户信息'),
           (2, 'USER_CREATE', '新建用户'),
           (3, 'USER_UPDATE', '编辑用户信息'),
           (4, 'USER_ADMIN', '用户管理');
insert into users(id, username, mobile, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, using_mfa, email)
    values (1, 'Zhang San', '13012341234', '{bcrypt}$2a$10$fr7CfgR/yeuQEsgs9nJ18.vzikm5SheLeAKbwFiAMsdvqEZFIFcDO', 1, 1, 1, 1, false, 'zhangsan@local.dev'),
           (2, 'Li Si', '13812341234', '{bcrypt}$2a$10$fr7CfgR/yeuQEsgs9nJ18.vzikm5SheLeAKbwFiAMsdvqEZFIFcDO', 1, 1, 1, 1, false,  'lisi@local.dev');
insert into roles(id, role_name, display_name)
    values (1, 'ROLE_USER', '客户端用户'),
           (2, 'ROLE_ADMIN', '超级管理员'),
           (3, 'ROLE_STAFF', '管理后台用户');
insert into users_roles(user_id, role_id) values (1, 1), (1, 2), (1, 3), (2, 1);
insert into roles_permissions(role_id, permission_id) values (1, 1), (2, 1), (2, 2), (2, 3), (2, 4);
