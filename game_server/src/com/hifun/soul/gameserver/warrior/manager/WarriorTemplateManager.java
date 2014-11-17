package com.hifun.soul.gameserver.warrior.manager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.warrior.WarriorQuest;
import com.hifun.soul.gameserver.warrior.WarriorQuestReward;
import com.hifun.soul.gameserver.warrior.WarriorQuestRewardState;
import com.hifun.soul.gameserver.warrior.WarriorQuestState;
import com.hifun.soul.gameserver.warrior.template.WarriorChallengeRewardTemplate;
import com.hifun.soul.gameserver.warrior.template.WarriorQuestTemplate;

@Scope("singleton")
@Component
public class WarriorTemplateManager implements IInitializeRequired {
	private Map<Integer, WarriorChallengeRewardTemplate> rewardTemplates;
	private Map<Integer, WarriorQuestTemplate> questTemplates;
	private WarriorQuestTemplate[] questTemplateArray;
	@Autowired
	private TemplateService templateService;
	private static Comparator<WarriorQuestTemplate> warriorQuestTemplateSorter = new Comparator<WarriorQuestTemplate>() {
		@Override
		public int compare(WarriorQuestTemplate o1, WarriorQuestTemplate o2) {
			return o1.getId() - o2.getId();
		}
	};

	@Override
	public void init() {
		rewardTemplates = templateService.getAll(WarriorChallengeRewardTemplate.class);
		questTemplates = templateService.getAll(WarriorQuestTemplate.class);
		questTemplateArray = questTemplates.values().toArray(new WarriorQuestTemplate[0]);
		Arrays.sort(questTemplateArray, warriorQuestTemplateSorter);
	}

	/**
	 * 初始化勇士之路任务
	 * 
	 * @return
	 */
	public WarriorQuest[] createWarriorQuests() {
		if (questTemplateArray.length == 0) {
			return new WarriorQuest[0];
		}
		WarriorQuest[] quests = new WarriorQuest[questTemplateArray.length];
		for (int i = 0; i < questTemplateArray.length; i++) {
			WarriorQuestTemplate template = questTemplateArray[i];
			quests[i] = new WarriorQuest();
			quests[i].setCompleteCount(0);
			quests[i].setDamageHpPercent(template.getDamageHpPercent());
			quests[i].setId(template.getId());
			quests[i].setQuestState(WarriorQuestState.UNOPEN.getIndex());
			quests[i].setTotalCount(template.getCounter());
		}
		quests[0].setQuestState(WarriorQuestState.ONGOING.getIndex());
		return quests;
	}

	/**
	 * 获取当前任务奖励列表
	 * 
	 * @param currentQuestIndex
	 * @param humanLevel
	 * @return
	 */
	public WarriorQuestReward[] getWarriorQuestRewards(int currentQuestIndex, int humanLevel) {
		if (questTemplateArray.length == 0) {
			return new WarriorQuestReward[0];
		}
		WarriorChallengeRewardTemplate rewardTemplate = rewardTemplates.get(humanLevel);
		WarriorQuestReward[] questRewards;
		if (currentQuestIndex < questTemplateArray.length - 1) {
			questRewards = new WarriorQuestReward[2];
			for (int i = 0; i < questRewards.length; i++) {
				WarriorQuestTemplate template = questTemplateArray[currentQuestIndex + i];
				questRewards[i] = new WarriorQuestReward();
				questRewards[i].setQuestId(template.getId());
				questRewards[i].setCoin((int) (rewardTemplate.getCoinBase() * template.getCoinRatio()));
				questRewards[i].setExp((int) (rewardTemplate.getExpBase() * template.getExpRatio()));
				questRewards[i].setTechPoint((int) (rewardTemplate.getTechPointBase() * template.getTechPointRatio()));
				questRewards[i].setState(WarriorQuestRewardState.UNVISABLE.getIndex());
			}
			questRewards[0].setState(WarriorQuestRewardState.VISABLE.getIndex());
		} else {
			WarriorQuestTemplate template = questTemplateArray[questTemplateArray.length - 1];
			questRewards = new WarriorQuestReward[1];
			questRewards[0] = new WarriorQuestReward();
			questRewards[0].setQuestId(template.getId());
			questRewards[0].setCoin((int) (rewardTemplate.getCoinBase() * template.getCoinRatio()));
			questRewards[0].setExp((int) (rewardTemplate.getExpBase() * template.getExpRatio()));
			questRewards[0].setTechPoint((int) (rewardTemplate.getTechPointBase() * template.getTechPointRatio()));
			questRewards[0].setState(WarriorQuestRewardState.VISABLE.getIndex());
		}

		return questRewards;
	}

	public WarriorChallengeRewardTemplate getWarriorChallengeRewardTemplate(int humanLevel) {
		return rewardTemplates.get(humanLevel);
	}

	public WarriorQuestTemplate getLastWarriorQuestTemplate() {
		if (questTemplateArray == null || questTemplateArray.length <= 0) {
			return null;
		}
		return questTemplateArray[questTemplateArray.length - 1];
	}

	/**
	 * 获取勇者之路挑战的总次数
	 * 
	 * @return
	 */
	public int getWarriorChanllengeCount() {
		int result = 0;
		for (WarriorQuestTemplate template : questTemplates.values()) {
			result += template.getCounter();
		}
		return result;
	}

	/**
	 * 获取当前任务奖励列表
	 * 
	 * @param currentQuestIndex
	 * @param humanLevel
	 * @return
	 */
	public WarriorQuestReward[] getAllWarriorQuestRewards(int humanLevel) {
		if (questTemplateArray.length == 0) {
			return new WarriorQuestReward[0];
		}
		WarriorChallengeRewardTemplate rewardTemplate = rewardTemplates.get(humanLevel);
		WarriorQuestReward[] questRewards = new WarriorQuestReward[questTemplateArray.length];
		for (int i = 0; i < questRewards.length; i++) {
			WarriorQuestTemplate template = questTemplateArray[i];
			questRewards[i] = new WarriorQuestReward();
			questRewards[i].setQuestId(template.getId());
			questRewards[i].setCoin((int) (rewardTemplate.getCoinBase() * template.getCoinRatio()));
			questRewards[i].setExp((int) (rewardTemplate.getExpBase() * template.getExpRatio()));
			questRewards[i].setTechPoint((int) (rewardTemplate.getTechPointBase() * template.getTechPointRatio()));
			questRewards[i].setState(WarriorQuestRewardState.UNVISABLE.getIndex());
		}
		questRewards[0].setState(WarriorQuestRewardState.VISABLE.getIndex());
		return questRewards;
	}
}
