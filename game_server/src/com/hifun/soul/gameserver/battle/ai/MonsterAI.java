package com.hifun.soul.gameserver.battle.ai;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.monster.Monster;

/**
 * 怪物AI;<br>
 * 使用技能系数从配置表中读取;
 * 
 * @author crazyjohn
 * 
 */
public class MonsterAI extends BaseMonsterAI {
	private Monster monster;

	public MonsterAI(Monster monster) {
		super(monster);
		this.monster = monster;
	}

	@Override
	protected double getUseSkillFactor() {
		return this.monster.getTemplate().getUseSkillFactor()
				/ SharedConstants.PROPERTY_DIVISOR;
	}

}
