package com.hifun.soul.gameserver.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hifun.soul.gamedb.entity.SentMailEntity;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.proto.services.Services.MailObject;

/**
 * 邮件业务类
 * 
 * @author magicstone
 * 
 */
public class Mail {

	/** 邮件id */
	private long mailId;
	/** 邮件类型 */
	private MailType mailType;
	/** 收件人id列表 */
	private List<Long> receiveHumanIds;
	/** 邮件主题 */
	private String theme;
	/** 邮件内容 */
	private String content;
	/** 发件人id */
	private Long sendHumanId;
	/** 发件人姓名 */
	private String sendHumanName;
	/** 邮件过期时间 */
	private Date expireDate;
	/** 邮件附带物品 */
	private List<Item> items;
	/** 发送时间 */
	private Date sendTime;
	/** 备注 */
	private String sendMemo;
	/** 物品数量 */
	private List<Integer> itemNums;

	public Mail() {
	}

	public Mail(MailObject mailObject) {
		this.mailId = mailObject.getMailId();
		this.mailType = MailType.indexOf(mailObject.getMailType());
		receiveHumanIds = new ArrayList<Long>();
		String receiveHumanIdStr = mailObject.getReceiveHumanIds();
		if (receiveHumanIdStr != null && receiveHumanIdStr.length() > 0) {
			String[] receiveIds = receiveHumanIdStr.split(",");
			for (String id : receiveIds) {
				if (id != null && id.length() > 0) {
					try {
						receiveHumanIds.add(Long.parseLong(id));
					} catch (NumberFormatException ex) {
						continue;
					}
				}
			}
		}
		this.theme = mailObject.getTheme();
		this.content = mailObject.getContent();
		this.sendHumanId = mailObject.getSendHumanId();
		this.sendHumanName = mailObject.getSendHumanName();
		this.expireDate = new Date(mailObject.getExpireDate());
		int itemIndex=-1;
		for(int itemId : mailObject.getItemIdList()){
			itemIndex++;
			if(itemId<=0){
				continue;
			}
			Item item = ItemFactory.creatNewItem(null, itemId);			
			if (item != null) {
				if (items == null) {
					items = new ArrayList<Item>();
					itemNums = new ArrayList<Integer>();
				}
				this.items.add(item);
				this.itemNums.add(mailObject.getItemNum(itemIndex));
			}
			
		}		
		this.sendMemo = mailObject.getSendMemo();
		this.sendTime = new Date(mailObject.getSendTime());
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public MailType getMailType() {
		return mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}

	public List<Long> getReceiveHumanIds() {
		return receiveHumanIds;
	}

	public void setReceiveHumanIds(List<Long> receiveHumanId) {
		this.receiveHumanIds = receiveHumanId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		if(theme.length()>60){
			this.theme = theme.substring(0, 60);
		}
		else{
			this.theme=theme;
		}
	}

	public Long getSendHumanId() {
		return sendHumanId;
	}

	public void setSendHumanId(Long sendHumanId) {
		this.sendHumanId = sendHumanId;
	}

	public String getSendHumanName() {
		return sendHumanName;
	}

	public void setSendHumanName(String sendHumanName) {
		this.sendHumanName = sendHumanName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if(content.length()>1000){
			this.content = content.substring(0, 1000);
		}
		else{
			this.content=content;
		}
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date deadline) {
		this.expireDate = deadline;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendMemo() {
		return sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}	

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public SentMailEntity toSentMailEntity() {
		SentMailEntity entity = new SentMailEntity();
		entity.setId(this.mailId);
		entity.setMailType(this.getMailType().getIndex());
		StringBuilder sb = new StringBuilder();
		for (Long humanId : this.getReceiveHumanIds()) {
			sb.append(humanId);
			sb.append(",");
		}
		entity.setReceiveHumanId(sb.toString());
		entity.setSendMemo(this.getSendMemo());
		List<Item> itemList = this.getItems();
		if (itemList != null && itemList.size()>0) {
			entity.setAttachment(true);
			StringBuilder itemIds = new StringBuilder();
			for(Item item : itemList){
				itemIds.append(item.getItemId());
				itemIds.append(",");
			}
			if(itemIds.length()>0){
				itemIds.delete(itemIds.length()-1, itemIds.length());
			}
			entity.setItemIds(itemIds.toString());
			StringBuilder itemNums = new StringBuilder();
			for(Integer itemNum : this.itemNums){
				itemNums.append(itemNum);
				itemNums.append(",");
			}
			if(itemNums.length()>0){
				itemNums.delete(itemNums.length()-1, itemNums.length());
			}
			entity.setItemNums(itemNums.toString());
		} else {
			entity.setAttachment(false);
		}
		entity.setContent(this.getContent());
		entity.setSendHumanId(this.getSendHumanId());
		entity.setSendHumanName(this.getSendHumanName());
		entity.setSendTime(new Date());
		entity.setExpireDate(this.getExpireDate());
		entity.setTheme(this.getTheme());
		return entity;
	}
}
