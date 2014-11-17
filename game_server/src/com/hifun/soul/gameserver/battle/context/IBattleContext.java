package com.hifun.soul.gameserver.battle.context;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.action.IInvalidMoveHandler;
import com.hifun.soul.gameserver.battle.property.BattleProperty;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BattleUnitBuffManager;

/**
 * 战斗上下文;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleContext {

	/**
	 * 获取当前的战斗对象;
	 * 
	 * @return
	 */
	public Battle getBattle();

	/**
	 * 获取当前的战斗单元;
	 * 
	 * @return
	 */
	public IBattleUnit getBattleUnit();

	/**
	 * 获取战斗属性;
	 * 
	 * @return
	 */
	public BattleProperty getBattleProperty();
	
	public BattleUnitBuffManager getBuffManager();

	public List<ISkill> getBattleSkills();

	public void buildBattleSkill(List<ISkill> carriedSkills);
	
	public IInvalidMoveHandler getInvalidMoveHandler();

}
