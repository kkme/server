package com.hifun.soul.gameserver.mars.convert;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.MarsMemberEntity;
import com.hifun.soul.gameserver.mars.MarsMember;

public class MarsMemberToEntityConverter implements
		IConverter<MarsMember, MarsMemberEntity> {

	@Override
	public MarsMemberEntity convert(MarsMember src) {
		MarsMemberEntity entity = new MarsMemberEntity();
		entity.setId(src.getHumanId());
		entity.setHumanName(src.getHumanName());
		entity.setHumanLevel(src.getHumanLevel());
		entity.setOccupation(src.getOccupation());
		entity.setTodayKillValue(src.getTodayKillValue());
		entity.setTotalKillValue(src.getTotalKillValue());
		entity.setRewardCoin(src.getRewardCoin());
		entity.setRewardPrestige(src.getRewardPrestige());
		entity.setRewardState(src.getRewardState());
		return entity;

	}

	@Override
	public MarsMember reverseConvert(MarsMemberEntity src) {
		MarsMember marsMember = new MarsMember();
		marsMember.setHumanId((Long) src.getId());
		marsMember.setHumanName(src.getHumanName());
		marsMember.setHumanLevel(src.getHumanLevel());
		marsMember.setOccupation(src.getOccupation());
		marsMember.setTodayKillValue(src.getTodayKillValue());
		marsMember.setTotalKillValue(src.getTotalKillValue());
		marsMember.setRewardCoin(src.getRewardCoin());
		marsMember.setRewardPrestige(src.getRewardPrestige());
		marsMember.setRewardState(src.getRewardState());
		return marsMember;
	}

}
