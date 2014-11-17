
USE sevensoul;

-- -----------------------------------------------------
-- Table structure for `t_boss`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_boss`;
CREATE  TABLE `t_boss` (
  `id` INT NOT NULL DEFAULT '0',
  `remainBlood` INT NULL COMMENT '剩余血量' ,
  `bossState` INT NULL COMMENT '状态' ,
  `joinPeopleNum` INT NULL COMMENT '参与玩家数量' ,
  `killId` BIGINT(20) NULL COMMENT '等级' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- -----------------------------------------------------
-- Table structure for `t_boss_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_boss_role`;
CREATE  TABLE `t_boss_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
  `rank` INT NOT NULL DEFAULT '0',
  `name` VARCHAR(50) NULL COMMENT '名称' ,
  `damage` INT NOT NULL DEFAULT '0',
  `chargedstrikeRate` INT NOT NULL DEFAULT '0' COMMENT '充能值' ,
  `encourageRate` INT NOT NULL DEFAULT '0' COMMENT '鼓舞值' ,
  `hasReward` boolean NULL COMMENT '是否有奖励' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20121105', NOW());