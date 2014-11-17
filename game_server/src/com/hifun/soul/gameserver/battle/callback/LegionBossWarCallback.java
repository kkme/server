package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

public class LegionBossWarCallback extends BaseBattleCallback {
	private int hp;
	private int encourageRate;

	public LegionBossWarCallback(Human challenger, int hp, int encourageRate) {
		super(challenger);
		this.hp = hp;
		this.encourageRate = encourageRate;
	}

	public int getHp() {
		return hp;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getLegionBossService().damageBoss(challenger,
				getDamage(beChallenged), true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getLegionBossService().damageBoss(challenger,
				getDamage(beChallenged), false);
	}

	private int getDamage(IBattleUnit beChallenged) {
		return hp - beChallenged.getBattleContext().getBattleProperty().getHp();
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// 宝石攻击加成公式改为:encourageRate/10000.0*宝石攻击力
		// 技能攻击加成公式改为:encourageRate/10000.0*技能攻击力
		if (encourageRate != 0) {
			int skillAttack = challenger.getHumanPropertiesManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.ATTACK);
			skillAttack = (int) (encourageRate
					/ SharedConstants.DEFAULT_FLOAT_BASE * skillAttack);
			int gemAttack = challenger.getHumanPropertiesManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_ATTACK);
			gemAttack = (int) (encourageRate
					/ SharedConstants.DEFAULT_FLOAT_BASE * gemAttack);
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

		beChallenged.getBattleContext().getBattleProperty().setHp(hp);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE_LEGION_BOSS;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.BOSS_WAR_VIEW
				.getIndex());
	}

}
