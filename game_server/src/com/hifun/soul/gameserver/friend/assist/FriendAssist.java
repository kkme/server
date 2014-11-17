package com.hifun.soul.gameserver.friend.assist;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;

/**
 * 好友辅助操作
 */
public class FriendAssist {

	/**
	 * human转化为FriendInfo
	 * @param human
	 * @return
	 */
	public static FriendInfo getFriendInfo(Human human) {
		if(human == null){
			return null;
		}
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setRoleId(human.getHumanGuid());
		friendInfo.setRoleName(human.getName());
		friendInfo.setLevel(human.getLevel());
		friendInfo.setOccupation(human.getOccupation().getIndex());
		friendInfo.setIsOnLine(true);
		return friendInfo;
	}
	
	/**
	 * 随机获取在线玩家
	 * @param sceneManager
	 * @param num
	 * @param humanGuid
	 * @return
	 */
	public static List<FriendInfo> getRandomFriendInfos(FriendService friendService, GameWorld sceneManager, int num, long selfUUID) {
		List<Human> humans = new ArrayList<Human>(sceneManager.getSceneHumanManager().getAllHumans());
		List<FriendInfo> friendInfos = new ArrayList<FriendInfo>();
		// 在线人数不足时直接全部推荐
		if(humans.size() <= num+1){
			for(Human human : humans){
				// 自己不能添加自己为好友
				if(human.getHumanGuid() == selfUUID){
					continue;
				}
				// 在好友列表的也不能添加
				if(friendService.isFriend(selfUUID,human.getHumanGuid())){
					continue;
				}
				// 自己发出过申请不能添加
				if(friendService.isSelfApplyed(selfUUID,human.getHumanGuid())) {
					continue;
				}
				// 在申请列表的不能发送
				if(friendService.isFriendApplying(selfUUID,human.getHumanGuid())){
					continue;
				}
				// 判断对方的好友申请是否达到上限
				if(friendService.friendApplyIsFull(human.getHumanGuid())){
					continue;
				}
				// 判断对方的好友是否已经达到上限
				if(!friendService.canAddFriends(human.getHumanGuid(), 1)){
					continue;
				}
				FriendInfo friendInfo = FriendAssist.getFriendInfo(human);
				if(friendInfo != null){
					friendInfos.add(friendInfo);
				}
			}
			return friendInfos;
		}
		// 每次添加一个就从humans里面去掉，严格控制循环跳出条件，避免造成死循环
		int i = 0;
		for(;friendInfos.size()<num&&i<1000;i++){
			if(humans.size() <= 0){
				break;
			}
			int index = MathUtils.random(0, humans.size()-1);
			Human human = humans.get(index);
			if(human == null){
				humans.remove(index);
				continue;
			}
			// 在好友列表的也不能添加
			if(friendService.isFriend(selfUUID,human.getHumanGuid())){
				humans.remove(index);
				continue;
			}
			// 自己发出过申请不能添加
			if(friendService.isSelfApplyed(selfUUID,human.getHumanGuid())) {
				humans.remove(index);
				continue;
			}
			// 在申请列表的不能发送
			if(friendService.isFriendApplying(selfUUID,human.getHumanGuid())){
				humans.remove(index);
				continue;
			}
			// 判断对方的好友申请是否达到上限
			if(friendService.friendApplyIsFull(human.getHumanGuid())){
				humans.remove(index);
				continue;
			}
			// 判断对方的好友是否已经达到上限
			if(!friendService.canAddFriends(human.getHumanGuid(), 1)){
				humans.remove(index);
				continue;
			}
			// 自己不能添加
			if(human.getHumanGuid() == selfUUID){
				humans.remove(index);
				continue;
			}
			// 添加好友
			FriendInfo friendInfo = FriendAssist.getFriendInfo(human);
			if(friendInfo != null){
				friendInfos.add(friendInfo);
			}
			humans.remove(index);
		}
		return friendInfos;
	}
	
}
