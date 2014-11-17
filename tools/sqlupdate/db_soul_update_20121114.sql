
USE sevensoul;

-- -----------------------------------------------------
-- Table structure for `t_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE  TABLE `t_question` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
  `questionId` bigint(20) NOT NULL DEFAULT '0' COMMENT '问题id' ,
  `questionType` INT NULL COMMENT '类型' ,
  `content` VARCHAR(400) NULL COMMENT '内容' ,
  `askerId` bigint(20) NOT NULL DEFAULT '0' COMMENT '询问者id' ,
  `askerName` VARCHAR(50) NULL COMMENT '名称' ,
  `askTime` DATETIME NULL COMMENT '时间' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

 -- -----------------------------------------------------
-- Table structure for `t_activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE  TABLE `t_activity` (
  `id` INT NOT NULL,
  `activityState` INT NULL COMMENT '活动状态' ,
  `timeSpanIndex` INT NULL COMMENT '时间段索引' ,
  `editTime` BIGINT NULL COMMENT '记录修改时间' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20121114', NOW());