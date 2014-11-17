package com.hifun.soul.gameserver.manage.handler;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.mail.manager.TimingMailManager;
import com.hifun.soul.gameserver.mall.msg.internal.SendTimingMailMessage;
import com.hifun.soul.gameserver.manage.msg.GMGetTimingMailList;
import com.hifun.soul.gameserver.manage.msg.MGGetTimingMailList;
import com.hifun.soul.proto.services.Services.MailObject;

@Component
public class MGGetTimingMailListHandler implements IMessageHandlerWithType<MGGetTimingMailList> {
	@Autowired
	private TimingMailManager timingMailManager;
	
	@Override
	public short getMessageType() {		
		return MessageType.MG_GET_TIMING_MAIL_LIST;
	}

	@Override
	public void execute(MGGetTimingMailList message) {
		Collection<SendTimingMailMessage> timingMails = timingMailManager.getTimingMailMessages();
		MailObject[] mailList = new MailObject[timingMails.size()];
		int index=0;
		for(SendTimingMailMessage timingMailMsg : timingMails){
			mailList[index] = timingMailMsg.getMail().build();
			index++;
		}
		GMGetTimingMailList gmMsg = new GMGetTimingMailList();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setMailObjects(mailList);
		message.getSession().write(gmMsg);
	}

}
