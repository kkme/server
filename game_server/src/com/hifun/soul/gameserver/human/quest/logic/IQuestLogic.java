package com.hifun.soul.gameserver.human.quest.logic;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;

/**
 * 任务逻辑接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IQuestLogic {

	/**
	 * 完成任务;
	 * 
	 * @param questInstance
	 * @param human
	 */
	public void finishQuest(Quest questInstance, Human human);

	/**
	 * 检查任务是否完成;
	 * @param questInstance
	 * @param human
	 * @return
	 */
	public boolean checkFinish(Quest questInstance, Human human);

	/**
	 * 当所有的任务目标都完成了的回调;
	 * 
	 * @param questInstance
	 * @param human
	 */
	public void whenAllAimFinished(Quest questInstance, Human human);
}
