package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;

/**
 * 法力燃烧;<br>
 * 减少对方所有的魔法值;<br>
 * params[0] = 减少的法力值;<br>
 * 
 * @author crazyjohn
 * 
 */
public class MagicBurnValueEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 减少的魔法值
		int value = params[0];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		for (EnergyType each : EnergyType.values()) {
			target.reduceMagicEnergy(each, value);
		}
		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(target.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(target.getCurrentMagicSnap());
		battle.broadcastToBattleUnits(magicChangeMsg);

		return EffectResult.createNullEffect();

	}

}
