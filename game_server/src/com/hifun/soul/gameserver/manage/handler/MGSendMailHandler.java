package com.hifun.soul.gameserver.manage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.mail.Mail;
import com.hifun.soul.gameserver.mail.MailSendState;
import com.hifun.soul.gameserver.mail.manager.TimingMailManager;
import com.hifun.soul.gameserver.mail.service.MailService;
import com.hifun.soul.gameserver.mall.msg.internal.SendTimingMailMessage;
import com.hifun.soul.gameserver.manage.msg.GMSendMail;
import com.hifun.soul.gameserver.manage.msg.MGSendMail;
import com.hifun.soul.proto.services.Services.MailObject;

@Component
public class MGSendMailHandler implements IMessageHandlerWithType<MGSendMail> {
	
	@Autowired
	private TimingMailManager timingMailManager;
	
	@Override
	public short getMessageType() {
		return MessageType.MG_SEND_MAIL;
	}

	@Override
	public void execute(MGSendMail message) {
		boolean result = false;
		MailObject mailObj = message.getMailObject();		
		mailObj = mailObj.toBuilder().setSendState(MailSendState.NOT_SEND.getIndex()).setValid(true).build();
		if(mailObj.getIsTimingMail()){
			long now = GameServerAssist.getSystemTimeService().now();
			long delay = mailObj.getPlanSendTime()-now;
			if(delay>0){				
				SendTimingMailMessage timingMailTask = new SendTimingMailMessage(mailObj.toBuilder());
				timingMailManager.putTimingMail(timingMailTask);
				GameServerAssist.getGameWorld().scheduleOnece(timingMailTask, delay);
				result = true;
			}
		}else{
			Mail mail = new Mail(message.getMailObject());
			MailService.sendMail(mail);
			result = true;
		}
		GMSendMail gmMsg = new GMSendMail();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setResult(result);
		message.getSession().write(gmMsg);
	}

}
