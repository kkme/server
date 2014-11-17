package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;

/**
 * 根据生命值进行治疗的效果;<br>
 * params[0] = 恢复比率;<br>
 * 
 * @author crazyjohn
 * 
 */
public class HealByMaxHpEffector implements INoneAttackEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		Role<?> role = (Role<?>) attacker;
		// 直接恢复的生命值
		int maxHp = role.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.MAX_HP);
		int healValue = (int) ((params[0] / SharedConstants.PROPERTY_DIVISOR) * maxHp);

		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 攻击者加血
		int currentHp = attacker.getBattleContext().getBattleProperty().getHp();
		// 真实的回血数量;
		if (currentHp + healValue > maxHp) {
			healValue = maxHp - currentHp;
		}
		attacker.getBattleContext()
				.getBattleProperty()
				.setHp(attacker.getBattleContext().getBattleProperty().getHp()
						+ healValue);
		// 发送血量变化
		GCBattleHpChanged hpChangeMsg = new GCBattleHpChanged();
		hpChangeMsg.setTargetId(attacker.getUnitGuid());
		hpChangeMsg.setChangeHp(-healValue);
		battle.broadcastToBattleUnits(hpChangeMsg);

		return EffectResult.createNullEffect();
	}
}
