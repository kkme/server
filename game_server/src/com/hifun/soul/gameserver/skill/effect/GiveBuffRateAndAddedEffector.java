package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.buff.CommonRoundBuff;
import com.hifun.soul.gameserver.skill.effect.buff.AbstractBuffEffector;

/**
 * 一定几率给对方上buff,并且根据指定的能量类型进行buff回合加成;<br>
 * params[0] = 概率;<br>
 * params[1] = buff类型;<br>
 * params[2] = 回合数;<br>
 * params[3] = 能量类型;<br>
 * params[4] = 单位数量;<br>
 * params[5] = 单位回合;<br>
 * 
 * @author crazyjohn
 * 
 */
public class GiveBuffRateAndAddedEffector extends AbstractBuffEffector
		implements ISkillEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		int rate = params[0];
		int buffTemplateId = params[1];
		// 获取模版
		tempTemplate = this.factory.getBuffTemplateByTemplateId(buffTemplateId);
		int round = params[2];
		int intEnergy = params[3];
		int perCount = params[4];
		int perRound = params[5];
		BuffType buffType = BuffType.typeOf(tempTemplate.getBuffType());
		if (buffType == null) {
			return null;
		}
		EnergyType energyType = EnergyType.typeOf(intEnergy);
		if (energyType == null) {
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
			MagicSlotInfo slot = null;
			for (MagicSlotInfo each : attacker.getAllMagicSlots()) {
				if (each.getEnergyType() == intEnergy) {
					slot = each;
					break;
				}
			}
			if (slot == null) {
				return null;
			}
			int addRound = slot.getCurrentSize() / perCount * perRound;
			context.getBuffManager().addBuff(
					new CommonRoundBuff(target, buffType, tempTemplate, round
							+ addRound));
		}

		return EffectResult.createNullEffect();
	}

	@Override
	public Integer getBuffResourceId(int[] params) {
		tempTemplate = factory.getBuffTemplateByTemplateId(params[1]);
		return tempTemplate.getBuffResourceId();
	}

}
