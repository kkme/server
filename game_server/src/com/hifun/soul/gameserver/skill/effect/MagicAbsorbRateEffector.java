package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;

/**
 * 法力吸收;<br>
 * 吸收对方所有魔法的一定比例;<br>
 * params[0] = 吸收比例;<br>
 * 
 * @author crazyjohn
 * 
 */
public class MagicAbsorbRateEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 吸收比例
		int rate = params[0];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}

		for (EnergyType each : EnergyType.values()) {
			for (MagicSlotInfo slot : target.getAllMagicSlots()) {
				if (slot.getEnergyType() == each.getIndex()) {
					int reduceValue = (int) Math.floor(slot.getCurrentSize()
							* rate / SharedConstants.PROPERTY_DIVISOR);
					target.reduceMagicEnergy(each, reduceValue);
					break;
				}
			}
		}
		// 获取受击者魔法变化
		for (EnergyType each : EnergyType.values()) {
			for (MagicSlotInfo slot : target.getAllMagicSlots()) {
				if (slot.getEnergyType() == each.getIndex()) {
					int reduceValue = (int) Math.floor(slot.getCurrentSize()
							* rate / SharedConstants.PROPERTY_DIVISOR);
					attacker.addMagicEnergy(each, reduceValue);
					break;
				}
			}
		}
		// 获取攻击者魔法变化

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
