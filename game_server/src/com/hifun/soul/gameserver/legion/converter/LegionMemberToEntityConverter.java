package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionMemberEntity;
import com.hifun.soul.gameserver.legion.LegionMember;

public class LegionMemberToEntityConverter implements
		IConverter<LegionMember, LegionMemberEntity> {

	@Override
	public LegionMemberEntity convert(LegionMember legionMember) {
		LegionMemberEntity legionMemberEntity = new LegionMemberEntity();
		legionMemberEntity.setId(legionMember.getHumanGuid());
		legionMemberEntity.setLegionId(legionMember.getLegionId());
		legionMemberEntity.setLevel(legionMember.getLevel());
		legionMemberEntity.setMemberName(legionMember.getMemberName());
		legionMemberEntity.setOffLineTime(legionMember.getOffLineTime());
		legionMemberEntity.setPosition(legionMember.getPosition());
		legionMemberEntity.setPositionName(legionMember.getPositionName());
		legionMemberEntity.setTodayContribution(legionMember
				.getTodayContribution());
		legionMemberEntity.setTotalContribution(legionMember
				.getTotalContribution());
		legionMemberEntity.setTodayTaskScore(legionMember.getTodayTaskScore());
		legionMemberEntity.setMedal(legionMember.getMedal());
		legionMemberEntity.setYesterdayScoreRank(legionMember
				.getYesterdayScoreRank());
		return legionMemberEntity;
	}

	@Override
	public LegionMember reverseConvert(LegionMemberEntity legionMemberEntity) {
		LegionMember legionMember = new LegionMember();
		legionMember.setLegionId(legionMemberEntity.getLegionId());
		legionMember.setHumanGuid((Long) legionMemberEntity.getId());
		legionMember.setMemberName(legionMemberEntity.getMemberName());
		legionMember.setLevel(legionMemberEntity.getLevel());
		legionMember.setOffLineTime(legionMemberEntity.getOffLineTime());
		legionMember.setPosition(legionMemberEntity.getPosition());
		legionMember.setPositionName(legionMemberEntity.getPositionName());
		legionMember.setTodayContribution(legionMemberEntity
				.getTodayContribution());
		legionMember.setTotalContribution(legionMemberEntity
				.getTotalContribution());
		legionMember.setTodayTaskScore(legionMemberEntity.getTodayTaskScore());
		legionMember.setMedal(legionMemberEntity.getMedal());
		legionMember.setYesterdayScoreRank(legionMemberEntity
				.getYesterdayScoreRank());
		return legionMember;
	}

}
