package com.hifun.soul.gameserver.manage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.mail.manager.TimingMailManager;
import com.hifun.soul.gameserver.manage.msg.GMUpdateMail;
import com.hifun.soul.gameserver.manage.msg.MGUpdateMail;
import com.hifun.soul.proto.services.Services.MailObject;

@Component
public class MGUpdateMailHandler implements IMessageHandlerWithType<MGUpdateMail> {
	
	@Autowired
	private TimingMailManager timingMailManager;
	
	@Override
	public short getMessageType() {		
		return MessageType.MG_UPDATE_MAIL;
	}

	@Override
	public void execute(MGUpdateMail message) {
		boolean result = false;
		MailObject mailObj = message.getMailObject();
		if(mailObj.getIsTimingMail()){
			result = timingMailManager.updateTimingMail(mailObj);
		}
		GMUpdateMail gmMsg = new GMUpdateMail();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setResult(result);
		message.getSession().write(gmMsg);
	}

}
