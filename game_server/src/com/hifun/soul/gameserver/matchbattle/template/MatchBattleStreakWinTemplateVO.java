package com.hifun.soul.gameserver.matchbattle.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 匹配战连胜奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MatchBattleStreakWinTemplateVO extends TemplateObject {

	/** 连胜荣誉奖励 */
	@ExcelCellBinding(offset = 1)
	protected int honour;

	/** 终结他人连胜获得荣誉 */
	@ExcelCellBinding(offset = 2)
	protected int finishOhterStreakWinHonour;

	/** 连胜金币奖励 */
	@ExcelCellBinding(offset = 3)
	protected int coin;


	public int getHonour() {
		return this.honour;
	}

	public void setHonour(int honour) {
		if (honour < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[连胜荣誉奖励]honour的值不得小于0");
		}
		this.honour = honour;
	}
	
	public int getFinishOhterStreakWinHonour() {
		return this.finishOhterStreakWinHonour;
	}

	public void setFinishOhterStreakWinHonour(int finishOhterStreakWinHonour) {
		if (finishOhterStreakWinHonour < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[终结他人连胜获得荣誉]finishOhterStreakWinHonour的值不得小于0");
		}
		this.finishOhterStreakWinHonour = finishOhterStreakWinHonour;
	}
	
	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		if (coin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[连胜金币奖励]coin的值不得小于0");
		}
		this.coin = coin;
	}
	

	@Override
	public String toString() {
		return "MatchBattleStreakWinTemplateVO[honour=" + honour + ",finishOhterStreakWinHonour=" + finishOhterStreakWinHonour + ",coin=" + coin + ",]";

	}
}