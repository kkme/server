package com.hifun.soul.gameserver.meditation.handler;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.MeditationTemplateManager;
import com.hifun.soul.gameserver.meditation.msg.CGUpdateMeditationPannel;
import com.hifun.soul.gameserver.meditation.msg.GCUpdateMeditationPannel;
import com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo;
import com.hifun.soul.gameserver.meditation.template.MeditationAssistPositionTemplate;
import com.hifun.soul.proto.data.entity.Entity.MeditationAssistFriend;

@Component
public class CGUpdateMeditationPannelHandler implements IMessageHandlerWithType<CGUpdateMeditationPannel> {

	@Autowired
	private MeditationTemplateManager templateManager;
	@Autowired
	private FriendService friendService;
	@Override
	public short getMessageType() {
		return MessageType.CG_UPDATE_MEDITATION_PANNEL;
	}

		

	@Override
	public void execute(CGUpdateMeditationPannel message) {		
		final Human human = message.getPlayer().getHuman();
		HumanMeditationManager manager = human.getHumanMeditationManager();		
		int meditationState = manager.getMeditatiomState();
		int totalTechPoint = manager.getTotalTechPoint();
		Collection<MeditationAssistFriend> assistFriends = manager.getAssistFriends();
		MeditationFriendAssistInfo[] friendsAssist = new MeditationFriendAssistInfo[assistFriends.size()];
		if(assistFriends!=null && assistFriends.size()>0){			
			int index=0;
			for(final MeditationAssistFriend friend : assistFriends){				
				FriendInfo friendInfo = friendService.getFriendInfo(human.getHumanGuid(),friend.getAssistFriendId());
				if(friendInfo!=null){
					friendsAssist[index] = new MeditationFriendAssistInfo();
					friendsAssist[index].setIndex(friend.getPositionIndex());					
					friendsAssist[index].setFriendInfo(friendInfo);
					friendsAssist[index].setAssistRate(templateManager.getAssistRate(friend.getPositionIndex()+1));
					friendsAssist[index].setTotalTechPoint(totalTechPoint);
					index++;
				}
			}
		}
		GCUpdateMeditationPannel gcMsg = new GCUpdateMeditationPannel();
		gcMsg.setAssistPositionNum(manager.getAssistPositionNum());
		gcMsg.setHelpFriendTimesRemain(manager.getRemainAssistMeditatiomTimes());
		gcMsg.setMeditationInfo(templateManager.getMeditationTimeModes(human));
		gcMsg.setMeditationMode(templateManager.getAvailableMeditatiomMode(human));
		gcMsg.setCurrentTechPoint(manager.getCurrentTechPoint());
		gcMsg.setTotalTechPoint(totalTechPoint);
		gcMsg.setMeditationState(meditationState);
		gcMsg.setSelectedModeIndex(manager.getSelectedModeIndex());
		gcMsg.setSelectedTimeIndex(manager.getSelectedTimeIndex());
		gcMsg.setAssistRate(templateManager.getAssistRate(manager.getAssistFriends().size()));
		MeditationAssistPositionTemplate assistPositionTemplate = templateManager.getAssistPositionTemplate(human.getHumanMeditationManager().getAssistPositionNum()+1);
		if(assistPositionTemplate != null){
			gcMsg.setLevelLimit(assistPositionTemplate.getOpenLevel());
			gcMsg.setCoinLimit(assistPositionTemplate.getCostCoinNum());
			gcMsg.setCrystalLimit(assistPositionTemplate.getCostCrystalNum());
		}
		gcMsg.setFriendAssist(friendsAssist);
		human.sendMessage(gcMsg);
		// 下发冥想CD信息
		human.getHumanCdManager().snapCdQueueInfo(CdType.MEDITATION);
	}
	
}
