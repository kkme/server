package com.hifun.soul.gameserver.friend.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.FriendBattleEntity;
import com.hifun.soul.gameserver.friend.FriendBattleInfo;

public class FriendBattleToEntityConverter implements IConverter<FriendBattleInfo, FriendBattleEntity>{

	@Override
	public FriendBattleEntity convert(FriendBattleInfo friendBattleInfo) {
		FriendBattleEntity entity = new FriendBattleEntity();
		entity.setOtherRoleId(friendBattleInfo.getOtherRoleId());
		entity.setOtherRoleName(friendBattleInfo.getOtherRoleName());
		entity.setRoleId(friendBattleInfo.getRoleId());
		entity.setRoleName(friendBattleInfo.getRoleName());
		entity.setWin(friendBattleInfo.getWin());
		entity.setBattleTime(friendBattleInfo.getBattleTime());
		entity.setChallenger(friendBattleInfo.getIsChallenger());
		return entity;
	}

	@Override
	public FriendBattleInfo reverseConvert(FriendBattleEntity src) {
		FriendBattleInfo friendBattleInfo = new FriendBattleInfo();
		friendBattleInfo.setRoleId(src.getRoleId());
		friendBattleInfo.setRoleName(src.getRoleName());
		friendBattleInfo.setOtherRoleId(src.getOtherRoleId());
		friendBattleInfo.setOtherRoleName(src.getOtherRoleName());
		friendBattleInfo.setWin(src.isWin());
		friendBattleInfo.setBattleTime(src.getBattleTime());
		friendBattleInfo.setIsChallenger(src.isChallenger());
		return friendBattleInfo;
	}
	
}
