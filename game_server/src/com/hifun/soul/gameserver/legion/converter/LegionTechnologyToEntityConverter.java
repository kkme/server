package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionTechnologyEntity;
import com.hifun.soul.gameserver.legion.LegionTechnology;

public class LegionTechnologyToEntityConverter implements
		IConverter<LegionTechnology, LegionTechnologyEntity> {

	@Override
	public LegionTechnologyEntity convert(LegionTechnology src) {
		LegionTechnologyEntity entity = new LegionTechnologyEntity();
		entity.setId(src.getId());
		entity.setLegionId(src.getLegionId());
		entity.setTechnologyType(src.getTechnologyType());
		entity.setTechnologyLevel(src.getTechnologyLevel());
		entity.setCurrentCoin(src.getCurrentCoin());
		return entity;
	}

	@Override
	public LegionTechnology reverseConvert(LegionTechnologyEntity src) {
		LegionTechnology technology = new LegionTechnology();
		technology.setId((Long) src.getId());
		technology.setLegionId(src.getLegionId());
		technology.setTechnologyType(src.getTechnologyType());
		technology.setTechnologyLevel(src.getTechnologyLevel());
		technology.setCurrentCoin(src.getCurrentCoin());
		return technology;
	}

}
