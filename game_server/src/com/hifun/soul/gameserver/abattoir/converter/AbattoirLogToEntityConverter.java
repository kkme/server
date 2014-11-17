package com.hifun.soul.gameserver.abattoir.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.AbattoirLogEntity;
import com.hifun.soul.gameserver.abattoir.AbattoirLog;

public class AbattoirLogToEntityConverter implements
		IConverter<AbattoirLog, AbattoirLogEntity> {

	@Override
	public AbattoirLogEntity convert(AbattoirLog abattoirLog) {
		AbattoirLogEntity abattoirLogEntity = new AbattoirLogEntity();
		abattoirLogEntity.setId(abattoirLog.getId());
		abattoirLogEntity.setFirstId(abattoirLog.getFirstId());
		abattoirLogEntity.setFirstLevel(abattoirLog.getFirstLevel());
		abattoirLogEntity.setFirstName(abattoirLog.getFirstName());
		abattoirLogEntity.setSecondId(abattoirLog.getSecondId());
		abattoirLogEntity.setResult(abattoirLog.getResult());
		abattoirLogEntity.setLootTime(abattoirLog.getLootTime());
		return abattoirLogEntity;
	}

	@Override
	public AbattoirLog reverseConvert(AbattoirLogEntity abattoirLogEntity) {
		AbattoirLog abattoirLog = new AbattoirLog();
		abattoirLog.setId((Integer) abattoirLogEntity.getId());
		abattoirLog.setFirstId(abattoirLogEntity.getFirstId());
		abattoirLog.setFirstLevel(abattoirLogEntity.getFirstLevel());
		abattoirLog.setFirstName(abattoirLogEntity.getFirstName());
		abattoirLog.setSecondId(abattoirLogEntity.getSecondId());
		abattoirLog.setResult(abattoirLogEntity.getResult());
		abattoirLog.setLootTime(abattoirLogEntity.getLootTime());
		return abattoirLog;
	}

}
