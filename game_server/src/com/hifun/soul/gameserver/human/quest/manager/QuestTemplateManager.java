package com.hifun.soul.gameserver.human.quest.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestInfo;
import com.hifun.soul.gameserver.human.quest.QuestType;
import com.hifun.soul.gameserver.human.quest.daily.DailyQuestAimType;
import com.hifun.soul.gameserver.human.quest.daily.template.DailyQuestRewardTemplate;
import com.hifun.soul.gameserver.human.quest.template.QuestTemplate;

/**
 * 任务模版数据管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class QuestTemplateManager implements IInitializeRequired {
	/** 主线任务 */
	private Map<Integer, QuestTemplate> mainQuestTemplates = new HashMap<Integer, QuestTemplate>();
	/** 日常任务 */
	private Map<Integer, QuestTemplate> dailyQuestTemplates = new HashMap<Integer, QuestTemplate>();
	@Autowired
	private TemplateService templateService;

	public QuestTemplate getQuestTemplateByQuestId(int questId) {
		return this.templateService.getAll(QuestTemplate.class).get(questId);
	}

	@Override
	public void init() {
		for (QuestTemplate quest : this.templateService.getAll(
				QuestTemplate.class).values()) {
			if (quest.getQuestType() == QuestType.QUEST_DAILY.getIndex()) {
				dailyQuestTemplates.put(quest.getId(), quest);
				continue;
			}
			if (quest.getQuestType() == QuestType.QUEST_MAIN.getIndex()
					|| quest.getQuestType() == QuestType.QUEST_BRANCH
							.getIndex()) {
				mainQuestTemplates.put(quest.getId(), quest);
				continue;
			}

		}

	}

	public Set<Integer> getPreQuestsIds(int questId) {
		return this.mainQuestTemplates.get(questId).getPreQuestIdSet();
	}

	/**
	 * 给玩家添加适当的任务;
	 * 
	 * @param human
	 * @return
	 */
	public List<QuestInfo> addSuitableQuestToHuman(Human human) {
		List<QuestInfo> result = new ArrayList<QuestInfo>();
		for (QuestTemplate quest : mainQuestTemplates.values()) {
			// 看角色等级是否合适
			if (human.getLevel() < quest.getMinGetLevel()
					|| human.getLevel() > quest.getMaxGetLevel()) {
				continue;
			}
			// 任务是否已经完成
			if (human.getHumanQuestManager().isFinishedMainQuest(quest.getId())) {
				continue;
			}
			// 任务是否正在接取
			if (human.getHumanQuestManager().containSuchAcceptingQuest(
					quest.getId())) {
				continue;
			}
			// 任务的前置任务是否完成
			if (human.getHumanQuestManager().preQuestsFinished(quest)) {
				// 添加到角色的正在接取任务列表中
				human.getHumanQuestManager().appendNewMainQuest(quest.getId());
			}

		}
		return result;
	}

	/**
	 * 获取所有日常任务;
	 * 
	 * @return
	 */
	public Map<Integer, QuestTemplate> getAllDailyQuest() {
		return Collections.unmodifiableMap(dailyQuestTemplates);
	}

	/**
	 * 获取不与之前的任务类型重复的任务
	 */
	public List<QuestTemplate> getSomeDailyQuests(int num,
			Map<Integer, Quest> oldQuestMap, int level) {
		List<QuestTemplate> questTemplateList = new ArrayList<QuestTemplate>();
		Map<Integer, List<QuestTemplate>> templateListMap = new HashMap<Integer, List<QuestTemplate>>();
		// 在可见类型中随机出固定数量的任务类型
		List<DailyQuestAimType> questTypeList = getVisibleQuestTypes(level);
		List<Integer> typeWeights = new ArrayList<Integer>();
		for (DailyQuestAimType aimType : questTypeList) {
			if (typeInQuestMap(oldQuestMap, aimType.getIndex())) {
				typeWeights.add(0);
			} else {
				typeWeights.add(1);
			}
		}
		// 如果取的数量大于备选的个数，会导致选出来的是空数组
		if (num > typeWeights.size()) {
			num = typeWeights.size();
		}
		int[] indexs = MathUtils.getRandomUniqueIndex(typeWeights, num);
		if (indexs != null) {
			for (int index : indexs) {
				DailyQuestAimType aimType = questTypeList.get(index);
				templateListMap.put(aimType.getIndex(),
						getDailyQuestTemplateList(aimType, level));
			}
		}
		// 按权重获取出每个类型的其中一个任务
		for (Integer type : templateListMap.keySet()) {
			List<Integer> questWeights = new ArrayList<Integer>();
			for (QuestTemplate template : templateListMap.get(type)) {
				questWeights.add(template.getSystemRefreshWeight());
			}
			int[] questIds = MathUtils.getRandomUniqueIndex(questWeights, 1);
			if (questIds != null) {
				for (int id : questIds) {
					questTemplateList.add(templateListMap.get(type).get(id));
				}
			}
		}
		return questTemplateList;
	}

	/**
	 * 获取等级可见的类型(类型下有等级可见的任务)
	 */
	private List<DailyQuestAimType> getVisibleQuestTypes(int level) {
		List<DailyQuestAimType> questTypeList = new ArrayList<DailyQuestAimType>();
		for (DailyQuestAimType aimType : DailyQuestAimType.values()) {
			if (getDailyQuestTemplateList(aimType, level).size() > 0) {
				questTypeList.add(aimType);
			}
		}
		return questTypeList;
	}

	/**
	 * 获取某类型并且等级可见的任务模板列表
	 */
	private List<QuestTemplate> getDailyQuestTemplateList(
			DailyQuestAimType type, int level) {
		List<QuestTemplate> templateList = new ArrayList<QuestTemplate>();
		for (QuestTemplate template : dailyQuestTemplates.values()) {
			if (template.getDailyQuestType() == type.getIndex()
					&& level >= template.getMinGetLevel()
					&& level <= template.getMaxGetLevel()) {
				templateList.add(template);
			}
		}
		return templateList;
	}

	/**
	 * 类型是否在map中
	 * 
	 */
	public boolean typeInQuestMap(Map<Integer, Quest> questMap, int type) {
		if (questMap == null) {
			return false;
		}
		for (Integer questType : questMap.keySet()) {
			if (questMap.get(questType).getQuestTemplate().getDailyQuestType() == type) {
				return true;
			}
		}
		return false;
	}

	public List<DailyQuestRewardTemplate> getDailyRewardTemplateList() {
		List<DailyQuestRewardTemplate> result = new ArrayList<DailyQuestRewardTemplate>();
		Map<Integer, DailyQuestRewardTemplate> templates = this.templateService
				.getAll(DailyQuestRewardTemplate.class);
		for (DailyQuestRewardTemplate each : templates.values()) {
			result.add(each);
		}
		return result;
	}

	public DailyQuestRewardTemplate getDailyRewardById(int boxId) {
		Map<Integer, DailyQuestRewardTemplate> templates = this.templateService
				.getAll(DailyQuestRewardTemplate.class);
		return templates.get(boxId);
	}

	/**
	 * 获取所有主线任务;
	 * 
	 * @return
	 */
	public Collection<QuestTemplate> getAllMainQuest() {
		return mainQuestTemplates.values();
	}
}
