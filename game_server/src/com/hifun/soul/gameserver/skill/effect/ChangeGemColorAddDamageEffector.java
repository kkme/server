package com.hifun.soul.gameserver.skill.effect;

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
 * 转换宝石颜色的效果;<br>
 * params[0] = 源颜色;<br>
 * params[1] = 目标颜色;<br>
 * params[2] = 单位能量;<br>
 * params[3] = 单位伤害;<br>
 * 
 * @author crazyjohn
 * 
 */
public class ChangeGemColorAddDamageEffector extends AbstractDamageEffector {

	@Override
	protected EffectResult doEffect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 源颜色
		int srcColor = params[0];
		// 目标颜色
		int desColor = params[1];
		GemType srcType = GemType.typeOf(srcColor);
		GemType desType = GemType.typeOf(desColor);
		if (srcType == null) {
			return null;
		}
		if (desType == null) {
			return null;
		}
		int perCount = params[2];
		int perDamage = params[3];
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
		MagicChange change = attacker.updateMagicSlots(newSnaps);
		// 额外添加的伤害;
		int totalCount = battle.getLastChangeGemColorCount();
		int addDamage = totalCount / perCount * perDamage;
		// 计算技能伤害
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
		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(attacker.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(change);
		battle.broadcastToBattleUnits(magicChangeMsg);
		return EffectResult.createNullEffect();
	}

}
