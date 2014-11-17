package com.hifun.soul.gameserver.battle.gem;

import java.util.List;

/**
 * 寻找可移动的Move策略接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ISearchMoveStrategy {

	/**
	 * 获取所有正确的Move;
	 * 
	 * @return 不会返回空;
	 */
	public List<Move> getMoves();
	
	/**
	 * 获取四连消移动
	 * @return
	 */
	public List<Move> getFourBombsMoves();

}
