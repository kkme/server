package com.hifun.soul.gameserver.skill.effect.buff;

import com.hifun.soul.gameserver.skill.buff.factory.BuffFactory;
import com.hifun.soul.gameserver.skill.buff.template.BuffTemplate;
import com.hifun.soul.gameserver.skill.effect.IBuffEffEctor;

/**
 * 抽象的buff效果器;
 * 
 * @author crazyjohn
 * 
 */
public abstract class AbstractBuffEffector implements IBuffEffEctor {
	protected BuffFactory factory = new BuffFactory();
	protected BuffTemplate tempTemplate;
}
