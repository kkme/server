package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanLegionTaskEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.LegionTask;

public class LegionTaskToEntityConverter implements
		IConverter<LegionTask, HumanLegionTaskEntity> {
	private Human human;

	public LegionTaskToEntityConverter(Human human) {
		this.human = human;
	}

	@Override
	public HumanLegionTaskEntity convert(LegionTask src) {
		HumanLegionTaskEntity entity = new HumanLegionTaskEntity();
		entity.getBuilder().setHumanGuid(human.getHumanGuid());
		entity.getBuilder().setLegionTask(
				com.hifun.soul.proto.common.LegionTasks.LegionTask.newBuilder()
						.setTaskId(src.getTaskId())
						.setEndTime(src.getEndTime())
						.setTemplateId(src.getTemplateId())
						.setHasReward(src.isHasReward())
						.setTaskState(src.getTaskState()));
		return entity;
	}

	@Override
	public LegionTask reverseConvert(HumanLegionTaskEntity src) {
		throw new UnsupportedOperationException();
	}

}
