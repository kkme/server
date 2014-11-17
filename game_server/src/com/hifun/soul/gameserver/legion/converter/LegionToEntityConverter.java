package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionEntity;
import com.hifun.soul.gameserver.legion.Legion;

public class LegionToEntityConverter implements
		IConverter<Legion, LegionEntity> {

	@Override
	public LegionEntity convert(Legion legion) {
		LegionEntity legionEntity = new LegionEntity();
		legionEntity.setId(legion.getId());
		legionEntity.setCommanderId(legion.getCommanderId());
		legionEntity.setCommanderName(legion.getCommanderName());
		legionEntity.setExperience(legion.getExperience());
		legionEntity.setLegionLevel(legion.getLegionLevel());
		legionEntity.setLegionName(legion.getLegionName());
		legionEntity.setMemberLimit(legion.getMemberLimit());
		legionEntity.setMemberNum(legion.getMemberNum());
		legionEntity.setNotice(legion.getNotice());
		legionEntity.setLegionCoin(legion.getLegionCoin());
		return legionEntity;
	}

	@Override
	public Legion reverseConvert(LegionEntity legionEntity) {
		Legion legion = new Legion();
		legion.setId((Long) legionEntity.getId());
		legion.setCommanderId(legionEntity.getCommanderId());
		legion.setCommanderName(legionEntity.getCommanderName());
		legion.setExperience(legionEntity.getExperience());
		legion.setLegionLevel(legionEntity.getLegionLevel());
		legion.setLegionName(legionEntity.getLegionName());
		legion.setMemberNum(legionEntity.getMemberNum());
		legion.setMemberLimit(legionEntity.getMemberLimit());
		legion.setNotice(legionEntity.getNotice());
		legion.setLegionCoin(legionEntity.getLegionCoin());
		return legion;
	}

}
