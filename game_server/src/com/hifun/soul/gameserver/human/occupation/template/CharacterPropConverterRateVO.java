package com.hifun.soul.gameserver.human.occupation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 角色职业一二级属性转化率
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CharacterPropConverterRateVO extends TemplateObject {

	/**  职业 */
	@ExcelCellBinding(offset = 1)
	protected int occupation;

	/**  一级属性索引ID */
	@ExcelCellBinding(offset = 2)
	protected int level1PropIndex;

	/**  一级属性名称 */
	@ExcelCellBinding(offset = 3)
	protected String level1PropName;

	/**  到hp的转化; */
	@ExcelCellBinding(offset = 4)
	protected int hp;

	/**  到技能攻击力的转化 */
	@ExcelCellBinding(offset = 5)
	protected int skillAttack;

	/**  到技能防御力的转化 */
	@ExcelCellBinding(offset = 6)
	protected int skillDefense;

	/**  到宝石攻击力的转化 */
	@ExcelCellBinding(offset = 7)
	protected int gemAttack;

	/**  到宝石防御力的转化 */
	@ExcelCellBinding(offset = 8)
	protected int gemDefense;

	/**  到命中的转化 */
	@ExcelCellBinding(offset = 9)
	protected int hit;

	/**  到闪避的转化 */
	@ExcelCellBinding(offset = 10)
	protected int dodge;

	/**  到暴击的转化 */
	@ExcelCellBinding(offset = 11)
	protected int critical;

	/**  到韧性的转化 */
	@ExcelCellBinding(offset = 12)
	protected int uncritical;

	/**  到暴击伤害的转化 */
	@ExcelCellBinding(offset = 13)
	protected int criticalDamage;

	/**  到韧性伤害的转化 */
	@ExcelCellBinding(offset = 14)
	protected int uncriticalDamage;

	/**  到格挡的转化 */
	@ExcelCellBinding(offset = 15)
	protected int parry;

	/**  到格挡伤害的转化 */
	@ExcelCellBinding(offset = 16)
	protected int parryDamage;

	/**  到破击的转化 */
	@ExcelCellBinding(offset = 17)
	protected int unparry;

	/**  到破击伤害的转化 */
	@ExcelCellBinding(offset = 18)
	protected int unparryDamage;

	/**  到先攻的转化 */
	@ExcelCellBinding(offset = 19)
	protected int firstAttack;


	public int getOccupation() {
		return this.occupation;
	}

	public void setOccupation(int occupation) {
		if (occupation < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 职业]occupation的值不得小于0");
		}
		this.occupation = occupation;
	}
	
	public int getLevel1PropIndex() {
		return this.level1PropIndex;
	}

	public void setLevel1PropIndex(int level1PropIndex) {
		if (level1PropIndex < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 一级属性索引ID]level1PropIndex的值不得小于0");
		}
		this.level1PropIndex = level1PropIndex;
	}
	
	public String getLevel1PropName() {
		return this.level1PropName;
	}

	public void setLevel1PropName(String level1PropName) {
		if (StringUtils.isEmpty(level1PropName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 一级属性名称]level1PropName不可以为空");
		}
		this.level1PropName = level1PropName;
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		if (hp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 到hp的转化;]hp的值不得小于0");
		}
		this.hp = hp;
	}
	
	public int getSkillAttack() {
		return this.skillAttack;
	}

	public void setSkillAttack(int skillAttack) {
		if (skillAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 到技能攻击力的转化]skillAttack的值不得小于0");
		}
		this.skillAttack = skillAttack;
	}
	
	public int getSkillDefense() {
		return this.skillDefense;
	}

	public void setSkillDefense(int skillDefense) {
		if (skillDefense < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 到技能防御力的转化]skillDefense的值不得小于0");
		}
		this.skillDefense = skillDefense;
	}
	
	public int getGemAttack() {
		return this.gemAttack;
	}

	public void setGemAttack(int gemAttack) {
		if (gemAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 到宝石攻击力的转化]gemAttack的值不得小于0");
		}
		this.gemAttack = gemAttack;
	}
	
	public int getGemDefense() {
		return this.gemDefense;
	}

	public void setGemDefense(int gemDefense) {
		if (gemDefense < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 到宝石防御力的转化]gemDefense的值不得小于0");
		}
		this.gemDefense = gemDefense;
	}
	
	public int getHit() {
		return this.hit;
	}

	public void setHit(int hit) {
		if (hit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 到命中的转化]hit的值不得小于0");
		}
		this.hit = hit;
	}
	
	public int getDodge() {
		return this.dodge;
	}

	public void setDodge(int dodge) {
		if (dodge < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 到闪避的转化]dodge的值不得小于0");
		}
		this.dodge = dodge;
	}
	
	public int getCritical() {
		return this.critical;
	}

	public void setCritical(int critical) {
		if (critical < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 到暴击的转化]critical的值不得小于0");
		}
		this.critical = critical;
	}
	
	public int getUncritical() {
		return this.uncritical;
	}

	public void setUncritical(int uncritical) {
		if (uncritical < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 到韧性的转化]uncritical的值不得小于0");
		}
		this.uncritical = uncritical;
	}
	
	public int getCriticalDamage() {
		return this.criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		if (criticalDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 到暴击伤害的转化]criticalDamage的值不得小于0");
		}
		this.criticalDamage = criticalDamage;
	}
	
	public int getUncriticalDamage() {
		return this.uncriticalDamage;
	}

	public void setUncriticalDamage(int uncriticalDamage) {
		if (uncriticalDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 到韧性伤害的转化]uncriticalDamage的值不得小于0");
		}
		this.uncriticalDamage = uncriticalDamage;
	}
	
	public int getParry() {
		return this.parry;
	}

	public void setParry(int parry) {
		if (parry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 到格挡的转化]parry的值不得小于0");
		}
		this.parry = parry;
	}
	
	public int getParryDamage() {
		return this.parryDamage;
	}

	public void setParryDamage(int parryDamage) {
		if (parryDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 到格挡伤害的转化]parryDamage的值不得小于0");
		}
		this.parryDamage = parryDamage;
	}
	
	public int getUnparry() {
		return this.unparry;
	}

	public void setUnparry(int unparry) {
		if (unparry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 到破击的转化]unparry的值不得小于0");
		}
		this.unparry = unparry;
	}
	
	public int getUnparryDamage() {
		return this.unparryDamage;
	}

	public void setUnparryDamage(int unparryDamage) {
		if (unparryDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 到破击伤害的转化]unparryDamage的值不得小于0");
		}
		this.unparryDamage = unparryDamage;
	}
	
	public int getFirstAttack() {
		return this.firstAttack;
	}

	public void setFirstAttack(int firstAttack) {
		if (firstAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 到先攻的转化]firstAttack的值不得小于0");
		}
		this.firstAttack = firstAttack;
	}
	

	@Override
	public String toString() {
		return "CharacterPropConverterRateVO[occupation=" + occupation + ",level1PropIndex=" + level1PropIndex + ",level1PropName=" + level1PropName + ",hp=" + hp + ",skillAttack=" + skillAttack + ",skillDefense=" + skillDefense + ",gemAttack=" + gemAttack + ",gemDefense=" + gemDefense + ",hit=" + hit + ",dodge=" + dodge + ",critical=" + critical + ",uncritical=" + uncritical + ",criticalDamage=" + criticalDamage + ",uncriticalDamage=" + uncriticalDamage + ",parry=" + parry + ",parryDamage=" + parryDamage + ",unparry=" + unparry + ",unparryDamage=" + unparryDamage + ",firstAttack=" + firstAttack + ",]";

	}
}