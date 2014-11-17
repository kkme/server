package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.ArenaNotice;
import com.hifun.soul.gameserver.arena.converter.ArenaNoticeToEntityConverter;
import com.hifun.soul.gameserver.arena.template.ArenaBattleRewardTemplate;
import com.hifun.soul.gameserver.arena.template.ArenaConfigTemplate;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.ArenaChallengeEvent;
import com.hifun.soul.gameserver.human.Human;

/**
 * 竞技场战斗回调;
 * 
 * @author magicstone
 * 
 */
public class ArenaBattleFinishedCallback extends PVPBattleCallback {
	private ArenaNoticeToEntityConverter converter = new ArenaNoticeToEntityConverter();
	private Human human;
	/** 是否是前5名，且高排名挑战低排名 */
	private boolean isUpFiveAndHigherVsLower;

	public ArenaBattleFinishedCallback(Human challenger,
			boolean isUpFiveAndHigherVsLower) {
		super(challenger);
		human = challenger;
		this.isUpFiveAndHigherVsLower = isUpFiveAndHigherVsLower;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		// 消除战斗次数
		challenger.getHumanArenaManager().reduceArenaBattleTime();
		arenaBattleFinished(challenger, beChallenged, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		// 消除战斗次数
		challenger.getHumanArenaManager().reduceArenaBattleTime();
		arenaBattleFinished(challenger, beChallenged, false);
	}

	/**
	 * 战斗结束处理
	 * 
	 * @param challenger
	 * @param beChallenged
	 * @param isWin
	 */
	private void arenaBattleFinished(Human challenger,
			IBattleUnit beChallenged, boolean isWin) {
		// 添加战斗cd
		addBattleCd(challenger);

		// 被挑战者
		Human beChallenger = GameServerAssist.getGameWorld()
				.getSceneHumanManager()
				.getHumanByGuid(beChallenged.getUnitGuid());
		// 给予奖励
		giveBattleReward(challenger, beChallenger, isWin);

		// 战斗后排名变更，变更规则是挑战者变为被挑战者排名，其他顺次后移
		if (isWin) {
			// 如果是前5名，而且是高排名挑战低排名，排名不变
			if (!isUpFiveAndHigherVsLower) {
				GameServerAssist.getArenaService().rankArenaMember(
						challenger.getHumanGuid(), beChallenged.getUnitGuid());
			}
			// 向战俘营中推送手下败将
			GameServerAssist.getGlobalPrisonManager().addLooser(challenger,
					beChallenged.getUnitGuid());
		}

		// 根据战斗结果生成提示信息
		refreshArenaNotice(challenger, beChallenger, isWin, beChallenged);

		// 更新竞技场面板
		ArenaMember arenaMember = GameServerAssist.getArenaService()
				.getArenaMember(challenger.getHumanGuid());
		challenger.getHumanArenaManager().updateArenaPanel(arenaMember,
				GameServerAssist.getArenaService(),
				GameServerAssist.getTemplateService());
		// 发送事件
		human.getEventBus().fireEvent(new ArenaChallengeEvent());
	}

	/**
	 * 添加战斗cd
	 * 
	 * @param challenger
	 */
	private void addBattleCd(Human challenger) {
		// 添加战斗cd
		ArenaConfigTemplate configTemplate = GameServerAssist
				.getTemplateService().get(
						SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
						ArenaConfigTemplate.class);
		HumanCdManager cdManager = challenger.getHumanCdManager();
		long spendTime = cdManager.getSpendTime(CdType.ARENA_BATTLE,
				configTemplate.getBattleCd() * TimeUtils.SECOND);
		cdManager.addCd(CdType.ARENA_BATTLE, spendTime);
		// 下发战斗cd
		cdManager.snapCdQueueInfo(CdType.ARENA_BATTLE);
	}

	/**
	 * 给予战斗奖励
	 * 
	 * @param challenger
	 * @param beChallenger
	 * @param isWin
	 */
	private void giveBattleReward(Human challenger, Human beChallenger,
			boolean isWin) {
		// 取出竞技场数据
		ArenaMember arenaMember = GameServerAssist.getArenaService()
				.getArenaMember(challenger.getHumanGuid());
		// 战斗后根据竞技场排名给予奖励：挑战者战胜获得全额奖励，战败奖励减半；被挑战者没有奖励
		ArenaBattleRewardTemplate battleRewardTemplate = GameServerAssist
				.getArenaTemplateManager().getSuitableBattleRewardTemplate(
						arenaMember.getRank());
		if (battleRewardTemplate != null) {
			int experience = battleRewardTemplate.getExperience();
			int coin = battleRewardTemplate.getMoney();
			int honour = battleRewardTemplate.getHonour();
			// 挑战者
			if (challenger != null) {
				int addExperience = experience;
				int addCoin = coin;
				int addHonour = honour;

				if (!isWin) {
					addExperience = addExperience / 2;
					addCoin = addCoin / 2;
					addHonour = addHonour / 2;
				}

				challenger.addExperience(addExperience, true,
						ExperienceLogReason.ARENA_BATTLE_ADD_EXP, "");
				challenger.getWallet().addMoney(CurrencyType.COIN, addCoin,
						true, MoneyLogReason.ARENA_BATTLE_REWARD, "");
				challenger.addArenaHonor(addHonour, true,
						HonourLogReason.ARENA_BATTLE_ADD_HONOUR, "");

			}
		}
	}

	/**
	 * 战斗后提示信息
	 * 
	 * @param challenger
	 * @param beChallenger
	 * @param isWin
	 * @param beChallenged
	 */
	private void refreshArenaNotice(Human challenger, Human beChallenger,
			boolean isWin, IBattleUnit beChallenged) {
		long now = GameServerAssist.getSystemTimeService().now();
		ArenaMember self = GameServerAssist.getArenaService().getArenaMember(
				challenger.getHumanGuid());
		ArenaMember other = GameServerAssist.getArenaService().getArenaMember(
				beChallenged.getUnitGuid());

		ArenaNotice challengerNotice = new ArenaNotice();
		challengerNotice.setRoleId(challenger.getHumanGuid());
		challengerNotice.setRoleName(challenger.getName());
		challengerNotice.setOtherRoleId(beChallenged.getUnitGuid());
		challengerNotice.setOtherRoleName(beChallenged.getUnitName());
		challengerNotice.setWin(isWin);
		challengerNotice.setChallengeTime(now);
		challengerNotice.setIsChallenger(true);
		challengerNotice.setRank(self.getRank());
		challengerNotice.setIsUpFiveAndHigherVsLower(isUpFiveAndHigherVsLower);
		challenger.getHumanArenaManager().addArenaNotice(challengerNotice);

		ArenaNotice beChallengerNotice = new ArenaNotice();
		beChallengerNotice.setRoleId(beChallenged.getUnitGuid());
		beChallengerNotice.setRoleName(beChallenged.getUnitName());
		beChallengerNotice.setOtherRoleId(challenger.getHumanGuid());
		beChallengerNotice.setOtherRoleName(challenger.getName());
		beChallengerNotice.setWin(!isWin);
		beChallengerNotice.setChallengeTime(now);
		beChallengerNotice.setIsChallenger(false);
		beChallengerNotice.setRank(other.getRank());
		beChallengerNotice
				.setIsUpFiveAndHigherVsLower(isUpFiveAndHigherVsLower);
		if (beChallenger != null) {
			beChallenger.getHumanArenaManager().addArenaNotice(
					beChallengerNotice);
		}

		GameServerAssist.getDataService().insert(
				converter.convert(challengerNotice), null);
		GameServerAssist.getDataService().insert(
				converter.convert(beChallengerNotice), null);

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_ARENA;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.ARENA_VIEW.getIndex());
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(human.getHumanGuid());
		requestBattle.setChallengerName(human.getName());
		requestBattle.setContent(GameServerAssist.getSysLangService().read(
				LangConstants.ARENA_BATTLE));
		beChallenge.sendMessage(requestBattle);
	}

}
