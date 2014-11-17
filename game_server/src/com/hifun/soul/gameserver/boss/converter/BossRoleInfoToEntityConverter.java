package com.hifun.soul.gameserver.boss.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.BossRoleEntity;
import com.hifun.soul.gameserver.boss.BossRoleInfo;

public class BossRoleInfoToEntityConverter implements IConverter<BossRoleInfo, BossRoleEntity>{

	@Override
	public BossRoleEntity convert(BossRoleInfo src) {
		BossRoleEntity entity = new BossRoleEntity();
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
		entity.setStageReward(src.getStageReward());
		return entity;
	}

	@Override
	public BossRoleInfo reverseConvert(BossRoleEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
