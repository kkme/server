USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `specialShopItems` BLOB DEFAULT NULL AFTER `mine`;

 -- -----------------------------------------------------
-- Table structure for `t_special_shop_notify`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_special_shop_notify`;
CREATE  TABLE `t_special_shop_notify` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
  `roleName` VARCHAR(50) NULL DEFAULT NULL COMMENT '角色名称' ,
  `rewardName` VARCHAR(100) NULL DEFAULT NULL  COMMENT '奖品名称' ,
  `specialShopItemId` INT NULL COMMENT '神秘商品id' ,
  `itemId` INT NULL COMMENT '物品id' ,
  `itemNum` INT NULL COMMENT '物品数量' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20121215', NOW());
