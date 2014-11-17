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
 * 魔法燃烧;<br>
 * 按照百分比减少对方所有魔法;<br>
 * params[0] = 吸收比率;<br>
 * 
 * @author crazyjohn
 * 
 */
public class MagicBurnRateEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		int rate = params[0];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 获取魔法变化
		for (EnergyType each : EnergyType.values()) {
			for (MagicSlotInfo slot : target.getAllMagicSlots()) {
				if (slot.getEnergyType() == each.getIndex()) {
					int reduceValue = (int) Math.floor(slot.getCurrentSize() * rate
							/ SharedConstants.PROPERTY_DIVISOR);
					target.reduceMagicEnergy(each, reduceValue);
					break;
				}
			}
		}

		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(target.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(target.getCurrentMagicSnap());
		battle.broadcastToBattleUnits(magicChangeMsg);

		return EffectResult.createNullEffect();
	}

}
