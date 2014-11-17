package com.hifun.soul.gameserver.item.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.msg.CGMaterialGuide;
import com.hifun.soul.gameserver.item.msg.GCMaterialGuide;
import com.hifun.soul.gameserver.item.service.EquipMakeTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipMakeGuideTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGMaterialGuideHandler implements
		IMessageHandlerWithType<CGMaterialGuide> {

	@Autowired
	private EquipMakeTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_MATERIAL_GUIDE;
	}

	@Override
	public void execute(CGMaterialGuide message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 找到材料对应的引导
		EquipMakeGuideTemplate template = templateManager.getEquipMakeGuideTemplate(message.getItemId());
		if(template == null){
			return;
		}
		
		// 判断材料对应的引导关卡是否开启
		String[] guides = template.getGuide().split(",");
		if(guides.length < 2){
			return;
		}
		Integer guideStageId = Integer.parseInt(guides[1]);
		if(human.getHumanStageManager().getNextStageId() < guideStageId){
			human.sendWarningMessage(LangConstants.STAGE_NOT_OPEN);
			return;
		}
		
		// 通知客户端引导
		GCMaterialGuide gcMsg = new GCMaterialGuide();
		gcMsg.setGuide(template.getGuide());
		human.sendMessage(gcMsg);
	}

}
