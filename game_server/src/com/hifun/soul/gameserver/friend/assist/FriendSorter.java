package com.hifun.soul.gameserver.friend.assist;

import java.util.Comparator;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;

/**
 * 好友排序规则
 */
public class FriendSorter implements Comparator<FriendInfo>{
	@Override
	public int compare(FriendInfo friendInfoA, FriendInfo friendInfoB) {
		boolean isOnlineA = GameServerAssist.getGameWorld().getSceneHumanManager().getHumanByGuid(friendInfoA.getRoleId())!=null;
		boolean isOnlineB = GameServerAssist.getGameWorld().getSceneHumanManager().getHumanByGuid(friendInfoA.getRoleId())!=null;
		// 根据玩家是否在线判断
		if(isOnlineA != isOnlineB){
			if(isOnlineA){
				return 1;
			}
			else{
				return -1;
			}
		}
		// 根据玩家的等级判断
		if (friendInfoA.getLevel() != friendInfoB.getLevel()) {
			return friendInfoA.getLevel() - friendInfoB.getLevel();
		}
		// 根据角色的id判断
		return (int) (friendInfoA.getRoleId() - friendInfoB.getRoleId());
	}
	
}
