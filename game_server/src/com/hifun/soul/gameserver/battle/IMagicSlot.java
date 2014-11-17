package com.hifun.soul.gameserver.battle;

import com.hifun.soul.gameserver.battle.gem.GemType;

/**
 * 魔法槽;
 * 
 * @author crazyjohn
 * 
 */
public interface IMagicSlot {

	public int getCapacity();

	public void reset();

	public int getCurrentSize();

	public GemType getType();
}
