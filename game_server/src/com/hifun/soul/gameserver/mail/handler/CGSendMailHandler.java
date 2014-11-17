package com.hifun.soul.gameserver.mail.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mail.Mail;
import com.hifun.soul.gameserver.mail.MailType;
import com.hifun.soul.gameserver.mail.msg.CGSendMail;
import com.hifun.soul.gameserver.mail.msg.GCSendMail;
import com.hifun.soul.gameserver.mail.service.MailService;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 发送邮件处理
 * 
 * @author magicstone
 *
 */
@Component
public class CGSendMailHandler implements IMessageHandlerWithType<CGSendMail> {
	private static Logger logger = Loggers.MAIL_LOGGER;
	@Autowired
	private IDataService dataService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SEND_MAIL;
	}

	@Override
	public void execute(CGSendMail message) {		
		final CGSendMail msg = message;
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		final Human human = player.getHuman();
		if(human == null){
			return;
		}
		String name = message.getReceiveHumanName();
		if(human.getName().equals(name)){
			human.sendWarningMessage(LangConstants.CONNOT_SEND_TO_SELF);
			GCSendMail gcMsg = new GCSendMail(false);
			human.sendMessage(gcMsg);
			return;
		}
		//检查发送次数
		final int sendTimes = human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
		.getPropertyValue(HumanIntProperty.SEND_MAIL_TIMES);
		if(sendTimes>=GameServerAssist.getGameConstants().getMaxSendMailNum()){
			human.sendWarningMessage(LangConstants.SEND_MAIL_NUM_LIMITED);
			GCSendMail gcMsg = new GCSendMail(false);
			human.sendMessage(gcMsg);
			return;
		}
		dataService.query(DataQueryConstants.QUERY_HUMAN_BY_NAME, new String[] { "name" }, new Object[] { name },
				new IDBCallback<List<?>>() {					
					@Override
					public void onSucceed(List<?> result) {
						// 判断用户名是否存在
						if (result.isEmpty()) {
							human.sendWarningMessage(LangConstants.RECIEVER_NOT_EXIST);
							GCSendMail gcMsg = new GCSendMail(false);
							human.sendMessage(gcMsg);
							return;
						} else {
							Mail mail = new Mail();							
							mail.setContent(msg.getContent());
							mail.setTheme(msg.getTheme());
							mail.setMailType(MailType.USER_MAIL);
							mail.setSendTime(new Date());
							mail.setExpireDate(new Date());
							List<Long> receiveIds = new ArrayList<Long>();
							receiveIds.add((Long) result.get(0));
							mail.setReceiveHumanIds(receiveIds);
							mail.setSendHumanId(human.getHumanGuid());
							mail.setSendHumanName(human.getName());
							MailService.sendMail(mail);
							human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
							.setPropertyValue(HumanIntProperty.SEND_MAIL_TIMES,sendTimes+1);
							human.sendSuccessMessage(LangConstants.SEND_MAIL_SUCCESS);
							GCSendMail gcMsg = new GCSendMail(true);
							human.sendMessage(gcMsg);
						}
					}

					@Override
					public void onFailed(String errorMsg) {						
						logger.error("邮件发送失败。[humanId:"+ human.getHumanGuid() +"] [errorMsg:" + errorMsg);
						human.sendWarningMessage(LangConstants.SEND_MAIL_FAILED);
						GCSendMail gcMsg = new GCSendMail(false);
						human.sendMessage(gcMsg);
					}
				});
	}

}
