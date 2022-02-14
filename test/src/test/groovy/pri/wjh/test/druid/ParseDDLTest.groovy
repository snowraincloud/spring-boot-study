package pri.wjh.test.druid

import com.alibaba.druid.sql.SQLUtils
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement
import spock.lang.Specification

class ParseDDLTest extends Specification{

    def str = "CREATE TABLE `user` (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
            "  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,\n" +
            "  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,\n" +
            "  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',\n" +
            "  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `username_un` (`username`),\n" +
            "  KEY `user_login_time_IDX` (`login_time`) USING BTREE\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COMMENT='后台用户表';"

    def "test parse ddl"(){
        given:
        def sqlStatements = SQLUtils.parseStatements(str, "mysql")
        expect:
        sqlStatements.size() == 1
        sqlStatements.get(0) instanceof SQLCreateTableStatement
    }
}
