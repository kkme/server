package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 主城怪物模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MainCityMonsterTemplateVO extends TemplateObject {

	/** 怪物ID */
	@ExcelCellBinding(offset = 1)
	protected int monsterId;

	/** 怪物数量 */
	@ExcelCellBinding(offset = 2)
	protected int monsterNum;

	/** 奖励经验 */
	@ExcelCellBinding(offset = 3)
	protected int rewardExperience;

	/** 奖励金币 */
	@ExcelCellBinding(offset = 4)
	protected int rewardCoin;


	public int getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(int monsterId) {
		if (monsterId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[怪物ID]monsterId不可以为0");
		}
		this.monsterId = monsterId;
	}
	
	public int getMonsterNum() {
		return this.monsterNum;
	}

	public void setMonsterNum(int monsterNum) {
		if (monsterNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[怪物数量]monsterNum不可以为0");
		}
		this.monsterNum = monsterNum;
	}
	
	public int getRewardExperience() {
		return this.rewardExperience;
	}

	public void setRewardExperience(int rewardExperience) {
		if (rewardExperience == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[奖励经验]rewardExperience不可以为0");
		}
		this.rewardExperience = rewardExperience;
	}
	
	public int getRewardCoin() {
		return this.rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		if (rewardCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[奖励金币]rewardCoin不可以为0");
		}
		this.rewardCoin = rewardCoin;
	}
	

	@Override
	public String toString() {
		return "MainCityMonsterTemplateVO[monsterId=" + monsterId + ",monsterNum=" + monsterNum + ",rewardExperience=" + rewardExperience + ",rewardCoin=" + rewardCoin + ",]";

	}
}