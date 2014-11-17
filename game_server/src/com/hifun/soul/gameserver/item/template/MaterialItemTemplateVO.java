package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 材料物品模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MaterialItemTemplateVO extends TemplateObject {

	/**  多语言物品的名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  物品的名字 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  多语言物品描述id */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  物品描述 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  道具种类（道具的详细种类，可能排序的时候用到） */
	@ExcelCellBinding(offset = 5)
	protected int type;

	/**  物品图标 */
	@ExcelCellBinding(offset = 6)
	protected int icon;

	/**  稀有程度 */
	@ExcelCellBinding(offset = 7)
	protected int rarity;

	/**  绑定模式 */
	@ExcelCellBinding(offset = 8)
	protected int bind;

	/**  等级限制 */
	@ExcelCellBinding(offset = 9)
	protected int limitLevel;

	/**  性别限制 */
	@ExcelCellBinding(offset = 10)
	protected int limitSex;

	/**  这个物品堆叠一个格子数量 */
	@ExcelCellBinding(offset = 11)
	protected int maxOverlap;

	/**  是否允许出售 */
	@ExcelCellBinding(offset = 12)
	protected boolean sell;

	/**  卖出货币类型 */
	@ExcelCellBinding(offset = 13)
	protected short currencyType;

	/**  卖出货币数量 */
	@ExcelCellBinding(offset = 14)
	protected int currencyNum;

	/**  宝石的属性 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.item.template.GemAttribute.class, collectionNumber = "15,16,17;18,19,20;21,22,23")
	protected List<com.hifun.soul.gameserver.item.template.GemAttribute> attributes;

	/**  镶嵌花费货币类型 */
	@ExcelCellBinding(offset = 24)
	protected short gemEmbedCurrencyType;

	/**  镶嵌花费货币数量 */
	@ExcelCellBinding(offset = 25)
	protected int gemEmbedCurrencyNum;

	/**  卸下花费货币类型 */
	@ExcelCellBinding(offset = 26)
	protected short gemExtractCurrencyType;

	/**  卸下花费货币数量 */
	@ExcelCellBinding(offset = 27)
	protected int gemExtractCurrencyNum;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 多语言物品的名字id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 物品的名字]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 多语言物品描述id]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 物品描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		if (type == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 道具种类（道具的详细种类，可能排序的时候用到）]type不可以为0");
		}
		if (type < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 道具种类（道具的详细种类，可能排序的时候用到）]type的值不得小于1");
		}
		this.type = type;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 物品图标]icon不可以为0");
		}
		this.icon = icon;
	}
	
	public int getRarity() {
		return this.rarity;
	}

	public void setRarity(int rarity) {
		if (rarity == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 稀有程度]rarity不可以为0");
		}
		if (rarity < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 稀有程度]rarity的值不得小于1");
		}
		this.rarity = rarity;
	}
	
	public int getBind() {
		return this.bind;
	}

	public void setBind(int bind) {
		if (bind == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 绑定模式]bind不可以为0");
		}
		this.bind = bind;
	}
	
	public int getLimitLevel() {
		return this.limitLevel;
	}

	public void setLimitLevel(int limitLevel) {
		if (limitLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 等级限制]limitLevel不可以为0");
		}
		if (limitLevel < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 等级限制]limitLevel的值不得小于1");
		}
		this.limitLevel = limitLevel;
	}
	
	public int getLimitSex() {
		return this.limitSex;
	}

	public void setLimitSex(int limitSex) {
		if (limitSex == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 性别限制]limitSex不可以为0");
		}
		if (limitSex < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 性别限制]limitSex的值不得小于1");
		}
		this.limitSex = limitSex;
	}
	
	public int getMaxOverlap() {
		return this.maxOverlap;
	}

	public void setMaxOverlap(int maxOverlap) {
		if (maxOverlap == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 这个物品堆叠一个格子数量]maxOverlap不可以为0");
		}
		if (maxOverlap < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 这个物品堆叠一个格子数量]maxOverlap的值不得小于1");
		}
		this.maxOverlap = maxOverlap;
	}
	
	public boolean isSell() {
		return this.sell;
	}

	public void setSell(boolean sell) {
		this.sell = sell;
	}
	
	public short getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(short currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 卖出货币类型]currencyType不可以为0");
		}
		if (currencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 卖出货币类型]currencyType的值不得小于1");
		}
		this.currencyType = currencyType;
	}
	
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		if (currencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 卖出货币数量]currencyNum不可以为0");
		}
		if (currencyNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 卖出货币数量]currencyNum的值不得小于1");
		}
		this.currencyNum = currencyNum;
	}
	
	public List<com.hifun.soul.gameserver.item.template.GemAttribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<com.hifun.soul.gameserver.item.template.GemAttribute> attributes) {
		if (attributes == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 宝石的属性]attributes不可以为空");
		}	
		this.attributes = attributes;
	}
	
	public short getGemEmbedCurrencyType() {
		return this.gemEmbedCurrencyType;
	}

	public void setGemEmbedCurrencyType(short gemEmbedCurrencyType) {
		if (gemEmbedCurrencyType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[ 镶嵌花费货币类型]gemEmbedCurrencyType的值不得小于0");
		}
		this.gemEmbedCurrencyType = gemEmbedCurrencyType;
	}
	
	public int getGemEmbedCurrencyNum() {
		return this.gemEmbedCurrencyNum;
	}

	public void setGemEmbedCurrencyNum(int gemEmbedCurrencyNum) {
		if (gemEmbedCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[ 镶嵌花费货币数量]gemEmbedCurrencyNum的值不得小于0");
		}
		this.gemEmbedCurrencyNum = gemEmbedCurrencyNum;
	}
	
	public short getGemExtractCurrencyType() {
		return this.gemExtractCurrencyType;
	}

	public void setGemExtractCurrencyType(short gemExtractCurrencyType) {
		if (gemExtractCurrencyType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[ 卸下花费货币类型]gemExtractCurrencyType的值不得小于0");
		}
		this.gemExtractCurrencyType = gemExtractCurrencyType;
	}
	
	public int getGemExtractCurrencyNum() {
		return this.gemExtractCurrencyNum;
	}

	public void setGemExtractCurrencyNum(int gemExtractCurrencyNum) {
		if (gemExtractCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[ 卸下花费货币数量]gemExtractCurrencyNum的值不得小于0");
		}
		this.gemExtractCurrencyNum = gemExtractCurrencyNum;
	}
	

	@Override
	public String toString() {
		return "MaterialItemTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",type=" + type + ",icon=" + icon + ",rarity=" + rarity + ",bind=" + bind + ",limitLevel=" + limitLevel + ",limitSex=" + limitSex + ",maxOverlap=" + maxOverlap + ",sell=" + sell + ",currencyType=" + currencyType + ",currencyNum=" + currencyNum + ",attributes=" + attributes + ",gemEmbedCurrencyType=" + gemEmbedCurrencyType + ",gemEmbedCurrencyNum=" + gemEmbedCurrencyNum + ",gemExtractCurrencyType=" + gemExtractCurrencyType + ",gemExtractCurrencyNum=" + gemExtractCurrencyNum + ",]";

	}
}