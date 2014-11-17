package com.hifun.soul.gameserver.human.occupation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 角色职业基础属性模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CharacterOccupationInitPropVO extends TemplateObject {

	/**  力量 */
	@ExcelCellBinding(offset = 1)
	protected int power;

	/**  敏捷 */
	@ExcelCellBinding(offset = 2)
	protected int agile;

	/**  体力 */
	@ExcelCellBinding(offset = 3)
	protected int stamina;

	/**  智力 */
	@ExcelCellBinding(offset = 4)
	protected int intelligence;

	/**  精神 */
	@ExcelCellBinding(offset = 5)
	protected int spirit;

	/**  血量 */
	@ExcelCellBinding(offset = 6)
	protected int hp;

	/**  技能攻击力 */
	@ExcelCellBinding(offset = 7)
	protected int skillAttack;

	/**  技能防御力 */
	@ExcelCellBinding(offset = 8)
	protected int skillDefense;

	/**  宝石攻击力 */
	@ExcelCellBinding(offset = 9)
	protected int gemAttack;

	/**  宝石防御力 */
	@ExcelCellBinding(offset = 10)
	protected int gemDefense;

	/**  命中力 */
	@ExcelCellBinding(offset = 11)
	protected int hit;

	/**  闪避 */
	@ExcelCellBinding(offset = 12)
	protected int dodge;

	/**  暴击 */
	@ExcelCellBinding(offset = 13)
	protected int critical;

	/**  韧性 */
	@ExcelCellBinding(offset = 14)
	protected int uncritical;

	/**  暴击伤害 */
	@ExcelCellBinding(offset = 15)
	protected int criticalDamage;

	/**  韧性伤害 */
	@ExcelCellBinding(offset = 16)
	protected int uncriticalDamage;

	/**  格挡 */
	@ExcelCellBinding(offset = 17)
	protected int parry;

	/**  格挡伤害 */
	@ExcelCellBinding(offset = 18)
	protected int parryDamage;

	/**  破击 */
	@ExcelCellBinding(offset = 19)
	protected int unparry;

	/**  破击伤害 */
	@ExcelCellBinding(offset = 20)
	protected int unparryDamage;

	/**  红魔初始值 */
	@ExcelCellBinding(offset = 21)
	protected int redInit;

	/**  黄魔初始值 */
	@ExcelCellBinding(offset = 22)
	protected int yellowInit;

	/**  蓝魔初始值 */
	@ExcelCellBinding(offset = 23)
	protected int blueInit;

	/**  绿魔初始值 */
	@ExcelCellBinding(offset = 24)
	protected int greenInit;

	/**  紫魔初始值 */
	@ExcelCellBinding(offset = 25)
	protected int purpleInit;

	/**  红魔上限 */
	@ExcelCellBinding(offset = 26)
	protected int redMax;

	/**  黄魔上限 */
	@ExcelCellBinding(offset = 27)
	protected int yellowMax;

	/**  蓝魔上限 */
	@ExcelCellBinding(offset = 28)
	protected int blueMax;

	/**  绿魔上限 */
	@ExcelCellBinding(offset = 29)
	protected int greenMax;

	/**  紫魔上限 */
	@ExcelCellBinding(offset = 30)
	protected int purpleMax;

	/**  消除红魔加成值 */
	@ExcelCellBinding(offset = 31)
	protected int redEliminateBonus;

	/**  消除黄魔加成值 */
	@ExcelCellBinding(offset = 32)
	protected int yellowEliminateBonus;

	/**  蓝魔黄魔加成值 */
	@ExcelCellBinding(offset = 33)
	protected int blueEliminateBonus;

	/**  消除绿魔加成值 */
	@ExcelCellBinding(offset = 34)
	protected int greenEliminateBonus;

	/**  消除紫魔加成值 */
	@ExcelCellBinding(offset = 35)
	protected int purpleEliminateBonus;

	/**  先攻 */
	@ExcelCellBinding(offset = 36)
	protected int firstAttack;

	/**  物理抗性 */
	@ExcelCellBinding(offset = 37)
	protected int physicalResistance;

	/**  法术抗性 */
	@ExcelCellBinding(offset = 38)
	protected int magicResistance;


	public int getPower() {
		return this.power;
	}

	public void setPower(int power) {
		if (power < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 力量]power的值不得小于0");
		}
		this.power = power;
	}
	
	public int getAgile() {
		return this.agile;
	}

	public void setAgile(int agile) {
		if (agile < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 敏捷]agile的值不得小于0");
		}
		this.agile = agile;
	}
	
	public int getStamina() {
		return this.stamina;
	}

	public void setStamina(int stamina) {
		if (stamina < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 体力]stamina的值不得小于0");
		}
		this.stamina = stamina;
	}
	
	public int getIntelligence() {
		return this.intelligence;
	}

	public void setIntelligence(int intelligence) {
		if (intelligence < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 智力]intelligence的值不得小于0");
		}
		this.intelligence = intelligence;
	}
	
	public int getSpirit() {
		return this.spirit;
	}

	public void setSpirit(int spirit) {
		if (spirit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 精神]spirit的值不得小于0");
		}
		this.spirit = spirit;
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		if (hp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 血量]hp的值不得小于0");
		}
		this.hp = hp;
	}
	
	public int getSkillAttack() {
		return this.skillAttack;
	}

	public void setSkillAttack(int skillAttack) {
		if (skillAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 技能攻击力]skillAttack的值不得小于0");
		}
		this.skillAttack = skillAttack;
	}
	
	public int getSkillDefense() {
		return this.skillDefense;
	}

	public void setSkillDefense(int skillDefense) {
		if (skillDefense < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 技能防御力]skillDefense的值不得小于0");
		}
		this.skillDefense = skillDefense;
	}
	
	public int getGemAttack() {
		return this.gemAttack;
	}

	public void setGemAttack(int gemAttack) {
		if (gemAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 宝石攻击力]gemAttack的值不得小于0");
		}
		this.gemAttack = gemAttack;
	}
	
	public int getGemDefense() {
		return this.gemDefense;
	}

	public void setGemDefense(int gemDefense) {
		if (gemDefense < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 宝石防御力]gemDefense的值不得小于0");
		}
		this.gemDefense = gemDefense;
	}
	
	public int getHit() {
		return this.hit;
	}

	public void setHit(int hit) {
		if (hit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 命中力]hit的值不得小于0");
		}
		this.hit = hit;
	}
	
	public int getDodge() {
		return this.dodge;
	}

	public void setDodge(int dodge) {
		if (dodge < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 闪避]dodge的值不得小于0");
		}
		this.dodge = dodge;
	}
	
	public int getCritical() {
		return this.critical;
	}

	public void setCritical(int critical) {
		if (critical < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 暴击]critical的值不得小于0");
		}
		this.critical = critical;
	}
	
	public int getUncritical() {
		return this.uncritical;
	}

	public void setUncritical(int uncritical) {
		if (uncritical < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 韧性]uncritical的值不得小于0");
		}
		this.uncritical = uncritical;
	}
	
	public int getCriticalDamage() {
		return this.criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		if (criticalDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 暴击伤害]criticalDamage的值不得小于0");
		}
		this.criticalDamage = criticalDamage;
	}
	
	public int getUncriticalDamage() {
		return this.uncriticalDamage;
	}

	public void setUncriticalDamage(int uncriticalDamage) {
		if (uncriticalDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 韧性伤害]uncriticalDamage的值不得小于0");
		}
		this.uncriticalDamage = uncriticalDamage;
	}
	
	public int getParry() {
		return this.parry;
	}

	public void setParry(int parry) {
		if (parry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 格挡]parry的值不得小于0");
		}
		this.parry = parry;
	}
	
	public int getParryDamage() {
		return this.parryDamage;
	}

	public void setParryDamage(int parryDamage) {
		if (parryDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 格挡伤害]parryDamage的值不得小于0");
		}
		this.parryDamage = parryDamage;
	}
	
	public int getUnparry() {
		return this.unparry;
	}

	public void setUnparry(int unparry) {
		if (unparry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 破击]unparry的值不得小于0");
		}
		this.unparry = unparry;
	}
	
	public int getUnparryDamage() {
		return this.unparryDamage;
	}

	public void setUnparryDamage(int unparryDamage) {
		if (unparryDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 破击伤害]unparryDamage的值不得小于0");
		}
		this.unparryDamage = unparryDamage;
	}
	
	public int getRedInit() {
		return this.redInit;
	}

	public void setRedInit(int redInit) {
		if (redInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[ 红魔初始值]redInit的值不得小于0");
		}
		this.redInit = redInit;
	}
	
	public int getYellowInit() {
		return this.yellowInit;
	}

	public void setYellowInit(int yellowInit) {
		if (yellowInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[ 黄魔初始值]yellowInit的值不得小于0");
		}
		this.yellowInit = yellowInit;
	}
	
	public int getBlueInit() {
		return this.blueInit;
	}

	public void setBlueInit(int blueInit) {
		if (blueInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[ 蓝魔初始值]blueInit的值不得小于0");
		}
		this.blueInit = blueInit;
	}
	
	public int getGreenInit() {
		return this.greenInit;
	}

	public void setGreenInit(int greenInit) {
		if (greenInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[ 绿魔初始值]greenInit的值不得小于0");
		}
		this.greenInit = greenInit;
	}
	
	public int getPurpleInit() {
		return this.purpleInit;
	}

	public void setPurpleInit(int purpleInit) {
		if (purpleInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[ 紫魔初始值]purpleInit的值不得小于0");
		}
		this.purpleInit = purpleInit;
	}
	
	public int getRedMax() {
		return this.redMax;
	}

	public void setRedMax(int redMax) {
		if (redMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[ 红魔上限]redMax的值不得小于0");
		}
		this.redMax = redMax;
	}
	
	public int getYellowMax() {
		return this.yellowMax;
	}

	public void setYellowMax(int yellowMax) {
		if (yellowMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[ 黄魔上限]yellowMax的值不得小于0");
		}
		this.yellowMax = yellowMax;
	}
	
	public int getBlueMax() {
		return this.blueMax;
	}

	public void setBlueMax(int blueMax) {
		if (blueMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					29, "[ 蓝魔上限]blueMax的值不得小于0");
		}
		this.blueMax = blueMax;
	}
	
	public int getGreenMax() {
		return this.greenMax;
	}

	public void setGreenMax(int greenMax) {
		if (greenMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					30, "[ 绿魔上限]greenMax的值不得小于0");
		}
		this.greenMax = greenMax;
	}
	
	public int getPurpleMax() {
		return this.purpleMax;
	}

	public void setPurpleMax(int purpleMax) {
		if (purpleMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					31, "[ 紫魔上限]purpleMax的值不得小于0");
		}
		this.purpleMax = purpleMax;
	}
	
	public int getRedEliminateBonus() {
		return this.redEliminateBonus;
	}

	public void setRedEliminateBonus(int redEliminateBonus) {
		if (redEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					32, "[ 消除红魔加成值]redEliminateBonus的值不得小于0");
		}
		this.redEliminateBonus = redEliminateBonus;
	}
	
	public int getYellowEliminateBonus() {
		return this.yellowEliminateBonus;
	}

	public void setYellowEliminateBonus(int yellowEliminateBonus) {
		if (yellowEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[ 消除黄魔加成值]yellowEliminateBonus的值不得小于0");
		}
		this.yellowEliminateBonus = yellowEliminateBonus;
	}
	
	public int getBlueEliminateBonus() {
		return this.blueEliminateBonus;
	}

	public void setBlueEliminateBonus(int blueEliminateBonus) {
		if (blueEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[ 蓝魔黄魔加成值]blueEliminateBonus的值不得小于0");
		}
		this.blueEliminateBonus = blueEliminateBonus;
	}
	
	public int getGreenEliminateBonus() {
		return this.greenEliminateBonus;
	}

	public void setGreenEliminateBonus(int greenEliminateBonus) {
		if (greenEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[ 消除绿魔加成值]greenEliminateBonus的值不得小于0");
		}
		this.greenEliminateBonus = greenEliminateBonus;
	}
	
	public int getPurpleEliminateBonus() {
		return this.purpleEliminateBonus;
	}

	public void setPurpleEliminateBonus(int purpleEliminateBonus) {
		if (purpleEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[ 消除紫魔加成值]purpleEliminateBonus的值不得小于0");
		}
		this.purpleEliminateBonus = purpleEliminateBonus;
	}
	
	public int getFirstAttack() {
		return this.firstAttack;
	}

	public void setFirstAttack(int firstAttack) {
		if (firstAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[ 先攻]firstAttack的值不得小于0");
		}
		this.firstAttack = firstAttack;
	}
	
	public int getPhysicalResistance() {
		return this.physicalResistance;
	}

	public void setPhysicalResistance(int physicalResistance) {
		if (physicalResistance < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					38, "[ 物理抗性]physicalResistance的值不得小于0");
		}
		this.physicalResistance = physicalResistance;
	}
	
	public int getMagicResistance() {
		return this.magicResistance;
	}

	public void setMagicResistance(int magicResistance) {
		if (magicResistance < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[ 法术抗性]magicResistance的值不得小于0");
		}
		this.magicResistance = magicResistance;
	}
	

	@Override
	public String toString() {
		return "CharacterOccupationInitPropVO[power=" + power + ",agile=" + agile + ",stamina=" + stamina + ",intelligence=" + intelligence + ",spirit=" + spirit + ",hp=" + hp + ",skillAttack=" + skillAttack + ",skillDefense=" + skillDefense + ",gemAttack=" + gemAttack + ",gemDefense=" + gemDefense + ",hit=" + hit + ",dodge=" + dodge + ",critical=" + critical + ",uncritical=" + uncritical + ",criticalDamage=" + criticalDamage + ",uncriticalDamage=" + uncriticalDamage + ",parry=" + parry + ",parryDamage=" + parryDamage + ",unparry=" + unparry + ",unparryDamage=" + unparryDamage + ",redInit=" + redInit + ",yellowInit=" + yellowInit + ",blueInit=" + blueInit + ",greenInit=" + greenInit + ",purpleInit=" + purpleInit + ",redMax=" + redMax + ",yellowMax=" + yellowMax + ",blueMax=" + blueMax + ",greenMax=" + greenMax + ",purpleMax=" + purpleMax + ",redEliminateBonus=" + redEliminateBonus + ",yellowEliminateBonus=" + yellowEliminateBonus + ",blueEliminateBonus=" + blueEliminateBonus + ",greenEliminateBonus=" + greenEliminateBonus + ",purpleEliminateBonus=" + purpleEliminateBonus + ",firstAttack=" + firstAttack + ",physicalResistance=" + physicalResistance + ",magicResistance=" + magicResistance + ",]";

	}
}