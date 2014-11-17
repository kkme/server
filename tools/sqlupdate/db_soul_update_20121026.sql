USE sevensoul;
SET FOREIGN_KEY_CHECKS=0;

-- -----------------------------------------------------
-- Table structure for `t_arena_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_arena_member`;
CREATE  TABLE `t_arena_member` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `rank` INT NULL COMMENT '名次' ,
  `icon` INT NULL COMMENT '头像' ,
  `name` VARCHAR(50) NULL COMMENT '名称' ,
  `level` INT NULL COMMENT '等级' ,
  `rankRewardId` INT NULL COMMENT '排名奖励id' ,
  `rankRewardState` INT NULL COMMENT '排名奖励领取情况' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- -----------------------------------------------------
-- Table structure for `t_arena_notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_arena_notice`;
CREATE  TABLE `t_arena_notice` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
  `roleId` bigint(20) NOT NULL DEFAULT '0',
  `roleName` VARCHAR(50) NULL COMMENT '名称' ,
  `otherRoleId` bigint(20) NOT NULL DEFAULT '0',
  `otherRoleName` VARCHAR(50) NULL COMMENT '其他角色名称' ,
  `content` VARCHAR(200) NULL COMMENT '内容' ,
  `win` boolean NULL COMMENT '是否战胜' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

 -- -----------------------------------------------------
-- Table structure for `t_global_keyvalue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_global_keyvalue`;
CREATE  TABLE `t_global_keyvalue` (
  `id` INT NULL COMMENT 'id' ,
  `value` VARCHAR(200) NULL COMMENT '属性值' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;
		

-- 插入数据版本信息
INSERT INTO t_version values ('20121026', NOW());
