package com.hifun.soul.gameserver.mall.msg.internal;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.mail.manager.TimingMailManager;
import com.hifun.soul.proto.services.Services.MailObject;

public class SendTimingMailMessage extends SceneScheduleMessage {
	private MailObject.Builder mail;

	private TimingMailManager mailManager;
	public SendTimingMailMessage(MailObject.Builder mailBuilder) {
		this.mail = mailBuilder;
	}
	
	

	public MailObject.Builder getMail() {
		return mail;
	}



	public void setMail(MailObject.Builder mail) {
		this.mail = mail;
	}



	public TimingMailManager getMailManager() {
		return mailManager;
	}



	public void setMailManager(TimingMailManager mailManager) {
		this.mailManager = mailManager;
	}



	@Override
	public void execute() {
		if(isCanceled()){
			return;
		}
		mailManager.sendMail(mail.getMailId());
		
	}

}
