package com.hifun.soul.gameserver.rechargetx.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 腾讯充值模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RechargeTXTemplateVO extends TemplateObject {

	/**  充值档位名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/**  充值档位名称多语言 */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/**  充值档位描述 */
	@ExcelCellBinding(offset = 3)
	protected String desc;

	/**  充值档位描述多语言 */
	@ExcelCellBinding(offset = 4)
	protected int descLangId;

	/**  建筑图标 */
	@ExcelCellBinding(offset = 5)
	protected int icon;

	/**  魔晶数量 */
	@ExcelCellBinding(offset = 6)
	protected int crystal;

	/**  价格(Q点) */
	@ExcelCellBinding(offset = 7)
	protected int price;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 充值档位名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 充值档位名称多语言]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 充值档位描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 充值档位描述多语言]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 建筑图标]icon的值不得小于0");
		}
		this.icon = icon;
	}
	
	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		if (crystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 魔晶数量]crystal的值不得小于0");
		}
		this.crystal = crystal;
	}
	
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		if (price == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 价格(Q点)]price不可以为0");
		}
		if (price < 2) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 价格(Q点)]price的值不得小于2");
		}
		this.price = price;
	}
	

	@Override
	public String toString() {
		return "RechargeTXTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",icon=" + icon + ",crystal=" + crystal + ",price=" + price + ",]";

	}
}