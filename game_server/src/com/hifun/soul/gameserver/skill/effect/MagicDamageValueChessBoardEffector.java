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

/**
 * 魔法伤害值;<br>
 * 直接造成x点伤害, 棋盘中每x个x颜色的宝石造成x点伤害;<br>
 * params[0] = 直接伤害;<br>
 * params[1] = 能量类型;<br>
 * params[2] = 单位能量;<br>
 * params[3] = 单位伤害;<br>
 * 
 * @author crazyjohn
 * 
 */
public class MagicDamageValueChessBoardEffector extends AbstractDamageEffector {

	@Override
	protected EffectResult doEffect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 直接伤害值
		int damageValue = params[0];
		int energy = params[1];
		GemType energyType = GemType.typeOf(energy);
		if (energyType == null) {
			return null;
		}
		int perCount = params[2];
		int perDamage = params[3];
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		int addDamage = battle.getChessBoardGemCounts(energyType) / perCount * perDamage;
		// 计算技能伤害
		EffectResult result = BattleCalculator.calculateSkillFinalDamage(true,
				attacker, target, 0, damageValue + addDamage);
		// 受击者掉血
		int hp = target.getBattleContext().getBattleProperty().getHp()
				- result.getFinalDamage();
		target.getBattleContext().getBattleProperty().setHp(hp);

		// 发送血量变化
		GCBattleHpChanged hpChangeMsg = new GCBattleHpChanged();
		hpChangeMsg.setTargetId(target.getUnitGuid());
		hpChangeMsg.setChangeHp(result.getFinalDamage());
		battle.broadcastToBattleUnits(hpChangeMsg);

		return result;
	}

}
