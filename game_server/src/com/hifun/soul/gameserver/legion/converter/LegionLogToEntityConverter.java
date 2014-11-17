package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionLogEntity;
import com.hifun.soul.gameserver.legion.LegionLog;

public class LegionLogToEntityConverter implements
		IConverter<LegionLog, LegionLogEntity> {

	@Override
	public LegionLogEntity convert(LegionLog legionLog) {
		LegionLogEntity legionLogEntity = new LegionLogEntity();
		legionLogEntity.setId(legionLog.getId());
		legionLogEntity.setLegionId(legionLog.getLegionId());
		legionLogEntity.setContent(legionLog.getContent());
		legionLogEntity.setOperateTime(legionLog.getOperateTime());
		return legionLogEntity;
	}

	@Override
	public LegionLog reverseConvert(LegionLogEntity legionLogEntity) {
		LegionLog legionLog = new LegionLog();
		legionLog.setId((Long) legionLogEntity.getId());
		legionLog.setLegionId(legionLogEntity.getLegionId());
		legionLog.setContent(legionLogEntity.getContent());
		legionLog.setOperateTime(legionLogEntity.getOperateTime());
		return legionLog;
	}

}
