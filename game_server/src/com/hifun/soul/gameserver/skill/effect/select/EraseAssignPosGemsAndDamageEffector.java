package com.hifun.soul.gameserver.skill.effect.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.gem.erase.ErasePositionType;
import com.hifun.soul.gameserver.battle.gem.erase.EraseRangeType;
import com.hifun.soul.gameserver.battle.gem.function.AllTypeFunctionGem;
import com.hifun.soul.gameserver.battle.gem.function.AttackFunctionGems;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.EffectResult;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.effect.AbsorbType;
import com.hifun.soul.gameserver.skill.effect.AbstractDamageEffector;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 消除指定范围的宝石;<br>
 * params[0] = 位置类型;<br>
 * params[1] = 范围类型;<br>
 * params[2] = 单位宝石;<br>
 * params[3] = 宝石颜色;<br>
 * params[4] = 单位伤害;<br>
 * params[5] = 消除的宝石能量是否会被吸收;<br>
 * 
 * @author crazyjohn
 * 
 */
public class EraseAssignPosGemsAndDamageEffector extends AbstractDamageEffector {
	/** 全能宝石 */
	private AllTypeFunctionGem allTypeGem = new AllTypeFunctionGem();
	/** 强袭宝石 */
	private AttackFunctionGems attackGem = new AttackFunctionGems();
	
	
	@Override
	protected EffectResult doEffect(IBattleUnit attacker, IBattleUnit target,
			ISkill skill, int[] params, int combo, List<ChessBoardSnap> snaps,
			GemPosition selectedGem) {
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
		// 造成伤害的宝石单位
		int perCount = params[2];
		// 宝石颜色
		int color = params[3];
		GemType gemType = GemType.typeOf(color);
		if (gemType == null) {
			return null;
		}
		// 单位伤害值
		int perDamage = params[4];
		if (eraseRangeType == null) {
			return null;
		}
		// 消除的能量是否会被吸收
		int absorb = params[5];
		AbsorbType absorbType = AbsorbType.typeOf(absorb);
		if (absorbType == null) {
			throw new IllegalArgumentException("AbsortType can not be null.");
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

		// 2. 造成伤害
		List<GemPosition> poses = new ArrayList<GemPosition>();
		for (ChessBoardSnap each : newSnaps) {
			for (GemPosition eachPos : each.getErasableGems()) {
				if (eachPos.getType() == gemType.getIndex()) {
					poses.add(eachPos);
				}
			}
		}
		int addDamage = perDamage * (poses.size() / perCount);
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
		// 设置魔法变化
		if (absorbType == AbsorbType.YES) {
			MagicChange attackerMagicChanged = attacker
					.updateMagicSlots(newSnaps);
			// 黑白宝石过滤
			for (ChessBoardSnap eachSnap : newSnaps) {
				this.allTypeGem.doEffect(attacker, eachSnap);
				this.attackGem.doEffect(attacker, eachSnap);
			}
			// 发送魔法变化
			GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
			magicChangeMsg.setTargetId(attacker.getUnitGuid());
			magicChangeMsg.setTargetMagicChange(attackerMagicChanged);
			battle.broadcastToBattleUnits(magicChangeMsg);
		}
		return result;
	}

}
