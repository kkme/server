package com.hifun.soul.gameserver.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.ai.MonsterAI;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.RoleType;
import com.hifun.soul.gameserver.role.properties.manager.MonsterPropertyManager;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillInstance;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;

/**
 * 怪物的抽象;
 * 
 * @author crazyjohn
 * 
 */
public class Monster extends Role<MonsterPropertyManager> implements
		IBattleUnit {
	/** 怪物的类型 */
	private MonsterType type;
	/** 怪物模版配置 */
	private MonsterTemplate template;
	/** 普通技能 */
	private ISkill normalAttackSkill;
	/** 连击技能 */
	private ISkill comboAttackSkill;
	Random rand = new Random();

	public Monster(MonsterTemplate template) {
		this.template = template;
		this.propertyManager = new MonsterPropertyManager(this);
		propertyManager.recalculateInitProperties(this);
		propertyManager.recalculateAllProperties();
		this.setId(template.getId());
		this.normalAttackSkill = new SkillInstance(GameServerAssist
				.getSkillTemplateManager().getSkillTemplate(
						template.getNormalAttackSkillId()), this);
		comboAttackSkill = new SkillInstance(GameServerAssist
				.getSkillTemplateManager().getSkillTemplate(
						template.getComboAttackSkillId()), this);
		// 设置AI
		this.battleAI = new MonsterAI(this);
	}

	public MonsterPropertyManager getPropertyManager() {
		return (MonsterPropertyManager) this.propertyManager;
	}

	@Override
	public String getName() {
		return this.template.getName();
	}

	public MonsterType getType() {
		return type;
	}

	public void setType(MonsterType type) {
		this.type = type;
	}

	public MonsterTemplate getTemplate() {
		return template;
	}

	public void setTemplate(MonsterTemplate template) {
		this.template = template;
	}

	public void setInBattleState(boolean isInBattleState) {
		this.isInBattleState = isInBattleState;
	}

	@Override
	public ISkill getComboAttackSkill() {
		return comboAttackSkill;
	}

	@Override
	public ISkill getNormalAttackSkill() {
		return normalAttackSkill;
	}

	@Override
	public int getUnitModelId() {
		return this.template.getModelId();
	}

	@Override
	public List<ISkill> getCarriedSkills() {
		List<ISkill> skills = new ArrayList<ISkill>();
		skills.addAll(super.getCarriedSkills());
		for (int skillId : this.template.getSkills()) {
			SkillTemplate skillData = GameServerAssist
					.getSkillTemplateManager().getSkillTemplate(skillId);
			if (skillData == null) {
				continue;
			}
			ISkill skill = new SkillInstance(skillData, this);
			skills.add(skill);
		}
		return skills;
	}

	@Override
	public void notifyAction() {
		super.notifyAction();
		// ai动作
		long thinkTimes = getThinkTimes();
		this.battleAI.action(thinkTimes);
	}

	// 获取思考时间;
	private long getThinkTimes() {
		if (this.template.getType() != MonsterType.BRAVE_NPC.getIndex()) {
			return SharedConstants.BATTLE_MONSTER_THINK_TIMES;
		}
		// 模拟随机的思考时间;范围1-10s;
		long result = (rand.nextInt(5) + 1) * 1000;
		return result;
	}

	@Override
	public int getLevel() {
		return this.template.getLevel();
	}

	@Override
	public RoleType getRoleType() {
		return RoleType.MONSTER;
	}

	@Override
	public Occupation getOccupation() {
		return Occupation.typeOf(template.getOccupationType());
	}

	@Override
	public int getUnitHeadId() {
		return this.template.getPicId();
	}

	@Override
	public int getDefaultActionId() {
		return this.template.getDefaultActionId();
	}
}
