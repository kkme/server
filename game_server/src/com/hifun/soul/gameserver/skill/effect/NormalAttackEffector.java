package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 普通攻击效果;
 * 
 * @author crazyjohn
 * 
 */
public class NormalAttackEffector extends AbstractDamageEffector {
	
	@Override
	protected EffectResult doEffect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		
		// 计算最终伤害值
		EffectResult result = BattleCalculator.calculateNormalAttakFinalDamage(
				attacker, target, 0, 0, combo);
		// 受击者掉血
		int hp = target.getBattleContext().getBattleProperty().getHp()
				- result.getFinalDamage();
		target.getBattleContext().getBattleProperty().setHp(hp);
		
		// 设置魔法变化
		MagicChange attackerMagicChanged = attacker.updateMagicSlots(snaps);

		// 发送血量变化
		GCBattleHpChanged hpChangeMsg = new GCBattleHpChanged();
		hpChangeMsg.setTargetId(target.getUnitGuid());
		hpChangeMsg.setChangeHp(result.getFinalDamage());
		battle.broadcastToBattleUnits(hpChangeMsg);
		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(attacker.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(attackerMagicChanged);
		battle.broadcastToBattleUnits(magicChangeMsg);
		return result;
	}
}
