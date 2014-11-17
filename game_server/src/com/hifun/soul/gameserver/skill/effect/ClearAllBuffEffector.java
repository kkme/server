package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.ClearBuffType;

/**
 * 清除所有buff的效果;<br>
 * params[0] = 目标;<br>
 * params[1] = 清除类型;<br>
 * 
 * @author crazyjohn
 * 
 */
public class ClearAllBuffEffector implements IControlableEffector {

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		boolean isSelf = (params[0] == 1);
		if (isSelf) {
			doClearAction(params[1], attacker);
		} else {
			doClearAction(params[1], target);
		}
		return EffectResult.createNullEffect();
	}

	/**
	 * 指定清除动作;
	 * 
	 * @param clearType
	 * @param attacker
	 */
	private void doClearAction(int clearType, IBattleUnit target) {
		ClearBuffType.typeOf(clearType).doClearAction(target);
	}

}
