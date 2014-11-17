package com.hifun.soul.gameserver.arena.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.ArenaMemberEntity;
import com.hifun.soul.gameserver.arena.ArenaMember;

public class ArenaMemberToEntityConverter implements IConverter<ArenaMember, ArenaMemberEntity>{

	@Override
	public ArenaMemberEntity convert(ArenaMember arenaMember) {
		ArenaMemberEntity entity = new ArenaMemberEntity();
		entity.setIcon(arenaMember.getIcon());
		entity.setId(arenaMember.getRoleId());
		entity.setLevel(arenaMember.getLevel());
		entity.setName(arenaMember.getName());
		entity.setRank(arenaMember.getRank());
		entity.setRankRewardId(arenaMember.getRankRewardId());
		entity.setRankRewardState(arenaMember.getRankRewardState());
		entity.setOccupation(arenaMember.getOccupation());
		return entity;
	}

	@Override
	public ArenaMember reverseConvert(ArenaMemberEntity src) {
		ArenaMember arenaMember = new ArenaMember();
		arenaMember.setIcon(src.getIcon());
		arenaMember.setRoleId((Long) src.getId());
		arenaMember.setLevel(src.getLevel());
		arenaMember.setName(src.getName());
		arenaMember.setRank(src.getRank());
		arenaMember.setRankRewardId(src.getRankRewardId());
		arenaMember.setRankRewardState(src.getRankRewardState());
		arenaMember.setOccupation(src.getOccupation());
		return arenaMember;
	}
	
}
