package com.hifun.soul.gameserver.technology.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.technology.manager.HumanTechnologyManager;
import com.hifun.soul.gameserver.technology.msg.CGShowTechnologyInfo;
import com.hifun.soul.gameserver.technology.msg.GCShowTechnologyInfo;
import com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo;
import com.hifun.soul.gameserver.technology.service.TechnologyTemplateManager;

@Component
public class CGShowTechnologyInfoHandler implements
		IMessageHandlerWithType<CGShowTechnologyInfo> {
	@Autowired
	private TechnologyTemplateManager technologyTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_TECHNOLOGY_INFO;
	}

	@Override
	public void execute(CGShowTechnologyInfo message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.TECHNOLOGY, true)){
			return;
		}
		
		HumanTechnologyManager humanTechnologyManager = human.getHumanTechnologyManager();
		TechnologyDetailInfo technologyDetailInfo 
			= technologyTemplateManager.getTechnologyDetailInfo(message.getTechnologyId(), humanTechnologyManager.getTechnologyLevel(message.getTechnologyId()));
		if(technologyDetailInfo == null){
			return;
		}
		
		// 返回科技信息
		GCShowTechnologyInfo gcMsg = new GCShowTechnologyInfo();
		gcMsg.setTechnologyDetailInfo(technologyDetailInfo);
		human.sendMessage(gcMsg);
	}

}
