package com.hifun.soul.gameserver.arena.manager;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.ArenaNoticeEntity;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.ArenaNotice;
import com.hifun.soul.gameserver.arena.ArenaRankRewardState;
import com.hifun.soul.gameserver.arena.converter.ArenaNoticeToEntityConverter;
import com.hifun.soul.gameserver.arena.msg.GCGetArenaRankReward;
import com.hifun.soul.gameserver.arena.msg.GCOpenArenaPanel;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.arena.template.ArenaConfigTemplate;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.template.SpreeTemplate;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.rank.template.RankRewardTemplate;
import com.hifun.soul.gameserver.rank.template.RankTemplate;
import com.hifun.soul.gameserver.reward.RewardType;
import com.hifun.soul.gameserver.reward.service.RewardService;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

public class HumanArenaManager implements ILoginManager, IEventListener {
	private static Logger logger = Loggers.ARENA_LOGGER;
	private Human _human;
	private LinkedList<ArenaNotice> arenaNoticeList = new LinkedList<ArenaNotice>();
	private ArenaNoticeToEntityConverter converter = new ArenaNoticeToEntityConverter();

	public HumanArenaManager(Human human) {
		this._human = human;
		_human.registerLoginManager(this);
		_human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	public Human getHuman() {
		return _human;
	}

	public int getArenaBattleTime() {
		return _human.getArenaBattleTime();
	}

	public void addArenaBattleTime() {
		_human.setArenaBattleTime(getArenaBattleTime() + 1);
	}

	public void reduceArenaBattleTime() {
		_human.setArenaBattleTime(getArenaBattleTime() - 1);
	}

	public void setArenaBattleTime(int arenaBattleTime) {
		_human.setArenaBattleTime(arenaBattleTime);
	}

	public int getMaxArenaBuyTime() {
		return _human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.MAX_ARENA_BUY_TIMES);
	}

	public void addMaxArenaBuyTime() {
		_human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.MAX_ARENA_BUY_TIMES, getMaxArenaBuyTime() + 1);
	}

	public void setMaxArenaBuyTime(int buyTimes) {
		_human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.MAX_ARENA_BUY_TIMES, buyTimes);
	}

	public int getArenaHonour() {
		return _human.getArenaHonor();
	}

	public void setLastResetBattleTimeTime(long lastTime) {
		_human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_ARENA_BATTLE_TIME_RESET_TIME,
				lastTime);
	}

	public long getLastResetBattleTimeTime() {
		return _human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_ARENA_BATTLE_TIME_RESET_TIME);
	}

	public void setLastResetRankRewardTime(long lastTime) {
		_human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_ARENA_RANK_REWARD_RESET_TIME,
				lastTime);
	}

	public long getLastResetRankRewardTime() {
		return _human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_ARENA_RANK_REWARD_RESET_TIME);
	}

	public void setLastResetBuyTimeTime(long lastTime) {
		_human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_ARENA_BUY_TIME_RESET_TIME,
				lastTime);
	}

	public long getLastResetBuyTimeTime() {
		return _human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_ARENA_BUY_TIME_RESET_TIME);
	}

	/**
	 * 获取竞技场提示信息
	 * 
	 * @return
	 */
	public List<ArenaNotice> getArenaNotices() {
		return arenaNoticeList;
	}

	/**
	 * 添加竞技场提示
	 * 
	 * @param arenaNotice
	 */
	public void addArenaNotice(ArenaNotice arenaNotice) {
		if (arenaNoticeList.size() >= SharedConstants.ARENA_NOTICE_NUM) {
			// 库中要移除
			GameServerAssist.getDataService().delete(converter.convert(
					arenaNoticeList.getFirst()));
			arenaNoticeList.removeFirst();
		}

		arenaNoticeList.addLast(arenaNotice);
	}

	/**
	 * 更新竞技场面板
	 * 
	 * @param arenaMember
	 * @param arenaService
	 * @param templateService
	 */
	public void updateArenaPanel(ArenaMember arenaMember, ArenaService arenaService, TemplateService templateService) {
		// 下发CD信息
		_human.getHumanCdManager().snapCdQueueInfo(CdType.ARENA_BATTLE);
		
		long now = GameServerAssist.getSystemTimeService().now();
		GCOpenArenaPanel gcMsg = new GCOpenArenaPanel();
		int rank = arenaMember.getRank();
		gcMsg.setRank(rank);
		gcMsg.setArenaMembers(arenaService.getVisibleArenaMembers(rank).toArray(new ArenaMember[0]));
		ArenaNotice[] arenaNotices = new ArenaNotice[getArenaNotices().size()];
		int i=0;
		for(ArenaNotice arenaNotice : getArenaNotices()){
			arenaNotice.setTimeInterval((int) (now - arenaNotice.getChallengeTime()));
			arenaNotices[i] = arenaNotice;
			i++;
		}
		gcMsg.setArenaNotices(arenaNotices);
		gcMsg.setBattleTimes(getArenaBattleTime());
		gcMsg.setCrystal(GameServerAssist.getArenaTemplateManager()
				.getBuyTimeCost(getMaxArenaBuyTime()+1));
		gcMsg.setCurrentHonor(getArenaHonour());
		gcMsg.setMaxHonor(GameServerAssist.getTitleTemplateManager().getTitleMaxHonor(_human.getCurrentTitle()));
		gcMsg.setRewardState(arenaMember.getRankRewardState());
		// 竞技场奖励模版
		RankTemplate rankTemplate = GameServerAssist.getRankTemplateManager().getRankTemplate(RankType.ARENA_RANK.getIndex());
		int giftCd = 0;
		if(arenaService.getLastResetRankRewardTime() <= 0){
			giftCd = (int) (rankTemplate.getDays()*TimeUtils.DAY-(GameServerAssist.getSystemTimeService().now()-TimeUtils.getBeginOfDay(GameServerAssist.getSystemTimeService().now())));
		}
		else{
			giftCd = (int) (rankTemplate.getDays()*TimeUtils.DAY-(GameServerAssist.getSystemTimeService().now()-TimeUtils.getBeginOfDay(arenaService.getLastResetRankRewardTime())));
		}
		gcMsg.setGiftCd(giftCd);
		RankRewardTemplate rankRewardTemplate = GameServerAssist.getRankTemplateManager().getRankRewardTemplate(RankType.ARENA_RANK.getIndex(), rank);
		if(rankRewardTemplate != null){
			SpreeTemplate spreeTemplate = GameServerAssist.getSpreeTemplateManager().getSpreeTemplate(rankRewardTemplate.getGiftId());
			gcMsg.setGiftCoin(spreeTemplate.getCoinNum());
			gcMsg.setGiftHonor(spreeTemplate.getHonourNum());
		}
		gcMsg.setHonorIsFull(false);
		_human.sendMessage(gcMsg);
	}

	@Override
	public void onLogin() {
		sendRewardNotify();
		GameServerAssist.getDataService().query(DataQueryConstants.QUERY_ARENA_NOTICE_BY_HUMANGUID, new String[] { "roleId" },
				new Object[] { _human.getHumanGuid() }, new IDBCallback<List<?>>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onSucceed(List<?> result) {
						if (!result.isEmpty()) {
							List<ArenaNoticeEntity> arenaNotices = (List<ArenaNoticeEntity>) result;
							for (ArenaNoticeEntity arenaNoticeEntity : arenaNotices) {
								ArenaNotice arenaNotice = converter.reverseConvert(arenaNoticeEntity);
								arenaNoticeList.addFirst(arenaNotice);
							}
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error(errorMsg);
					}
				});
	}

	/**
	 * 重置战斗次数
	 */
	public void resetBattleTime() {
		ArenaConfigTemplate template = GameServerAssist.getTemplateService().get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
				ArenaConfigTemplate.class);
		setArenaBattleTime(template.getMaxBattleTimes());
	}

	/**
	 * 重置购买次数
	 */
	public void resetBuyTime() {
		setMaxArenaBuyTime(0);
	}

	@Override
	public void onEvent(IEvent event) {
		updateArenaMember();
	}

	private void updateArenaMember() {
		ArenaMember arenaMember = GameServerAssist.getArenaService().getArenaMember(_human.getHumanGuid());
		if (arenaMember != null) {
			arenaMember.setLevel(_human.getLevel());
			GameServerAssist.getArenaService().updateArenaMember(arenaMember.getRoleId());
		}
	}

	/**
	 * 是否能领取竞技场排名奖励
	 * 
	 * @return
	 */
	public boolean canGetArenaRankReward() {
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(_human, GameFuncType.ARENA, false)) {
			return false;
		}
		ArenaMember arenaMember = GameServerAssist.getArenaService().getArenaMember(_human.getHumanGuid());
		if (arenaMember == null) {
			return false;
		}
		// 判断竞技场排名奖励是否可以领取
		if (!GameServerAssist.getArenaService().hasRankReward(_human.getHumanGuid())) {
			return false;
		}
		return true;
	}

	public void recieveArenaRankReward() {
		// 判断功能是否开放
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(_human, GameFuncType.ARENA, true)) {
			return;
		}

		ArenaMember arenaMember = GameServerAssist.getArenaService().getArenaMember(_human.getHumanGuid());
		if (arenaMember == null) {
			return;
		}

		// 判断竞技场排名奖励是否可以领取
		if (!GameServerAssist.getArenaService().hasRankReward(_human.getHumanGuid())) {
			_human.sendErrorMessage(LangConstants.ARENA_NO_RANK_REWARD);
			return;
		}

		// 判断是否有空余的格子
		if (_human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 1) {
			_human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}

		// 获取奖励模版
		RankRewardTemplate rankRewardTemplate = GameServerAssist.getTemplateService().get(arenaMember.getRankRewardId(),
				RankRewardTemplate.class);
		if (rankRewardTemplate == null) {
			logger.error(String.format("can not find ArenaRankRewardTemplate! humanGuid: %d templateId: %d",
					_human.getHumanGuid(), arenaMember.getRankRewardId()));
			return;
		}

		// 设置奖励领取状态
		arenaMember.setRankRewardState(ArenaRankRewardState.GETTED.getIndex());
		GameServerAssist.getArenaService().updateArenaMember(arenaMember.getRoleId());

		// 给玩家奖励
		_human.getBagManager().putItems(BagType.MAIN_BAG, rankRewardTemplate.getGiftId(), 1,
				ItemLogReason.ARENA_RANK_REWARD, "");

		// 通知玩家领取状态变更
		GCGetArenaRankReward gcMsg = new GCGetArenaRankReward();
		gcMsg.setRewardState(arenaMember.getRankRewardState());
		_human.sendMessage(gcMsg);
		// 从可领取奖励列表中移除该奖励
		GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(_human,RewardType.ARENA_RANK_REWARD);
	}
	
	public void sendRewardNotify(){
		if(canGetArenaRankReward()){
			RewardService rewardService = ApplicationContext.getApplicationContext().getBean(RewardService.class);
			rewardService.sendAddCommonRewardMessage(_human, RewardType.ARENA_RANK_REWARD);
		}
	}
}
