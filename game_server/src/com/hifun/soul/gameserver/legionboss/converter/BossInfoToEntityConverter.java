package com.hifun.soul.gameserver.legionboss.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionBossEntity;
import com.hifun.soul.gameserver.legionboss.LegionBossInfo;

public class BossInfoToEntityConverter implements
		IConverter<LegionBossInfo, LegionBossEntity> {

	@Override
	public LegionBossEntity convert(LegionBossInfo src) {
		LegionBossEntity entity = new LegionBossEntity();
		entity.setId(src.getBossId());
		entity.setBossState(src.getBossState());
		entity.setRemainBlood(src.getRemainBlood());
		entity.setJoinPeopleNum(src.getJoinPeopleNum());
		entity.setKillId(src.getKillerId());
		return entity;
	}

	@Override
	public LegionBossInfo reverseConvert(LegionBossEntity src) {
		throw new UnsupportedOperationException();
	}

}
