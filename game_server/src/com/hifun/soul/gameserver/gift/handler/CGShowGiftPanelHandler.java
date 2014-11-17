package com.hifun.soul.gameserver.gift.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.gift.manager.HumanGiftManager;
import com.hifun.soul.gameserver.gift.msg.CGShowGiftPanel;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowGiftPanelHandler implements IMessageHandlerWithType<CGShowGiftPanel> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_SHOW_GIFT_PANEL;
	}

	@Override
	public void execute(CGShowGiftPanel message) {
		if(message.getPlayer() == null){
			return ;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		HumanGiftManager giftManager = human.getHumanGiftManager();
		giftManager.sendShowGiftPanelMessage();	
		human.getHumanGuideManager().showGuide(GuideType.OPEN_GIFT_PANEL.getIndex());
	}

}
