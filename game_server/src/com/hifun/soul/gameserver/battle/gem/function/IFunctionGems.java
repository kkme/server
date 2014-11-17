package com.hifun.soul.gameserver.battle.gem.function;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;

/**
 * 功能宝石接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IFunctionGems {
	
	public void doEffect(IBattleUnit unit, ChessBoardSnap chessBoardSnap);
	
}
