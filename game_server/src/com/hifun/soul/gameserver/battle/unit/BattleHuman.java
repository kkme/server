package com.hifun.soul.gameserver.battle.unit;

import java.util.List;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.RoleType;
import com.hifun.soul.gameserver.role.properties.manager.RolePropertyManager;
import com.hifun.soul.gameserver.skill.ISkill;

/**
 * 战斗玩家对象;
 * 
 * @author crazyjohn
 * 
 */
@Deprecated
public class BattleHuman extends BaseBattleUnit<Human> {

	public BattleHuman(Human role) {
		super(role);
	}

	@Override
	public List<ISkill> getCarriedSkills() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public ISkill getComboAttackSkill() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISkill getNormalAttackSkill() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onExitBattle() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RoleType getRoleType() {
		return RoleType.HUMAN;
	}

	@Override
	public Occupation getOccupation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onNormalActionInvalid(int row1, int col1, int row2, int col2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RolePropertyManager<?> getPropertyManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUnitHeadId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDefaultActionId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
