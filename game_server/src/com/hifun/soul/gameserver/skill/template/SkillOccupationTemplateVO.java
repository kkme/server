package com.hifun.soul.gameserver.skill.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 职业技能模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SkillOccupationTemplateVO extends TemplateObject {

	/**  角色等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/**  职业 */
	@ExcelCellBinding(offset = 2)
	protected int occupation;

	/**  技能职业系 */
	@ExcelCellBinding(offset = 3)
	protected int skillOccupationType;

	/**  红魔初始值 */
	@ExcelCellBinding(offset = 4)
	protected int redInit;

	/**  黄魔初始值 */
	@ExcelCellBinding(offset = 5)
	protected int yellowInit;

	/**  蓝魔初始值 */
	@ExcelCellBinding(offset = 6)
	protected int blueInit;

	/**  绿魔初始值 */
	@ExcelCellBinding(offset = 7)
	protected int greenInit;

	/**  紫魔初始值 */
	@ExcelCellBinding(offset = 8)
	protected int purpleInit;

	/**  红魔上限 */
	@ExcelCellBinding(offset = 9)
	protected int redMax;

	/**  黄魔上限 */
	@ExcelCellBinding(offset = 10)
	protected int yellowMax;

	/**  蓝魔上限 */
	@ExcelCellBinding(offset = 11)
	protected int blueMax;

	/**  绿魔上限 */
	@ExcelCellBinding(offset = 12)
	protected int greenMax;

	/**  紫魔上限 */
	@ExcelCellBinding(offset = 13)
	protected int purpleMax;

	/**  消除红魔加成值 */
	@ExcelCellBinding(offset = 14)
	protected int redEliminateBonus;

	/**  消除黄魔加成值 */
	@ExcelCellBinding(offset = 15)
	protected int yellowEliminateBonus;

	/**  蓝魔黄魔加成值 */
	@ExcelCellBinding(offset = 16)
	protected int blueEliminateBonus;

	/**  消除绿魔加成值 */
	@ExcelCellBinding(offset = 17)
	protected int greenEliminateBonus;

	/**  消除紫魔加成值 */
	@ExcelCellBinding(offset = 18)
	protected int purpleEliminateBonus;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 角色等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getOccupation() {
		return this.occupation;
	}

	public void setOccupation(int occupation) {
		if (occupation < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 职业]occupation的值不得小于0");
		}
		this.occupation = occupation;
	}
	
	public int getSkillOccupationType() {
		return this.skillOccupationType;
	}

	public void setSkillOccupationType(int skillOccupationType) {
		if (skillOccupationType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 技能职业系]skillOccupationType不可以为0");
		}
		this.skillOccupationType = skillOccupationType;
	}
	
	public int getRedInit() {
		return this.redInit;
	}

	public void setRedInit(int redInit) {
		if (redInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 红魔初始值]redInit的值不得小于0");
		}
		this.redInit = redInit;
	}
	
	public int getYellowInit() {
		return this.yellowInit;
	}

	public void setYellowInit(int yellowInit) {
		if (yellowInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 黄魔初始值]yellowInit的值不得小于0");
		}
		this.yellowInit = yellowInit;
	}
	
	public int getBlueInit() {
		return this.blueInit;
	}

	public void setBlueInit(int blueInit) {
		if (blueInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 蓝魔初始值]blueInit的值不得小于0");
		}
		this.blueInit = blueInit;
	}
	
	public int getGreenInit() {
		return this.greenInit;
	}

	public void setGreenInit(int greenInit) {
		if (greenInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 绿魔初始值]greenInit的值不得小于0");
		}
		this.greenInit = greenInit;
	}
	
	public int getPurpleInit() {
		return this.purpleInit;
	}

	public void setPurpleInit(int purpleInit) {
		if (purpleInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 紫魔初始值]purpleInit的值不得小于0");
		}
		this.purpleInit = purpleInit;
	}
	
	public int getRedMax() {
		return this.redMax;
	}

	public void setRedMax(int redMax) {
		if (redMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 红魔上限]redMax的值不得小于0");
		}
		this.redMax = redMax;
	}
	
	public int getYellowMax() {
		return this.yellowMax;
	}

	public void setYellowMax(int yellowMax) {
		if (yellowMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 黄魔上限]yellowMax的值不得小于0");
		}
		this.yellowMax = yellowMax;
	}
	
	public int getBlueMax() {
		return this.blueMax;
	}

	public void setBlueMax(int blueMax) {
		if (blueMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 蓝魔上限]blueMax的值不得小于0");
		}
		this.blueMax = blueMax;
	}
	
	public int getGreenMax() {
		return this.greenMax;
	}

	public void setGreenMax(int greenMax) {
		if (greenMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 绿魔上限]greenMax的值不得小于0");
		}
		this.greenMax = greenMax;
	}
	
	public int getPurpleMax() {
		return this.purpleMax;
	}

	public void setPurpleMax(int purpleMax) {
		if (purpleMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 紫魔上限]purpleMax的值不得小于0");
		}
		this.purpleMax = purpleMax;
	}
	
	public int getRedEliminateBonus() {
		return this.redEliminateBonus;
	}

	public void setRedEliminateBonus(int redEliminateBonus) {
		if (redEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 消除红魔加成值]redEliminateBonus的值不得小于0");
		}
		this.redEliminateBonus = redEliminateBonus;
	}
	
	public int getYellowEliminateBonus() {
		return this.yellowEliminateBonus;
	}

	public void setYellowEliminateBonus(int yellowEliminateBonus) {
		if (yellowEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 消除黄魔加成值]yellowEliminateBonus的值不得小于0");
		}
		this.yellowEliminateBonus = yellowEliminateBonus;
	}
	
	public int getBlueEliminateBonus() {
		return this.blueEliminateBonus;
	}

	public void setBlueEliminateBonus(int blueEliminateBonus) {
		if (blueEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 蓝魔黄魔加成值]blueEliminateBonus的值不得小于0");
		}
		this.blueEliminateBonus = blueEliminateBonus;
	}
	
	public int getGreenEliminateBonus() {
		return this.greenEliminateBonus;
	}

	public void setGreenEliminateBonus(int greenEliminateBonus) {
		if (greenEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 消除绿魔加成值]greenEliminateBonus的值不得小于0");
		}
		this.greenEliminateBonus = greenEliminateBonus;
	}
	
	public int getPurpleEliminateBonus() {
		return this.purpleEliminateBonus;
	}

	public void setPurpleEliminateBonus(int purpleEliminateBonus) {
		if (purpleEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 消除紫魔加成值]purpleEliminateBonus的值不得小于0");
		}
		this.purpleEliminateBonus = purpleEliminateBonus;
	}
	

	@Override
	public String toString() {
		return "SkillOccupationTemplateVO[level=" + level + ",occupation=" + occupation + ",skillOccupationType=" + skillOccupationType + ",redInit=" + redInit + ",yellowInit=" + yellowInit + ",blueInit=" + blueInit + ",greenInit=" + greenInit + ",purpleInit=" + purpleInit + ",redMax=" + redMax + ",yellowMax=" + yellowMax + ",blueMax=" + blueMax + ",greenMax=" + greenMax + ",purpleMax=" + purpleMax + ",redEliminateBonus=" + redEliminateBonus + ",yellowEliminateBonus=" + yellowEliminateBonus + ",blueEliminateBonus=" + blueEliminateBonus + ",greenEliminateBonus=" + greenEliminateBonus + ",purpleEliminateBonus=" + purpleEliminateBonus + ",]";

	}
}