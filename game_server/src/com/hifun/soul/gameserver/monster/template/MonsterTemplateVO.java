package com.hifun.soul.gameserver.monster.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 怪物模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MonsterTemplateVO extends TemplateObject {

	/**  怪物名称多语言ID */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  怪物名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  怪物描述多语言ID */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  怪物描述 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  怪物类型 */
	@ExcelCellBinding(offset = 5)
	protected int type;

	/**  怪物模型ID */
	@ExcelCellBinding(offset = 6)
	protected int modelId;

	/**  默认的怪物动作ID */
	@ExcelCellBinding(offset = 7)
	protected int defaultActionId;

	/**  怪物图片ID */
	@ExcelCellBinding(offset = 8)
	protected int picId;

	/**  怪物等级 */
	@ExcelCellBinding(offset = 9)
	protected int level;

	/**  血量 */
	@ExcelCellBinding(offset = 10)
	protected int hp;

	/**  技能攻击力 */
	@ExcelCellBinding(offset = 11)
	protected int skillAttack;

	/**  技能防御力 */
	@ExcelCellBinding(offset = 12)
	protected int skillDefense;

	/**  宝石攻击力 */
	@ExcelCellBinding(offset = 13)
	protected int gemAttack;

	/**  宝石防御力 */
	@ExcelCellBinding(offset = 14)
	protected int gemDefense;

	/**  命中力 */
	@ExcelCellBinding(offset = 15)
	protected int hit;

	/**  闪避 */
	@ExcelCellBinding(offset = 16)
	protected int dodge;

	/**  暴击 */
	@ExcelCellBinding(offset = 17)
	protected int critical;

	/**  韧性 */
	@ExcelCellBinding(offset = 18)
	protected int uncritical;

	/**  暴击伤害 */
	@ExcelCellBinding(offset = 19)
	protected int criticalDamage;

	/**  韧性伤害 */
	@ExcelCellBinding(offset = 20)
	protected int uncriticalDamage;

	/**  格挡 */
	@ExcelCellBinding(offset = 21)
	protected int parry;

	/**  破击 */
	@ExcelCellBinding(offset = 22)
	protected int unparry;

	/**  格挡伤害 */
	@ExcelCellBinding(offset = 23)
	protected int parryDamage;

	/**  破击伤害 */
	@ExcelCellBinding(offset = 24)
	protected int unparryDamage;

	/**  红魔初始值 */
	@ExcelCellBinding(offset = 25)
	protected int redInit;

	/**  黄魔初始值 */
	@ExcelCellBinding(offset = 26)
	protected int yellowInit;

	/**  蓝魔初始值 */
	@ExcelCellBinding(offset = 27)
	protected int blueInit;

	/**  绿魔初始值 */
	@ExcelCellBinding(offset = 28)
	protected int greenInit;

	/**  紫魔初始值 */
	@ExcelCellBinding(offset = 29)
	protected int purpleInit;

	/**  红魔上限 */
	@ExcelCellBinding(offset = 30)
	protected int redMax;

	/**  黄魔上限 */
	@ExcelCellBinding(offset = 31)
	protected int yellowMax;

	/**  蓝魔上限 */
	@ExcelCellBinding(offset = 32)
	protected int blueMax;

	/**  绿魔上限 */
	@ExcelCellBinding(offset = 33)
	protected int greenMax;

	/**  紫魔上限 */
	@ExcelCellBinding(offset = 34)
	protected int purpleMax;

	/**  消除红魔加成值 */
	@ExcelCellBinding(offset = 35)
	protected int redEliminateBonus;

	/**  消除黄魔加成值 */
	@ExcelCellBinding(offset = 36)
	protected int yellowEliminateBonus;

	/**  蓝魔黄魔加成值 */
	@ExcelCellBinding(offset = 37)
	protected int blueEliminateBonus;

	/**  消除绿魔加成值 */
	@ExcelCellBinding(offset = 38)
	protected int greenEliminateBonus;

	/**  消除紫魔加成值 */
	@ExcelCellBinding(offset = 39)
	protected int purpleEliminateBonus;

	/**  先攻 */
	@ExcelCellBinding(offset = 40)
	protected int firstAttack;

	/**  职业类型(1 = 战士; 2 = 游侠; 3 = 法师) */
	@ExcelCellBinding(offset = 41)
	protected int occupationType;

	/**  物理抗性 */
	@ExcelCellBinding(offset = 42)
	protected int physicalResistance;

	/**  法术抗性 */
	@ExcelCellBinding(offset = 43)
	protected int magicResistance;

	/**  普通攻击技能ID */
	@ExcelCellBinding(offset = 44)
	protected int normalAttackSkillId;

	/**  combo攻击技能ID */
	@ExcelCellBinding(offset = 45)
	protected int comboAttackSkillId;

	/**  怪物使用技能的概率 */
	@ExcelCellBinding(offset = 46)
	protected int useSkillFactor;

	/**  携带的技能列表 */
	@ExcelCollectionMapping(clazz = Integer.class, collectionNumber = "47;48;49;50;51")
	protected List<Integer> skills;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 怪物名称多语言ID]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 怪物名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 怪物描述多语言ID]descLangId不可以为0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 怪物描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 怪物类型]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getModelId() {
		return this.modelId;
	}

	public void setModelId(int modelId) {
		if (modelId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 怪物模型ID]modelId的值不得小于0");
		}
		this.modelId = modelId;
	}
	
	public int getDefaultActionId() {
		return this.defaultActionId;
	}

	public void setDefaultActionId(int defaultActionId) {
		if (defaultActionId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 默认的怪物动作ID]defaultActionId的值不得小于0");
		}
		this.defaultActionId = defaultActionId;
	}
	
	public int getPicId() {
		return this.picId;
	}

	public void setPicId(int picId) {
		if (picId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 怪物图片ID]picId的值不得小于0");
		}
		this.picId = picId;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 怪物等级]level的值不得小于1");
		}
		this.level = level;
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		if (hp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 血量]hp的值不得小于0");
		}
		this.hp = hp;
	}
	
	public int getSkillAttack() {
		return this.skillAttack;
	}

	public void setSkillAttack(int skillAttack) {
		if (skillAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 技能攻击力]skillAttack的值不得小于0");
		}
		this.skillAttack = skillAttack;
	}
	
	public int getSkillDefense() {
		return this.skillDefense;
	}

	public void setSkillDefense(int skillDefense) {
		if (skillDefense < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 技能防御力]skillDefense的值不得小于0");
		}
		this.skillDefense = skillDefense;
	}
	
	public int getGemAttack() {
		return this.gemAttack;
	}

	public void setGemAttack(int gemAttack) {
		if (gemAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 宝石攻击力]gemAttack的值不得小于0");
		}
		this.gemAttack = gemAttack;
	}
	
	public int getGemDefense() {
		return this.gemDefense;
	}

	public void setGemDefense(int gemDefense) {
		if (gemDefense < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 宝石防御力]gemDefense的值不得小于0");
		}
		this.gemDefense = gemDefense;
	}
	
	public int getHit() {
		return this.hit;
	}

	public void setHit(int hit) {
		if (hit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 命中力]hit的值不得小于0");
		}
		this.hit = hit;
	}
	
	public int getDodge() {
		return this.dodge;
	}

	public void setDodge(int dodge) {
		if (dodge < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 闪避]dodge的值不得小于0");
		}
		this.dodge = dodge;
	}
	
	public int getCritical() {
		return this.critical;
	}

	public void setCritical(int critical) {
		if (critical < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 暴击]critical的值不得小于0");
		}
		this.critical = critical;
	}
	
	public int getUncritical() {
		return this.uncritical;
	}

	public void setUncritical(int uncritical) {
		if (uncritical < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 韧性]uncritical的值不得小于0");
		}
		this.uncritical = uncritical;
	}
	
	public int getCriticalDamage() {
		return this.criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		if (criticalDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 暴击伤害]criticalDamage的值不得小于0");
		}
		this.criticalDamage = criticalDamage;
	}
	
	public int getUncriticalDamage() {
		return this.uncriticalDamage;
	}

	public void setUncriticalDamage(int uncriticalDamage) {
		if (uncriticalDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 韧性伤害]uncriticalDamage的值不得小于0");
		}
		this.uncriticalDamage = uncriticalDamage;
	}
	
	public int getParry() {
		return this.parry;
	}

	public void setParry(int parry) {
		if (parry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[ 格挡]parry的值不得小于0");
		}
		this.parry = parry;
	}
	
	public int getUnparry() {
		return this.unparry;
	}

	public void setUnparry(int unparry) {
		if (unparry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[ 破击]unparry的值不得小于0");
		}
		this.unparry = unparry;
	}
	
	public int getParryDamage() {
		return this.parryDamage;
	}

	public void setParryDamage(int parryDamage) {
		if (parryDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[ 格挡伤害]parryDamage的值不得小于0");
		}
		this.parryDamage = parryDamage;
	}
	
	public int getUnparryDamage() {
		return this.unparryDamage;
	}

	public void setUnparryDamage(int unparryDamage) {
		if (unparryDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[ 破击伤害]unparryDamage的值不得小于0");
		}
		this.unparryDamage = unparryDamage;
	}
	
	public int getRedInit() {
		return this.redInit;
	}

	public void setRedInit(int redInit) {
		if (redInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[ 红魔初始值]redInit的值不得小于0");
		}
		this.redInit = redInit;
	}
	
	public int getYellowInit() {
		return this.yellowInit;
	}

	public void setYellowInit(int yellowInit) {
		if (yellowInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[ 黄魔初始值]yellowInit的值不得小于0");
		}
		this.yellowInit = yellowInit;
	}
	
	public int getBlueInit() {
		return this.blueInit;
	}

	public void setBlueInit(int blueInit) {
		if (blueInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[ 蓝魔初始值]blueInit的值不得小于0");
		}
		this.blueInit = blueInit;
	}
	
	public int getGreenInit() {
		return this.greenInit;
	}

	public void setGreenInit(int greenInit) {
		if (greenInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					29, "[ 绿魔初始值]greenInit的值不得小于0");
		}
		this.greenInit = greenInit;
	}
	
	public int getPurpleInit() {
		return this.purpleInit;
	}

	public void setPurpleInit(int purpleInit) {
		if (purpleInit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					30, "[ 紫魔初始值]purpleInit的值不得小于0");
		}
		this.purpleInit = purpleInit;
	}
	
	public int getRedMax() {
		return this.redMax;
	}

	public void setRedMax(int redMax) {
		if (redMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					31, "[ 红魔上限]redMax的值不得小于0");
		}
		this.redMax = redMax;
	}
	
	public int getYellowMax() {
		return this.yellowMax;
	}

	public void setYellowMax(int yellowMax) {
		if (yellowMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					32, "[ 黄魔上限]yellowMax的值不得小于0");
		}
		this.yellowMax = yellowMax;
	}
	
	public int getBlueMax() {
		return this.blueMax;
	}

	public void setBlueMax(int blueMax) {
		if (blueMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[ 蓝魔上限]blueMax的值不得小于0");
		}
		this.blueMax = blueMax;
	}
	
	public int getGreenMax() {
		return this.greenMax;
	}

	public void setGreenMax(int greenMax) {
		if (greenMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[ 绿魔上限]greenMax的值不得小于0");
		}
		this.greenMax = greenMax;
	}
	
	public int getPurpleMax() {
		return this.purpleMax;
	}

	public void setPurpleMax(int purpleMax) {
		if (purpleMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[ 紫魔上限]purpleMax的值不得小于0");
		}
		this.purpleMax = purpleMax;
	}
	
	public int getRedEliminateBonus() {
		return this.redEliminateBonus;
	}

	public void setRedEliminateBonus(int redEliminateBonus) {
		if (redEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[ 消除红魔加成值]redEliminateBonus的值不得小于0");
		}
		this.redEliminateBonus = redEliminateBonus;
	}
	
	public int getYellowEliminateBonus() {
		return this.yellowEliminateBonus;
	}

	public void setYellowEliminateBonus(int yellowEliminateBonus) {
		if (yellowEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[ 消除黄魔加成值]yellowEliminateBonus的值不得小于0");
		}
		this.yellowEliminateBonus = yellowEliminateBonus;
	}
	
	public int getBlueEliminateBonus() {
		return this.blueEliminateBonus;
	}

	public void setBlueEliminateBonus(int blueEliminateBonus) {
		if (blueEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					38, "[ 蓝魔黄魔加成值]blueEliminateBonus的值不得小于0");
		}
		this.blueEliminateBonus = blueEliminateBonus;
	}
	
	public int getGreenEliminateBonus() {
		return this.greenEliminateBonus;
	}

	public void setGreenEliminateBonus(int greenEliminateBonus) {
		if (greenEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[ 消除绿魔加成值]greenEliminateBonus的值不得小于0");
		}
		this.greenEliminateBonus = greenEliminateBonus;
	}
	
	public int getPurpleEliminateBonus() {
		return this.purpleEliminateBonus;
	}

	public void setPurpleEliminateBonus(int purpleEliminateBonus) {
		if (purpleEliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[ 消除紫魔加成值]purpleEliminateBonus的值不得小于0");
		}
		this.purpleEliminateBonus = purpleEliminateBonus;
	}
	
	public int getFirstAttack() {
		return this.firstAttack;
	}

	public void setFirstAttack(int firstAttack) {
		if (firstAttack < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[ 先攻]firstAttack的值不得小于0");
		}
		this.firstAttack = firstAttack;
	}
	
	public int getOccupationType() {
		return this.occupationType;
	}

	public void setOccupationType(int occupationType) {
		if (occupationType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					42, "[ 职业类型(1 = 战士; 2 = 游侠; 3 = 法师)]occupationType不可以为0");
		}
		this.occupationType = occupationType;
	}
	
	public int getPhysicalResistance() {
		return this.physicalResistance;
	}

	public void setPhysicalResistance(int physicalResistance) {
		if (physicalResistance < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					43, "[ 物理抗性]physicalResistance的值不得小于0");
		}
		this.physicalResistance = physicalResistance;
	}
	
	public int getMagicResistance() {
		return this.magicResistance;
	}

	public void setMagicResistance(int magicResistance) {
		if (magicResistance < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					44, "[ 法术抗性]magicResistance的值不得小于0");
		}
		this.magicResistance = magicResistance;
	}
	
	public int getNormalAttackSkillId() {
		return this.normalAttackSkillId;
	}

	public void setNormalAttackSkillId(int normalAttackSkillId) {
		if (normalAttackSkillId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					45, "[ 普通攻击技能ID]normalAttackSkillId不可以为0");
		}
		this.normalAttackSkillId = normalAttackSkillId;
	}
	
	public int getComboAttackSkillId() {
		return this.comboAttackSkillId;
	}

	public void setComboAttackSkillId(int comboAttackSkillId) {
		if (comboAttackSkillId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					46, "[ combo攻击技能ID]comboAttackSkillId不可以为0");
		}
		this.comboAttackSkillId = comboAttackSkillId;
	}
	
	public int getUseSkillFactor() {
		return this.useSkillFactor;
	}

	public void setUseSkillFactor(int useSkillFactor) {
		if (useSkillFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					47, "[ 怪物使用技能的概率]useSkillFactor的值不得小于0");
		}
		this.useSkillFactor = useSkillFactor;
	}
	
	public List<Integer> getSkills() {
		return this.skills;
	}

	public void setSkills(List<Integer> skills) {
		if (skills == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					48, "[ 携带的技能列表]skills不可以为空");
		}	
		this.skills = skills;
	}
	

	@Override
	public String toString() {
		return "MonsterTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",type=" + type + ",modelId=" + modelId + ",defaultActionId=" + defaultActionId + ",picId=" + picId + ",level=" + level + ",hp=" + hp + ",skillAttack=" + skillAttack + ",skillDefense=" + skillDefense + ",gemAttack=" + gemAttack + ",gemDefense=" + gemDefense + ",hit=" + hit + ",dodge=" + dodge + ",critical=" + critical + ",uncritical=" + uncritical + ",criticalDamage=" + criticalDamage + ",uncriticalDamage=" + uncriticalDamage + ",parry=" + parry + ",unparry=" + unparry + ",parryDamage=" + parryDamage + ",unparryDamage=" + unparryDamage + ",redInit=" + redInit + ",yellowInit=" + yellowInit + ",blueInit=" + blueInit + ",greenInit=" + greenInit + ",purpleInit=" + purpleInit + ",redMax=" + redMax + ",yellowMax=" + yellowMax + ",blueMax=" + blueMax + ",greenMax=" + greenMax + ",purpleMax=" + purpleMax + ",redEliminateBonus=" + redEliminateBonus + ",yellowEliminateBonus=" + yellowEliminateBonus + ",blueEliminateBonus=" + blueEliminateBonus + ",greenEliminateBonus=" + greenEliminateBonus + ",purpleEliminateBonus=" + purpleEliminateBonus + ",firstAttack=" + firstAttack + ",occupationType=" + occupationType + ",physicalResistance=" + physicalResistance + ",magicResistance=" + magicResistance + ",normalAttackSkillId=" + normalAttackSkillId + ",comboAttackSkillId=" + comboAttackSkillId + ",useSkillFactor=" + useSkillFactor + ",skills=" + skills + ",]";

	}
}