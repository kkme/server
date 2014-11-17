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
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.buff.IBuff;
import com.hifun.soul.gameserver.skill.buff.factory.BuffFactory;
import com.hifun.soul.gameserver.skill.effect.buff.AbstractBuffEffector;

/**
 * 根据当前魔法值给目标上回血buff;<br>
 * params[0] = 概率;<br>
 * params[1] = 能量类型;<br>
 * params[2] = 单位数量;<br>
 * params[3] = 单位数值;<br>
 * params[4] = 回合数;<br>
 * params[5] = buff模版id;<br>
 * 
 * @author crazyjohn
 * 
 */
public class HealBuffByCurrentEnergy extends AbstractBuffEffector implements
		IBuffEffEctor {
	private BuffFactory factory = new BuffFactory();

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 概率
		int rate = params[0];
		// 能量类型
		int energy = params[1];
		EnergyType energyType = EnergyType.typeOf(energy);
		if (energyType == null) {
			return null;
		}
		// 单位数量
		int perCount = params[2];
		// 单位数值
		int perValue = params[3];

		// 回合数
		int rounds = params[4];

		int buffTemplateId = params[5];
		// 获取模版
		tempTemplate = this.factory.getBuffTemplateByTemplateId(buffTemplateId);
		// 自己战斗上下文
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		boolean success = BattleCalculator.diceRate(rate
				/ SharedConstants.PROPERTY_DIVISOR);

		// 给自己上buff; 加成類型都是AmendType.ADD
		if (success) {
			// 能量值
			MagicSlotInfo targetMagic = null;
			for (MagicSlotInfo slot : attacker.getAllMagicSlots()) {
				if (slot.getEnergyType() == energyType.getIndex()) {
					targetMagic = slot;
				}
			}
			IBuff buff = factory.createCommonAmendRoundBuff(attacker,
					BuffType.HEALING, tempTemplate, rounds,
					(targetMagic.getCurrentSize() * perCount * perValue),
					AmendMethod.ADD.getIndex());
			context.getBuffManager().addBuff(buff);
			// 给自己上的buff在播放效果前,先播放一次buff效果;
			buff.beforeAction();

		}
		return EffectResult.createNullEffect();
	}

	@Override
	public Integer getBuffResourceId(int[] params) {
		tempTemplate = factory.getBuffTemplateByTemplateId(params[5]);
		return tempTemplate.getBuffResourceId();
	}

}
