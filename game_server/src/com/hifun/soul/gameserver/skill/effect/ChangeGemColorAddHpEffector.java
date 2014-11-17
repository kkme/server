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
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 转换宝石颜色的效果;<br>
 * params[0] = 源颜色;<br>
 * params[1] = 目标颜色;<br>
 * params[2] = 单位数量;<br>
 * params[3] = 单位hp增加量;<br>
 * 
 * @author crazyjohn
 * 
 */
public class ChangeGemColorAddHpEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 源颜色
		int srcColor = params[0];
		// 目标颜色
		int desColor = params[1];
		int perCount = params[2];
		int perAdd = params[3];
		GemType srcType = GemType.typeOf(srcColor);
		GemType desType = GemType.typeOf(desColor);
		if (srcType == null) {
			return null;
		}
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
		List<ChessBoardSnap> newSnaps = battle.changeGemColor(srcType, desType);
		int totalCount = battle.getLastChangeGemColorCount();
		int addHp = (totalCount / perCount) * perAdd;
		if (addHp > 0) {
			context.getBattleProperty().setHp(
					context.getBattleProperty().getHp() + addHp);
			// 发送血量变化, 血量增加;
			GCBattleHpChanged hpChangeMsg = new GCBattleHpChanged();
			hpChangeMsg.setTargetId(attacker.getUnitGuid());
			hpChangeMsg.setChangeHp(-addHp);
			battle.broadcastToBattleUnits(hpChangeMsg);
		}
		MagicChange change = attacker.updateMagicSlots(newSnaps);

		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(attacker.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(change);
		battle.broadcastToBattleUnits(magicChangeMsg);
		return EffectResult.createNullEffect();
	}
}
