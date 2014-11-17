package com.hifun.soul.gameserver.legionmine.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionMineMemberEntity;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;

public class LegionMineMemberConverter implements
		IConverter<LegionMineMember, LegionMineMemberEntity> {

	@Override
	public LegionMineMemberEntity convert(LegionMineMember src) {
		LegionMineMemberEntity entity = new LegionMineMemberEntity();
		entity.setId(src.getHumanId());
		entity.setMineIndex(src.getMineIndex());
		entity.setOccupyValue(src.getOccupyValue());
		entity.setOccupyTime(src.getOccupyTime());
		entity.setEncourageRate(src.getEncourageRate());
		entity.setAttackRate(src.getAttackRate());
		entity.setDefenseRate(src.getDefenseRate());
		entity.setQuit(src.isQuit());
		entity.setJoin(src.isJoin());
		entity.setRank(src.getRank());
		entity.setLegionWin(src.isLegionWin());
		return entity;
	}

	@Override
	public LegionMineMember reverseConvert(LegionMineMemberEntity src) {
		LegionMineMember member = new LegionMineMember();
		member.setHumanId((Long) src.getId());
		member.setMineIndex(src.getMineIndex());
		member.setOccupyValue(src.getOccupyValue());
		member.setOccupyTime(src.getOccupyTime());
		member.setEncourageRate(src.getEncourageRate());
		member.setQuit(src.isQuit());
		member.setJoin(src.isJoin());
		member.setRank(src.getRank());
		member.setLegionWin(src.isLegionWin());
		return member;
	}
}
