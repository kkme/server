package com.hifun.soul.gameserver.battle.callback;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.BattleLogReason;
import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.battle.BaseBattleCallback;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.BattleWinEvent;
import com.hifun.soul.gameserver.event.CollectItemEvent;
import com.hifun.soul.gameserver.event.MonsterDeadEvent;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.guide.manager.HumanGuideManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.stage.StageMapState;
import com.hifun.soul.gameserver.stage.TriggerType;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageDramaInfo;
import com.hifun.soul.gameserver.stage.model.StageMapInfo;
import com.hifun.soul.gameserver.stage.msg.GCAttackStage;
import com.hifun.soul.gameserver.stage.msg.GCStageDramaInfo;
import com.hifun.soul.gameserver.stage.template.StageTemplate;
import com.hifun.soul.gameserver.stagestar.StageStarInfo;
import com.hifun.soul.gameserver.stagestar.StageStarType;
import com.hifun.soul.gameserver.turntable.template.ItemRateData;

/**
 * 地图通关战斗回调;
 * 
 * @author crazyjohn
 * 
 */
public class BattleWithMapMonsterCallback extends BaseBattleCallback {
	private static final int INVALID_ITEMID = 0;
	private Logger logger = Loggers.STAGE_LOGGER;

	public BattleWithMapMonsterCallback(Human human, Monster monster) {
		super(human);
		this.beChallenged = monster;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		HumanStageManager humanStageManager = challenger.getHumanStageManager();
		// 战斗关卡id
		int stageId = humanStageManager.getStageId();
		if (stageId <= 0) {
			return;
		}
		// 战斗关卡对应的模版信息
		StageTemplate stageTemplate = GameServerAssist
				.getStageTemplateManager().getStageTemplate(stageId);
		if (stageTemplate == null) {
			logger.error(String.format(
					"can not find stageTemplate. stageId:%s", stageId));
			return;
		}
		// 判断是否应该结束战斗引导
		HumanGuideManager guideManager = challenger.getHumanGuideManager();
		if (GameServerAssist.getGuideTemplateManager().guideIsOpen()
				&& !guideManager.isFinishBattleGuide()
				&& stageId >= GameServerAssist.getGameConstants()
						.getBattleGuideFinishStageId()) {
			guideManager.setIsFinishBattleGuide(1);
			guideManager.showGuideInBattle(GuideType.BATTLE_GUIDE_FOUR_BOMBS
					.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_BLACK
							.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_WHITE
							.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_RED
							.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_YELLOW
							.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_BLUE
							.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_GREEN
							.getIndex());
			guideManager
					.showGuideInBattle(GuideType.BATTLE_GUIDE_THREE_BOMBS_PURPLE
							.getIndex());
			guideManager.showGuideInBattle(GuideType.BATTLE_GUIDE_SKILL_INFO
					.getIndex());
			guideManager.showGuideInBattle(GuideType.BATTLE_GUIDE_USE_SKILL
					.getIndex());
		}
		// 第一次关卡战斗胜利引导
		if (this.beChallenged instanceof Monster
				&& ((Monster) this.beChallenged).getTemplate().getId() == 10101) {
			challenger.getHumanGuideManager().showGuide(
					GuideType.STAGE_10101.getIndex());
		}
		// 第二关卡战斗胜利引导
		if (this.beChallenged instanceof Monster
				&& ((Monster) this.beChallenged).getTemplate().getId() == 10102) {
			challenger.getHumanGuideManager().showGuide(
					GuideType.STAGE_10102.getIndex());
		}
		// 消耗精力值
		challenger.setEnergy(challenger.getEnergy()
				- SharedConstants.STAGE_ENERGY_NUM,
				EnergyLogReason.BATTLE_WITH_MAP_MONSTER_USE_ENERGY, "stageId:"
						+ stageId + " result:win");
		// 发送怪物死亡计时事件
		MonsterDeadEvent monsterDead = new MonsterDeadEvent();
		monsterDead.setMonsterId((int) this.beChallenged.getUnitGuid());
		this.challenger.getEventBus().fireEvent(monsterDead);
		// 发送战斗胜利事件
		BattleWinEvent battleWin = new BattleWinEvent();
		this.challenger.getEventBus().fireEvent(battleWin);

		// 设置下一关卡(下一关卡id大于当前下一关卡时设置)
		if (stageTemplate.getNextStageId() > humanStageManager.getNextStageId()) {
			humanStageManager.setNextStageId(stageTemplate.getNextStageId());
		}

		// 根据战斗情况判断此次战斗的星级
		int star = StageStarType.StageStarOne.getIndex();
		// 剩余血量/玩家血量>=加星血量条件/10000
		double hpRate = challenger.getBattleContext().getBattleProperty()
				.getHp()
				/ (challenger.getHp() * 1.0);
		if (hpRate >= stageTemplate.getAddStarOne()
				/ SharedConstants.DEFAULT_FLOAT_BASE) {
			star++;
		}
		if (hpRate >= stageTemplate.getAddStarTwo()
				/ SharedConstants.DEFAULT_FLOAT_BASE) {
			star++;
		}
		if (hpRate >= stageTemplate.getAddStarThree()
				/ SharedConstants.DEFAULT_FLOAT_BASE) {
			star++;
		}
		if (hpRate >= stageTemplate.getAddStarFour()
				/ SharedConstants.DEFAULT_FLOAT_BASE) {
			star++;
		}
		// 更新评星记录
		StageStarInfo stageStarInfo = new StageStarInfo();
		stageStarInfo.setStageId(stageId);
		stageStarInfo.setStageStar(star);
		humanStageManager.updateStageStar(stageStarInfo);
		// 发奖
		float rewardRate = GameServerAssist.getStageTemplateManager()
				.getRewardRateByStar(humanStageManager.getLastStageStar());
		// 加钱
		int money = stageTemplate.getRewardCurrencyNum();
		money = (int) (money * rewardRate);

		// 获得军团科技收益加成 edit by yandajun
		money = challenger.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.STAGE, money);

		challenger.getWallet().addMoney(CurrencyType.COIN, money, true,
				MoneyLogReason.BATTLE_REWARD, "");
		// 加经验
		int exp = (int) (stageTemplate.getRewardExperience() * rewardRate);
		challenger.addExperience(exp, true, ExperienceLogReason.BATTLE_ADD_EXP,
				"");

		// 获取掉落物品
		ItemRateData itemRate = stageTemplate.getRewardItems().get(0);
		CommonItem rewardItem = null;
		if (itemRate.getItemId() != INVALID_ITEMID) {
			if (MathUtils.shake(itemRate.getRate()
					/ SharedConstants.PROPERTY_PERCENT_DIVISOR)) {
				rewardItem = CommonItemBuilder.genCommonItem(itemRate
						.getItemId());
				// 发送收集物品事件
				CollectItemEvent collectItemEvent = new CollectItemEvent();
				collectItemEvent.setItemId(itemRate.getItemId());
				challenger.getEventBus().fireEvent(collectItemEvent);
				// 检查背包容量
				if (challenger.getBagManager().getFreeSize(BagType.MAIN_BAG) <= 0) {
					challenger
							.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
					return;
				}
				// 发物品
				challenger.getBagManager().putItems(BagType.MAIN_BAG,
						itemRate.getItemId(), 1, ItemLogReason.BATTLE_REWARD,
						"");
			}
		}
		// 奖励信息下发给客户端
		GCAttackStage gcAttackStage = new GCAttackStage();
		gcAttackStage.setStageId(stageId);
		gcAttackStage.setNextStageId(humanStageManager.getNextStageId());

		gcAttackStage
				.setCoin((int) (stageTemplate.getRewardCurrencyNum() * rewardRate));
		gcAttackStage
				.setExperience((int) (stageTemplate.getRewardExperience() * rewardRate));

		List<CommonItem> items = new ArrayList<CommonItem>();
		if (rewardItem != null) {
			items.add(rewardItem);
		}
		gcAttackStage.setItems(items.toArray(new CommonItem[0]));
		gcAttackStage.setResult(true);
		gcAttackStage.setStar(star);
		challenger.sendMessage(gcAttackStage);

		int mapId = stageTemplate.getMapId();
		// 设置关卡地图状态
		if (GameServerAssist.getStageTemplateManager().isMapLastStage(stageId)
				&& humanStageManager.getStageMapState(mapId) != StageMapState.PASSED
				&& humanStageManager.getStageMapState(mapId) != StageMapState.GETTED) {
			// 设置当前地图状态
			humanStageManager.setStageMapState(mapId, StageMapState.PASSED);
			// 发送通关奖励
			humanStageManager.sendPassStageRewardInfo(mapId);
			// 开启下一张地图
			StageMapInfo stageMapInfo = GameServerAssist
					.getStageTemplateManager().getStageMapInfo(mapId);
			if (stageMapInfo != null && stageMapInfo.getNextMapId() > 0) {
				humanStageManager.setStageMapState(stageMapInfo.getNextMapId(),
						StageMapState.OPEN);
				humanStageManager.setNextMapId(stageMapInfo.getNextMapId());
			}
		}

		// 判断是否有要发送的剧情
		if (!humanStageManager.isTrigger(stageId, TriggerType.STAGE_SUCCESS)) {
			List<StageDramaInfo> stageDramaInfos = GameServerAssist
					.getStageTemplateManager().getStageDramaInfos(stageId,
							TriggerType.STAGE_SUCCESS.getIndex());
			GCStageDramaInfo gcStageDramaInfo = new GCStageDramaInfo();
			if (stageDramaInfos != null) {
				gcStageDramaInfo.setStageId(stageId);
				gcStageDramaInfo.setStageDramaInfos(stageDramaInfos
						.toArray(new StageDramaInfo[0]));
				gcStageDramaInfo.setBeforeBattle(false);
				challenger.sendMessage(gcStageDramaInfo);
			}

			humanStageManager.triggerDrama(stageId, TriggerType.STAGE_SUCCESS);
		}

		// 发送战斗日志
		GameServerAssist.getLogService().sendBattleLog(challenger,
				BattleLogReason.STAGE_ATTACK, "", stageId, 1,
				rewardItem == null ? "0" : rewardItem.getItemId().toString());
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		HumanStageManager humanStageManager = challenger.getHumanStageManager();
		// 战斗关卡id
		int stageId = humanStageManager.getStageId();
		if (stageId <= 0) {
			return;
		}
		// 战斗关卡对应的模版信息
		StageTemplate stageTemplate = GameServerAssist
				.getStageTemplateManager().getStageTemplate(stageId);
		if (stageTemplate == null) {
			logger.error(String.format(
					"can not find stageTemplate. stageId:%s", stageId));
			return;
		}
		// 消耗精力值
		challenger.setEnergy(challenger.getEnergy()
				- SharedConstants.STAGE_ENERGY_NUM,
				EnergyLogReason.BATTLE_WITH_MAP_MONSTER_USE_ENERGY, "stageId:"
						+ stageId + " result:failed");
		// 更新评星记录
		StageStarInfo stageStarInfo = new StageStarInfo();
		stageStarInfo.setStageId(stageId);
		stageStarInfo.setStageStar(0);
		humanStageManager.updateStageStar(stageStarInfo);
		// 关卡战斗失败(奖励减半,没有抽奖)
		float rewardRate = GameServerAssist.getStageTemplateManager()
				.getRewardRateByStar(humanStageManager.getLastStageStar());
		// giveLoseReward();
		// 加钱
		int money = stageTemplate.getRewardCurrencyNum();
		money = (int) (money
				* challenger.getHumanAntiIndulgeManager().getRevenueRate() * rewardRate);
		// 获得军团科技收益加成 edit by yandajun
		money = challenger.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.STAGE, money);
		challenger.getWallet().addMoney(CurrencyType.COIN, money, true,
				MoneyLogReason.BATTLE_REWARD, "");
		// 加经验
		int exp = (int) (stageTemplate.getRewardExperience()
				* challenger.getHumanAntiIndulgeManager().getRevenueRate() * rewardRate);
		challenger.addExperience(exp, true, ExperienceLogReason.BATTLE_ADD_EXP,
				"");
		// 发送通知消息
		GCAttackStage gcAttackStage = new GCAttackStage();
		gcAttackStage.setStageId(stageId);
		gcAttackStage
				.setCoin((int) (stageTemplate.getRewardCurrencyNum() * rewardRate));
		gcAttackStage
				.setExperience((int) (stageTemplate.getRewardExperience() * rewardRate));
		gcAttackStage.setItems(new CommonItem[0]);
		gcAttackStage.setResult(false);
		challenger.sendMessage(gcAttackStage);

		// 判断是否有要发送的剧情
		if (!humanStageManager.isTrigger(stageId, TriggerType.STAGE_FAIL)) {
			List<StageDramaInfo> stageDramaInfos = GameServerAssist
					.getStageTemplateManager().getStageDramaInfos(stageId,
							TriggerType.STAGE_FAIL.getIndex());
			GCStageDramaInfo gcStageDramaInfo = new GCStageDramaInfo();
			if (stageDramaInfos != null) {
				gcStageDramaInfo.setStageId(stageId);
				gcStageDramaInfo.setStageDramaInfos(stageDramaInfos
						.toArray(new StageDramaInfo[0]));
				gcStageDramaInfo.setBeforeBattle(false);
				challenger.sendMessage(gcStageDramaInfo);
			}

			humanStageManager.triggerDrama(stageId, TriggerType.STAGE_FAIL);
		}

		// 发送战斗日志
		GameServerAssist.getLogService().sendBattleLog(challenger,
				BattleLogReason.STAGE_ATTACK, "", stageId, 0, "");
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// TODO Auto-generated method stub

	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		// do nothing
		exitBattle.setGameSceneType(ClientGameSceneType.AREA_VIEW.getIndex());
	}

}
