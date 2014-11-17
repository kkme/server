package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestInstanceToQuestInfoConverter;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.quest.msg.CGGetDailyQuestReward;
import com.hifun.soul.gameserver.quest.msg.GCGetDailyQuestReward;
import com.hifun.soul.gameserver.quest.msg.GCUpdateDailyQuestScore;

@Component
public class CGGetDailyQuestRewardHandler implements
		IMessageHandlerWithType<CGGetDailyQuestReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_DAILY_QUEST_REWARD;
	}

	@Override
	public void execute(CGGetDailyQuestReward message) {
		Human human = message.getPlayer().getHuman();
		HumanQuestManager questManager = human.getHumanQuestManager();
		int questId = message.getQuestId();
		Quest quest = questManager.getDailyQuest(questId);
		if (quest == null) {
			return;
		}
		// 状态校验
		if (quest.getCurrentState() != QuestState.QUEST_CAN_FINISH) {
			human.sendErrorMessage(LangConstants.DAILY_QUEST_WRONG_STATE);
			return;
		}
		// 如果没有免费次数
		if (GameServerAssist.getGameConstants().getFreeQuestNum()
				- human.getDailyQuestReceivedNum() <= 0) {
			// 判断是否还有魔晶购买次数
			int questBuyNum = human.getDailyQuestBuyNum();
			if (questBuyNum >= GameServerAssist
					.getVipPrivilegeTemplateManager()
					.getVipTemplate(human.getVipLevel())
					.getMaxBuyDailyQuestTimes()) {
				human.sendErrorMessage(LangConstants.DAILY_QUEST_HAVE_NO_TIMES);
				return;
			}
			// 消耗魔晶
			int costCrystal = GameServerAssist.getVipPrivilegeTemplateManager()
					.getDailyQuestBuyNumCost(questBuyNum + 1,
							human.getVipLevel());
			if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
					MoneyLogReason.DAILY_QUEST, "")) {
				// 任务购买次数变更
				human.setDailyQuestBuyNum(questBuyNum + 1);
				getRewardHandler(human, quest);
			}
		} else {
			// 任务次数变更
			human.setDailyQuestReceivedNum(human.getDailyQuestReceivedNum() + 1);
			getRewardHandler(human, quest);
		}

	}

	/**
	 * 领取奖励处理
	 */
	private void getRewardHandler(Human human, Quest quest) {
		// 领取奖励
		int rewardCoin = (int) quest.getQuestTemplate().getRewardMoney();
		human.getWallet().addMoney(CurrencyType.COIN, rewardCoin, true,
				MoneyLogReason.DAILY_QUEST, "");
		int rewardExp = (int) quest.getQuestTemplate().getRewardExp();
		// 军团科技收益加成
		rewardExp = human.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.DAILY_QUEST, rewardExp);
		human.addExperience(rewardExp, true, ExperienceLogReason.DAILY_QUEST,
				"");
		int rewardScore = quest.getQuestTemplate().getGetScore();
		human.setDailyQuestScore(human.getDailyQuestScore() + rewardScore);

		// 任务状态变更
		quest.setState(QuestState.QUEST_CAN_ACCEPT);
		human.getHumanQuestManager().addUpdateQuest(quest);

		// 通知客户端积分更新
		GCUpdateDailyQuestScore updateScoreMsg = new GCUpdateDailyQuestScore();
		updateScoreMsg.setCurrentScore(human.getDailyQuestScore());
		human.sendMessage(updateScoreMsg);
		// 更新日常任务是否有奖励可领取
		human.getHumanQuestManager().updateDailyQuestReward();
		// 返回刷新出来的任务
		GCGetDailyQuestReward msg = new GCGetDailyQuestReward();
		msg.setOldQuestId(quest.getQuestId());
		Quest newQuest = human.getHumanQuestManager().refreshDailyQuest(
				quest.getQuestId());
		msg.setNewQuestInfo(new QuestInstanceToQuestInfoConverter()
				.convert(newQuest));
		msg.setRemainFreeQuestNum(GameServerAssist.getGameConstants()
				.getFreeQuestNum() - human.getDailyQuestReceivedNum());
		msg.setRemainCrystalQuestNum(GameServerAssist
				.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel()).getMaxBuyDailyQuestTimes()
				- human.getDailyQuestBuyNum());
		msg.setReceiveQuestCost(GameServerAssist
				.getVipPrivilegeTemplateManager().getDailyQuestBuyNumCost(
						human.getDailyQuestBuyNum() + 1, human.getVipLevel()));
		human.sendMessage(msg);
	}
}
