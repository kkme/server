package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 矿坑类型模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MineFieldTypeTemplateVO extends TemplateObject {

	/** 类型 */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 名称多语言id */
	@ExcelCellBinding(offset = 3)
	protected int nameLangId;

	/** 描述 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/** 描述多语言id */
	@ExcelCellBinding(offset = 5)
	protected int descLangId;

	/** 图片id */
	@ExcelCellBinding(offset = 6)
	protected int picId;

	/** 基础收获cd时间 */
	@ExcelCellBinding(offset = 7)
	protected int cdTime;

	/** 怪物出现概率 */
	@ExcelCellBinding(offset = 8)
	protected int monsterRate;

	/** 产出物品id数组 */
	@ExcelCellBinding(offset = 9)
	protected String itemIds;

	/** 产出物品数量数组 */
	@ExcelCellBinding(offset = 10)
	protected String itemNums;

	/** 产出物品权重数组 */
	@ExcelCellBinding(offset = 11)
	protected String itemWeights;

	/** 产出物品的图标数组 */
	@ExcelCellBinding(offset = 12)
	protected String itemIcons;


	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		if (type == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[类型]type不可以为0");
		}
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[名称多语言id]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[描述多语言id]descLangId不可以为0");
		}
		this.descLangId = descLangId;
	}
	
	public int getPicId() {
		return this.picId;
	}

	public void setPicId(int picId) {
		if (picId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[图片id]picId不可以为0");
		}
		this.picId = picId;
	}
	
	public int getCdTime() {
		return this.cdTime;
	}

	public void setCdTime(int cdTime) {
		if (cdTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[基础收获cd时间]cdTime的值不得小于0");
		}
		this.cdTime = cdTime;
	}
	
	public int getMonsterRate() {
		return this.monsterRate;
	}

	public void setMonsterRate(int monsterRate) {
		if (monsterRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[怪物出现概率]monsterRate不可以为0");
		}
		this.monsterRate = monsterRate;
	}
	
	public String getItemIds() {
		return this.itemIds;
	}

	public void setItemIds(String itemIds) {
		if (StringUtils.isEmpty(itemIds)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[产出物品id数组]itemIds不可以为空");
		}
		this.itemIds = itemIds;
	}
	
	public String getItemNums() {
		return this.itemNums;
	}

	public void setItemNums(String itemNums) {
		if (StringUtils.isEmpty(itemNums)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[产出物品数量数组]itemNums不可以为空");
		}
		this.itemNums = itemNums;
	}
	
	public String getItemWeights() {
		return this.itemWeights;
	}

	public void setItemWeights(String itemWeights) {
		if (StringUtils.isEmpty(itemWeights)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[产出物品权重数组]itemWeights不可以为空");
		}
		this.itemWeights = itemWeights;
	}
	
	public String getItemIcons() {
		return this.itemIcons;
	}

	public void setItemIcons(String itemIcons) {
		if (StringUtils.isEmpty(itemIcons)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[产出物品的图标数组]itemIcons不可以为空");
		}
		this.itemIcons = itemIcons;
	}
	

	@Override
	public String toString() {
		return "MineFieldTypeTemplateVO[type=" + type + ",name=" + name + ",nameLangId=" + nameLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",picId=" + picId + ",cdTime=" + cdTime + ",monsterRate=" + monsterRate + ",itemIds=" + itemIds + ",itemNums=" + itemNums + ",itemWeights=" + itemWeights + ",itemIcons=" + itemIcons + ",]";

	}
}