package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.msg.CGGiveEnergy;
import com.hifun.soul.gameserver.friend.msg.GCGiveEnergy;
import com.hifun.soul.gameserver.friend.msg.GCUpdateFriendInfo;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 给好友赠送体力
 */
@Component
public class CGGiveEnergyHandler implements
		IMessageHandlerWithType<CGGiveEnergy> {
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GIVE_ENERGY;
	}

	@Override
	public void execute(CGGiveEnergy message) {
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
		// 判断两个人是不是好友
		if(!friendService.isFriend(human.getHumanGuid(),message.getToRoleId())){
			human.sendErrorMessage(LangConstants.NOT_FRIEND);
			return;
		}
		// 判断是否已经赠送过
		if(friendService.EnergyIsSended(human.getHumanGuid(), message.getToRoleId())){
			human.sendGenericMessage(LangConstants.ENERGY_SENDED);
			return;
		}
		// 判断自己的发送次数已经达到上限
		if(friendService.sendEnergyTimesIsFull(human.getHumanGuid())){
			human.sendWarningMessage(LangConstants.SEND_ENERGY_TIMES_FULL);
			return;
		}
		// 判断对方的接受次数已经达到上限
		if(friendService.beSendedEnergyTimesIsFull(message.getToRoleId())){
			human.sendWarningMessage(LangConstants.BE_SENDED_ENERGY_TIMES_FULL);
			return;
		}
		// 修改自己的是否发送状态
		friendService.sendEnergy(human.getHumanGuid(), message.getToRoleId());
		FriendInfo friendInfo = friendService.getFriendInfo(human.getHumanGuid(),message.getToRoleId());
		if(friendInfo == null){
			return;
		}
		// 更新好友信息
		GCUpdateFriendInfo gcUpdateFriendInfo = new GCUpdateFriendInfo();
		gcUpdateFriendInfo.setFriendInfo(friendInfo);
		human.sendMessage(gcUpdateFriendInfo);
		// 提示自己赠送了别人体力
		human.sendSuccessMessage(LangConstants.GIVE_ENERGY, friendInfo.getRoleName(), GameServerAssist.getGameConstants().getFriendRewardEnergy());
		// 判断对方玩家是否在线
		Human friend = sceneManager.getSceneHumanManager().getHumanByGuid(message.getToRoleId());
		if(friend != null){
			// 通知在线好友有人赠送体力(用于客户端收到消息之后的弹框,领取并回赠)
			GCGiveEnergy gcGiveEnergy = new GCGiveEnergy();
			FriendInfo otherInfo = friendService.getFriendInfo(message.getToRoleId(),human.getHumanGuid());
			if(otherInfo == null){
				return;
			}
			gcGiveEnergy.setFriendInfo(otherInfo);
			gcGiveEnergy.setEnergy(GameServerAssist.getGameConstants().getFriendRewardEnergy());
			friend.sendMessage(gcGiveEnergy);
		}
	}

}
