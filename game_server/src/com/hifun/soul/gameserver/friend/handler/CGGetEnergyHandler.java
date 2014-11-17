package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.manager.HumanFriendManager;
import com.hifun.soul.gameserver.friend.msg.CGGetEnergy;
import com.hifun.soul.gameserver.friend.msg.GCLeftFriendRewardTimes;
import com.hifun.soul.gameserver.friend.msg.GCUpdateFriendInfo;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 领取好友能量
 */
@Component
public class CGGetEnergyHandler implements IMessageHandlerWithType<CGGetEnergy> {
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_ENERGY;
	}

	@Override
	public void execute(CGGetEnergy message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		long friendId = message.getFromRoleId();
		HumanFriendManager friendManager = human.getHumanFriendManager();
		// 判断玩家是否是自己的好友
		if(!friendService.isFriend(human.getHumanGuid(),friendId)){
			human.sendErrorMessage(LangConstants.NOT_FRIEND);
			return;
		}
		// 判断领取次数是否已经够了
		if(!friendManager.canGetEnergy()){
			human.sendGenericMessage(LangConstants.FRIEND_REWARD_FULL);
			return;
		}
		// 判断能量值是否充沛
		if(human.getEnergy()+GameServerAssist.getGameConstants().getFriendRewardEnergy()>GameServerAssist.getGameConstants().getMaxEnergy()){
			human.sendGenericMessage(LangConstants.ENERGY_FULL);
			return;
		}
		// 判断当前好友状态是否是可以领取的状态
		if(!friendService.canGetEnergy(human.getHumanGuid(),friendId)){
			return;
		}
		// 领取次数
		friendManager.setGetRewardTime(friendManager.getGetRewardTime()+1);
		// 玩家修改自己的体力值
		human.setEnergy(human.getEnergy()+GameServerAssist.getGameConstants().getFriendRewardEnergy(),EnergyLogReason.FRIEND_GIVE_ENERGY,"friendId:"+friendId);
		// 领取体力
		friendService.getEnergy(human.getHumanGuid(), friendId);
		// 提示接收体力
		int leftTimes = GameServerAssist.getGameConstants().getFriendRewardMax() - friendManager.getGetRewardTime();
		human.sendSuccessMessage(LangConstants.GET_ENERGY, GameServerAssist.getGameConstants().getFriendRewardEnergy(), leftTimes);
		// 同步数据库
		FriendInfo friendInfo = friendService.getFriendInfo(human.getHumanGuid(),friendId);
		if(friendInfo == null){
			return;
		}
		// 更新好友信息
		GCUpdateFriendInfo gcMsg = new GCUpdateFriendInfo();
		gcMsg.setFriendInfo(friendInfo);
		human.sendMessage(gcMsg);
		// 更新体力次数
		GCLeftFriendRewardTimes gcLeftFriendRewardTimes = new GCLeftFriendRewardTimes();
		gcLeftFriendRewardTimes.setLeftTimes(leftTimes);
		human.sendMessage(gcLeftFriendRewardTimes);
	}

}
