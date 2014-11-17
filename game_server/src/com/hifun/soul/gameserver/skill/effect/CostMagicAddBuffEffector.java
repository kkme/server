package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.effect.buff.AbstractBuffEffector;

/**
 * 消耗魔法并且上buff;<br>
 * params[0] = 能量类型;<br>
 * params[1] = 单位数量;<br>
 * params[2] = buff类型;<br>
 * params[3] = 单位数值;<br>
 * params[4] = 回合数;<br>
 * params[5] = 目标;<br>
 * 
 * @author crazyjohn
 * 
 */
public class CostMagicAddBuffEffector extends AbstractBuffEffector implements ISkillEffector {
	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 能量类型
		int energy = params[0];
		EnergyType energyType = EnergyType.typeOf(energy);
		if (energyType == null) {
			return null;
		}
		// 单位数量
		int perCount = params[1];
		// 二级属性id
		int buffTemplateId = params[2];
		// 获取模版 
		tempTemplate = this.factory
				.getBuffTemplateByTemplateId(buffTemplateId);
		// 单位数值
		int perValue = params[3];
		// 回合数
		int rounds = params[4];
		// 目标
		boolean isSelf = params[5] == 1;
		// 自己战斗上下文
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 敌方战斗上下文
		IBattleContext targetContext = target.getBattleContext();
		if (targetContext == null) {
			return null;
		}
		// 给自己上buff; 加成類型都是AmendType.ADD
		BuffType buffType = BuffType.typeOf(tempTemplate.getBuffType());
		if (buffType == null) {
			return null;
		}
		if (isSelf) {
			context.getBuffManager().addBuff(
					factory.createCommonAmendRoundBuff(attacker, buffType,
							tempTemplate, rounds, perCount * perValue,
							AmendMethod.ADD.getIndex()));
		} else {
			targetContext.getBuffManager().addBuff(
					factory.createCommonAmendRoundBuff(target, buffType,
							tempTemplate, rounds, perCount * perValue,
							AmendMethod.ADD.getIndex()));
		}
		return EffectResult.createNullEffect();
	}

	@Override
	public Integer getBuffResourceId(int[] params) {
		tempTemplate = factory.getBuffTemplateByTemplateId(params[2]);
		return tempTemplate.getBuffResourceId();
	}

}
