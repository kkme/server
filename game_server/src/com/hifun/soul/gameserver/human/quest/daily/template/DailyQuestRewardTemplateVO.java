package com.hifun.soul.gameserver.human.quest.daily.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 日常任务积分奖励
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class DailyQuestRewardTemplateVO extends TemplateObject {

	/**  积分限制 */
	@ExcelCellBinding(offset = 1)
	protected int scoreLimit;

	/**  日常奖励金币 */
	@ExcelCellBinding(offset = 2)
	protected int rewardMoney;

	/**  日常任务物品奖励列表 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.human.quest.daily.DailyItemReward.class, collectionNumber = "3,4;5,6;7,8")
	protected List<com.hifun.soul.gameserver.human.quest.daily.DailyItemReward> dailyRewardItems;


	public int getScoreLimit() {
		return this.scoreLimit;
	}

	public void setScoreLimit(int scoreLimit) {
		if (scoreLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 积分限制]scoreLimit的值不得小于0");
		}
		this.scoreLimit = scoreLimit;
	}
	
	public int getRewardMoney() {
		return this.rewardMoney;
	}

	public void setRewardMoney(int rewardMoney) {
		if (rewardMoney < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 日常奖励金币]rewardMoney的值不得小于0");
		}
		this.rewardMoney = rewardMoney;
	}
	
	public List<com.hifun.soul.gameserver.human.quest.daily.DailyItemReward> getDailyRewardItems() {
		return this.dailyRewardItems;
	}

	public void setDailyRewardItems(List<com.hifun.soul.gameserver.human.quest.daily.DailyItemReward> dailyRewardItems) {
		this.dailyRewardItems = dailyRewardItems;
	}
	

	@Override
	public String toString() {
		return "DailyQuestRewardTemplateVO[scoreLimit=" + scoreLimit + ",rewardMoney=" + rewardMoney + ",dailyRewardItems=" + dailyRewardItems + ",]";

	}
}