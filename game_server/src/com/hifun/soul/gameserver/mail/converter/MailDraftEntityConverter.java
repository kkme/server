package com.hifun.soul.gameserver.mail.converter;

import java.util.Date;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.MailDraftEntity;
import com.hifun.soul.proto.services.Services.MailObject;

public class MailDraftEntityConverter implements IConverter<MailObject.Builder, MailDraftEntity> {

	@Override
	public MailDraftEntity convert(MailObject.Builder mail) {
		MailDraftEntity entity = new MailDraftEntity();
		entity.setId(mail.getMailId());
		entity.setContent(mail.getContent());
		if(mail.getCreateTime() >0 ){
			entity.setCreateTime(new Date(mail.getCreateTime()));
		}
		if(mail.getExpireDate()>0){
			entity.setExpireDate(new Date(mail.getExpireDate()));
		}
		if(mail.getLastEditTime()>0){
			entity.setLastEditTime(new Date(mail.getLastEditTime()));
		}
		if(mail.getPlanSendTime() >0){
			entity.setPlanSendTime(new Date(mail.getPlanSendTime()));
		}
		entity.setReceiveHumanId(mail.getReceiveHumanIds());
		entity.setSendHumanId(mail.getSendHumanId());
		entity.setSendHumanName(mail.getSendHumanName());
		entity.setSendMemo(mail.getSendMemo());
		entity.setTheme(mail.getTheme());
		entity.setTimingMail(mail.getIsTimingMail());
		entity.setValid(mail.getValid());
		if(mail.getItemIdCount()>0){
			String itemIds = "";
			for (int itemId : mail.getItemIdList()) {
				itemIds += itemId+",";
			}
			if(itemIds.length()>0){
				itemIds = itemIds.substring(0, itemIds.length()-1);
			}
			entity.setItemIds(itemIds);
			entity.setAttachment(true);
		}else{				
			entity.setAttachment(false);
		}
		if(mail.getSendTime() > 0){
			entity.setSendTime(new Date(mail.getSendTime()));
		}
		entity.setSendState(mail.getSendState());
		return entity;
	}

	@Override
	public MailObject.Builder reverseConvert(MailDraftEntity entity) {
		MailObject.Builder builder = MailObject.newBuilder();
		builder.setMailId((Long) entity.getId());
		builder.setMailType(1);//1为系统邮件，2为用户邮件
		builder.setReceiveHumanIds(entity.getReceiveHumanId());
		builder.setTheme(entity.getTheme());
		builder.setContent(entity.getContent());
		builder.setSendHumanId(entity.getSendHumanId());
		builder.setSendHumanName(entity.getSendHumanName());
		if(entity.getExpireDate() != null){
			builder.setExpireDate(entity.getExpireDate().getTime());
		}
		if(entity.getItemIds()!=null && entity.getItemIds().length()>0){
			String[] itemIds = entity.getItemIds().split(",");
			for (String itemIdStr : itemIds) {
				builder.addItemId(Integer.parseInt(itemIdStr));
			}	
		}
		builder.setSendMemo(entity.getSendMemo());
		if(entity.getSendTime() != null){
			builder.setSendTime(entity.getSendTime().getTime());
		}
		builder.setIsTimingMail(true);
		if(entity.getPlanSendTime() != null){
			builder.setPlanSendTime(entity.getPlanSendTime().getTime());
		}
		builder.setValid(entity.isValid());
		if(entity.getCreateTime() != null){
			builder.setCreateTime(entity.getCreateTime().getTime());
		}
		if(entity.getLastEditTime() != null){
			builder.setLastEditTime(entity.getLastEditTime().getTime());
		}
		builder.setSendState(entity.getSendState());
		return builder;
	}

}
