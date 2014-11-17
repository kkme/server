package com.hifun.soul.gameserver.bloodtemple.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanBloodTemplePrestigeEntity;
import com.hifun.soul.gameserver.bloodtemple.HumanBloodTemplePrestige;

public class HumanBloodTemplePrestigeToEntityConverter implements
		IConverter<HumanBloodTemplePrestige, HumanBloodTemplePrestigeEntity> {

	@Override
	public HumanBloodTemplePrestigeEntity convert(
			HumanBloodTemplePrestige bloodTempleHonor) {
		HumanBloodTemplePrestigeEntity entity = new HumanBloodTemplePrestigeEntity();
		entity.setId(bloodTempleHonor.getHumanId());
		entity.setHonor(bloodTempleHonor.getPrestige());
		entity.setLastExtractTime(bloodTempleHonor.getLastExtractTime());
		return entity;
	}

	@Override
	public HumanBloodTemplePrestige reverseConvert(
			HumanBloodTemplePrestigeEntity honorEntity) {
		HumanBloodTemplePrestige bloodTempleHonor = new HumanBloodTemplePrestige();
		bloodTempleHonor.setHumanId((Long) honorEntity.getId());
		bloodTempleHonor.setPrestige(honorEntity.getHonor());
		bloodTempleHonor.setLastExtractTime(honorEntity.getLastExtractTime());
		return bloodTempleHonor;
	}

}
