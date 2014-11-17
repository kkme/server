package com.hifun.soul.gameserver.refine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.msg.CGOpenRefinePanel;

@Component
public class CGOpenRefinePanelHandler implements
		IMessageHandlerWithType<CGOpenRefinePanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_REFINE_PANEL;
	}

	@Override
	public void execute(CGOpenRefinePanel message) {
		Human human = message.getPlayer().getHuman();
		human.getHumanRefineManager().openRefinePanelHandler(message.getRefineType());
		human.getHumanGuideManager().showGuide(GuideType.OPEN_REFINE_PANEL.getIndex());
	}

}
