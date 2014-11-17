package com.hifun.soul.gameserver.legionboss.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionBossRoleEntity;
import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;

public class BossRoleInfoToEntityConverter implements
		IConverter<LegionBossRoleInfo, LegionBossRoleEntity> {

	@Override
	public LegionBossRoleEntity convert(LegionBossRoleInfo src) {
		LegionBossRoleEntity entity = new LegionBossRoleEntity();
		entity.setId(src.getHumanGuid());
		entity.setName(src.getName());
		entity.setDamage(src.getDamage());
		entity.setChargedstrikeRate(src.getChargedstrikeRate());
		entity.setEncourageRate(src.getEncourageRate());
		entity.setRank(src.getRank());
		entity.setHasDamageReward(src.getHasDamageReward());
		entity.setHasKillReward(src.getHasKillReward());
		entity.setHasRankReward(src.getHasRankReward());
		entity.setJoin(src.isJoin());
		return entity;
	}

	@Override
	public LegionBossRoleInfo reverseConvert(LegionBossRoleEntity src) {
		throw new UnsupportedOperationException();
	}

}
