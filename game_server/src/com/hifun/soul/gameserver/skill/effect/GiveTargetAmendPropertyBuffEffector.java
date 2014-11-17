package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.buff.factory.BuffFactory;
import com.hifun.soul.gameserver.skill.effect.buff.AbstractBuffEffector;

/**
 * 给敌方上带属性加成效果的buff;<br>
 * params[0] = 概率;<br>
 * params[1] = buff类型;<br>
 * params[2] = 二级属性值;<br>
 * params[3] = 属性加成类型;<br>
 * params[4] = 回合数;<br>
 * 
 * @author crazyjohn
 * 
 */
public class GiveTargetAmendPropertyBuffEffector extends AbstractBuffEffector
		implements ISkillEffector, IBuffEffEctor {
	private BuffFactory factory = new BuffFactory();

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		int rate = params[0];
		int buffTemplateId = params[1];
		int propValue = params[2];
		int amendType = params[3];
		int round = params[4];
		BuffType buffType = BuffType.typeOf(buffTemplateId);
		// 获取模版
		tempTemplate = this.factory.getBuffTemplateByTemplateId(buffTemplateId);
		if (buffType == null) {
			return null;
		}
		IBattleContext context = target.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		boolean success = BattleCalculator.diceRate(rate
				/ SharedConstants.PROPERTY_DIVISOR);
		if (success) {
			context.getBuffManager().addBuff(
					factory.createCommonAmendRoundBuff(target, buffType,
							tempTemplate, round, propValue, amendType));
		}

		return EffectResult.createNullEffect();
	}

	@Override
	public Integer getBuffResourceId(int[] params) {
		tempTemplate = factory.getBuffTemplateByTemplateId(params[1]);
		return tempTemplate.getBuffResourceId();
	}

}
