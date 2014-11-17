
-- add column `interactType`

USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_prison_log
-- ----------------------------
DROP TABLE IF EXISTS `t_prison_log`;
CREATE TABLE `t_prison_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logType` tinyint(1) DEFAULT '0',
  `firstId` bigint(20) DEFAULT '0',
  `secondId` bigint(20) DEFAULT '0',
  `thirdId` bigint(20) DEFAULT '0',
  `content` varchar(100) DEFAULT NULL COMMENT '??',
  `result` tinyint(1) DEFAULT '0',
  `operateTime` bigint(20) DEFAULT '0',
  `interactType` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131230', NOW());
