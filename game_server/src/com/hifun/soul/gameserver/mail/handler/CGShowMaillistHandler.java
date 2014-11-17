package com.hifun.soul.gameserver.mail.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mail.manager.HumanMailManager;
import com.hifun.soul.gameserver.mail.model.HumanMailInfo;
import com.hifun.soul.gameserver.mail.model.MailListItemInfo;
import com.hifun.soul.gameserver.mail.msg.CGShowMaillist;
import com.hifun.soul.gameserver.mail.msg.GCShowMaillist;

/**
 * 显示邮件列表处理类
 * 
 * @author magicstone
 *
 */
@Component
public class CGShowMaillistHandler implements IMessageHandlerWithType<CGShowMaillist> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_SHOW_MAILLIST;
	}

	@Override
	public void execute(CGShowMaillist message) {
		String sendTime = "";
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Human human = message.getPlayer().getHuman();
		HumanMailManager mailManager = human.getHumanMailManager();
		MailListItemInfo[] mails = new MailListItemInfo[mailManager.getMailList().size()];
		for( int i=0;i<mailManager.getMailList().size();i++){
			HumanMailInfo mail = mailManager.getMailList().get(i);
			mails[i] =new MailListItemInfo();
			mails[i].setMailId(mail.getReceiveEntity().getMailId());
			mails[i].setMailType(mail.getSentEntity().getMailType());
			mails[i].setSendHumanName(mail.getSentEntity().getSendHumanName());
			mails[i].setTheme(mail.getSentEntity().getTheme());
			mails[i].setIsRead(mail.getReceiveEntity().isRead());
			mails[i].setIsPickUp(mail.getReceiveEntity().isPickUp());
			mails[i].setIsAttachment(mail.getSentEntity().isAttachment());
			mails[i].setChecked(false);
			sendTime =	dateformat.format(mail.getSentEntity().getSendTime());
			mails[i].setSendTime(sendTime);
			Date expireDate = mail.getSentEntity().getExpireDate();
			Date now = new Date();
			if(mails[i].getIsAttachment() && expireDate.after(now)){
				int expireDays = (int)((expireDate.getTime()-now.getTime())/(24*60*60*1000));
				mails[i].setExpireDays(expireDays);
			}else{
				mails[i].setExpireDays(0);
			}			
		}
		GCShowMaillist gcMsg = new GCShowMaillist();
		gcMsg.setMailList(mails);
		human.sendMessage(gcMsg);		
	}

}
