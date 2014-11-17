package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 税收通灵收益模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LevyAuraTemplateVO extends TemplateObject {

	/**  灵气值收益 */
	@ExcelCellBinding(offset = 1)
	protected int winAura;

	/** 集齐1颗奖励 */
	@ExcelCellBinding(offset = 2)
	protected int oneAura;

	/** 集齐2颗奖励 */
	@ExcelCellBinding(offset = 3)
	protected int twoAura;

	/** 集齐3颗奖励 */
	@ExcelCellBinding(offset = 4)
	protected int threeAura;

	/** 集齐4颗奖励 */
	@ExcelCellBinding(offset = 5)
	protected int fourAura;


	public int getWinAura() {
		return this.winAura;
	}

	public void setWinAura(int winAura) {
		if (winAura == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 灵气值收益]winAura不可以为0");
		}
		this.winAura = winAura;
	}
	
	public int getOneAura() {
		return this.oneAura;
	}

	public void setOneAura(int oneAura) {
		if (oneAura == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[集齐1颗奖励]oneAura不可以为0");
		}
		this.oneAura = oneAura;
	}
	
	public int getTwoAura() {
		return this.twoAura;
	}

	public void setTwoAura(int twoAura) {
		if (twoAura == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[集齐2颗奖励]twoAura不可以为0");
		}
		this.twoAura = twoAura;
	}
	
	public int getThreeAura() {
		return this.threeAura;
	}

	public void setThreeAura(int threeAura) {
		if (threeAura == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[集齐3颗奖励]threeAura不可以为0");
		}
		this.threeAura = threeAura;
	}
	
	public int getFourAura() {
		return this.fourAura;
	}

	public void setFourAura(int fourAura) {
		if (fourAura == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[集齐4颗奖励]fourAura不可以为0");
		}
		this.fourAura = fourAura;
	}
	

	@Override
	public String toString() {
		return "LevyAuraTemplateVO[winAura=" + winAura + ",oneAura=" + oneAura + ",twoAura=" + twoAura + ",threeAura=" + threeAura + ",fourAura=" + fourAura + ",]";

	}
}