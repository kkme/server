package com.hifun.soul.gameserver.human.quest.daily;

import com.hifun.soul.gameserver.human.quest.daily.template.DailyQuestRewardTemplate;

/**
 * 日常任务奖励宝箱业务对象;
 * 
 * @author crazyjohn
 * 
 */
public class DailyQuestRewardBox {
	/** 宝箱模版 */
	private DailyQuestRewardTemplate template;
	/** 宝箱状态 */
	private DailyRewardBoxState state;

	public DailyQuestRewardTemplate getTemplate() {
		return template;
	}

	public void setTemplate(DailyQuestRewardTemplate template) {
		this.template = template;
	}

	public DailyRewardBoxState getState() {
		return state;
	}

	public void setState(DailyRewardBoxState state) {
		this.state = state;
	}

}
