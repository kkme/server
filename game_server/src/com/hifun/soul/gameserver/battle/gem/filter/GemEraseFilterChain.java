package com.hifun.soul.gameserver.battle.gem.filter;

import java.util.LinkedList;
import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;

/**
 * 宝石消除过滤器链;
 * 
 * @author crazyjohn
 * 
 */
public class GemEraseFilterChain {
	/** 所有过滤器 */
	private LinkedList<IGemEraseFilter> filters = new LinkedList<IGemEraseFilter>();

	{
		// 四联消过滤器
		this.addFilter(new FourSameColorFilter());
		// 功能过滤
		this.addFilter(new FuctionGemsFilter());
	}

	/**
	 * 在链上执行过滤动作;
	 * 
	 * @param erasableGems
	 * @return 伤害加成值;
	 */
	public void doFilter(List<ChessBoardSnap> snaps, IBattleUnit unit) {
		for (IGemEraseFilter filter : filters) {
			filter.doFilter(snaps, unit);
		}
	}

	/**
	 * 添加过滤器;
	 * 
	 * @param filter
	 */
	public void addFilter(IGemEraseFilter filter) {
		filters.add(filter);
	}
}
