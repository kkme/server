package com.hifun.soul.gameserver.abattoir.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanAbattoirPrestigeEntity;
import com.hifun.soul.gameserver.abattoir.HumanAbattoirPrestige;

public class HumanAbattoirPrestigeToEntityConverter implements
		IConverter<HumanAbattoirPrestige, HumanAbattoirPrestigeEntity> {

	@Override
	public HumanAbattoirPrestigeEntity convert(HumanAbattoirPrestige abattoirPrestige) {
		HumanAbattoirPrestigeEntity entity = new HumanAbattoirPrestigeEntity();
		entity.setId(abattoirPrestige.getHumanId());
		entity.setHonor(abattoirPrestige.getPrestige());
		entity.setLastExtractTime(abattoirPrestige.getLastExtractTime());
		return entity;
	}

	@Override
	public HumanAbattoirPrestige reverseConvert(
			HumanAbattoirPrestigeEntity prestigeEntity) {
		HumanAbattoirPrestige abattoirPrestige = new HumanAbattoirPrestige();
		abattoirPrestige.setHumanId((Long) prestigeEntity.getId());
		abattoirPrestige.setPrestige(prestigeEntity.getHonor());
		abattoirPrestige.setLastExtractTime(prestigeEntity.getLastExtractTime());
		return abattoirPrestige;
	}

}
