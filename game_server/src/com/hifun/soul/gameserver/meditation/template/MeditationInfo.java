package com.hifun.soul.gameserver.meditation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 冥想信息
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class MeditationInfo {
	/** 冥想时长(分钟) */
	@BeanFieldNumber(number = 1)
	private int meditationTime;
	/** 所需货币类型 */
	@BeanFieldNumber(number = 2)
	private int currencyType;
	/** 所需货币数量 */
	@BeanFieldNumber(number = 3)
	private int currencyNum;
	/** 单位时间科技点产出 */
	@BeanFieldNumber(number = 4)
	private int unitTechPoint;
	
	public int getMeditationTime() {
		return meditationTime;
	}
	public void setMeditationTime(int meditationTime) {
		this.meditationTime = meditationTime;
	}
	public int getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}
	public int getCurrencyNum() {
		return currencyNum;
	}
	public void setCurrencyNum(int currencyNum) {
		this.currencyNum = currencyNum;
	}
	public int getUnitTechPoint() {
		return unitTechPoint;
	}
	public void setUnitTechPoint(int unitTechPoint) {
		this.unitTechPoint = unitTechPoint;
	}
	
}
