
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20)  NOT NULL,
  `password` char(32)  NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_un` (`username`),
  KEY `user_login_time_IDX` (`login_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户表';

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20)  NOT NULL COMMENT '名称',
  `description` varchar(100)  DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_un` (`name`),
  KEY `role_create_time_IDX` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户角色表';

CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint NOT NULL DEFAULT '0' COMMENT '父级权限id',
  `name` varchar(20)  NOT NULL COMMENT '名称',
  `value` varchar(100)  DEFAULT NULL COMMENT '权限值',
  `type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '权限类型：0->目录；1->菜单;',
  `uri` varchar(100)  DEFAULT NULL COMMENT '前端资源路径',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '启用状态；0->禁用；1->启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_un` (`name`),
  KEY `permission_pid_IDX` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户权限表';

CREATE TABLE `role_permission_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_permission_relation_role_id_IDX` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户角色和权限关系表';


CREATE TABLE `user_role_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_relation_user_id_IDX` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户和角色关系表';