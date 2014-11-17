package com.hifun.soul.gameserver.mail.manager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.MailDraftEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.mail.Mail;
import com.hifun.soul.gameserver.mail.MailSendState;
import com.hifun.soul.gameserver.mail.converter.MailDraftEntityConverter;
import com.hifun.soul.gameserver.mail.service.MailService;
import com.hifun.soul.gameserver.mall.msg.internal.SendTimingMailMessage;
import com.hifun.soul.proto.services.Services.MailObject;

@Scope("singleton")
@Component
public class TimingMailManager implements ICachableComponent {
	private MailDraftEntityConverter mailDraftEntityConverter = new MailDraftEntityConverter();
	private Map<Long, SendTimingMailMessage> timeMails = new HashMap<Long, SendTimingMailMessage>();
	private CacheEntry<Serializable,IEntity> mailDraftCache = new CacheEntry<Serializable, IEntity>();
	@Autowired
	private IDataService dataService;
	public TimingMailManager(){
		CacheManager cacheManager = ApplicationContext.getApplicationContext().getBean(CacheManager.class);
		cacheManager.registerOtherCachableComponent(this);
	}
	
	public void start(IDBService dbService){
		List<?> entityList = dbService.findByNamedQuery(DataQueryConstants.QUERY_VALID_TIMING_MAIL_LIST);
		Date now = new Date();
		if(entityList != null && !entityList.isEmpty()){
			for(int i=0; i<entityList.size(); i++){
				MailDraftEntity entity = (MailDraftEntity)entityList.get(i);
				if(entity.getPlanSendTime().before(now)){
					//overdue
					entity.setSendState(MailSendState.OVERDUE.getIndex());
					mailDraftCache.addUpdate(entity.getId(), entity);					
					continue;
				}
				SendTimingMailMessage mailMsg = new SendTimingMailMessage(mailDraftEntityConverter.reverseConvert(entity));
				timeMails.put(mailMsg.getMail().getMailId(), mailMsg);
				GameServerAssist.getGameWorld().scheduleOnece(mailMsg, entity.getPlanSendTime().getTime() - now.getTime());
			}
		}
	}
	
	public Collection<SendTimingMailMessage> getTimingMailMessages(){
		return timeMails.values();
	}

	public void putTimingMail(SendTimingMailMessage mailMessage) {
		if (mailMessage != null) {
			boolean updateFlag = false;
			if(timeMails.containsKey(mailMessage.getMail().getMailId())){
				updateFlag = true;
			}else{
				long mailId=GameServerAssist.getUuidService()
						.getNextUUID(UUIDType.MAILDRAFT);
				mailMessage.getMail().setMailId(mailId);
			}
			timeMails.put(mailMessage.getMail().getMailId(), mailMessage);
			mailMessage.setMailManager(this);
			MailDraftEntity entity = mailDraftEntityConverter.convert(mailMessage.getMail());
			if(updateFlag){
				dataService.update(entity);
			}else{
				dataService.insert(entity);
			}
		}
	}

	public void cancelTimingMail(long mailId) {
		if (timeMails.containsKey(mailId)) {			
			SendTimingMailMessage mailMessage = timeMails.remove(mailId);
			mailMessage.cancel();			
			mailMessage.getMail().setValid(false);			
			MailDraftEntity entity = mailDraftEntityConverter.convert(mailMessage.getMail());			
			mailDraftCache.addUpdate(entity.getId(), entity);
		}
	}

	public void sendMail(long mailId) {
		if(!timeMails.containsKey(mailId)){
			return;
		}
		MailObject.Builder mailBuilder = timeMails.remove(mailId).getMail();
		sendMail(mailBuilder);
	}
	
	private void sendMail(MailObject.Builder mailBuilder){
		Date now = new Date();
		mailBuilder.setSendState(MailSendState.HAS_SENT.getIndex());
		mailBuilder.setSendTime(now.getTime());
		mailBuilder.setValid(false);
		Mail mail = new Mail(mailBuilder.build());
		MailService.sendMail(mail);
		MailDraftEntity entity = mailDraftEntityConverter.convert(mailBuilder);		
		mailDraftCache.addUpdate(entity.getId(), entity);
	}
	
	public boolean updateTimingMail(MailObject mailObj){
		if(!timeMails.containsKey(mailObj.getMailId())){
			return false;
		}
		long now = GameServerAssist.getSystemTimeService().now();
		long delay = mailObj.getPlanSendTime()-now;
		if(delay<0){
			return false;
		}
		SendTimingMailMessage mailMsg = timeMails.get(mailObj.getMailId());
		MailObject.Builder mailBuilder = mailMsg.getMail();
		mailBuilder.setContent(mailObj.getContent());
		mailBuilder.setExpireDate(mailObj.getExpireDate());
		mailBuilder.setLastEditTime(mailObj.getLastEditTime());
		mailBuilder.setReceiveHumanIds(mailObj.getReceiveHumanIds());
		mailBuilder.setSendHumanId(mailObj.getSendHumanId());
		mailBuilder.setSendHumanName(mailObj.getSendHumanName());
		mailBuilder.setSendMemo(mailObj.getSendMemo());
		mailBuilder.setTheme(mailObj.getTheme());
		mailBuilder.clearItemId();
		mailBuilder.addAllItemId(mailObj.getItemIdList());		
		if(mailObj.getPlanSendTime() == mailMsg.getMail().getPlanSendTime()){			
			MailDraftEntity entity = mailDraftEntityConverter.convert(mailBuilder);		
			mailDraftCache.addUpdate(entity.getId(), entity);
			return true;
		}		
		mailBuilder.setPlanSendTime(mailObj.getPlanSendTime());
		MailDraftEntity entity = mailDraftEntityConverter.convert(mailBuilder);		
		mailDraftCache.addUpdate(entity.getId(), entity);
		if(delay == 0){
			cancelTimingMail(mailObj.getMailId());
			mailMsg.cancel();
			sendMail(mailBuilder);
			return true;
		}else if(delay>0){
			cancelTimingMail(mailObj.getMailId());
			SendTimingMailMessage newTask = new SendTimingMailMessage(mailBuilder);
			putTimingMail(newTask);
			GameServerAssist.getGameWorld().scheduleOnece(newTask, delay);
			return true;
		}
		return false;
	}

	@Override
	public List<IEntity> getUpdateEntities() {		
		return mailDraftCache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return mailDraftCache.getAllDeleteData();
	}
}
