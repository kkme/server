package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团冥想方式模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMeditationTemplateVO extends TemplateObject {

	/** 冥想名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** vip开启等级 */
	@ExcelCellBinding(offset = 2)
	protected int vipLevel;

	/** 货币类型 */
	@ExcelCellBinding(offset = 3)
	protected int currencyType;

	/** 消耗数量 */
	@ExcelCellBinding(offset = 4)
	protected int costNum;

	/** 获得贡献 */
	@ExcelCellBinding(offset = 5)
	protected int contribution;

	/** 获得勋章 */
	@ExcelCellBinding(offset = 6)
	protected int medal;

	/** 获得军团资金 */
	@ExcelCellBinding(offset = 7)
	protected int legionCoin;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[冥想名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		if (vipLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[vip开启等级]vipLevel的值不得小于0");
		}
		this.vipLevel = vipLevel;
	}
	
	public int getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(int currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		if (costNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[消耗数量]costNum不可以为0");
		}
		this.costNum = costNum;
	}
	
	public int getContribution() {
		return this.contribution;
	}

	public void setContribution(int contribution) {
		if (contribution == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[获得贡献]contribution不可以为0");
		}
		this.contribution = contribution;
	}
	
	public int getMedal() {
		return this.medal;
	}

	public void setMedal(int medal) {
		if (medal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[获得勋章]medal不可以为0");
		}
		this.medal = medal;
	}
	
	public int getLegionCoin() {
		return this.legionCoin;
	}

	public void setLegionCoin(int legionCoin) {
		if (legionCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[获得军团资金]legionCoin不可以为0");
		}
		this.legionCoin = legionCoin;
	}
	

	@Override
	public String toString() {
		return "LegionMeditationTemplateVO[name=" + name + ",vipLevel=" + vipLevel + ",currencyType=" + currencyType + ",costNum=" + costNum + ",contribution=" + contribution + ",medal=" + medal + ",legionCoin=" + legionCoin + ",]";

	}
}