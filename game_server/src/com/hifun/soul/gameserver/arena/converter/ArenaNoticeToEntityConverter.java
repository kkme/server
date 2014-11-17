package com.hifun.soul.gameserver.arena.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.ArenaNoticeEntity;
import com.hifun.soul.gameserver.arena.ArenaNotice;

public class ArenaNoticeToEntityConverter implements IConverter<ArenaNotice, ArenaNoticeEntity>{

	@Override
	public ArenaNoticeEntity convert(ArenaNotice arenaNotice) {
		ArenaNoticeEntity entity = new ArenaNoticeEntity();
		entity.setOtherRoleId(arenaNotice.getOtherRoleId());
		entity.setOtherRoleName(arenaNotice.getOtherRoleName());
		entity.setRoleId(arenaNotice.getRoleId());
		entity.setRoleName(arenaNotice.getRoleName());
		entity.setWin(arenaNotice.getWin());
		entity.setBattleTime(arenaNotice.getChallengeTime());
		entity.setChallenger(arenaNotice.getIsChallenger());
		entity.setRank(arenaNotice.getRank());
		entity.setIsUpFiveAndHigherVsLower(arenaNotice.getIsUpFiveAndHigherVsLower());
		return entity;
	}

	@Override
	public ArenaNotice reverseConvert(ArenaNoticeEntity src) {
		ArenaNotice arenaNotice = new ArenaNotice();
		arenaNotice.setRoleId(src.getRoleId());
		arenaNotice.setRoleName(src.getRoleName());
		arenaNotice.setOtherRoleId(src.getOtherRoleId());
		arenaNotice.setOtherRoleName(src.getOtherRoleName());
		arenaNotice.setWin(src.isWin());
		arenaNotice.setChallengeTime(src.getBattleTime());
		arenaNotice.setIsChallenger(src.isChallenger());
		arenaNotice.setRank(src.getRank());
		arenaNotice.setIsUpFiveAndHigherVsLower(src.getIsUpFiveAndHigherVsLower());
		return arenaNotice;
	}
	
}
