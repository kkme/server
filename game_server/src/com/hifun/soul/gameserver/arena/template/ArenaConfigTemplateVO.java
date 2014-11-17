package com.hifun.soul.gameserver.arena.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 竞技场常量配置模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ArenaConfigTemplateVO extends TemplateObject {

	/** 每日挑战次数上限 */
	@ExcelCellBinding(offset = 1)
	protected int maxBattleTimes;

	/** 排名奖励结算周期 */
	@ExcelCellBinding(offset = 2)
	protected int randRewardPeriod;

	/** 战斗cd(秒) */
	@ExcelCellBinding(offset = 3)
	protected int battleCd;


	public int getMaxBattleTimes() {
		return this.maxBattleTimes;
	}

	public void setMaxBattleTimes(int maxBattleTimes) {
		if (maxBattleTimes == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[每日挑战次数上限]maxBattleTimes不可以为0");
		}
		this.maxBattleTimes = maxBattleTimes;
	}
	
	public int getRandRewardPeriod() {
		return this.randRewardPeriod;
	}

	public void setRandRewardPeriod(int randRewardPeriod) {
		if (randRewardPeriod == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[排名奖励结算周期]randRewardPeriod不可以为0");
		}
		this.randRewardPeriod = randRewardPeriod;
	}
	
	public int getBattleCd() {
		return this.battleCd;
	}

	public void setBattleCd(int battleCd) {
		if (battleCd == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[战斗cd(秒)]battleCd不可以为0");
		}
		this.battleCd = battleCd;
	}
	

	@Override
	public String toString() {
		return "ArenaConfigTemplateVO[maxBattleTimes=" + maxBattleTimes + ",randRewardPeriod=" + randRewardPeriod + ",battleCd=" + battleCd + ",]";

	}
}