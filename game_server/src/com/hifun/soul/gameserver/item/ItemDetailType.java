package com.hifun.soul.gameserver.item;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;


/**
 * 
 * 道具的小类
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum ItemDetailType implements IndexedEnum{
	/** 帽子 */
	@ClientEnumComment(comment="头盔")
	HAT(1),
	/** 衣服 */
	@ClientEnumComment(comment="衣服")
	CLOTH(2),
	/** 裤子*/
	@ClientEnumComment(comment="裤子")
	TROUSERS(3),
	/** 鞋子 */
	@ClientEnumComment(comment="鞋子")
	SHOES(4),
	/** 武器 */
	@ClientEnumComment(comment="武器")
	WEAPON(5),
	/** 饰品 */
	@ClientEnumComment(comment="饰品")
	NECKLACE(6),
	/** 戒指 */
	@ClientEnumComment(comment="戒指")
	RING(7),
	/** 护符 */
	@ClientEnumComment(comment="护符")
	AMULET(8),
	/** 材料 */
	@ClientEnumComment(comment="材料")
	MATERIAL(11),
	/** 消耗品 */
	@ClientEnumComment(comment="消耗品")
	CONSUME(12),
	/** 宝石(可镶嵌) */
	@ClientEnumComment(comment="宝石(可镶嵌)")
	GEM(13),
	/** 守护石（防降级道具） */
	@ClientEnumComment(comment="守护石（防降级道具）")
	GUARDSTONE(14),	
	/** 幸运石 （加成功几率）*/
	@ClientEnumComment(comment=" 幸运石 （加成功几率）")
	FORTUNESTONE(15),
	/** 强化石 */
	@ClientEnumComment(comment="强化石")
	UPGRADESTONE(16),
	/** 装备图纸 */
	@ClientEnumComment(comment="装备图纸")
	EQUIPPAPER(17),
	/** 礼包 */
	@ClientEnumComment(comment="礼包")
	SPREE(18),
	/** 技能卷轴 */
	@ClientEnumComment(comment="技能卷轴")
	SKILLSCROLL(19),
	/** 属性点药水 */
	@ClientEnumComment(comment="属性点重置药水")
	PROPERTY_POINT_RESET_ITEM(20),
	/** 虚拟礼包 */
	@ClientEnumComment(comment="虚拟礼包")
	VIRTUAL_SPREE(21),
	/** 小喇叭 */
	@ClientEnumComment(comment="小喇叭")
	HORN(22),
	/** 动态属性物品 */
	@ClientEnumComment(comment="动态属性物品")
	DYNAMIC_PROPERTY_ITEM(23),
	/** 增加体力道具 */
	@ClientEnumComment(comment="增加体力道具")
	ENERGY_ITEM(24),
	/** vip体验卡 */
	@ClientEnumComment(comment="vip体验卡")
	VIP_TEMP(25),
	/** 技能点重置道具 */
	@ClientEnumComment(comment="技能点重置道具")
	RESET_SKILL_POINT_TEMP(26),
	/** 天赋点重置道具 */
	@ClientEnumComment(comment="天赋点重置道具")
	RESET_GIFT_POINT_TEMP(27),
	/** 秘药 */
	@ClientEnumComment(comment="秘药")
	NOSTRUM(28),
	/** 灵图 */
	@ClientEnumComment(comment="灵图")
	MAGIC_PAPER(29),
	/** 天赋碎片 */
	@ClientEnumComment(comment="天赋碎片")
	GIFT_CHIP(30)
	;
	
	private int index;
	
	private ItemDetailType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
