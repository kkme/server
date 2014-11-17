package com.hifun.soul.gameserver.role.properties.manager;

import com.hifun.soul.gameserver.monster.Monster;

/**
 * 怪物属性管理器;
 * 
 * @author crazyjohn
 * 
 */
public class MonsterPropertyManager extends RolePropertyManager<Monster> {

	public MonsterPropertyManager(Monster role) {
		super(role);
	}

	@Override
	protected void calculateLevel1ToLevel2Properties() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void calculateLevel1Properties() {
		// TODO Auto-generated method stub

	}

	@Override
	public void recalculateInitProperties(Monster monster) {
		RoleInitPropsHelper.initMonsterProps(monster);
	}

}
