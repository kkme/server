package com.hifun.soul.gameserver.timetask.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.IHeartBeatListener;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.HumanActivityManager;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.antiindulge.service.TencentService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;
import com.hifun.soul.gameserver.timetask.model.AutoRecoverEnergyDailyTask;
import com.hifun.soul.gameserver.timetask.model.EnergyRecoverTask;
import com.hifun.soul.gameserver.timetask.model.HumanDailyQuestEndTask;
import com.hifun.soul.gameserver.timetask.model.HumanLegionEndTask;
import com.hifun.soul.gameserver.timetask.model.HumanLegionTitleTask;
import com.hifun.soul.gameserver.timetask.model.NostrumOutDateTimeTask;
import com.hifun.soul.gameserver.timetask.model.RefreshHumanLegionTask;
import com.hifun.soul.gameserver.timetask.model.RefreshSpecialShopTask;
import com.hifun.soul.gameserver.timetask.model.ResetAbattoirDataTask;
import com.hifun.soul.gameserver.timetask.model.ResetAnswerQuestionDailyTask;
import com.hifun.soul.gameserver.timetask.model.ResetAnswerQuestionWeeklyTask;
import com.hifun.soul.gameserver.timetask.model.ResetArenaBattleTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetArenaBuyTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetAssistMeditationRemainTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetBloodTempleDataTask;
import com.hifun.soul.gameserver.timetask.model.ResetBuyEnergyTimesDailyTask;
import com.hifun.soul.gameserver.timetask.model.ResetCdTiredTask;
import com.hifun.soul.gameserver.timetask.model.ResetCrystalExchangeTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetDailyQuestTask;
import com.hifun.soul.gameserver.timetask.model.ResetEliteStageTask;
import com.hifun.soul.gameserver.timetask.model.ResetFreeEquipForgeTimesTask;
import com.hifun.soul.gameserver.timetask.model.ResetFriendRewardTask;
import com.hifun.soul.gameserver.timetask.model.ResetHoroscopeGambleTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetHumanEnergyDailyTask;
import com.hifun.soul.gameserver.timetask.model.ResetHumanEscortDataTask;
import com.hifun.soul.gameserver.timetask.model.ResetHumanLegionDataDailyTask;
import com.hifun.soul.gameserver.timetask.model.ResetHumanMarsDataDailyTask;
import com.hifun.soul.gameserver.timetask.model.ResetHumanSpritePubDailyDataTask;
import com.hifun.soul.gameserver.timetask.model.ResetLevyTask;
import com.hifun.soul.gameserver.timetask.model.ResetLoginRewardTask;
import com.hifun.soul.gameserver.timetask.model.ResetMineDataDailyTask;
import com.hifun.soul.gameserver.timetask.model.ResetRefineRefreshTimesTask;
import com.hifun.soul.gameserver.timetask.model.ResetSpecialShopRefreshTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetSubmitGmQuestionTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetTitleGetSalaryTask;
import com.hifun.soul.gameserver.timetask.model.ResetTotalRechargeWeeklyTask;
import com.hifun.soul.gameserver.timetask.model.ResetTrainingTimeTask;
import com.hifun.soul.gameserver.timetask.model.ResetTurntableNumTask;
import com.hifun.soul.gameserver.timetask.model.ResetWarriorDataTask;
import com.hifun.soul.gameserver.timetask.model.ResetYellowVipDailyDataTask;
import com.hifun.soul.gameserver.timetask.model.TemporaryVipTask;
import com.hifun.soul.gameserver.timetask.model.UpdateRevenueRateTask;

public class HumanTimeTaskManager implements IHeartBeatListener {

	private Human human;
	private List<IHeartBeatTask> tasks = new ArrayList<IHeartBeatTask>();
	private Logger logger = Loggers.TIMMER_LOGGER;
	
	public HumanTimeTaskManager(Human human) {
		this.human = human;
		
		registerTasks();
	}
	
	public Human getHuman() {
		return human;
	}
	
	/**
	 * 所有定时相关的任务
	 * 
	 */
	private void registerTasks() {

		/** vip体验倒计时 */
		TemporaryVipTask  temporaryVipTask = new TemporaryVipTask(human);
		tasks.add(temporaryVipTask);
		
		ResetTurntableNumTask resetAstrologicalNumTask = new ResetTurntableNumTask(human.getHumanTurntableManager());
		tasks.add(resetAstrologicalNumTask);
		
		ResetCrystalExchangeTimeTask resetCrystalExchangeTimeTask = new ResetCrystalExchangeTimeTask(human.getHumanCrystalExchangeManager());
		tasks.add(resetCrystalExchangeTimeTask);
		
		/** 重置训练数据 */
		ResetTrainingTimeTask resetTrainingTimeTask = new ResetTrainingTimeTask(human.getHumanTrainingManager());
		tasks.add(resetTrainingTimeTask);
		
		/** 防沉迷收入倍率更新 */
		UpdateRevenueRateTask updateRevenueRateTask = new UpdateRevenueRateTask(human,new TencentService());
		tasks.add(updateRevenueRateTask);
		
		ResetCdTiredTask resetCdTiredTask = new ResetCdTiredTask(human.getHumanCdManager());
		tasks.add(resetCdTiredTask);
		
		/** 重置税收数据 */
		ResetLevyTask  resetLevyTask = new ResetLevyTask(human.getLevyManager());
		tasks.add(resetLevyTask);
		
		/** 恢复体力任务 */
		EnergyRecoverTask energyRecoverTask = new EnergyRecoverTask(human);
		tasks.add(energyRecoverTask);
		
		/** 重置购买体力次数任务 */
		ResetBuyEnergyTimesDailyTask resetBuyEnergyTimesTask = new ResetBuyEnergyTimesDailyTask(human);
		tasks.add(resetBuyEnergyTimesTask);
		
		/** 重置问答活动相关数据 */
		HumanActivityManager activityManager = human.getHumanActivityManager();
		AnswerQuestionManager answerQuestionManager = (AnswerQuestionManager)activityManager.getActivityManager(ActivityType.ANSWER_QUESTION);
		if(answerQuestionManager!=null){
			ResetAnswerQuestionDailyTask resetAnswerQuestionDailyTask = new ResetAnswerQuestionDailyTask(answerQuestionManager);
			tasks.add(resetAnswerQuestionDailyTask);
			ResetAnswerQuestionWeeklyTask resetAnswerQuestionWeeklyTask = new ResetAnswerQuestionWeeklyTask(answerQuestionManager);
			tasks.add(resetAnswerQuestionWeeklyTask);
		}
		
		/** 竞技场战斗次数刷新 */
		ResetArenaBattleTimeTask resetArenaBattleTimeTask = new ResetArenaBattleTimeTask(human.getHumanArenaManager());
		tasks.add(resetArenaBattleTimeTask);
		
		/** 有奖励的协助冥想剩余次数刷新 */
		ResetAssistMeditationRemainTimeTask resetAssistMeditationTask = new ResetAssistMeditationRemainTimeTask(human);
		tasks.add(resetAssistMeditationTask);
		
		/** 竞技场购买次数刷新 */
		ResetArenaBuyTimeTask resetArenaBuyTimeTask = new ResetArenaBuyTimeTask(human.getHumanArenaManager());
		tasks.add(resetArenaBuyTimeTask);
		
		/** 每日任务刷新 */
		ResetDailyQuestTask resetDailyQuestTask = new ResetDailyQuestTask(human.getHumanQuestManager());
		tasks.add(resetDailyQuestTask);
		
		/** 重置占星次数 */
		ResetHoroscopeGambleTimeTask resetHoroscopeGambleTimeTask = new ResetHoroscopeGambleTimeTask(human.getHumanHoroscopeManager());
		tasks.add(resetHoroscopeGambleTimeTask);
		
		/** 每日重置矿场数据 */
		ResetMineDataDailyTask resetMineDataDailyTask = new ResetMineDataDailyTask(human);
		tasks.add(resetMineDataDailyTask);
		
		/** 神秘商店刷新的定时任务 */
		RefreshSpecialShopTask refreshSpecialShopTask = new RefreshSpecialShopTask(human.getHumanSpecialShopManager());
		tasks.add(refreshSpecialShopTask);
		
		/** 精英怪刷新 */
		ResetEliteStageTask resetEliteStageTask = new ResetEliteStageTask(human);
		tasks.add(resetEliteStageTask);
		
		/** 好友奖励 */
		ResetFriendRewardTask resetFriendRewardTask = new ResetFriendRewardTask(human.getHumanFriendManager());
		tasks.add(resetFriendRewardTask);
		
		/** 登陆奖励 */
		ResetLoginRewardTask resetLoginRewardTask = new ResetLoginRewardTask(human.getHumanLoginRewardManager());
		tasks.add(resetLoginRewardTask);
		
		/** gm问答 */
		ResetSubmitGmQuestionTimeTask resetSubmitGmQuestionTimeTask = new ResetSubmitGmQuestionTimeTask(human);
		tasks.add(resetSubmitGmQuestionTimeTask);
		
		/** 神秘商店刷新次数 */
		ResetSpecialShopRefreshTimeTask resetSpecialShopRefreshTimeTask = new ResetSpecialShopRefreshTimeTask(human.getHumanSpecialShopManager());
		tasks.add(resetSpecialShopRefreshTimeTask);
		
		/** 装备洗炼免费次数 */
		ResetFreeEquipForgeTimesTask resetFreeEquipForgeTimesTask = new ResetFreeEquipForgeTimesTask(human.getHumanForgeManager());
		tasks.add(resetFreeEquipForgeTimesTask);
		
		/** 重置试炼 */
		ResetRefineRefreshTimesTask resetRefineRefreshTimesTask = new ResetRefineRefreshTimesTask(human.getHumanRefineManager());
		tasks.add(resetRefineRefreshTimesTask);
		
		/** 重置勇士之路 */
		ResetWarriorDataTask resetWarriorDataTask = new ResetWarriorDataTask(human);
		tasks.add(resetWarriorDataTask);
		
		/** 重置黄钻每日礼包领取状态 */
		ResetYellowVipDailyDataTask resetYellowVipDataTask = new ResetYellowVipDailyDataTask(human);
		tasks.add(resetYellowVipDataTask);
		
		/** 重置军衔每日俸禄领取状态 */
		ResetTitleGetSalaryTask resetTitleGetSalaryTask = new ResetTitleGetSalaryTask(human.getHumanTitleManager());
		tasks.add(resetTitleGetSalaryTask);
		
		/** 角斗场数据重置任务 */
		ResetAbattoirDataTask resetAbattoirDataTask = new ResetAbattoirDataTask(human.getHumanAbattoirManager());
		tasks.add(resetAbattoirDataTask);
		
		/** 嗜血神殿数据重置任务 */
		ResetBloodTempleDataTask resetBloodTempleDataTask = new ResetBloodTempleDataTask(human.getHumanBloodTempleManager());
		tasks.add(resetBloodTempleDataTask);
		
		/** 每周累计充值数据重置任务 */
		ResetTotalRechargeWeeklyTask resetTotalRechargeWeeklyTask = new ResetTotalRechargeWeeklyTask(human.getHumanRechargeManager());
		tasks.add(resetTotalRechargeWeeklyTask);
		
		/** 每日重置角色战神之巅数据任务 */
		ResetHumanMarsDataDailyTask resetMarsDataDailyTask = new ResetHumanMarsDataDailyTask(human);
		tasks.add(resetMarsDataDailyTask);
		
		/** 秘药使用倒计时 */
		NostrumOutDateTimeTask nostrumOutDateTimeTask = new NostrumOutDateTimeTask(human.getHumanNostrumManager());
		tasks.add(nostrumOutDateTimeTask);
		
		/** 每日重置押运数据任务 */
		ResetHumanEscortDataTask resetHumanEscortDataTask = new ResetHumanEscortDataTask(human.getHumanEscortManager());
		tasks.add(resetHumanEscortDataTask);
		
		/** 军团任务到时结束 */
		HumanLegionEndTask legionEndTask = new HumanLegionEndTask(human);
		tasks.add(legionEndTask);
		
		/** 每日重置角色军团数据 */
		ResetHumanLegionDataDailyTask resetHumanLegionDataDailyTask = new ResetHumanLegionDataDailyTask(human.getHumanLegionManager());
		tasks.add(resetHumanLegionDataDailyTask);
		
		/** 定点刷新军团任务 */
		RefreshHumanLegionTask refreshLegionTask = new RefreshHumanLegionTask(human.getHumanLegionManager());
		tasks.add(refreshLegionTask);
		
		/** 军团头衔到期任务 */
		HumanLegionTitleTask legionTitleTask = new HumanLegionTitleTask(human);
		tasks.add(legionTitleTask);
		
		/** 托管日常任务到时结束 */
		HumanDailyQuestEndTask dailyQuestEndTask = new HumanDailyQuestEndTask(human);
		tasks.add(dailyQuestEndTask);
		
		/** 重置精灵酒馆每日数据任务 */
		ResetHumanSpritePubDailyDataTask resetSpritePubDataTask = new ResetHumanSpritePubDailyDataTask(human.getHumanSpritePubManager());
		tasks.add(resetSpritePubDataTask);
		
		/** 重置每日手动恢复体力次数 */
		ResetHumanEnergyDailyTask resetEnergyDailyTask = new ResetHumanEnergyDailyTask(human);
		tasks.add(resetEnergyDailyTask);
		
		/** 每日固定时间自动恢复体力值 */
		AutoRecoverEnergyDailyTask autoRecoverEnergyDailyTask = new AutoRecoverEnergyDailyTask(human);
		tasks.add(autoRecoverEnergyDailyTask);
		
		/** 重置角色每日押运数据 */
		ResetHumanEscortDataTask resetHumanEscortDailyTask = new ResetHumanEscortDataTask(human.getHumanEscortManager());
		tasks.add(resetHumanEscortDailyTask);
	}

	@Override
	public void onHeartBeat() {
		for(IHeartBeatTask task : tasks){
			if(task == null){
				continue;
			}
			
			if(task.isTimeUp(GameServerAssist.getSystemTimeService().now())){
				try{
					task.run();
				}
				catch (Exception e) {
					logger.error(e.toString());
				}
				task.nextRound();
			}
		}
	}
}
