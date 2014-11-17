
use sevensoul_gm;
-- ----------------------------
-- Table structure for `t_human`
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log` (
    id bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    userId int NOT NULL COMMENT '用户id',
    userName varchar(50) NOT NULL COMMENT '用户名',
    operationType int default 0 NULL COMMENT '操作类型' ,
    operationText varchar(100)  NULL COMMENT '操作描述',
    hasPermission tinyint  NULL COMMENT '是否有该权限',
    param varchar(500)  NULL COMMENT '操作参数',
    result tinyint   NULL COMMENT '操作结果',
    operateTime bigint   NULL COMMENT '操作时间',
    createTime timestamp default current_timestamp NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
