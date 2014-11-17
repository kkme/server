package com.hifun.soul.gameserver.mail.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.ReceivedMailEntity;
import com.hifun.soul.gamedb.entity.SentMailEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.mail.Mail;
import com.hifun.soul.gameserver.mail.model.HumanMailInfo;
import com.hifun.soul.gameserver.mail.msg.GCDeleteMail;
import com.hifun.soul.gameserver.mail.msg.GCReceiveMail;

/**
 * 绑在角色身上的邮件管理器
 * 
 * @author magicstone
 * 
 */

public class HumanMailManager implements IHumanPersistenceManager, ILoginManager{
	private static Logger logger = Loggers.MAIL_LOGGER;
	private List<HumanMailInfo> mailList = new ArrayList<HumanMailInfo>();
	private Human human;

	public HumanMailManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerLoginManager(this);
	}

	/**
	 * 获取角色的邮件列表
	 * 
	 * @return
	 */
	public List<HumanMailInfo> getMailList() {
		return this.mailList;
	}

	/**
	 * 接收邮件
	 * 
	 * @param mail
	 */
	public void ReceiveMail(Mail mail) {
		final Mail sendMail = mail;
		final ReceivedMailEntity entity = new ReceivedMailEntity();
		entity.setId(GameServerAssist.getUuidService().getNextUUID(UUIDType.RECEIVEDMAIL));
		entity.setMailId(sendMail.getMailId());
		entity.setPickUp(false);
		entity.setRead(false);
		entity.setReceiveHumanId(human.getHumanGuid());
		HumanMailInfo mailInfo = new HumanMailInfo(sendMail.toSentMailEntity(), entity);
		mailList.add(0,mailInfo);
		if (GameServerAssist.getGameConstants().getMaxRecievedMailNum() < mailList.size()) {
			mailList.remove(mailList.size() - 1);
		}
		GCReceiveMail gcMsg = new GCReceiveMail();
		human.sendMessage(gcMsg);
		GameServerAssist.getDataService().insert(entity, new IDBCallback<Long>() {
			@Override
			public void onSucceed(Long result) {
				
			}

			@Override
			public void onFailed(String errorMsg) {
				logger.error("插入ReceivedMailEntity失败。" + entity.toString());
			}
		});
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		final HumanEntity hEntity = humanEntity;
		GameServerAssist.getDataService().query(DataQueryConstants.QUERY_MAIL_LIST_BY_HUMAN_ID, new String[] { "receiveHumanId" },
				new Object[] { human.getHumanGuid() }, new IDBCallback<List<?>>() {
					@Override
					public void onSucceed(List<?> result) {
						mailList.clear();
						if (result == null) {
							return;
						}
						if (result.isEmpty()) {
							return;
						}
						for (int i = 0; i < result.size(); i++) {
							Object[] objs = (Object[]) result.get(i);
							SentMailEntity sent = (SentMailEntity) objs[0];
							ReceivedMailEntity received = (ReceivedMailEntity) objs[1];
							HumanMailInfo mailInfo = new HumanMailInfo(sent, received);
							mailList.add(mailInfo);
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error("查询角色的邮件列表失败。humanGuid:" + hEntity.getId() + " errorMsg:" + errorMsg);
					}

				});
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {

	}

	/**
	 * 读取邮件
	 * 
	 * @param mailId
	 */
	public void readReceivedMail(long mailId) {
		for (HumanMailInfo mail : this.mailList) {
			if (mail.getReceiveEntity().getMailId() == mailId) {
				ReceivedMailEntity entity = mail.getReceiveEntity();
				entity.setRead(true);
				GameServerAssist.getDataService().update(entity);
			}
		}
	}

	/**
	 * 拾取邮件中的物品
	 * 
	 * @param mailId
	 */
	public boolean pickUpItemFromReceivedMail(long mailId) {
		for (HumanMailInfo mail : this.mailList) {
			if (mail.getReceiveEntity().getMailId() == mailId) {
				if(mail.getSentEntity().getItemIds()==null && mail.getSentEntity().getItemIds().length()<=0){
					return false;
				}
				if (mail.getReceiveEntity().isPickUp()) {
					logger.info("已经领取过该邮件附带的物品。[humanId:" + human.getHumanGuid() + "] [mailId:" + mailId+"]");
					return false;
				}
				if(mail.getSentEntity().getExpireDate().before(new Date())){
					logger.info("邮件已过期。[humanId:" + human.getHumanGuid() + "] [mailId:" + mailId+"]");
					return false;
				}				
				String[] itemIdArray = mail.getSentEntity().getItemIds().split(",");
				String[] itemNumArray = mail.getSentEntity().getItemNums().split(",");
				// 构建物品
				List<Item> attachItems = new ArrayList<Item>();
				for(int i=0;i<itemIdArray.length;i++){
					int itemId = Integer.parseInt(itemIdArray[i]);
					Item item = ItemFactory.creatNewItem(human, itemId);
					if (item == null) {
						logger.error("领取邮件附带的物品出错,配置表中没有找到itemId='"+itemIdArray[i]+"'的物品。[humanId:" + human.getHumanGuid() + "] [mailId:" + mailId);
						continue;
					}
					int num = Integer.parseInt(itemNumArray[i]);
					if(item.isOverlapable()){
						if(num<=item.getMaxOverlap()){
							item.setOverlapNum(num);
							attachItems.add(item);		
						}else{
							int overlapedItemCount = num/(item.getMaxOverlap()+1)+1;						
							for(int n=0;n<overlapedItemCount;n++){
								Item overlapItem = ItemFactory.creatNewItem(null, itemId);
								if(n<overlapedItemCount-1){								
									overlapItem.setOverlapNum(item.getMaxOverlap());								
								}else{
									overlapItem.setOverlapNum(num%item.getMaxOverlap());
								}
								attachItems.add(overlapItem);
							}
						}
					}else{
						for(int j=0;j<num;j++){
							attachItems.add(ItemFactory.creatNewItem(human, itemId));
						}
					}					
				}				
				if(human.getBagManager().getFreeSize(BagType.MAIN_BAG)<attachItems.size()){
					human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
					return false;
				}
				ReceivedMailEntity entity = mail.getReceiveEntity();
				entity.setPickUp(true);
				GameServerAssist.getDataService().update(entity);
				for(Item attachItem:attachItems){					
					human.getBagManager().putItem(BagType.MAIN_BAG, attachItem,ItemLogReason.MAIL_REWARD,"");
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据邮件Id删除所接收到的邮件
	 * 
	 * @param mailId
	 * @return
	 */
	public void deleteReveivedMail(long[] mailIds) {
		for (int m = mailIds.length - 1; m >= 0; m--) {
			final long id = mailIds[m];
			for (int i = mailList.size() - 1; i >= 0; i--) {
				final int j = i;
				HumanMailInfo mail = this.mailList.get(i);
				if (mail.getReceiveEntity().getMailId() == id) {
					mailList.remove(j);
					logger.info("删除接收邮件记录[humanId:" + human.getHumanGuid() + "],[mailId:" + id + "]。");
					GCDeleteMail msg = new GCDeleteMail();
					msg.setMailId(new long[] { id });
					human.sendMessage(msg);
					GameServerAssist.getDataService().delete(mail.getReceiveEntity(), new IDBCallback<Void>() {
						@Override
						public void onSucceed(Void result) {							
							logger.info("删除接收邮件记录[humanId:" + human.getHumanGuid() + "],[mailId:" + id + "]。");							
						}

						@Override
						public void onFailed(String errorMsg) {
							logger.error("删除接收邮件记录失败[humanId:" + human.getHumanGuid() + "],[mailId:" + id + "]。");
						}

					});
				}
			}
		}
	}

	@Override
	public void onLogin() {
		for(HumanMailInfo mail: mailList){
			if(!mail.getReceiveEntity().isRead()){
				GCReceiveMail gcMsg = new GCReceiveMail();
				human.sendMessage(gcMsg);
				break;
			}
		}
		
	}
}
