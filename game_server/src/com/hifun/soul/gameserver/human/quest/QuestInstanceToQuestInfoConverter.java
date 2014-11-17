package com.hifun.soul.gameserver.human.quest;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.quest.aim.counter.AbstractDailyQuestAimCounter;
import com.hifun.soul.gameserver.human.quest.aim.counter.IQuestAimCounter;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;

public class QuestInstanceToQuestInfoConverter implements
		IConverter<Quest, QuestInfo> {

	@Override
	public QuestInfo convert(Quest src) {
		QuestInfo quest = new QuestInfo();
		quest.setQuestId(src.getQuestTemplate().getId());
		quest.setQuestName(src.getQuestTemplate().getQuestName());
		quest.setQuestDesc(src.getQuestTemplate().getQuestDesc());
		quest.setQuestType(src.getQuestTemplate().getQuestType());
		quest.setRewardExp((int) src.getQuestTemplate().getRewardExp());
		quest.setRewardMoney((int) src.getQuestTemplate().getRewardMoney());
		quest.setRewardStore(src.getQuestTemplate().getGetScore());
		quest.setQuestState(src.getCurrentState().getIndex());
		quest.setNpcIcon(src.getQuestTemplate().getNpcIcon());
		quest.setQuestIcon(src.getQuestTemplate().getIconId());
		quest.setQuestAimDesc(src.getQuestTemplate().getQuestAimDesc());
		// 设置任务目标
		List<QuestAimInfo> questAims = new ArrayList<QuestAimInfo>();
		List<IQuestAimCounter> aims = src.getAimList();
		for (IQuestAimCounter counter : aims) {
			QuestAimInfo aim = new QuestAimInfo();
			aim.setAimIndex(counter.getIndex());
			aim.setAimType(counter.getType().getAimType());
			aim.setCurrentValue(counter.getValue());
			// 设置目标值
			if (counter instanceof AbstractDailyQuestAimCounter) {
				// 如果是日常的目标值; 则去param1
				aim.setAimValue(counter.getAim().getParam1());
			} else {
				aim.setAimValue(counter.getAim().getParam2());
			}
			questAims.add(aim);
		}
		quest.setQuestAims(questAims.toArray(new QuestAimInfo[0]));
		quest.setStageGuideInfo(src.getQuestTemplate().getStageGuideInfo());
		if (src.getQuestTemplate().getItemId() > 0) {
			SimpleCommonItem questItem = CommonItemBuilder
					.genSimpleCommonItem(src.getQuestTemplate().getItemId());
			if (questItem != null) {
				SimpleCommonItem[] questItems = { questItem };
				quest.setQuestItems(questItems);
			}
		}
		// 任务剩余时间
		quest.setRemainTime((int) (src.getEndTime() - GameServerAssist
				.getSystemTimeService().now()));
		quest.setQuantity(src.getQuestTemplate().getQuestQuanlity());
		quest.setGameFuncId(src.getQuestTemplate().getGameFuncId());
		return quest;
	}

	@Override
	public Quest reverseConvert(QuestInfo src) {
		return null;
	}

}
