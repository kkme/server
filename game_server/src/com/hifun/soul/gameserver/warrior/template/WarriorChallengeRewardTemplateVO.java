package com.hifun.soul.gameserver.warrior.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 勇士之路奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class WarriorChallengeRewardTemplateVO extends TemplateObject {

	/** 金币基数 */
	@ExcelCellBinding(offset = 1)
	protected int coinBase;

	/** 经验基数 */
	@ExcelCellBinding(offset = 2)
	protected int expBase;

	/** 科技点基数 */
	@ExcelCellBinding(offset = 3)
	protected int techPointBase;

	/** 接受挑战胜利金币奖励 */
	@ExcelCellBinding(offset = 4)
	protected int otherWinCoin;

	/** 接受挑战失败金币奖励 */
	@ExcelCellBinding(offset = 5)
	protected int otherLoseCoin;


	public int getCoinBase() {
		return this.coinBase;
	}

	public void setCoinBase(int coinBase) {
		if (coinBase == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[金币基数]coinBase不可以为0");
		}
		this.coinBase = coinBase;
	}
	
	public int getExpBase() {
		return this.expBase;
	}

	public void setExpBase(int expBase) {
		if (expBase == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[经验基数]expBase不可以为0");
		}
		this.expBase = expBase;
	}
	
	public int getTechPointBase() {
		return this.techPointBase;
	}

	public void setTechPointBase(int techPointBase) {
		if (techPointBase == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[科技点基数]techPointBase不可以为0");
		}
		this.techPointBase = techPointBase;
	}
	
	public int getOtherWinCoin() {
		return this.otherWinCoin;
	}

	public void setOtherWinCoin(int otherWinCoin) {
		if (otherWinCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[接受挑战胜利金币奖励]otherWinCoin的值不得小于0");
		}
		this.otherWinCoin = otherWinCoin;
	}
	
	public int getOtherLoseCoin() {
		return this.otherLoseCoin;
	}

	public void setOtherLoseCoin(int otherLoseCoin) {
		if (otherLoseCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[接受挑战失败金币奖励]otherLoseCoin的值不得小于0");
		}
		this.otherLoseCoin = otherLoseCoin;
	}
	

	@Override
	public String toString() {
		return "WarriorChallengeRewardTemplateVO[coinBase=" + coinBase + ",expBase=" + expBase + ",techPointBase=" + techPointBase + ",otherWinCoin=" + otherWinCoin + ",otherLoseCoin=" + otherLoseCoin + ",]";

	}
}