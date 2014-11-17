package com.hifun.soul.gameserver.battle.ai;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.Move;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.battle.msg.schedule.MonsterAIActionSchedule;
import com.hifun.soul.gameserver.battle.processor.IBattleDispatcher;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillType;

/**
 * 基础的怪物AI;
 * 
 * @author crazyjohn
 * 
 */
public class BaseMonsterAI implements IBattleAI {
	private IBattleUnit monster;

	public BaseMonsterAI(IBattleUnit monster) {
		this.monster = monster;
	}

	@Override
	public void action(long thinkTimes) {
		// 模拟怪物思考一段时间, 然后再开始行动; 这样出了更形象以外还可以一定程度上减轻服务器计算压力;
		Battle battle = monster.getBattleContext().getBattle();
		if (battle == null) {
			return;
		}
		IBattleDispatcher processor = battle.getBattleProcessor();
		if (processor == null) {
			return;
		}
		// 增加調度
		processor.scheduleOnece(new MonsterAIActionSchedule(battle, this), thinkTimes);
	}

	/**
	 * 怪物思考后的行动;
	 */
	public void afterThink() {
		// 是否处于战斗状态
		if (!monster.isInBattleState()) {
			return;
		}
		IBattleContext context = monster.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 一定几率的使用普通攻击或者其它技能;
		double skillRate = getUseSkillFactor();
		boolean useOtherSkill = BattleCalculator.diceRate(skillRate);
		if (useOtherSkill) {
			// 随机使用可以使用的技能;
			if (monster.forbidMagic()) {
				useNormalAttack(battle);
				return;
			}
			List<ISkill> canUseSkills = new ArrayList<ISkill>();
			for (ISkill eachSkill : context.getBattleSkills()) {
				if (eachSkill.getSkillTemplate().getSkillType() == SkillType.OTHER
						.getIndex()
						&& eachSkill.canUseSkill()
						&& monster.hasEnoughMagicToUseSuchSkill(eachSkill)) {
					canUseSkills.add(eachSkill);
				}
			}
			if (canUseSkills.isEmpty()) {
				useNormalAttack(battle);
				return;
			}
			ISkill chosedSkill = MathUtils.randomFromArray(canUseSkills
					.toArray(new ISkill[0]));
			battle.useOtherSkill(monster, chosedSkill, 0,
					new ArrayList<ChessBoardSnap>(), -1, -1);
			return;
		} else {
			useNormalAttack(battle);
		}

	}

	protected double getUseSkillFactor() {
		return 0.5;
	}

	private void useNormalAttack(Battle battle) {
		List<Move> moves = battle.getMoves();
		if (moves.size() == 0) {
			return;
		}
		Move move = MathUtils.randomFromArray(moves.toArray(new Move[0]));
		// 执行普通攻击
		battle.onNormalAttack(monster, move.getRow1(), move.getCol1(),
				move.getRow2(), move.getCol2());
	}

}
