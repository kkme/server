package com.hifun.soul.gameserver.skill.effect;

import java.util.Collection;
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
 * 消除所有在棋盘中的指定颜色的宝石;<br>
 * params[0] = 宝石颜色;<br>
 * params[1] = 是否吸收能量;<br>
 * 
 * @author crazyjohn
 * 
 */
public class EraseAllGemsEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 获取颜色
		int color = params[0];
		GemType type = GemType.typeOf(color);
		if (type == null) {
			return null;
		}
		// 消除的能量是否会被吸收
		int absorb = params[1];
		AbsorbType absorbType = AbsorbType.typeOf(absorb);
		if (absorbType == null) {
			throw new IllegalArgumentException("AbsortType can not be null.");
		}
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		Collection<GemPosition> erasableGems = battle
				.getEraseGemsByGemType(type);
		// 没有可消除的宝石
		if (erasableGems.size() == 0) {
			return EffectResult.createNullEffect();
		}
		// 消除指定的宝石
		List<ChessBoardSnap> newSnaps = battle
				.eraseAssignPositionGemsAndUpdateChessBoard(erasableGems);
		if (absorbType == AbsorbType.YES) {
			// 设置魔法变化
			MagicChange attackerMagicChanged = attacker
					.updateMagicSlots(newSnaps);
			// 发送魔法变化
			GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
			magicChangeMsg.setTargetId(attacker.getUnitGuid());
			magicChangeMsg.setTargetMagicChange(attackerMagicChanged);
			battle.broadcastToBattleUnits(magicChangeMsg);
		}
		return EffectResult.createNullEffect();
	}

}
