package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 关卡通用奖励消耗模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageRewardConsumeTemplateVO extends TemplateObject {

	/**  消耗金币数量 */
	@ExcelCellBinding(offset = 1)
	protected int consumeCoin;

	/**  消耗魔晶数量 */
	@ExcelCellBinding(offset = 2)
	protected int consumeCrystal;


	public int getConsumeCoin() {
		return this.consumeCoin;
	}

	public void setConsumeCoin(int consumeCoin) {
		if (consumeCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 消耗金币数量]consumeCoin的值不得小于0");
		}
		this.consumeCoin = consumeCoin;
	}
	
	public int getConsumeCrystal() {
		return this.consumeCrystal;
	}

	public void setConsumeCrystal(int consumeCrystal) {
		if (consumeCrystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 消耗魔晶数量]consumeCrystal的值不得小于0");
		}
		this.consumeCrystal = consumeCrystal;
	}
	

	@Override
	public String toString() {
		return "StageRewardConsumeTemplateVO[consumeCoin=" + consumeCoin + ",consumeCrystal=" + consumeCrystal + ",]";

	}
}