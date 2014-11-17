package com.hifun.soul.gameserver.monster;

/**
 * 怪物工厂接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IMonsterFactory {
	public Monster createMonster(int monsterId);
}
