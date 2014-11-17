package com.hifun.soul.gameserver.mail.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.mail.manager.HumanMailManager;
import com.hifun.soul.gameserver.mail.model.HumanMailInfo;
import com.hifun.soul.gameserver.mail.msg.CGShowMail;
import com.hifun.soul.gameserver.mail.msg.GCShowMail;
import com.hifun.soul.gameserver.player.Player;

/**
 * 请求显示消息详细信息处理类
 * 
 * @author magicstone
 *
 */
@Component
public class CGShowMailHandler implements IMessageHandlerWithType<CGShowMail> {
	
	private static Logger logger = Loggers.MAIL_LOGGER;
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_MAIL;
	}

	@Override
	public void execute(CGShowMail message) {
		Player player = message.getPlayer();
		HumanMailManager mailManager = player.getHuman().getHumanMailManager();
		long mailId = message.getMailId();
		for(HumanMailInfo mail : mailManager.getMailList()){
			if(mail.getReceiveEntity().getMailId()==mailId){
				GCShowMail gcMsg  = new GCShowMail();
				gcMsg.setIsPickUp(mail.getReceiveEntity().isPickUp());
				gcMsg.setMailId(mail.getReceiveEntity().getMailId());
				gcMsg.setMailType(mail.getSentEntity().getMailType());
				gcMsg.setSendHumanId(mail.getSentEntity().getSendHumanId());
				gcMsg.setSendHumanName(mail.getSentEntity().getSendHumanName());
				gcMsg.setTheme(mail.getSentEntity().getTheme());
				gcMsg.setContent(mail.getSentEntity().getContent());
				CommonItem[] items = new CommonItem[0];
				if(mail.getSentEntity().isAttachment()){
					String itemIds = mail.getSentEntity().getItemIds();
					if(itemIds!=null && itemIds.length()>0){
						String[] itemIdArray = itemIds.split(",");
						String[] itemNumArray = mail.getSentEntity().getItemNums().split(",");
						List<Item> attaches = new ArrayList<Item>();
						for(int i=0;i<itemIdArray.length;i++){
							Item item = ItemFactory.creatNewItem(player.getHuman(), Integer.parseInt(itemIdArray[i]));
							if(item!=null){
								attaches.add(item);
							}else{
								logger.error("随邮件发送的物品创建失败。[mailId:"+mailId+"] [itemId:"+itemIdArray[i]+"]");
							}
						}
						items = CommonItemBuilder.converToCommonItemArray(attaches);
						for(int itemIndex=0;itemIndex<items.length;itemIndex++){
							items[itemIndex].setOverlapNum(Integer.parseInt(itemNumArray[itemIndex]));
						}
					}
				}				
				gcMsg.setItems(items);
				Date expireDate = mail.getSentEntity().getExpireDate();
				Date now = new Date();
				if(items.length>0 && expireDate.after(now)){
					int expireDays = (int)(expireDate.getTime()-now.getTime())/(24*60*60*1000);
					gcMsg.setExpireDays(expireDays);
				}
				else{
					gcMsg.setExpireDays(0);
				}
				if(!mail.getReceiveEntity().isRead()){
					mailManager.readReceivedMail(mailId);
				}
				player.sendMessage(gcMsg);
				return;
			}
		}
	}

}
