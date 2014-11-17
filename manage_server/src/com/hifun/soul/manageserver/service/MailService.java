package com.hifun.soul.manageserver.service;

import java.util.Date;
import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.rpc.server.RpcServiceRouter;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.SentMailEntity;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.manageserver.msg.MGCancelMail;
import com.hifun.soul.manageserver.msg.MGGetTimingMailList;
import com.hifun.soul.manageserver.msg.MGSendMail;
import com.hifun.soul.manageserver.msg.MGUpdateMail;
import com.hifun.soul.proto.services.Services.CancelTimingMailRequest;
import com.hifun.soul.proto.services.Services.CancelTimingMailResponse;
import com.hifun.soul.proto.services.Services.MailObject;
import com.hifun.soul.proto.services.Services.MailRpcService;
import com.hifun.soul.proto.services.Services.QuerySendMailListRequest;
import com.hifun.soul.proto.services.Services.QuerySendMailListResponse;
import com.hifun.soul.proto.services.Services.QueryTimingMailListRequest;
import com.hifun.soul.proto.services.Services.QueryTimingMailListResponse;
import com.hifun.soul.proto.services.Services.SendMailRequest;
import com.hifun.soul.proto.services.Services.SendMailResponse;
import com.hifun.soul.proto.services.Services.UpdateTimingMailRequest;
import com.hifun.soul.proto.services.Services.UpdateTimingMailResponse;

public class MailService extends MailRpcService {
	private DBServiceManager dbManager;
	private ServerSessionManager sessionManager;
	private RpcCallbackManager callbackManager;
//	private MailDraftEntityConverter mailDraftEntityConverter = new MailDraftEntityConverter();
	

	public MailService(DBServiceManager dbManager, ServerSessionManager sessionManager, RpcCallbackManager callbackManager) {		
		this.dbManager = dbManager;
		this.sessionManager = sessionManager;
		this.callbackManager = callbackManager;
	}

	@Override
	public void querySendMailList(RpcController controller, QuerySendMailListRequest request,
			RpcCallback<QuerySendMailListResponse> done) {
		String themeKey = "%%";
		String contentKey="%%";
		String sendHumanNameKey="%%";
		String receiveHumanId="%%";
		String sendMemoKey="%%";
		String specificId = "";
		if(request.getTheme()!=null && request.getTheme().length()>0){
			themeKey="%"+request.getTheme()+"%";
		}
		if(request.getContent()!=null && request.getContent().length()>0){
			contentKey="%"+request.getContent()+"%";
		}
		if(request.getSendHumanName()!=null && request.getSendHumanName().length()>0){
			sendHumanNameKey="%"+request.getSendHumanName()+"%";
		}
		if(request.getReceiveHumanName() != null && request.getReceiveHumanName().length()>0){
			RpcServiceRouter serviceRouter = ApplicationContext.getApplicationContext().getBean(RpcServiceRouter.class);
			CharacterService charService = (CharacterService)serviceRouter.getService(CharacterService.getDescriptor().getName());
			long charId = charService.queryHumanIdByName(request.getReceiveHumanName());
			if(charId>0){
				specificId = String.valueOf(charId);
			}			
		}
		if(specificId.length()>0){
			receiveHumanId="%"+specificId+"%";
		}else if(request.getReceiveHumanId()!=null && request.getReceiveHumanId().length()>0){
			receiveHumanId="%"+request.getReceiveHumanId()+"%";
		}
		if(request.getSendMemo()!=null && request.getSendMemo().length()>0){
			sendMemoKey="%"+request.getSendMemo()+"%";
		}
		List<?> entityList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_SENT_MAIL_LIST_BY_CONDITION, 
				new String[] {"theme","content","sendHumanName","sendMemo","receiveHumanId"}, 
				new Object[] {themeKey,contentKey,sendHumanNameKey,sendMemoKey,receiveHumanId},
				request.getMaxResult(), 
				request.getStart());
		QuerySendMailListResponse.Builder responseBuilder= QuerySendMailListResponse.newBuilder();
		if(entityList==null || entityList.isEmpty()){
			done.run(responseBuilder.setTotalCount(0).build());
			return;
		}
		for(Object obj : entityList){
			responseBuilder.addMails(this.convertEntityToMailObject((SentMailEntity)obj));
		}
		List<?> resultList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_SENT_MAIL_COUNT_BY_CONDITION, 
				new String[] {"theme","content","sendHumanName","sendMemo","receiveHumanId"}, 
				new Object[] {themeKey,contentKey,sendHumanNameKey,sendMemoKey,receiveHumanId});
		responseBuilder.setTotalCount((Long)resultList.get(0));
		done.run(responseBuilder.build());
	}

	@Override
	public void sendMail(RpcController controller, SendMailRequest request, RpcCallback<SendMailResponse> done) {		
		MailObject.Builder mail = request.getMail().toBuilder();
		Date nowDate = new Date();
		long now = nowDate.getTime();
		mail.setCreateTime(now);
		mail.setLastEditTime(now);		
		MGSendMail mgMsg = new MGSendMail();
		mgMsg.setMailObject(mail.build());
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	/**
	 * 将实体转换为传输对象
	 * 
	 * @param entity
	 * @return
	 */
	private MailObject convertEntityToMailObject(SentMailEntity entity) {
		MailObject.Builder builder = MailObject.newBuilder();
		builder.setMailId((Long) entity.getId());
		builder.setMailType(entity.getMailType());
		builder.setReceiveHumanIds(entity.getReceiveHumanId());
		builder.setTheme(entity.getTheme());
		builder.setContent(entity.getContent());
		builder.setSendHumanId(entity.getSendHumanId());
		builder.setSendHumanName(entity.getSendHumanName());
		builder.setExpireDate(entity.getExpireDate().getTime());
		if(entity.getItemIds()!=null && entity.getItemIds().length()>0){
			String[] itemIds = entity.getItemIds().split(",");
			for (String itemIdStr : itemIds) {
				builder.addItemId(Integer.parseInt(itemIdStr));
			}	
		}
		builder.setSendMemo(entity.getSendMemo());
		builder.setSendTime(entity.getSendTime().getTime());
		if(entity.getItemNums()!=null && entity.getItemNums().length()>0){
			String[] itemNums = entity.getItemNums().split(",");
			for(String itemNumStr : itemNums){
				builder.addItemNum(Integer.parseInt(itemNumStr));
			}
		}
		return builder.build();
	}
		

	@Override
	public void queryTimingMailList(RpcController controller, QueryTimingMailListRequest request,
			RpcCallback<QueryTimingMailListResponse> done) {		
//		List<?> entityList = this.dbManager.getGameDbService().findByNamedQuery(
//				DataQueryConstants.QUERY_VALID_TIMING_MAIL_LIST);
//		QueryTimingMailListResponse.Builder responseBuilder= QueryTimingMailListResponse.newBuilder();
//		if(entityList==null || entityList.isEmpty()){
//			done.run(responseBuilder.setTotalCount(0).build());
//			return;
//		}
//		for(Object obj : entityList){
//			responseBuilder.addMails(this.mailDraftEntityConverter.reverseConvert((MailDraftEntity)obj));
//		}		
//		responseBuilder.setTotalCount(entityList.size());
//		done.run(responseBuilder.build());
		MGGetTimingMailList mgMsg = new MGGetTimingMailList();
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	@Override
	public void cancelTimingMail(RpcController controller, CancelTimingMailRequest request,
			RpcCallback<CancelTimingMailResponse> done) {
		MGCancelMail mgMsg = new MGCancelMail();
		mgMsg.setMailId(request.getMailId());
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	@Override
	public void updateTimingMail(RpcController controller, UpdateTimingMailRequest request,
			RpcCallback<UpdateTimingMailResponse> done) {
		MailObject.Builder mail = request.getMail().toBuilder();
		Date nowDate = new Date();
		long now = nowDate.getTime();		
		mail.setLastEditTime(now);
		MGUpdateMail mgMsg = new MGUpdateMail();		
		mgMsg.setMailObject(mail.build());
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

}
