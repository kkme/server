package com.hifun.soul.gameserver.skill.effect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 消除所有在棋盘中的指定颜色的宝石, 并造成伤害(棋盘中每X个X宝石造成x点伤害值);<br>
 * params[0] = 宝石颜色;<br>
 * params[1] = 宝石单位数量;<br>
 * params[2] = 单位伤害;<br>
 * params[3] = 是否吸收能量;<br>
 * 
 * @author crazyjohn
 * 
 */
public class EraseAllGemsAndDamageEffector extends AbstractDamageEffector {

	@Override
	protected EffectResult doEffect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 获取颜色
		int color = params[0];
		GemType type = GemType.typeOf(color);
		if (type == null) {
			return null;
		}
		int perCount = params[1];
		int perDamage = params[2];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		// 消除的能量是否会被吸收
		int absorb = params[3];
		AbsorbType absorbType = AbsorbType.typeOf(absorb);
		if (absorbType == null) {
			throw new IllegalArgumentException("AbsortType can not be null.");
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 1. 消除
		Collection<GemPosition> erasableGems = battle
				.getEraseGemsByGemType(type);
		List<ChessBoardSnap> newSnaps = battle
				.eraseAssignPositionGemsAndUpdateChessBoard(erasableGems);

		// 2. 造成伤害
		List<GemPosition> poses = new ArrayList<GemPosition>();
		for (ChessBoardSnap each : newSnaps) {
			for (GemPosition eachPos : each.getErasableGems()) {
				if (eachPos.getType() == type.getIndex()) {
					poses.add(eachPos);
				}
			}
		}
		// 计算伤害加成值
		int addDamage = perDamage * (poses.size() / perCount);
		EffectResult result = BattleCalculator.calculateSkillFinalDamage(true,
				attacker, target, 0, addDamage);
		// 受击者掉血
		int hp = target.getBattleContext().getBattleProperty().getHp()
				- result.getFinalDamage();
		target.getBattleContext().getBattleProperty().setHp(hp);
		// 发送血量变化
		GCBattleHpChanged hpChangeMsg = new GCBattleHpChanged();
		hpChangeMsg.setTargetId(target.getUnitGuid());
		hpChangeMsg.setChangeHp(result.getFinalDamage());
		battle.broadcastToBattleUnits(hpChangeMsg);
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
		return result;
	}

}
