package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionApplyEntity;
import com.hifun.soul.gameserver.legion.LegionApply;

public class LegionApplyToEntityConverter implements
		IConverter<LegionApply, LegionApplyEntity> {

	@Override
	public LegionApplyEntity convert(LegionApply legionApply) {
		LegionApplyEntity legionApplyEntity = new LegionApplyEntity();
		legionApplyEntity.setId(legionApply.getId());
		legionApplyEntity.setApplyHumanGuid(legionApply.getApplyHumanGuid());
		legionApplyEntity.setApplyArenaRank(legionApply.getApplyArenaRank());
		legionApplyEntity.setApplyHumanLevel(legionApply.getApplyHumanLevel());
		legionApplyEntity.setApplyHumanName(legionApply.getApplyHumanName());
		legionApplyEntity.setApplyLegionId(legionApply.getApplyLegionId());
		legionApplyEntity.setApplyStatus(legionApply.getApplyStatus());
		legionApplyEntity.setApplyTime(legionApply.getApplyTime());
		return legionApplyEntity;
	}

	@Override
	public LegionApply reverseConvert(LegionApplyEntity legionApplyEntity) {
		LegionApply legionApply = new LegionApply();
		legionApply.setId((Long) legionApplyEntity.getId());
		legionApply.setApplyHumanGuid((Long) legionApplyEntity.getApplyHumanGuid());
		legionApply.setApplyHumanName(legionApplyEntity.getApplyHumanName());
		legionApply.setApplyArenaRank(legionApplyEntity.getApplyArenaRank());
		legionApply.setApplyHumanLevel(legionApplyEntity.getApplyHumanLevel());
		legionApply.setApplyLegionId(legionApplyEntity.getApplyLegionId());
		legionApply.setApplyStatus(legionApplyEntity.getApplyStatus());
		legionApply.setApplyTime(legionApplyEntity.getApplyTime());
		return legionApply;
	}

}
