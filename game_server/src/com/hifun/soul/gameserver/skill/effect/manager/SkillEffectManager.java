package com.hifun.soul.gameserver.skill.effect.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.skill.effect.ISkillEffector;
import com.hifun.soul.gameserver.skill.effect.SkillEffectType;

/**
 * 效果管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class SkillEffectManager implements ISkillEffectManager {
	private Map<SkillEffectType, ISkillEffector> effectors = new HashMap<SkillEffectType, ISkillEffector>();

	public SkillEffectManager() {
		// 注册各个效果
		for (SkillEffectType type : SkillEffectType.values()) {
			effectors.put(type, type.getRelatedEffector());
		}
	}

	@Override
	public ISkillEffector getSkillEffector(SkillEffectType type) {
		return effectors.get(type);
	}

}
