package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 转换宝石颜色的效果;<br>
 * params[0] = 目标颜色;<br>
 * params[1] = 生成数量;<br>
 * 
 * @author crazyjohn
 * 
 */
public class RandomGenerateGemsEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 目标颜色
		int desColor = params[0];
		int count = params[1];
		GemType desType = GemType.typeOf(desColor);
		if (desType == null) {
			return null;
		}
		// 战斗上下文
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 改变棋盘颜色
		List<ChessBoardSnap> newSnaps = battle.generateGemByType(desType, count);
		MagicChange change = attacker.updateMagicSlots(newSnaps);

		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(attacker.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(change);
		battle.broadcastToBattleUnits(magicChangeMsg);
		return EffectResult.createNullEffect();
	}

}
