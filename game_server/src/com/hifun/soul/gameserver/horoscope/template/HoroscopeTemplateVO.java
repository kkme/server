package com.hifun.soul.gameserver.horoscope.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 星运模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HoroscopeTemplateVO extends TemplateObject {

	/**  星运名字多语言 */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  星运名字 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  星运说明多语言 */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  星运说明 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  颜色 */
	@ExcelCellBinding(offset = 5)
	protected Short color;

	/**  星运等级 */
	@ExcelCellBinding(offset = 6)
	protected int level;

	/**  单个经验 */
	@ExcelCellBinding(offset = 7)
	protected int defaultExperience;

	/**  升级所需经验 */
	@ExcelCellBinding(offset = 8)
	protected int experience;

	/**  星运效果 */
	@ExcelCellBinding(offset = 9)
	protected int horoscopeKey;

	/**  效果值 */
	@ExcelCellBinding(offset = 10)
	protected int horoscopeValue;

	/**  图标 */
	@ExcelCellBinding(offset = 11)
	protected int icon;

	/**  下一级星运 */
	@ExcelCellBinding(offset = 12)
	protected int nextHoroscopeId;

	/**  属性加成方式 */
	@ExcelCellBinding(offset = 13)
	protected int propertyType;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 星运名字多语言]nameLangId不可以为0");
		}
		if (nameLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 星运名字多语言]nameLangId的值不得小于1");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 星运名字]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 星运说明多语言]descLangId不可以为0");
		}
		if (descLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 星运说明多语言]descLangId的值不得小于1");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 星运说明]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public Short getColor() {
		return this.color;
	}

	public void setColor(Short color) {
		if (color == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 颜色]color不可以为空");
		}	
		if (color < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 颜色]color的值不得小于1");
		}
		this.color = color;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 星运等级]level不可以为0");
		}
		if (level < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 星运等级]level的值不得小于1");
		}
		this.level = level;
	}
	
	public int getDefaultExperience() {
		return this.defaultExperience;
	}

	public void setDefaultExperience(int defaultExperience) {
		if (defaultExperience == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 单个经验]defaultExperience不可以为0");
		}
		if (defaultExperience < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 单个经验]defaultExperience的值不得小于1");
		}
		this.defaultExperience = defaultExperience;
	}
	
	public int getExperience() {
		return this.experience;
	}

	public void setExperience(int experience) {
		if (experience < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 升级所需经验]experience的值不得小于0");
		}
		this.experience = experience;
	}
	
	public int getHoroscopeKey() {
		return this.horoscopeKey;
	}

	public void setHoroscopeKey(int horoscopeKey) {
		if (horoscopeKey == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 星运效果]horoscopeKey不可以为0");
		}
		this.horoscopeKey = horoscopeKey;
	}
	
	public int getHoroscopeValue() {
		return this.horoscopeValue;
	}

	public void setHoroscopeValue(int horoscopeValue) {
		if (horoscopeValue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 效果值]horoscopeValue不可以为0");
		}
		this.horoscopeValue = horoscopeValue;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 图标]icon不可以为0");
		}
		this.icon = icon;
	}
	
	public int getNextHoroscopeId() {
		return this.nextHoroscopeId;
	}

	public void setNextHoroscopeId(int nextHoroscopeId) {
		this.nextHoroscopeId = nextHoroscopeId;
	}
	
	public int getPropertyType() {
		return this.propertyType;
	}

	public void setPropertyType(int propertyType) {
		if (propertyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 属性加成方式]propertyType不可以为0");
		}
		if (propertyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 属性加成方式]propertyType的值不得小于1");
		}
		this.propertyType = propertyType;
	}
	

	@Override
	public String toString() {
		return "HoroscopeTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",color=" + color + ",level=" + level + ",defaultExperience=" + defaultExperience + ",experience=" + experience + ",horoscopeKey=" + horoscopeKey + ",horoscopeValue=" + horoscopeValue + ",icon=" + icon + ",nextHoroscopeId=" + nextHoroscopeId + ",propertyType=" + propertyType + ",]";

	}
}