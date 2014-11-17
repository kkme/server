package com.hifun.soul.gameserver.battle.gem.filter;

import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;

/**
 * 宝石消除过滤器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IGemEraseFilter {

	/**
	 * 过滤动作;
	 * 
	 * @param snaps
	 *            棋盘快照;
	 * @param unit
	 *            战斗单元;
	 */
	public void doFilter(List<ChessBoardSnap> snaps, IBattleUnit unit);

}
