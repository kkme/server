package com.hifun.soul.gameserver.prison.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.PrisonLogEntity;
import com.hifun.soul.gameserver.prison.PrisonLog;

public class PrisonLogToEntityConverter implements
		IConverter<PrisonLog, PrisonLogEntity> {

	@Override
	public PrisonLogEntity convert(PrisonLog src) {
		PrisonLogEntity entity = new PrisonLogEntity();
		entity.setId(src.getId());
		entity.setLogType(src.getLogType());
		entity.setFirstId(src.getFirstId());
		entity.setSecondId(src.getSecondId());
		entity.setThirdId(src.getThirdId());
		entity.setContent(src.getContent());
		entity.setResult(src.getResult());
		entity.setOperateTime(src.getOperateTime());
		entity.setInteractType(src.getInteractType());
		return entity;
	}

	@Override
	public PrisonLog reverseConvert(PrisonLogEntity src) {
		PrisonLog prisonLog = new PrisonLog();
		prisonLog.setId((Long) src.getId());
		prisonLog.setLogType(src.getLogType());
		prisonLog.setFirstId(src.getFirstId());
		prisonLog.setSecondId(src.getSecondId());
		prisonLog.setThirdId(src.getThirdId());
		prisonLog.setContent(src.getContent());
		prisonLog.setResult(src.getResult());
		prisonLog.setOperateTime(src.getOperateTime());
		prisonLog.setInteractType(src.getInteractType());
		return prisonLog;
	}

}
