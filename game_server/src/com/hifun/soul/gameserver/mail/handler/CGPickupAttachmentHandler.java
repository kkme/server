package com.hifun.soul.gameserver.mail.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.mail.manager.HumanMailManager;
import com.hifun.soul.gameserver.mail.msg.CGPickupAttachment;
import com.hifun.soul.gameserver.mail.msg.GCPickupAttachment;
import com.hifun.soul.gameserver.player.Player;

/**
 * 拾取邮件中的附带物品处理类
 * 
 * @author magicstone
 *
 */
@Component
public class CGPickupAttachmentHandler implements IMessageHandlerWithType<CGPickupAttachment> {

	@Override
	public short getMessageType() {	
		return MessageType.CG_PICKUP_ATTACHMENT;
	}

	@Override
	public void execute(CGPickupAttachment message) {
		Player player = message.getPlayer();
		HumanMailManager mailManager = player.getHuman().getHumanMailManager();
		boolean result = mailManager.pickUpItemFromReceivedMail(message.getMailId());
		GCPickupAttachment gcMsg = new GCPickupAttachment();
		gcMsg.setMailId(message.getMailId());
		gcMsg.setIsSuccess(result);
		player.sendMessage(gcMsg);
	}

}
