package com.hifun.soul.gameserver.bloodtemple.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.BloodTempleLogEntity;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleLog;

public class BloodTempleLogToEntityConverter implements
		IConverter<BloodTempleLog, BloodTempleLogEntity> {

	@Override
	public BloodTempleLogEntity convert(BloodTempleLog bloodTempleLog) {
		BloodTempleLogEntity bloodTempleLogEntity = new BloodTempleLogEntity();
		bloodTempleLogEntity.setId(bloodTempleLog.getId());
		bloodTempleLogEntity.setFirstId(bloodTempleLog.getFirstId());
		bloodTempleLogEntity.setFirstLevel(bloodTempleLog.getFirstLevel());
		bloodTempleLogEntity.setFirstName(bloodTempleLog.getFirstName());
		bloodTempleLogEntity.setSecondId(bloodTempleLog.getSecondId());
		bloodTempleLogEntity.setResult(bloodTempleLog.getResult());
		bloodTempleLogEntity.setLootTime(bloodTempleLog.getLootTime());
		return bloodTempleLogEntity;
	}

	@Override
	public BloodTempleLog reverseConvert(
			BloodTempleLogEntity bloodTempleLogEntity) {
		BloodTempleLog bloodTempleLog = new BloodTempleLog();
		bloodTempleLog.setId((Integer) bloodTempleLogEntity.getId());
		bloodTempleLog.setFirstId(bloodTempleLogEntity.getFirstId());
		bloodTempleLog.setFirstLevel(bloodTempleLogEntity.getFirstLevel());
		bloodTempleLog.setFirstName(bloodTempleLogEntity.getFirstName());
		bloodTempleLog.setSecondId(bloodTempleLogEntity.getSecondId());
		bloodTempleLog.setResult(bloodTempleLogEntity.getResult());
		bloodTempleLog.setLootTime(bloodTempleLogEntity.getLootTime());
		return bloodTempleLog;
	}

}
