package com.hifun.soul.gameserver.human.quest.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanQuestDataEntity;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.aim.counter.IQuestAimCounter;
import com.hifun.soul.proto.common.Quests.QuestAimCounterData;
import com.hifun.soul.proto.common.Quests.QuestStateData;

public class QuestInstanceToEntityConverter implements
		IConverter<Quest, HumanQuestDataEntity> {

	@Override
	public HumanQuestDataEntity convert(Quest src) {
		HumanQuestDataEntity result = new HumanQuestDataEntity();
		result.getBuilder().setHumanGuid(src.getHuman().getHumanGuid());
		QuestStateData.Builder questStateData = QuestStateData.newBuilder();
		questStateData.setQuestId(src.getQuestId());
		questStateData.setState(src.getCurrentState().getStateCode());
		// 设置任务计数器
		for (IQuestAimCounter eachCounter : src.getAimList()) {
			QuestAimCounterData.Builder counter = QuestAimCounterData
					.newBuilder();
			counter.setAimIndex(eachCounter.getIndex());
			counter.setAimType(eachCounter.getType().getAimType());
			counter.setValue(eachCounter.getValue());
			
			questStateData.addAimCounter(counter);
		}
		result.getBuilder().setQuestStateData(questStateData);
		return result;
	}

	@Override
	public Quest reverseConvert(HumanQuestDataEntity src) {
		throw new UnsupportedOperationException();
	}

}
