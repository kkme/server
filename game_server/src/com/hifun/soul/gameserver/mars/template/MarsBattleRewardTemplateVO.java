package com.hifun.soul.gameserver.mars.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 战神之巅战斗奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MarsBattleRewardTemplateVO extends TemplateObject {

	/** 星魂 */
	@ExcelCellBinding(offset = 1)
	protected int starSoul;

	/** 金币 */
	@ExcelCellBinding(offset = 2)
	protected int coin;

	/** 接受战斗胜利奖励荣誉 */
	@ExcelCellBinding(offset = 3)
	protected int acceptWinHonor;

	/** 接受战斗失败奖励荣誉 */
	@ExcelCellBinding(offset = 4)
	protected int acceptLoseHonor;


	public int getStarSoul() {
		return this.starSoul;
	}

	public void setStarSoul(int starSoul) {
		if (starSoul == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[星魂]starSoul不可以为0");
		}
		this.starSoul = starSoul;
	}
	
	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		if (coin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[金币]coin不可以为0");
		}
		this.coin = coin;
	}
	
	public int getAcceptWinHonor() {
		return this.acceptWinHonor;
	}

	public void setAcceptWinHonor(int acceptWinHonor) {
		if (acceptWinHonor == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[接受战斗胜利奖励荣誉]acceptWinHonor不可以为0");
		}
		this.acceptWinHonor = acceptWinHonor;
	}
	
	public int getAcceptLoseHonor() {
		return this.acceptLoseHonor;
	}

	public void setAcceptLoseHonor(int acceptLoseHonor) {
		if (acceptLoseHonor == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[接受战斗失败奖励荣誉]acceptLoseHonor不可以为0");
		}
		this.acceptLoseHonor = acceptLoseHonor;
	}
	

	@Override
	public String toString() {
		return "MarsBattleRewardTemplateVO[starSoul=" + starSoul + ",coin=" + coin + ",acceptWinHonor=" + acceptWinHonor + ",acceptLoseHonor=" + acceptLoseHonor + ",]";

	}
}