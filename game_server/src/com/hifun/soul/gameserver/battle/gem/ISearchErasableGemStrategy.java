package com.hifun.soul.gameserver.battle.gem;

import java.util.Collection;
import java.util.List;

import com.hifun.soul.gameserver.battle.gem.function.IFunctionGems;


/**
 * 搜索可消除宝石的算法接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ISearchErasableGemStrategy {
	
	public Collection<GemPosition> searchErasableGems();
	
	public int getEraseCount();
	
	public boolean isTriggerNewRound();

	public List<IFunctionGems> getCurrentSnapFunctionGems();
}
