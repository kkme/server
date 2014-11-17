package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;

/**
 * 基础的攻击类效果抽象;
 * 
 * @author crazyjohn
 * 
 */
public abstract class AbstractDamageEffector implements ISkillEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target, ISkill skill,
			int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// // 判断角色是否死亡
		// if (target.isDead()) {
		// attacker.getBattleContext().getBattle().onBattleFinished();
		// }
		return doEffect(attacker, target, skill, params, combo, snaps, selectedGem);
	}

	/**
	 * 留给子类实现的效果解释接口;
	 * 
	 * @param attacker
	 * @param target
	 * @param skill
	 * @param params
	 * @param combo
	 * @param snaps
	 */
	protected abstract EffectResult doEffect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem);
}
