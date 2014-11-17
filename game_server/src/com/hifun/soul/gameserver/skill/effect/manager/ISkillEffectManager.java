package com.hifun.soul.gameserver.skill.effect.manager;

import com.hifun.soul.gameserver.skill.effect.ISkillEffector;
import com.hifun.soul.gameserver.skill.effect.SkillEffectType;

/**
 * 效果管理器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ISkillEffectManager {

	/**
	 * 获取技能效果;
	 * 
	 * @param type
	 * @return
	 */
	public ISkillEffector getSkillEffector(SkillEffectType type);

}
