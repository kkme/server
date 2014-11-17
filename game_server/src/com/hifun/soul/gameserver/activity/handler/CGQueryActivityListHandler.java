package com.hifun.soul.gameserver.activity.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.ActivityTemplateManager;
import com.hifun.soul.gameserver.activity.msg.CGQueryActivityList;
import com.hifun.soul.gameserver.activity.template.ActivityShowTypeTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 获取某个类型的活动列表处理
 * 
 * @author magicstone
 *
 */
@Component
public class CGQueryActivityListHandler implements IMessageHandlerWithType<CGQueryActivityList> {
	@Autowired
	private ActivityTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_QUERY_ACTIVITY_LIST;
	}

	@Override
	public void execute(CGQueryActivityList message) {
		if(message.getPlayer() == null){
			return;
		}
		if(message.getPlayer().getHuman() == null){
			return; 
		}
		Human human = message.getPlayer().getHuman();		
		ActivityShowTypeTemplate template = templateManager.getShowTypeTemplate(message.getActivityShowType());
		if(template == null || human.getLevel()<template.getOpenLevel()){
			return;
		}
		human.getHumanActivityManager().sendActivityListMessage(message.getActivityShowType());
	}

}
