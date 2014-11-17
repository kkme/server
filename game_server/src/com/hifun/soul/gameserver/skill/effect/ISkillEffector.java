package com.hifun.soul.gameserver.skill.effect;

import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;

/**
 * 技能效果接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ISkillEffector {

	public EffectResult effect(IBattleUnit attacker, IBattleUnit target, ISkill skill,
			int[] params, int combo, List<ChessBoardSnap> snaps, GemPosition selectedGem);

}
