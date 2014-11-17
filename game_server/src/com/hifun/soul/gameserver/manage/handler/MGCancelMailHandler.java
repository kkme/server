package com.hifun.soul.gameserver.manage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.mail.manager.TimingMailManager;
import com.hifun.soul.gameserver.manage.msg.GMCancelMail;
import com.hifun.soul.gameserver.manage.msg.MGCancelMail;

@Component
public class MGCancelMailHandler implements IMessageHandlerWithType<MGCancelMail> {
	
	@Autowired
	private TimingMailManager timingMailManager;
	
	@Override
	public short getMessageType() {		
		return MessageType.MG_CANCEL_MAIL;
	}

	@Override
	public void execute(MGCancelMail message) {		
		timingMailManager.cancelTimingMail(message.getMailId());
		GMCancelMail gmMsg = new GMCancelMail();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setResult(true);
		message.getSession().write(gmMsg);
	}

}
