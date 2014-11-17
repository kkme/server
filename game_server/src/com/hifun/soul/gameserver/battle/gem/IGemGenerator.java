package com.hifun.soul.gameserver.battle.gem;

/**
 * 宝石生成器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IGemGenerator {
	/**
	 * 生成一个宝石对象;
	 * 
	 * @return 返回一个宝石对象;
	 */
	public GemObject generateOneGemObject();

}
