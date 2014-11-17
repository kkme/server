package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;

/**
 * 治疗效果;<br>
 * 直接恢复生命x点, 并且每x点x能量恢复x点生命;<br>
 * params[0] = 直接恢复值;<br>
 * params[1] = 能量类型;<br>
 * params[2] = 单位能量;<br>
 * params[3] = 单位治疗;<br>
 * 
 * @author crazyjohn
 * 
 */
public class HealEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 直接恢复的生命值
		int healValue = params[0];
		int energy = params[1];
		EnergyType energyType = EnergyType.typeOf(energy);
		if (energyType == null) {
			return null;
		}
		int perCount = params[2];
		int perHeal = params[3];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 魔法值
		MagicSlotInfo slot = null;
		for (MagicSlotInfo each : attacker.getAllMagicSlots()) {
			if (each.getEnergyType() == energy) {
				slot = each;
				break;
			}
		}
		if (slot == null) {
			return null;
		}
		int addHeal = slot.getCurrentSize() / perCount * perHeal;
		int addResult = healValue + addHeal;
		// 攻击者加血
		attacker.getBattleContext()
				.getBattleProperty()
				.setHp(attacker.getBattleContext().getBattleProperty().getHp()
						+ addResult);
		// 发送血量变化
		GCBattleHpChanged hpChangeMsg = new GCBattleHpChanged();
		hpChangeMsg.setTargetId(attacker.getUnitGuid());
		hpChangeMsg.setChangeHp(-addResult);
		battle.broadcastToBattleUnits(hpChangeMsg);

		return EffectResult.createNullEffect();
	}

}
