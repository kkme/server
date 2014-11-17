package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.role.properties.CalculateSymbol;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 法力吸收;
 * <p>
 * 吸收对方所有魔法的指定值;<br>
 * params[0] = 吸收值;<br>
 * 
 * @author crazyjohn
 * 
 */
public class MagicAbsorbValueEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 吸收的魔法值
		int value = params[0];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		MagicChange targetBefore = target.getCurrentMagicSnap();
		// 敌方减少魔法
		for (EnergyType each : EnergyType.values()) {
			target.reduceMagicEnergy(each, value);
		}
		MagicChange targetAfter = target.getCurrentMagicSnap();
		// 自己增加魔法值
		for (EnergyType each : EnergyType.values()) {
			attacker.addMagicEnergy(each, each.getValueFromMagicChangeBean(
					CalculateSymbol.POSITIVE, targetBefore.reduce(targetAfter)));
		}

		// 发送魔法变化
		GCBattleMagicChanged attackerMagicChangeMsg = new GCBattleMagicChanged();
		attackerMagicChangeMsg.setTargetId(attacker.getUnitGuid());
		attackerMagicChangeMsg.setTargetMagicChange(attacker
				.getCurrentMagicSnap());
		battle.broadcastToBattleUnits(attackerMagicChangeMsg);

		GCBattleMagicChanged targetMagicChangeMsg = new GCBattleMagicChanged();
		targetMagicChangeMsg.setTargetId(target.getUnitGuid());
		targetMagicChangeMsg.setTargetMagicChange(target.getCurrentMagicSnap());
		battle.broadcastToBattleUnits(targetMagicChangeMsg);

		return EffectResult.createNullEffect();
	}

}
