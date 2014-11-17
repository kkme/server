package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

public class LegionMineWarCallback extends PVPBattleCallback {
	private LegionMineMember mineMember;

	public LegionMineWarCallback(Human challenger, LegionMineMember mineMember) {
		super(challenger);
		this.mineMember = mineMember;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		addBattleCd(challenger);
		GameServerAssist.getGlobalLegionMineWarManager().battleCallback(
				challenger, beChallenged.getUnitGuid(), true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		addBattleCd(challenger);
		GameServerAssist.getGlobalLegionMineWarManager().battleCallback(
				challenger, beChallenged.getUnitGuid(), false);
	}

	/**
	 * 添加战斗CD
	 */
	private void addBattleCd(Human chanllenger) {
		HumanCdManager cdManager = chanllenger.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.LEGION_MINE_WAR_BATTLE,
				GameServerAssist.getLegionMineWarTemplateManager()
						.getConstantsTemplate().getBattleBaseCdTime()
						* TimeUtils.SECOND);
		// 添加CD
		cdManager.addCd(CdType.LEGION_MINE_WAR_BATTLE, cd);
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// 攻击力加成
		int attackRate = mineMember.getAttackRate();
		// 宝石攻击加成公式:attackRate/10000.0*宝石攻击力
		// 技能攻击加成公式:attackRate/10000.0*技能攻击力
		if (attackRate != 0) {
			int skillAttack = challenger.getHumanPropertiesManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.ATTACK);
			skillAttack = (int) (attackRate
					/ SharedConstants.DEFAULT_FLOAT_BASE * skillAttack);
			int gemAttack = challenger.getHumanPropertiesManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_ATTACK);
			gemAttack = (int) (attackRate / SharedConstants.DEFAULT_FLOAT_BASE * gemAttack);
			challenger
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(
							AmendMethod.ADD,
							PropertyType.genPropertyKey(Level2Property.ATTACK,
									PropertyType.LEVEL2_PROPERTY), skillAttack);
			challenger
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(
							AmendMethod.ADD,
							PropertyType.genPropertyKey(
									Level2Property.GEM_ATTACK,
									PropertyType.LEVEL2_PROPERTY), gemAttack);
		}
		// 防御力加成
		int defenseRate = mineMember.getDefenseRate();
		if (defenseRate != 0) {
			// 宝石防御加成公式:attackRate/10000.0*宝石防御力
			// 技能防御加成公式:attackRate/10000.0*技能防御力
			int skillDefense = challenger.getHumanPropertiesManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.DEFENSE);
			skillDefense = (int) (defenseRate
					/ SharedConstants.DEFAULT_FLOAT_BASE * skillDefense);
			int gemDefense = challenger.getHumanPropertiesManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_DEFENSE);
			gemDefense = (int) (defenseRate
					/ SharedConstants.DEFAULT_FLOAT_BASE * gemDefense);
			challenger
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(
							AmendMethod.ADD,
							PropertyType.genPropertyKey(Level2Property.DEFENSE,
									PropertyType.LEVEL2_PROPERTY), skillDefense);
			challenger
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(
							AmendMethod.ADD,
							PropertyType.genPropertyKey(
									Level2Property.GEM_DEFENSE,
									PropertyType.LEVEL2_PROPERTY), gemDefense);
		}
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_LEGION_MINE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.LEGION_MINE_VIEW
				.getIndex());
	}

}
