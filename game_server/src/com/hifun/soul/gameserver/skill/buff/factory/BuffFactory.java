package com.hifun.soul.gameserver.skill.buff.factory;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.buff.CommonAmendPropertyBuff;
import com.hifun.soul.gameserver.skill.buff.HealBuff;
import com.hifun.soul.gameserver.skill.buff.IBuff;
import com.hifun.soul.gameserver.skill.buff.PoisonBuff;
import com.hifun.soul.gameserver.skill.buff.template.BuffTemplate;

/**
 * buff工厂;
 * 
 * @author crazyjohn
 * 
 */
public class BuffFactory {
	private TemplateService templateService = ApplicationContext
			.getApplicationContext().getBean(TemplateService.class);

	public BuffTemplate getBuffTemplateByTemplateId(int buffTemplateId) {
		return templateService.get(buffTemplateId, BuffTemplate.class);

	}

	public IBuff createCommonAmendRoundBuff(IBattleUnit target,
			BuffType buffType, BuffTemplate template, int lifeRound,
			int propValue, int amendType) {
		if (buffType == BuffType.POISONING) {
			return new PoisonBuff(target, buffType, template, lifeRound,
					propValue);
		}
		if (buffType == BuffType.HEALING) {
			return new HealBuff(target, buffType, template, lifeRound,
					propValue);
		}
		return new CommonAmendPropertyBuff(target, buffType, template,
				lifeRound, buffType.getPropId(), propValue, amendType);

	}
}
