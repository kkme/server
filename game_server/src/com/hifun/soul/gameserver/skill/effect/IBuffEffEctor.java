package com.hifun.soul.gameserver.skill.effect;


/**
 * 会产生buff的技能效果接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IBuffEffEctor extends INoneAttackEffector{
	/**
	 * 获取buff类型;
	 */
	public Integer getBuffResourceId(int[] params);
	
}
