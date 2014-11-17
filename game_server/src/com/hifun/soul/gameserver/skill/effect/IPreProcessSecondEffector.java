package com.hifun.soul.gameserver.skill.effect;

import com.hifun.soul.gameserver.skill.EffectResult;

/**
 * 需要预处理的第二效果接口;<br>
 * 此类型的效果要依赖于第一效果的数据;<br>
 * 
 * @author crazyjohn
 * 
 */
public interface IPreProcessSecondEffector {

	/**
	 * 获取第一效果的效果值;
	 * 
	 * @return
	 */
	public EffectResult getFirstEffectResult();

	/**
	 * 设置第一效果的效果值;
	 * 
	 * @param firstResult
	 */
	public void setFirstEffectResult(EffectResult firstResult);

}
