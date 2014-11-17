package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionHonorEntity;
import com.hifun.soul.gameserver.legion.LegionHonor;

public class LegionHonorToEntityConverter implements
		IConverter<LegionHonor, LegionHonorEntity> {

	@Override
	public LegionHonorEntity convert(LegionHonor src) {
		LegionHonorEntity entity = new LegionHonorEntity();
		entity.setId(src.getId());
		entity.setLegionId(src.getLegionId());
		entity.setTitleId(src.getTitleId());
		entity.setExchangeNum(src.getExchangeNum());
		return entity;
	}

	@Override
	public LegionHonor reverseConvert(LegionHonorEntity src) {
		LegionHonor honor = new LegionHonor();
		honor.setId((Long) src.getId());
		honor.setLegionId(src.getLegionId());
		honor.setTitleId(src.getTitleId());
		honor.setExchangeNum(src.getExchangeNum());
		return honor;
	}

}
