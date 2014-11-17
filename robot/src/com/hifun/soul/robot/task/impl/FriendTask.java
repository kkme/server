package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.msg.CGAddFriend;
import com.hifun.soul.gameserver.friend.msg.CGAddFriendAgree;
import com.hifun.soul.gameserver.friend.msg.CGAddFriendRefuse;
import com.hifun.soul.gameserver.friend.msg.CGAddFriends;
import com.hifun.soul.gameserver.friend.msg.CGFriendChat;
import com.hifun.soul.gameserver.friend.msg.CGGetEnergy;
import com.hifun.soul.gameserver.friend.msg.CGGiveEnergy;
import com.hifun.soul.gameserver.friend.msg.CGRemoveFriend;
import com.hifun.soul.gameserver.friend.msg.CGShowFriendApplyList;
import com.hifun.soul.gameserver.friend.msg.CGShowFriendBattleinfos;
import com.hifun.soul.gameserver.friend.msg.CGShowFriends;
import com.hifun.soul.gameserver.friend.msg.CGShowLatestFriends;
import com.hifun.soul.gameserver.friend.msg.CGShowLuckFriends;
import com.hifun.soul.gameserver.friend.msg.GCShowFriendApplyList;
import com.hifun.soul.gameserver.friend.msg.GCShowFriendBattleinfos;
import com.hifun.soul.gameserver.friend.msg.GCShowFriends;
import com.hifun.soul.gameserver.friend.msg.GCShowLatestFriends;
import com.hifun.soul.gameserver.friend.msg.GCShowLuckFriends;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class FriendTask extends LoopExecuteTask {
	
	public FriendTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		// 返回选中的位置
		int index = MathUtils.random(0, 4);
		switch(index){
		case 0:
			CGShowLuckFriends showLuckFriends = new CGShowLuckFriends();
			getRobot().sendMessage(showLuckFriends);
			break;
		case 1:
			CGShowFriends showFriends = new CGShowFriends();
			getRobot().sendMessage(showFriends);
			break;
		case 2:
			CGShowFriendApplyList showFriendApplyList = new CGShowFriendApplyList();
			getRobot().sendMessage(showFriendApplyList);
			break;
		case 3:
			CGShowLatestFriends showLatestFriends = new CGShowLatestFriends();
			getRobot().sendMessage(showLatestFriends);
			break;
		case 4:
			CGShowFriendBattleinfos showFriendBattleInfos = new CGShowFriendBattleinfos();
			getRobot().sendMessage(showFriendBattleInfos);
			break;
		}
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCShowLuckFriends){
			GCShowLuckFriends gcMsg = (GCShowLuckFriends)message;
			FriendInfo[] friendInfos = gcMsg.getFriendInfos();
			if(friendInfos.length > 0){
				// 延迟一定时间执行
				try{
					Thread.sleep(MathUtils.random(0, 1000));
				}
				catch (Exception e) {
					System.out.println(e);
				}
				// 不添加这个就会一直加好友,因为CGAddFriends的消息会回GCShowLuckFriends
				if(MathUtils.shake(0.2f)){
					long[] roleIds = new long[friendInfos.length];
					for(int i=0;i<friendInfos.length;i++){
						roleIds[i] = friendInfos[i].getRoleId();
					}
					CGAddFriends cgAddFriends = new CGAddFriends();
					cgAddFriends.setRoleIds(roleIds);
					getRobot().sendMessage(cgAddFriends);
				}
			}
		}
		else if(message instanceof GCShowFriendApplyList){
			GCShowFriendApplyList gcMsg = (GCShowFriendApplyList)message;
			FriendInfo[] applyInfos = gcMsg.getApplyList();
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			if(applyInfos.length > 0){
				// 同意和取消随便选一个
				int index = MathUtils.random(0, 1);
				switch(index){
				case 0:
					CGAddFriendAgree cgAddFriendAgree = new CGAddFriendAgree();
					cgAddFriendAgree.setFromRoleId(applyInfos[0].getRoleId());
					getRobot().sendMessage(cgAddFriendAgree);
					break;
				case 1:
					CGAddFriendRefuse cgAddFriendRefuse = new CGAddFriendRefuse();
					cgAddFriendRefuse.setFromRoleId(applyInfos[0].getRoleId());
					getRobot().sendMessage(cgAddFriendRefuse);
					break;
				}
			}
		}
		else if(message instanceof GCShowFriends){
			GCShowFriends gcMsg = (GCShowFriends)message;
			FriendInfo[] friendInfos = gcMsg.getFriendInfos();
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			if(friendInfos.length > 0){
				FriendInfo friendInfo = friendInfos[MathUtils.random(0, friendInfos.length-1)];
				// 发送聊天或赠送体力或删除好友或领取体力
				int index = MathUtils.random(0, 3);
				switch(index){
				case 0:
					CGFriendChat friendChat = new CGFriendChat();
					friendChat.setDestRoleId(friendInfo.getRoleId());
					friendChat.setDestRoleName(friendInfo.getRoleName());
					friendChat.setContent("阿拉克极度疯狂囧囧哦i金额佛家范德萨解放军库房就考完ieoroipoasdfkkowerq1123");
					getRobot().sendMessage(friendChat);
					break;
				case 1:
					CGGiveEnergy giveEnergy = new CGGiveEnergy();
					giveEnergy.setToRoleId(friendInfo.getRoleId());
					getRobot().sendMessage(giveEnergy);
					break;
				case 2:
					CGGetEnergy getEnergy = new CGGetEnergy();
					getEnergy.setFromRoleId(friendInfo.getRoleId());
					getRobot().sendMessage(getEnergy);
					break;
				case 3:
					CGRemoveFriend removeFriend = new CGRemoveFriend();
					removeFriend.setRoleId(friendInfo.getRoleId());
					getRobot().sendMessage(removeFriend);
					break;
				}
			}
		}
		else if(message instanceof GCShowLatestFriends){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			CGAddFriend cgAddFriend = new CGAddFriend();
			cgAddFriend.setName("bot66");
			getRobot().sendMessage(cgAddFriend);
		}
		else if(message instanceof GCShowFriendBattleinfos){
			
		}
	}

}
