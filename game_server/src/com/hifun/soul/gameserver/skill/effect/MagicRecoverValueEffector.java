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
 * 法力恢复;<br>
 * 魔法值恢复一定的值;<br>
 * params[0] = 能量类型;<br>
 * params[1] = 增加的值;<br>
 * 
 * @author crazyjohn
 * 
 */
public class MagicRecoverValueEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 魔法类型
		int type = params[0];
		// 增加的魔法值
		int value = params[1];
		EnergyType enertyType = EnergyType.typeOf(type);
		if (enertyType == null) {
			return null;
		}
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 添加能量;
		attacker.addMagicEnergy(enertyType, value);
		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(attacker.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(attacker.getCurrentMagicSnap());
		battle.broadcastToBattleUnits(magicChangeMsg);

		return EffectResult.createNullEffect();
	}

}
