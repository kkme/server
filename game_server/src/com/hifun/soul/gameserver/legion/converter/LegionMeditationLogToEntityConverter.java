package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionMeditationLogEntity;
import com.hifun.soul.gameserver.legion.LegionMeditationLog;

public class LegionMeditationLogToEntityConverter implements
		IConverter<LegionMeditationLog, LegionMeditationLogEntity> {

	@Override
	public LegionMeditationLogEntity convert(LegionMeditationLog src) {
		LegionMeditationLogEntity entity = new LegionMeditationLogEntity();
		entity.setId(src.getId());
		entity.setLegionId(src.getLegionId());
		entity.setContent(src.getContent());
		entity.setOperateTime(src.getOperateTime());
		return entity;
	}

	@Override
	public LegionMeditationLog reverseConvert(LegionMeditationLogEntity src) {
		LegionMeditationLog log = new LegionMeditationLog();
		log.setId((Long) src.getId());
		log.setLegionId(src.getLegionId());
		log.setContent(src.getContent());
		log.setOperateTime(src.getOperateTime());
		return log;
	}

}
