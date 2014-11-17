package com.hifun.soul.manageweb.mail.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.mail.MailModel;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.CancelTimingMailRequest;
import com.hifun.soul.proto.services.Services.MailObject;
import com.hifun.soul.proto.services.Services.MailRpcService;
import com.hifun.soul.proto.services.Services.QuerySendMailListRequest;
import com.hifun.soul.proto.services.Services.QuerySendMailListResponse;
import com.hifun.soul.proto.services.Services.QueryTimingMailListRequest;
import com.hifun.soul.proto.services.Services.QueryTimingMailListResponse;
import com.opensymphony.xwork2.ActionContext;

public class MailListAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MailModel> mailList = new ArrayList<MailModel>();
	private PagingInfo pagingInfo;
	private String contentKey="";
	private String themeKey="";
	private String sendHumanNameKey="";
	private String receiveHumanId="";
	private String sendMemoKey="";
	private String receiveHumanName="";
	
	public MailListAction(){
		pagingInfo = new PagingInfo();
		pagingInfo.setPageIndex(0);
		pagingInfo.setPageSize(20);
	}
	
	public List<MailModel> getMailList() {
		return mailList;
	}

	public void setMailList(List<MailModel> mailList) {
		this.mailList = mailList;
	}
	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public String getContentKey() {
		return contentKey;
	}

	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}

	public String getThemeKey() {
		return themeKey;
	}

	public void setThemeKey(String themeKey) {
		this.themeKey = themeKey;
	}

	public String getSendHumanNameKey() {
		return sendHumanNameKey;
	}

	public void setSendHumanNameKey(String sendHumanNameKey) {
		this.sendHumanNameKey = sendHumanNameKey;
	}
	
	public String getReceiveHumanId() {
		return receiveHumanId;
	}

	public void setReceiveHumanId(String receiveHumanId) {
		this.receiveHumanId = receiveHumanId;
	}
	
	public String getSendMemoKey() {
		return sendMemoKey;
	}

	public void setSendMemoKey(String sendMemoKey) {
		this.sendMemoKey = sendMemoKey;
	}

	public String getReceiveHumanName() {
		return receiveHumanName;
	}

	public void setReceiveHumanName(String receiveHumanName) {
		this.receiveHumanName = receiveHumanName;
	}

	/**
	 * 查询发送邮件列表
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String querySendMailList() throws ServiceException {
		mailList.clear();
		MailRpcService.BlockingInterface mailService = Managers.getServiceManager().getMailService();
		QuerySendMailListResponse response = mailService.querySendMailList(null,
				QuerySendMailListRequest.newBuilder().setStart(pagingInfo.getPageIndex() * pagingInfo.getPageSize())
						.setMaxResult(pagingInfo.getPageSize()).setTheme(themeKey).setContent(contentKey)
						.setSendHumanName(sendHumanNameKey).setReceiveHumanId(receiveHumanId).setSendMemo(sendMemoKey)
						.setReceiveHumanName(receiveHumanName).build());
		List<MailObject> mailObjectList = response.getMailsList();		
		pagingInfo.setTotalCount(response.getTotalCount());
		for(MailObject mailObj : mailObjectList){
			MailModel model =new MailModel(mailObj);
			mailList.add(model);
		}
		ActionContext.getContext().getSession().put("mailList", mailList);
		ManageLogger.log(ManageLogType.SEND_MAIL_LIST,PermissionType.SEND_MAIL_LIST, "", true);
		return "success";
	}	
	
	/**
	 * 查询定时邮件列表
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String queryTimingMailList() throws ServiceException {
		mailList.clear();
		MailRpcService.BlockingInterface mailService = Managers.getServiceManager().getMailService();
		QueryTimingMailListResponse response = mailService.queryTimingMailList(null,
				QueryTimingMailListRequest.newBuilder().build());
		List<MailObject> mailObjectList = response.getMailsList();		
		pagingInfo.setTotalCount(response.getTotalCount());
		for(MailObject mailObj : mailObjectList){
			MailModel model =new MailModel(mailObj);
			mailList.add(model);
		}
		ActionContext.getContext().getSession().put("mailList", mailList);
		ManageLogger.log(ManageLogType.SEND_MAIL_LIST,PermissionType.SEND_MAIL_LIST, "", true);
		return "success";
	}	
	
	/**
	 * 取消发送定时邮件
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String cancelTimingMail() throws ServiceException {
		String mailIdStr = ServletActionContext.getRequest().getParameter("mailId");
		if(mailIdStr==null || mailIdStr.length()==0){
			return "failed";
		}
		long mailId = Long.parseLong(mailIdStr);
		MailRpcService.BlockingInterface mailService = Managers.getServiceManager().getMailService();
		CancelTimingMailRequest.Builder request = CancelTimingMailRequest.newBuilder();
		request.setMailId(mailId);
		mailService.cancelTimingMail(null, request.build());
		return "success";
	}	
	
	
}
