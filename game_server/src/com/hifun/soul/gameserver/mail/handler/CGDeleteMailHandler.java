package com.hifun.soul.gameserver.mail.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.mail.manager.HumanMailManager;
import com.hifun.soul.gameserver.mail.msg.CGDeleteMail;
import com.hifun.soul.gameserver.player.Player;

/**
 * 删除接收邮件列表处理类
 * 
 * @author magicstone
 * 
 */
@Component
public class CGDeleteMailHandler implements IMessageHandlerWithType<CGDeleteMail> {

	@Override
	public short getMessageType() {
		return MessageType.CG_DELETE_MAIL;
	}

	@Override
	public void execute(CGDeleteMail message) {
		Player player = message.getPlayer();
		HumanMailManager mailManager = player.getHuman().getHumanMailManager();
		mailManager.deleteReveivedMail(message.getMailId());		
	}

}
