package com.hifun.soul.gameserver.matchbattle.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 匹配战战斗奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MatchBattleRewardTemplateVO extends TemplateObject {

	/** 轮空金币奖励 */
	@ExcelCellBinding(offset = 1)
	protected int outOfTurnCoin;

	/** 胜利获得的金币 */
	@ExcelCellBinding(offset = 2)
	protected int coinOfWin;

	/** 胜利获得的荣誉 */
	@ExcelCellBinding(offset = 3)
	protected int honorOfWin;

	/** 失败获得的金币 */
	@ExcelCellBinding(offset = 4)
	protected int coinOfLose;

	/** 失败获得的荣誉 */
	@ExcelCellBinding(offset = 5)
	protected int honorOfLose;


	public int getOutOfTurnCoin() {
		return this.outOfTurnCoin;
	}

	public void setOutOfTurnCoin(int outOfTurnCoin) {
		if (outOfTurnCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[轮空金币奖励]outOfTurnCoin不可以为0");
		}
		this.outOfTurnCoin = outOfTurnCoin;
	}
	
	public int getCoinOfWin() {
		return this.coinOfWin;
	}

	public void setCoinOfWin(int coinOfWin) {
		if (coinOfWin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[胜利获得的金币]coinOfWin的值不得小于0");
		}
		this.coinOfWin = coinOfWin;
	}
	
	public int getHonorOfWin() {
		return this.honorOfWin;
	}

	public void setHonorOfWin(int honorOfWin) {
		if (honorOfWin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[胜利获得的荣誉]honorOfWin的值不得小于0");
		}
		this.honorOfWin = honorOfWin;
	}
	
	public int getCoinOfLose() {
		return this.coinOfLose;
	}

	public void setCoinOfLose(int coinOfLose) {
		if (coinOfLose < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[失败获得的金币]coinOfLose的值不得小于0");
		}
		this.coinOfLose = coinOfLose;
	}
	
	public int getHonorOfLose() {
		return this.honorOfLose;
	}

	public void setHonorOfLose(int honorOfLose) {
		if (honorOfLose < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[失败获得的荣誉]honorOfLose的值不得小于0");
		}
		this.honorOfLose = honorOfLose;
	}
	

	@Override
	public String toString() {
		return "MatchBattleRewardTemplateVO[outOfTurnCoin=" + outOfTurnCoin + ",coinOfWin=" + coinOfWin + ",honorOfWin=" + honorOfWin + ",coinOfLose=" + coinOfLose + ",honorOfLose=" + honorOfLose + ",]";

	}
}