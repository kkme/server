package com.hifun.soul.gameserver.mail.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.ReceivedMailEntity;
import com.hifun.soul.gamedb.entity.SentMailEntity;
import com.hifun.soul.gamedb.service.DataService;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.mail.Mail;
import com.hifun.soul.gameserver.mail.MailType;
import com.hifun.soul.gameserver.mail.SpecialMailReceiver;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

public class MailService {
	private static Logger logger = Loggers.MAIL_LOGGER;

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 */
	public static void sendMail(Mail mail) {
		final Mail sendMail = mail;
		if (sendMail.getReceiveHumanIds().size() == 0) {
			return;
		}
		final long firstReceiveId = sendMail.getReceiveHumanIds().get(0).intValue();
		sendMail.setSendTime(new Date());
		final SentMailEntity entity = sendMail.toSentMailEntity();
		long mailId=GameServerAssist.getUuidService()
				.getNextUUID(UUIDType.SENTMAIL);
		entity.setId(mailId);
		sendMail.setMailId(mailId);
		SceneHumanManager sceneHumanManager = GameServerAssist.getGameWorld().getSceneHumanManager();
		if (SpecialMailReceiver.ALL_CHARACTER == firstReceiveId) {
			// 所有玩家
			GameServerAssist.getDataService().query(DataQueryConstants.QUERY_ALL_HUMAN_ID, new IDBCallback<List<?>>() {
				@Override
				public void onSucceed(List<?> result) {
					if (result == null || result.isEmpty()) {
						return;
					}
					SceneHumanManager sceneHumanManager = GameServerAssist.getGameWorld().getSceneHumanManager();
					for (Object obj : result) {
						Long receiverId = (Long) obj;
						Human receiver = sceneHumanManager.getHumanByGuid(receiverId);
						if (receiver != null) {
							receiver.getHumanMailManager().ReceiveMail(sendMail);
						} else {
							insertReceivedMailEntity(sendMail.getMailId(), receiverId);
						}
					}
				}

				@Override
				public void onFailed(String errorMsg) {
					logger.error("query all human id failed when send mail to all characters");
				}

			});

		} else if (SpecialMailReceiver.ALL_ONLINE_CHARACTER == firstReceiveId) {
			// 所有在线玩家
			for (Human human : sceneHumanManager.getAllHumans()) {
				human.getHumanMailManager().ReceiveMail(sendMail);
			}
		} else {
			for (Long receiverId : sendMail.getReceiveHumanIds()) {
				Human receiver = sceneHumanManager.getHumanByGuid(receiverId);
				if (receiver != null) {
					receiver.getHumanMailManager().ReceiveMail(sendMail);					
				} else {
					insertReceivedMailEntity(sendMail.getMailId(), receiverId);
				}
			}
		}
		GameServerAssist.getDataService().insert(entity, new IDBCallback<Long>() {
			@Override
			public void onSucceed(Long result) {
				
			}

			@Override
			public void onFailed(String errorMsg) {
				logger.error("插入SentMailEntity失败。" + entity.toString());
			}
		});
	}

	/**
	 * 发送系统邮件(给玩家的提示，或赠送物品)
	 * 
	 */
	public static void sendSystemMail(long humanId, String theme, String content, int itemId, Date expireDate) {
		Mail mail = new Mail();
		mail.setMailId(GameServerAssist.getUuidService().getNextUUID(UUIDType.SENTMAIL));
		mail.setContent(content);
		mail.setTheme(theme);
		mail.setMailType(MailType.SYSTEM_MAIL);
		mail.setSendTime(new Date());
		mail.setExpireDate(new Date());
		List<Long> receiveIds = new ArrayList<Long>();
		receiveIds.add(humanId);
		mail.setReceiveHumanIds(receiveIds);
		mail.setSendHumanId(0L);
		mail.setSendHumanName(GameServerAssist.getSysLangService().read(LangConstants.SYSTEM_MAIL));
		Item item = ItemFactory.creatNewItem(null, itemId);
		if (item != null) {
			mail.setExpireDate(expireDate);
			List<Item> itemList = new ArrayList<Item>();
			itemList.add(item);
			mail.setItems(itemList);
		}
		else{
			Calendar calendar = Calendar.getInstance();
			calendar.set(2015, 12, 20,0,0,0);
			mail.setExpireDate(calendar.getTime());
		}
		mail.setSendTime(new Date());
		sendMail(mail);
	}

	/**
	 * 将邮件接收实体保存到数据库
	 * 
	 * @param mailId
	 * @param receiverId
	 */
	private static void insertReceivedMailEntity(long mailId, long receiverId) {
		final ReceivedMailEntity receivedEntity = new ReceivedMailEntity();
		receivedEntity.setId(GameServerAssist.getUuidService().getNextUUID(UUIDType.RECEIVEDMAIL));
		receivedEntity.setMailId(mailId);
		receivedEntity.setPickUp(false);
		receivedEntity.setRead(false);
		receivedEntity.setReceiveHumanId(receiverId);
		IDataService dataService = ApplicationContext.getApplicationContext().getBean(DataService.class);
		dataService.insert(receivedEntity, new IDBCallback<Long>() {
			@Override
			public void onSucceed(Long result) {
			}

			@Override
			public void onFailed(String errorMsg) {
				logger.error("插入ReceivedMailEntity失败。" + receivedEntity.toString());
			}
		});
	}
}
