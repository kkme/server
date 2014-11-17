package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionBuildingEntity;
import com.hifun.soul.gameserver.legion.LegionBuilding;

public class LegionBuildingToEntityConverter implements
		IConverter<LegionBuilding, LegionBuildingEntity> {

	@Override
	public LegionBuildingEntity convert(LegionBuilding src) {
		LegionBuildingEntity entity = new LegionBuildingEntity();
		entity.setId(src.getId());
		entity.setBuildingType(src.getBuildingType());
		entity.setBuildingLevel(src.getBuildingLevel());
		entity.setLegionId(src.getLegionId());
		return entity;
	}

	@Override
	public LegionBuilding reverseConvert(LegionBuildingEntity src) {
		LegionBuilding building = new LegionBuilding();
		building.setId((Long) src.getId());
		building.setBuildingType(src.getBuildingType());
		building.setBuildingLevel(src.getBuildingLevel());
		building.setLegionId(src.getLegionId());
		return building;
	}

}
