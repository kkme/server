package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.MeditationState;
import com.hifun.soul.gameserver.meditation.msg.CGDealAssistMeditationApply;
import com.hifun.soul.gameserver.scene.SceneHumanManager;
import com.hifun.soul.proto.data.entity.Entity.MeditationAssistFriend;

@Component
public class CGDealAssistMeditationApplyHandler implements IMessageHandlerWithType<CGDealAssistMeditationApply> {

	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_DEAL_ASSIST_MEDITATION_APPLY;
	}

	@Override
	public void execute(CGDealAssistMeditationApply message) {
		if(message.getIsAccept()==false){
			return;
		}
		Human human = message.getPlayer().getHuman();
		SceneHumanManager humanManager = GameServerAssist.getGameWorld().getSceneHumanManager();
		Human applyHuman = humanManager.getHumanByGuid(message.getApplyHumanId());
		FriendInfo friend = friendService.getFriendInfo(human.getHumanGuid(),message.getApplyHumanId());
		if(applyHuman==null){
			human.sendGenericMessage(LangConstants.PLAYER_OFFLINE);
			return;
		}
		if(friend==null){
			//not friend;
			human.sendErrorMessage(LangConstants.NOT_FRIEND);
		}
		//判断申请人是否已经终止了冥想
		if(applyHuman.getHumanMeditationManager().getMeditatiomState() != MeditationState.INPROGRESS){
			human.sendErrorMessage(LangConstants.FRIEND_MEDITATION_FINISH,applyHuman.getName());
			return;
		}
		//判断是否已经协助了该申请人
		HumanMeditationManager applyManager = applyHuman.getHumanMeditationManager();
		for(MeditationAssistFriend assistFriend : applyManager.getAssistFriends()){
			if(assistFriend.getAssistFriendId()==human.getHumanGuid()){
				human.sendGenericMessage(LangConstants.ALREADY_IN_FRIEND_ASSIST_QUENE);
				return;
			}
		}
		//接收协助信息
		if(applyHuman.getHumanMeditationManager().receivedFriendAssist(human)){
			human.getHumanMeditationManager().acceptAssistReward();
		}
		else{
			human.sendErrorMessage(LangConstants.FRIEND_ASSIST_QUENE_FULL,applyHuman.getName());
		}
	}

}
