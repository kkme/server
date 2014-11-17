package com.hifun.soul.gameserver.battle.gem;

import java.util.concurrent.atomic.AtomicLong;

import com.hifun.soul.core.util.MathUtils;

/**
 * 宝石随机生成器;
 * 
 * @author crazyjohn
 * 
 */
public class GemRandomGenerator implements IGemGenerator {
	private static AtomicLong counter =  new AtomicLong();

	@Override
	public GemObject generateOneGemObject() {
		GemType gemType = MathUtils.random(GemType.class);
		GemObject gem = new GemObject();
		gem.setType(gemType);
		gem.setId(counter.incrementAndGet());
		return gem;
	}

}
