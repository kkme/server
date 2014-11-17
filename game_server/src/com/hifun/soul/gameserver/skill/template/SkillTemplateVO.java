package com.hifun.soul.gameserver.skill.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 技能
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SkillTemplateVO extends TemplateObject {

	/**  技能名称 */
	@ExcelCellBinding(offset = 1)
	protected String skillName;

	/**  技能描述 */
	@ExcelCellBinding(offset = 2)
	protected String skillDesc;

	/**  技能类型(普攻,combo,其它) */
	@ExcelCellBinding(offset = 3)
	protected int skillType;

	/**  攻击类型(近战, 远程) */
	@ExcelCellBinding(offset = 4)
	protected int attackType;

	/**  技能开放等级 */
	@ExcelCellBinding(offset = 5)
	protected int openLevel;

	/**  所属的角色;是玩家还是怪 */
	@ExcelCellBinding(offset = 6)
	protected int hostType;

	/**  所属职业 */
	@ExcelCellBinding(offset = 7)
	protected int occupation;

	/**  使用技能后;回合是否结束 */
	@ExcelCellBinding(offset = 8)
	protected int useRoundOver;

	/**  冷却回合 */
	@ExcelCellBinding(offset = 9)
	protected int cooldownRound;

	/**  红消耗 */
	@ExcelCellBinding(offset = 10)
	protected int redCost;

	/**  黄消耗 */
	@ExcelCellBinding(offset = 11)
	protected int yellowCost;

	/**  蓝消耗 */
	@ExcelCellBinding(offset = 12)
	protected int blueCost;

	/**  绿消耗 */
	@ExcelCellBinding(offset = 13)
	protected int greenCost;

	/**  紫消耗 */
	@ExcelCellBinding(offset = 14)
	protected int purpleCost;

	/**  技能音效 */
	@ExcelCellBinding(offset = 15)
	protected int skillSound;

	/**  技能图标 */
	@ExcelCellBinding(offset = 16)
	protected int skillIcon;

	/**  技能释放是否需要点选宝石 */
	@ExcelCellBinding(offset = 17)
	protected int needSelectedGem;

	/**  技能动作ID */
	@ExcelCellBinding(offset = 18)
	protected int skillActionId;

	/**  飞行特效ID */
	@ExcelCellBinding(offset = 19)
	protected int flyEffectId;

	/**  自己受击特效ID */
	@ExcelCellBinding(offset = 20)
	protected int beHitedEffectId;

	/**  地方受击特效ID */
	@ExcelCellBinding(offset = 21)
	protected int enemyBeHitedEffectId;

	/**  技能效果列表 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.skill.template.SkillEffectTemplate.class, collectionNumber = "22,23,24,25,26,27,28;29,30,31,32,33,34,35")
	protected List<com.hifun.soul.gameserver.skill.template.SkillEffectTemplate> effects;

	/**  第二效果是否可以被闪避 */
	@ExcelCellBinding(offset = 36)
	protected int secondEffectDodgeType;


	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		if (StringUtils.isEmpty(skillName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 技能名称]skillName不可以为空");
		}
		this.skillName = skillName;
	}
	
	public String getSkillDesc() {
		return this.skillDesc;
	}

	public void setSkillDesc(String skillDesc) {
		if (StringUtils.isEmpty(skillDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 技能描述]skillDesc不可以为空");
		}
		this.skillDesc = skillDesc;
	}
	
	public int getSkillType() {
		return this.skillType;
	}

	public void setSkillType(int skillType) {
		if (skillType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 技能类型(普攻,combo,其它)]skillType不可以为0");
		}
		this.skillType = skillType;
	}
	
	public int getAttackType() {
		return this.attackType;
	}

	public void setAttackType(int attackType) {
		if (attackType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 攻击类型(近战, 远程)]attackType不可以为0");
		}
		this.attackType = attackType;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 技能开放等级]openLevel的值不得小于1");
		}
		this.openLevel = openLevel;
	}
	
	public int getHostType() {
		return this.hostType;
	}

	public void setHostType(int hostType) {
		if (hostType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 所属的角色;是玩家还是怪]hostType不可以为0");
		}
		this.hostType = hostType;
	}
	
	public int getOccupation() {
		return this.occupation;
	}

	public void setOccupation(int occupation) {
		if (occupation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 所属职业]occupation不可以为0");
		}
		this.occupation = occupation;
	}
	
	public int getUseRoundOver() {
		return this.useRoundOver;
	}

	public void setUseRoundOver(int useRoundOver) {
		if (useRoundOver < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 使用技能后;回合是否结束]useRoundOver的值不得小于0");
		}
		this.useRoundOver = useRoundOver;
	}
	
	public int getCooldownRound() {
		return this.cooldownRound;
	}

	public void setCooldownRound(int cooldownRound) {
		if (cooldownRound < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 冷却回合]cooldownRound的值不得小于1");
		}
		this.cooldownRound = cooldownRound;
	}
	
	public int getRedCost() {
		return this.redCost;
	}

	public void setRedCost(int redCost) {
		if (redCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 红消耗]redCost的值不得小于0");
		}
		this.redCost = redCost;
	}
	
	public int getYellowCost() {
		return this.yellowCost;
	}

	public void setYellowCost(int yellowCost) {
		if (yellowCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 黄消耗]yellowCost的值不得小于0");
		}
		this.yellowCost = yellowCost;
	}
	
	public int getBlueCost() {
		return this.blueCost;
	}

	public void setBlueCost(int blueCost) {
		if (blueCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 蓝消耗]blueCost的值不得小于0");
		}
		this.blueCost = blueCost;
	}
	
	public int getGreenCost() {
		return this.greenCost;
	}

	public void setGreenCost(int greenCost) {
		if (greenCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 绿消耗]greenCost的值不得小于0");
		}
		this.greenCost = greenCost;
	}
	
	public int getPurpleCost() {
		return this.purpleCost;
	}

	public void setPurpleCost(int purpleCost) {
		if (purpleCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 紫消耗]purpleCost的值不得小于0");
		}
		this.purpleCost = purpleCost;
	}
	
	public int getSkillSound() {
		return this.skillSound;
	}

	public void setSkillSound(int skillSound) {
		if (skillSound < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 技能音效]skillSound的值不得小于0");
		}
		this.skillSound = skillSound;
	}
	
	public int getSkillIcon() {
		return this.skillIcon;
	}

	public void setSkillIcon(int skillIcon) {
		if (skillIcon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 技能图标]skillIcon的值不得小于0");
		}
		this.skillIcon = skillIcon;
	}
	
	public int getNeedSelectedGem() {
		return this.needSelectedGem;
	}

	public void setNeedSelectedGem(int needSelectedGem) {
		if (needSelectedGem < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 技能释放是否需要点选宝石]needSelectedGem的值不得小于0");
		}
		this.needSelectedGem = needSelectedGem;
	}
	
	public int getSkillActionId() {
		return this.skillActionId;
	}

	public void setSkillActionId(int skillActionId) {
		if (skillActionId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 技能动作ID]skillActionId的值不得小于0");
		}
		this.skillActionId = skillActionId;
	}
	
	public int getFlyEffectId() {
		return this.flyEffectId;
	}

	public void setFlyEffectId(int flyEffectId) {
		if (flyEffectId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 飞行特效ID]flyEffectId的值不得小于0");
		}
		this.flyEffectId = flyEffectId;
	}
	
	public int getBeHitedEffectId() {
		return this.beHitedEffectId;
	}

	public void setBeHitedEffectId(int beHitedEffectId) {
		if (beHitedEffectId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 自己受击特效ID]beHitedEffectId的值不得小于0");
		}
		this.beHitedEffectId = beHitedEffectId;
	}
	
	public int getEnemyBeHitedEffectId() {
		return this.enemyBeHitedEffectId;
	}

	public void setEnemyBeHitedEffectId(int enemyBeHitedEffectId) {
		if (enemyBeHitedEffectId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[ 地方受击特效ID]enemyBeHitedEffectId的值不得小于0");
		}
		this.enemyBeHitedEffectId = enemyBeHitedEffectId;
	}
	
	public List<com.hifun.soul.gameserver.skill.template.SkillEffectTemplate> getEffects() {
		return this.effects;
	}

	public void setEffects(List<com.hifun.soul.gameserver.skill.template.SkillEffectTemplate> effects) {
		if (effects == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[ 技能效果列表]effects不可以为空");
		}	
		this.effects = effects;
	}
	
	public int getSecondEffectDodgeType() {
		return this.secondEffectDodgeType;
	}

	public void setSecondEffectDodgeType(int secondEffectDodgeType) {
		if (secondEffectDodgeType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[ 第二效果是否可以被闪避]secondEffectDodgeType的值不得小于0");
		}
		this.secondEffectDodgeType = secondEffectDodgeType;
	}
	

	@Override
	public String toString() {
		return "SkillTemplateVO[skillName=" + skillName + ",skillDesc=" + skillDesc + ",skillType=" + skillType + ",attackType=" + attackType + ",openLevel=" + openLevel + ",hostType=" + hostType + ",occupation=" + occupation + ",useRoundOver=" + useRoundOver + ",cooldownRound=" + cooldownRound + ",redCost=" + redCost + ",yellowCost=" + yellowCost + ",blueCost=" + blueCost + ",greenCost=" + greenCost + ",purpleCost=" + purpleCost + ",skillSound=" + skillSound + ",skillIcon=" + skillIcon + ",needSelectedGem=" + needSelectedGem + ",skillActionId=" + skillActionId + ",flyEffectId=" + flyEffectId + ",beHitedEffectId=" + beHitedEffectId + ",enemyBeHitedEffectId=" + enemyBeHitedEffectId + ",effects=" + effects + ",secondEffectDodgeType=" + secondEffectDodgeType + ",]";

	}
}