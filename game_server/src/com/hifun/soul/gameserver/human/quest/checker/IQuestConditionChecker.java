package com.hifun.soul.gameserver.human.quest.checker;

import java.util.List;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.template.QuestTemplate;

/**
 * 任务条件 检查器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IQuestConditionChecker {

	public boolean checkCondition(Human human, QuestTemplate questTemplate,
			List<Integer> finishList);
}
