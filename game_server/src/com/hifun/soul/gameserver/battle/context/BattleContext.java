package com.hifun.soul.gameserver.battle.context;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.action.IInvalidMoveHandler;
import com.hifun.soul.gameserver.battle.action.PauseBattleHandler;
import com.hifun.soul.gameserver.battle.property.BattleProperty;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BattleUnitBuffManager;

/**
 * 战斗上下文;
 * 
 * @author crazyjohn
 * 
 */
public class BattleContext implements IBattleContext {
	/** 战斗对象 */
	private Battle battle;
	/** 战斗单元 */
	private IBattleUnit unit;
	/** 战斗属性 */
	private BattleProperty battleProperty;
	/** buff管理器 */
	private BattleUnitBuffManager buffManager = new BattleUnitBuffManager();
	private List<ISkill> skills;
	/** 棋盘不同步处理器 */
	private IInvalidMoveHandler invalidMoveHandler = new PauseBattleHandler();
	
	public BattleContext(Battle battle, IBattleUnit unit,
			BattleProperty property) {
		this.battle = battle;
		this.unit = unit;
		this.battleProperty = property;
	}

	@Override
	public Battle getBattle() {
		return battle;
	}

	@Override
	public IBattleUnit getBattleUnit() {
		return unit;
	}

	@Override
	public BattleProperty getBattleProperty() {
		return battleProperty;
	}

	@Override
	public BattleUnitBuffManager getBuffManager() {
		return buffManager;
	}

	@Override
	public List<ISkill> getBattleSkills() {
		return skills;
	}

	@Override
	public void buildBattleSkill(List<ISkill> carriedSkills) {
		this.skills = new ArrayList<ISkill>();
		for (ISkill skill : carriedSkills) {
			skills.add(skill.copy());
		}
	}

	@Override
	public IInvalidMoveHandler getInvalidMoveHandler() {
		return invalidMoveHandler;
	}

}
