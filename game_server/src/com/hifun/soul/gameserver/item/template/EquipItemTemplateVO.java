package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 装备物品模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipItemTemplateVO extends TemplateObject {

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

	/**  装备位置 */
	@ExcelCellBinding(offset = 8)
	protected int position;

	/**  耐久上限 */
	@ExcelCellBinding(offset = 9)
	protected int maxEndure;

	/**  绑定模式 */
	@ExcelCellBinding(offset = 10)
	protected int bind;

	/**  等级限制 */
	@ExcelCellBinding(offset = 11)
	protected int limitLevel;

	/**  职业限制(把可以用的加起来) */
	@ExcelCellBinding(offset = 12)
	protected int limitOccupation;

	/**  性别限制 */
	@ExcelCellBinding(offset = 13)
	protected int limitSex;

	/**  这个物品堆叠一个格子数量 */
	@ExcelCellBinding(offset = 14)
	protected int maxOverlap;

	/**  是否允许出售 */
	@ExcelCellBinding(offset = 15)
	protected boolean sell;

	/**  卖出货币类型 */
	@ExcelCellBinding(offset = 16)
	protected short currencyType;

	/**  卖出货币数量 */
	@ExcelCellBinding(offset = 17)
	protected int currencyNum;

	/**  装备的属性 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.item.template.EquipItemAttribute.class, collectionNumber = "18,19,20;21,22,23;24,25,26;27,28,29")
	protected List<com.hifun.soul.gameserver.item.template.EquipItemAttribute> attributes;

	/**  装备默认孔数 */
	@ExcelCellBinding(offset = 30)
	protected int equipHole;

	/**  装备的最大孔数[最大为3] */
	@ExcelCellBinding(offset = 31)
	protected int maxEquipHole;

	/**  开第一个孔消耗的物品ID */
	@ExcelCellBinding(offset = 32)
	protected int firstItemId;

	/**  开第一个孔消耗的物品数量 */
	@ExcelCellBinding(offset = 33)
	protected int firstItemNum;

	/**  开第二个孔消耗的物品ID */
	@ExcelCellBinding(offset = 34)
	protected int secondItemId;

	/**  开第二个孔消耗的物品数量 */
	@ExcelCellBinding(offset = 35)
	protected int secondItemNum;

	/**  开第三个孔消耗的物品ID */
	@ExcelCellBinding(offset = 36)
	protected int thirdItemId;

	/**  开第三个孔消耗的物品数量 */
	@ExcelCellBinding(offset = 37)
	protected int thirdItemNum;

	/**  开第四个孔消耗的物品ID */
	@ExcelCellBinding(offset = 38)
	protected int fourItemId;

	/**  开第四个孔消耗的物品数量 */
	@ExcelCellBinding(offset = 39)
	protected int fourItemNum;

	/**  装备的随机属性 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.item.template.GemAttribute.class, collectionNumber = "40,41,42;43,44,45;46,47,48")
	protected List<com.hifun.soul.gameserver.item.template.GemAttribute> specialAttributes;


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
	
	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		if (position == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 装备位置]position不可以为0");
		}
		if (position < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 装备位置]position的值不得小于1");
		}
		this.position = position;
	}
	
	public int getMaxEndure() {
		return this.maxEndure;
	}

	public void setMaxEndure(int maxEndure) {
		if (maxEndure == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 耐久上限]maxEndure不可以为0");
		}
		if (maxEndure < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 耐久上限]maxEndure的值不得小于1");
		}
		this.maxEndure = maxEndure;
	}
	
	public int getBind() {
		return this.bind;
	}

	public void setBind(int bind) {
		if (bind == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 绑定模式]bind不可以为0");
		}
		this.bind = bind;
	}
	
	public int getLimitLevel() {
		return this.limitLevel;
	}

	public void setLimitLevel(int limitLevel) {
		if (limitLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 等级限制]limitLevel不可以为0");
		}
		if (limitLevel < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 等级限制]limitLevel的值不得小于1");
		}
		this.limitLevel = limitLevel;
	}
	
	public int getLimitOccupation() {
		return this.limitOccupation;
	}

	public void setLimitOccupation(int limitOccupation) {
		if (limitOccupation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 职业限制(把可以用的加起来)]limitOccupation不可以为0");
		}
		if (limitOccupation < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 职业限制(把可以用的加起来)]limitOccupation的值不得小于1");
		}
		this.limitOccupation = limitOccupation;
	}
	
	public int getLimitSex() {
		return this.limitSex;
	}

	public void setLimitSex(int limitSex) {
		if (limitSex == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 性别限制]limitSex不可以为0");
		}
		if (limitSex < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 性别限制]limitSex的值不得小于1");
		}
		this.limitSex = limitSex;
	}
	
	public int getMaxOverlap() {
		return this.maxOverlap;
	}

	public void setMaxOverlap(int maxOverlap) {
		if (maxOverlap == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 这个物品堆叠一个格子数量]maxOverlap不可以为0");
		}
		if (maxOverlap < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 这个物品堆叠一个格子数量]maxOverlap的值不得小于1");
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
					17, "[ 卖出货币类型]currencyType不可以为0");
		}
		if (currencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 卖出货币类型]currencyType的值不得小于1");
		}
		this.currencyType = currencyType;
	}
	
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		if (currencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 卖出货币数量]currencyNum不可以为0");
		}
		if (currencyNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 卖出货币数量]currencyNum的值不得小于1");
		}
		this.currencyNum = currencyNum;
	}
	
	public List<com.hifun.soul.gameserver.item.template.EquipItemAttribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<com.hifun.soul.gameserver.item.template.EquipItemAttribute> attributes) {
		if (attributes == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 装备的属性]attributes不可以为空");
		}	
		this.attributes = attributes;
	}
	
	public int getEquipHole() {
		return this.equipHole;
	}

	public void setEquipHole(int equipHole) {
		if (equipHole < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					31, "[ 装备默认孔数]equipHole的值不得小于0");
		}
		this.equipHole = equipHole;
	}
	
	public int getMaxEquipHole() {
		return this.maxEquipHole;
	}

	public void setMaxEquipHole(int maxEquipHole) {
		if (maxEquipHole < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					32, "[ 装备的最大孔数[最大为3]]maxEquipHole的值不得小于0");
		}
		this.maxEquipHole = maxEquipHole;
	}
	
	public int getFirstItemId() {
		return this.firstItemId;
	}

	public void setFirstItemId(int firstItemId) {
		if (firstItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[ 开第一个孔消耗的物品ID]firstItemId不可以为0");
		}
		if (firstItemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[ 开第一个孔消耗的物品ID]firstItemId的值不得小于1");
		}
		this.firstItemId = firstItemId;
	}
	
	public int getFirstItemNum() {
		return this.firstItemNum;
	}

	public void setFirstItemNum(int firstItemNum) {
		if (firstItemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[ 开第一个孔消耗的物品数量]firstItemNum不可以为0");
		}
		if (firstItemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[ 开第一个孔消耗的物品数量]firstItemNum的值不得小于1");
		}
		this.firstItemNum = firstItemNum;
	}
	
	public int getSecondItemId() {
		return this.secondItemId;
	}

	public void setSecondItemId(int secondItemId) {
		if (secondItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[ 开第二个孔消耗的物品ID]secondItemId不可以为0");
		}
		if (secondItemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[ 开第二个孔消耗的物品ID]secondItemId的值不得小于1");
		}
		this.secondItemId = secondItemId;
	}
	
	public int getSecondItemNum() {
		return this.secondItemNum;
	}

	public void setSecondItemNum(int secondItemNum) {
		if (secondItemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[ 开第二个孔消耗的物品数量]secondItemNum不可以为0");
		}
		if (secondItemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[ 开第二个孔消耗的物品数量]secondItemNum的值不得小于1");
		}
		this.secondItemNum = secondItemNum;
	}
	
	public int getThirdItemId() {
		return this.thirdItemId;
	}

	public void setThirdItemId(int thirdItemId) {
		if (thirdItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[ 开第三个孔消耗的物品ID]thirdItemId不可以为0");
		}
		if (thirdItemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[ 开第三个孔消耗的物品ID]thirdItemId的值不得小于1");
		}
		this.thirdItemId = thirdItemId;
	}
	
	public int getThirdItemNum() {
		return this.thirdItemNum;
	}

	public void setThirdItemNum(int thirdItemNum) {
		if (thirdItemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					38, "[ 开第三个孔消耗的物品数量]thirdItemNum不可以为0");
		}
		if (thirdItemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					38, "[ 开第三个孔消耗的物品数量]thirdItemNum的值不得小于1");
		}
		this.thirdItemNum = thirdItemNum;
	}
	
	public int getFourItemId() {
		return this.fourItemId;
	}

	public void setFourItemId(int fourItemId) {
		if (fourItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[ 开第四个孔消耗的物品ID]fourItemId不可以为0");
		}
		if (fourItemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[ 开第四个孔消耗的物品ID]fourItemId的值不得小于1");
		}
		this.fourItemId = fourItemId;
	}
	
	public int getFourItemNum() {
		return this.fourItemNum;
	}

	public void setFourItemNum(int fourItemNum) {
		if (fourItemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[ 开第四个孔消耗的物品数量]fourItemNum不可以为0");
		}
		if (fourItemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[ 开第四个孔消耗的物品数量]fourItemNum的值不得小于1");
		}
		this.fourItemNum = fourItemNum;
	}
	
	public List<com.hifun.soul.gameserver.item.template.GemAttribute> getSpecialAttributes() {
		return this.specialAttributes;
	}

	public void setSpecialAttributes(List<com.hifun.soul.gameserver.item.template.GemAttribute> specialAttributes) {
		if (specialAttributes == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[ 装备的随机属性]specialAttributes不可以为空");
		}	
		this.specialAttributes = specialAttributes;
	}
	

	@Override
	public String toString() {
		return "EquipItemTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",type=" + type + ",icon=" + icon + ",rarity=" + rarity + ",position=" + position + ",maxEndure=" + maxEndure + ",bind=" + bind + ",limitLevel=" + limitLevel + ",limitOccupation=" + limitOccupation + ",limitSex=" + limitSex + ",maxOverlap=" + maxOverlap + ",sell=" + sell + ",currencyType=" + currencyType + ",currencyNum=" + currencyNum + ",attributes=" + attributes + ",equipHole=" + equipHole + ",maxEquipHole=" + maxEquipHole + ",firstItemId=" + firstItemId + ",firstItemNum=" + firstItemNum + ",secondItemId=" + secondItemId + ",secondItemNum=" + secondItemNum + ",thirdItemId=" + thirdItemId + ",thirdItemNum=" + thirdItemNum + ",fourItemId=" + fourItemId + ",fourItemNum=" + fourItemNum + ",specialAttributes=" + specialAttributes + ",]";

	}
}