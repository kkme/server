package com.hifun.soul.gameserver.warrior.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 勇士之路任务模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class WarriorQuestTemplateVO extends TemplateObject {

	/** 金币系数 */
	@ExcelCellBinding(offset = 1)
	protected float coinRatio;

	/** 经验系数 */
	@ExcelCellBinding(offset = 2)
	protected float expRatio;

	/** 科技点系数 */
	@ExcelCellBinding(offset = 3)
	protected float techPointRatio;

	/** 任务达成所需次数 */
	@ExcelCellBinding(offset = 4)
	protected int counter;

	/** 伤害血量百分比 */
	@ExcelCellBinding(offset = 5)
	protected int damageHpPercent;


	public float getCoinRatio() {
		return this.coinRatio;
	}

	public void setCoinRatio(float coinRatio) {
		if (coinRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[金币系数]coinRatio的值不得小于0");
		}
		this.coinRatio = coinRatio;
	}
	
	public float getExpRatio() {
		return this.expRatio;
	}

	public void setExpRatio(float expRatio) {
		if (expRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[经验系数]expRatio的值不得小于0");
		}
		this.expRatio = expRatio;
	}
	
	public float getTechPointRatio() {
		return this.techPointRatio;
	}

	public void setTechPointRatio(float techPointRatio) {
		if (techPointRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[科技点系数]techPointRatio的值不得小于0");
		}
		this.techPointRatio = techPointRatio;
	}
	
	public int getCounter() {
		return this.counter;
	}

	public void setCounter(int counter) {
		if (counter == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[任务达成所需次数]counter不可以为0");
		}
		this.counter = counter;
	}
	
	public int getDamageHpPercent() {
		return this.damageHpPercent;
	}

	public void setDamageHpPercent(int damageHpPercent) {
		if (damageHpPercent == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[伤害血量百分比]damageHpPercent不可以为0");
		}
		this.damageHpPercent = damageHpPercent;
	}
	

	@Override
	public String toString() {
		return "WarriorQuestTemplateVO[coinRatio=" + coinRatio + ",expRatio=" + expRatio + ",techPointRatio=" + techPointRatio + ",counter=" + counter + ",damageHpPercent=" + damageHpPercent + ",]";

	}
}