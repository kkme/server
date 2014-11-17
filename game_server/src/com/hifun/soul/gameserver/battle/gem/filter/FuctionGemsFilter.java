package com.hifun.soul.gameserver.battle.gem.filter;

import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;

/**
 * 功能型宝石消除过滤器;
 * 
 * @author crazyjohn
 * 
 */
public class FuctionGemsFilter implements IGemEraseFilter {

	@Override
	public void doFilter(List<ChessBoardSnap> snaps, IBattleUnit unit) {
		for (ChessBoardSnap snap : snaps) {
			snap.doFunctionGemsEffect(unit);
		}
	}

}
