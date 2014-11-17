package com.hifun.soul.gameserver.boss.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.BossEntity;
import com.hifun.soul.gameserver.boss.BossInfo;

public class BossInfoToEntityConverter implements IConverter<BossInfo, BossEntity>{

	@Override
	public BossEntity convert(BossInfo src) {
		BossEntity entity = new BossEntity();
		entity.setId(src.getBossId());
		entity.setBossState(src.getBossState());
		entity.setRemainBlood(src.getRemainBlood());
		entity.setJoinPeopleNum(src.getJoinPeopleNum());
		entity.setKillId(src.getKillerId());
		return entity;
	}

	@Override
	public BossInfo reverseConvert(BossEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
