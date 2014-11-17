package com.hifun.soul.gameserver.battle.unit;

import java.util.List;

import com.hifun.soul.gameserver.battle.BattleUtil;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.HumanSkillManager;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.RoleType;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.skill.ISkill;

/**
 * 玩家守卫者;
 * 
 * @author crazyjohn
 * 
 */
public class HumanGuarder extends Role<HumanPropertyManager> implements
		IBattleUnit {
	private HumanSkillManager skillManager;
	private long guid;

	public HumanGuarder(long guid, HumanPropertyManager propertyManager,
			HumanSkillManager skillManager) {
		this.propertyManager = propertyManager;
		this.skillManager = skillManager;
		this.guid = guid;
	}

	@Override
	public List<ISkill> getCarriedSkills() {
		List<ISkill> skills = super.getCarriedSkills();
		skills.addAll(this.skillManager.getCarriedSkills());
		return skills;
	}

	@Override
	public ISkill getComboAttackSkill() {
		return skillManager.getComboAttackSkill();
	}

	@Override
	public void notifyAction() {
		super.notifyAction();
		this.battleAI.action(BattleUtil.getHumanGuarderThinkTimes());
	}

	@Override
	public ISkill getNormalAttackSkill() {
		return skillManager.getNormalAttackSkill();
	}

	@Override
	public long getUnitGuid() {
		return guid;
	}

	@Override
	public int getUnitModelId() {
		return GameServerAssist
				.getOccupationTemplateManager()
				.getOccupationTemplateByOccupation(
						Occupation.typeOf(this.propertyManager
								.getIntPropertySet(
										PropertyType.HUMAN_INT_PROPERTY)
								.getPropertyValue(HumanIntProperty.OCCUPATION)))
				.getResourceId();
	}

	@Override
	public RoleType getRoleType() {
		return RoleType.HUMAN;
	}

	@Override
	public Occupation getOccupation() {
		return Occupation.typeOf(this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.OCCUPATION));
	}

	@Override
	public int getUnitHeadId() {
		return getUnitModelId();
	}

	@Override
	public int getDefaultActionId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
