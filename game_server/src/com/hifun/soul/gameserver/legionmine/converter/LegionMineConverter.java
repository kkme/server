package com.hifun.soul.gameserver.legionmine.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionMineEntity;
import com.hifun.soul.gameserver.legionmine.LegionMine;

public class LegionMineConverter implements
		IConverter<LegionMine, LegionMineEntity> {

	@Override
	public LegionMineEntity convert(LegionMine src) {
		LegionMineEntity entity = new LegionMineEntity();
		entity.setId(src.getMineIndex());
		entity.setDouble(src.isDouble());
		return entity;
	}

	@Override
	public LegionMine reverseConvert(LegionMineEntity src) {
		LegionMine mine = new LegionMine();
		mine.setMineIndex((Integer) src.getId());
		return mine;
	}

}
