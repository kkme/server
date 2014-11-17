package com.hifun.soul.gameserver.meditation.handler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.MeditationState;
import com.hifun.soul.gameserver.meditation.MeditationTemplateManager;
import com.hifun.soul.gameserver.meditation.msg.CGAssistMeditationApply;
import com.hifun.soul.gameserver.meditation.msg.GCAssistMeditationApply;
import com.hifun.soul.gameserver.meditation.msg.GCFriendForAssistNotEnough;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Component
public class CGAssistMeditationApplyHandler implements IMessageHandlerWithType<CGAssistMeditationApply> {

	@Autowired
	private MeditationTemplateManager templateManager ;
	@Autowired
	private FriendService friendService;
	@Override
	public short getMessageType() {		
		return MessageType.CG_ASSIST_MEDITATION_APPLY;
	}

	@Override
	public void execute(CGAssistMeditationApply message) {
		Human human = message.getPlayer().getHuman();
		HumanMeditationManager manager = human.getHumanMeditationManager();
		if(manager.getMeditatiomState()!=MeditationState.INPROGRESS){
			human.sendErrorMessage(LangConstants.FRIEND_ASSIST_NEED_MEDITATION_FIRST);
			return;
		}
		int assistFriendNum = manager.getAssistFriends().size();
		if(manager.getAssistPositionNum()-assistFriendNum<=0){
			human.sendGenericMessage(LangConstants.NEED_MORE_FRIEND_ASSIST_POSITION);
			return;
		}
		Set<Long> friends = friendService.getFriendIds(human.getHumanGuid());
		if(friends == null || friends.size()==0){
			sendFriendNotEnoughMessage(human,0);
			return;
		}
		int maxApplyNum = templateManager.getAssistApplyNum(manager.getAssistPositionNum()-assistFriendNum);		
		int applyNum = 0;
		SceneHumanManager humanManager = GameServerAssist.getGameWorld().getSceneHumanManager();
		for(Long friendRoleId : friends){
			if(applyNum>=maxApplyNum){
				break;
			}
			Human friend = humanManager.getHumanByGuid(friendRoleId);
			if(friend !=null && manager.isInAssistFriendQueue(friend.getHumanGuid())==false){
				GCAssistMeditationApply gcApplyMsg = new GCAssistMeditationApply();
				gcApplyMsg.setApplyHumanId(human.getHumanGuid());
				gcApplyMsg.setApplyHumanName(human.getName());
				friend.sendMessage(gcApplyMsg);
				applyNum++;
			}
		}
		if(applyNum<manager.getAssistPositionNum()-assistFriendNum){
			sendFriendNotEnoughMessage(human,applyNum);			
			return;
		}
		human.sendSuccessMessage(LangConstants.ASSIST_APPLY_SEND_SUCCESS,applyNum);
	}
	
	/**
	 * 发送提示好友太少的消息
	 * @param human
	 * @param applyNum
	 */
	private void sendFriendNotEnoughMessage(Human human,int applyNum){		
		GCFriendForAssistNotEnough gcFriendNotEnoughMsg = new GCFriendForAssistNotEnough();
		gcFriendNotEnoughMsg.setApplySendNum(applyNum);
		human.sendMessage(gcFriendNotEnoughMsg);
	}

}
