package com.hifun.soul.gameserver.human.quest.daily.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.human.quest.daily.DailyItemReward;
import com.hifun.soul.gameserver.human.quest.daily.DailyRewardBoxState;
import com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo;

@ExcelRowBinding
public class DailyQuestRewardTemplate extends DailyQuestRewardTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	/**
	 * 转化为{@code DailyScoreRewardInfo}
	 * @return
	 */
	public DailyScoreRewardInfo toInfo(String boxTips, DailyRewardBoxState state) {
		DailyScoreRewardInfo info = new DailyScoreRewardInfo();
		info.setBoxId(this.getId());
		info.setState(state.getIndex());
		info.setRewardMoney(this.getRewardMoney());
		info.setScoreLimit(this.getScoreLimit());
		info.setTip(boxTips);
		info.setDailyRewardItems(this.getDailyRewardItems().toArray(
				new DailyItemReward[0]));
		return info;
	}

}
