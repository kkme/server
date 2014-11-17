package com.hifun.soul.gameserver.skill.effect.select;

import java.util.Collection;
import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.erase.ErasePositionType;
import com.hifun.soul.gameserver.battle.gem.erase.EraseRangeType;
import com.hifun.soul.gameserver.battle.gem.function.AllTypeFunctionGem;
import com.hifun.soul.gameserver.battle.gem.function.AttackFunctionGems;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.effect.INoneAttackEffector;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 消除指定位置宝石的效果; params[0] = 位置类型;<br>
 * params[1] = 范围类型;<br>
 * 
 * @author crazyjohn
 * 
 */
public class EraseAssignPositionGemsEffector implements INoneAttackEffector {
	/** 全能宝石 */
	private AllTypeFunctionGem allTypeGem = new AllTypeFunctionGem();
	/** 强袭宝石 */
	private AttackFunctionGems attackGem = new AttackFunctionGems();

	@Override
	public EffectResult effect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
		// 如果行动单元是怪物的话,系统随机选择一个保护司
		// 消除指定位置并且添加能量;
		int positionType = params[0];
		ErasePositionType erasePosType = ErasePositionType.typeOf(positionType);
		if (erasePosType == null) {
			return null;
		}
		selectedGem = SelectGemUtil.getSelectedGem(attacker, selectedGem,
				erasePosType);
		// 获取范围类型
		int rangeType = params[1];
		EraseRangeType eraseRangeType = EraseRangeType.typeOf(rangeType);
		if (eraseRangeType == null) {
			return null;
		}
		IBattleContext context = attacker.getBattleContext();
		if (context == null) {
			return null;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return null;
		}
		// 获取可以消除的宝石
		Collection<GemPosition> erasableGems = eraseRangeType
				.getRangeGemsByRange(battle, skill.needSelectedGem(),
						selectedGem);
		// 消除指定的宝石
		List<ChessBoardSnap> newSnaps = battle
				.eraseAssignPositionGemsAndUpdateChessBoard(erasableGems);
		// 黑白宝石过滤
		for (ChessBoardSnap eachSnap : newSnaps) {
			this.allTypeGem.doEffect(attacker, eachSnap);
			this.attackGem.doEffect(attacker, eachSnap);
		}
		// 设置魔法变化
		MagicChange attackerMagicChanged = attacker.updateMagicSlots(newSnaps);

		// 发送魔法变化
		GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
		magicChangeMsg.setTargetId(attacker.getUnitGuid());
		magicChangeMsg.setTargetMagicChange(attackerMagicChanged);
		battle.broadcastToBattleUnits(magicChangeMsg);
		return EffectResult.createNullEffect();
	}

}
