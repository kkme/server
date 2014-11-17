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
import com.hifun.soul.gameserver.skill.buff.PoisonBuff;
import com.hifun.soul.gameserver.skill.effect.buff.AbstractBuffEffector;

/**
 * 中毒buff效果;<br>
 * 用于程序动态计算, 根据最后一次的伤害值*rate给出的单位回合中毒值;<br>
 * params[0] = 概率;<br>
 * params[1] = 伤害值比率;<br>
 * params[2] = 回合数;<br>
 * params[3] = 模版id;<br>
 * 
 * @author crazyjohn
 * 
 */
public class PoisonBuffEffector extends AbstractBuffEffector implements
		IBuffEffEctor, IPreProcessSecondEffector {
	private EffectResult firstResult;

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 此时说明第一效果是一个伤害效果, 而且此伤害效果被对方闪避了;
		if (firstResult == null) {
			return EffectResult.createNullEffect();
		}
		int rate = params[0];
		float poisonRate = params[1] / SharedConstants.PROPERTY_DIVISOR;
		int round = params[2];
		int buffTemplateId = params[3];
		// 获取模版
		tempTemplate = this.factory.getBuffTemplateByTemplateId(buffTemplateId);
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
			int poisonDamage = (int) (this.firstResult.getFinalDamage() * poisonRate);
			if (poisonDamage <= 0) {
				poisonDamage = 1;
			}
			context.getBuffManager().addBuff(
					new PoisonBuff(target, BuffType.POISONING, tempTemplate,
							round, -poisonDamage));
		}

		return EffectResult.createNullEffect();
	}

	@Override
	public Integer getBuffResourceId(int[] params) {
		tempTemplate = factory.getBuffTemplateByTemplateId(params[3]);
		return tempTemplate.getBuffResourceId();
	}

	@Override
	public EffectResult getFirstEffectResult() {
		return firstResult;
	}

	@Override
	public void setFirstEffectResult(EffectResult firstResult) {
		this.firstResult = firstResult;
	}

}
