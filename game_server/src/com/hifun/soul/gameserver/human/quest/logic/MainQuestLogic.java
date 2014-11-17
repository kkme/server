package com.hifun.soul.gameserver.human.quest.logic;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.checker.IQuestConditionChecker;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.template.SpreeTemplate;
import com.hifun.soul.gameserver.quest.msg.GCQuestState;

/**
 * 主线任务;
 * 
 * @author crazyjohn
 * 
 */
public class MainQuestLogic implements IQuestLogic {
	protected List<IQuestConditionChecker> checkerList = new ArrayList<IQuestConditionChecker>();

	public void finishQuest(Quest questInstance, Human human) {
		// 发放经验和金钱
		human.getWallet().addMoney(CurrencyType.COIN,
				(int) questInstance.getQuestTemplate().getRewardMoney(),true,MoneyLogReason.QUEST, "");
		human.addExperience((int)questInstance.getQuestTemplate().getRewardExp(),true,ExperienceLogReason.QUEST_ADD_EXP, "");
		if(questInstance.getQuestTemplate().getItemId()>0){
			Item questItem = ItemFactory.creatNewItem(human, questInstance.getQuestTemplate().getItemId());
			if(questItem != null){
				human.getBagManager().putItem(BagType.MAIN_BAG, questItem, ItemLogReason.MAIN_QUEST_REWARD, "");
			}
		}
		// 通知客户端任务状态
		GCQuestState questState = new GCQuestState();
		questState.setQuestId(questInstance.getQuestId());
		questState.setQuestState(QuestState.QUEST_FINISHED.getIndex());
		human.sendMessage(questState);

	}

	public boolean checkFinish(Quest questInstance, Human human) {
		//检查背包能否放下任务奖励物品
		if(questInstance.getQuestTemplate().getItemId()>0){
			Item questItem = ItemFactory.creatNewItem(human, questInstance.getQuestTemplate().getItemId());
			if(questItem != null){
				int itemCount = 1;
				if(questItem.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()){
					SpreeTemplate spreeTemplate = GameServerAssist.getSpreeTemplateManager().getSpreeTemplate(questItem.getItemId());
					if(spreeTemplate != null){
						itemCount = spreeTemplate.getItems().size();
					}
				}
				if(human.getBagManager().getFreeSize(BagType.MAIN_BAG)<itemCount){
					human.sendErrorMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
					return false;
				}
			}
		}
		return questInstance.isAllAimCounterFinished();
	}
	
	@Override
	public void whenAllAimFinished(Quest questInstance, Human human) {
		questInstance.setState(QuestState.QUEST_CAN_FINISH);
		GCQuestState questState = new GCQuestState();
		questState.setQuestId(questInstance.getQuestId());
		questState.setQuestState(QuestState.QUEST_CAN_FINISH.getIndex());
		human.sendMessage(questState);
		// 移除事件监听
		questInstance.removeListeners();
		// 下发后续任务;
		// 看是否有满足条件的任务需要推送;
		human.getHumanQuestManager().pushNewSuitableQuests();
	}

}
