package com.hifun.soul.gameserver.cd.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * cd模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CdTemplateVO extends TemplateObject {

	/**  cd类型名称多语言 */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  cd类型名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  cd上限值(分钟) */
	@ExcelCellBinding(offset = 3)
	protected int cdMaxNum;

	/**  cd是否可以花费消除 */
	@ExcelCellBinding(offset = 4)
	protected boolean canRemove;

	/**  消除cd消耗金币类型 */
	@ExcelCellBinding(offset = 5)
	protected Short currencyType;

	/**  消除cd消耗的金币数量 */
	@ExcelCellBinding(offset = 6)
	protected int currencyNum;

	/**  消除CD的单位 */
	@ExcelCellBinding(offset = 7)
	protected int costMins;

	/** cd类型[1可叠加，2不可叠加] */
	@ExcelCellBinding(offset = 8)
	protected int cdMode;

	/**  疲劳度系数 */
	@ExcelCellBinding(offset = 9)
	protected Float tiredRatio;

	/**  疲劳度基础值 */
	@ExcelCellBinding(offset = 10)
	protected int baseCd;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ cd类型名称多语言]nameLangId不可以为0");
		}
		if (nameLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ cd类型名称多语言]nameLangId的值不得小于1");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ cd类型名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getCdMaxNum() {
		return this.cdMaxNum;
	}

	public void setCdMaxNum(int cdMaxNum) {
		if (cdMaxNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ cd上限值(分钟)]cdMaxNum不可以为0");
		}
		if (cdMaxNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ cd上限值(分钟)]cdMaxNum的值不得小于1");
		}
		this.cdMaxNum = cdMaxNum;
	}
	
	public boolean isCanRemove() {
		return this.canRemove;
	}

	public void setCanRemove(boolean canRemove) {
		this.canRemove = canRemove;
	}
	
	public Short getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(Short currencyType) {
		if (currencyType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 消除cd消耗金币类型]currencyType不可以为空");
		}	
		if (currencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 消除cd消耗金币类型]currencyType的值不得小于1");
		}
		this.currencyType = currencyType;
	}
	
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		if (currencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 消除cd消耗的金币数量]currencyNum的值不得小于0");
		}
		this.currencyNum = currencyNum;
	}
	
	public int getCostMins() {
		return this.costMins;
	}

	public void setCostMins(int costMins) {
		if (costMins == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 消除CD的单位]costMins不可以为0");
		}
		if (costMins < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 消除CD的单位]costMins的值不得小于1");
		}
		this.costMins = costMins;
	}
	
	public int getCdMode() {
		return this.cdMode;
	}

	public void setCdMode(int cdMode) {
		if (cdMode == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[cd类型[1可叠加，2不可叠加]]cdMode不可以为0");
		}
		this.cdMode = cdMode;
	}
	
	public Float getTiredRatio() {
		return this.tiredRatio;
	}

	public void setTiredRatio(Float tiredRatio) {
		if (tiredRatio == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 疲劳度系数]tiredRatio不可以为空");
		}	
		this.tiredRatio = tiredRatio;
	}
	
	public int getBaseCd() {
		return this.baseCd;
	}

	public void setBaseCd(int baseCd) {
		if (baseCd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 疲劳度基础值]baseCd的值不得小于0");
		}
		this.baseCd = baseCd;
	}
	

	@Override
	public String toString() {
		return "CdTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",cdMaxNum=" + cdMaxNum + ",canRemove=" + canRemove + ",currencyType=" + currencyType + ",currencyNum=" + currencyNum + ",costMins=" + costMins + ",cdMode=" + cdMode + ",tiredRatio=" + tiredRatio + ",baseCd=" + baseCd + ",]";

	}
}