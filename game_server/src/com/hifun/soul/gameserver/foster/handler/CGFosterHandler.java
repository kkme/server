package com.hifun.soul.gameserver.foster.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.foster.manager.HumanFosterManager;
import com.hifun.soul.gameserver.foster.msg.CGFoster;
import com.hifun.soul.gameserver.foster.msg.GCFoster;
import com.hifun.soul.gameserver.foster.template.FosterModeTemplate;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGFosterHandler implements IMessageHandlerWithType<CGFoster> {
	@Autowired
	private GameFuncService gameFuncService;
	@Override
	public short getMessageType() {		
		return MessageType.CG_FOSTER;
	}

	@Override
	public void execute(CGFoster message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.FOSTER, true)) {
			return;
		}
		FosterModeTemplate modeTemplate = GameServerAssist.getFosterTemplateManager().getFosterModeTemplate(message.getModeId());
		if(modeTemplate==null){
			return;
		}
		if(modeTemplate.getOpenVipLevel()>human.getVipLevel()){
			human.sendErrorMessage(LangConstants.VIP_NOT_ENOUGH, modeTemplate.getOpenVipLevel());
			return;
		}
		HumanFosterManager fosterManager = human.getHumanFosterManager();
		int[] randomValues = fosterManager.getRandomForsterValues(message.getModeId());
		if(randomValues == null){
			return;
		}
		GCFoster gcMsg = new GCFoster();
		gcMsg.setFosterAttributeNum(randomValues);
		human.sendMessage(gcMsg);
	}

}
