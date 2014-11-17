package com.hifun.soul.manageweb.mail.action;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.mail.MailModel;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.MailObject;
import com.hifun.soul.proto.services.Services.MailRpcService;
import com.hifun.soul.proto.services.Services.SendMailRequest;
import com.hifun.soul.proto.services.Services.SendMailResponse;
import com.hifun.soul.proto.services.Services.UpdateTimingMailRequest;
import com.hifun.soul.proto.services.Services.UpdateTimingMailResponse;
import com.opensymphony.xwork2.ActionContext;

public class SendMailAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private long mailId;
	/** 收件人id */
	private String receiveHumanIds = "";
	/** 主题 */
	private String mailTheme = "";
	/** 邮件内容 */
	private String content = "";
	/** 过期时间 */
	private Date expireDate;
	/** 附件1 */
	private int itemId = 0;
	/** 附件2 */
	private int itemId2 = 0;
	/** 附件1数量 */
	private int itemNum = 1;
	/** 附件2数量 */
	private int itemNum2 = 1;
	/** 备注 */
	private String sendMemo = "";
	/** 是否有过期时间 */
	private boolean hasExpireDate;
	/** 是否为定时发送的邮件 */
	private boolean timingMail;
	/** 计划发送时间字符串 */
	private String planSendTimeString;
	/** 计划发送时间 */
	private Date planSendTime;
	private boolean editState = false;
	
	// #start properties

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public String getReceiveHumanIds() {
		return receiveHumanIds;
	}
	
	public void setReceiveHumanIds(String receiveHumanIds) {
		this.receiveHumanIds = receiveHumanIds;
	}

	public String getMailTheme() {
		return mailTheme;
	}

	public void setMailTheme(String theme) {
		this.mailTheme = theme;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getSendMemo() {
		return sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}

	public boolean getHasExpireDate() {
		return hasExpireDate;
	}

	public void setHasExpireDate(boolean hasExpireDate) {
		this.hasExpireDate = hasExpireDate;
	}

	public int getItemId2() {
		return itemId2;
	}

	public void setItemId2(int itemId2) {
		this.itemId2 = itemId2;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getItemNum2() {
		return itemNum2;
	}

	public void setItemNum2(int itemNum2) {
		this.itemNum2 = itemNum2;
	}

	public boolean getTimingMail() {
		return timingMail;
	}

	public void setTimingMail(boolean isTimingMail) {
		this.timingMail = isTimingMail;
	}

	public String getPlanSendTimeString() {
		return planSendTimeString;
	}

	public void setPlanSendTimeString(String planSendTimeString) {
		this.planSendTimeString = planSendTimeString;
	}
	
	public void setMailModel(MailModel model){
		this.editState = true;
		this.content = model.getContent();
		this.expireDate = model.getExpireDate();
		this.hasExpireDate = model.isHasExpireDate();
		this.itemId = model.getItemId();
		this.itemId2 = model.getItemId2();
		this.itemNum = model.getItemNum();
		this.itemNum2 = model.getItemNum2();
		this.mailId = model.getMailId();
		this.mailTheme = model.getTheme();
		this.timingMail = model.isTimingMail();
		if(timingMail){
			this.planSendTime = model.getPlanSendTime();
			this.planSendTimeString = dateFormat.format(planSendTime);
		}
	}
	
	// #end

	

	public boolean isEditState() {
		return editState;
	}

	public void setEditState(boolean editState) {
		this.editState = editState;
	}

	public String sendMailForward() {
		receiveHumanIds = ServletActionContext.getRequest().getParameter("receiverId");
		return "success";
	}

	public String sendMail() throws ServiceException {
		MailObject.Builder builder = MailObject.newBuilder();
		builder.setMailId(1);
		builder.setMailType(1);
		builder.setSendHumanId(0);
		builder.setSendHumanName("GM");
		builder.setReceiveHumanIds(receiveHumanIds);
		builder.setTheme(mailTheme);
		builder.setContent(content);
		Calendar cal = Calendar.getInstance();
		if (this.hasExpireDate) {
			cal.setTime(expireDate);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			builder.setExpireDate(cal.getTimeInMillis());
		} else {
			cal.set(2050, 12, 12);
		}
		builder.setExpireDate(cal.getTimeInMillis());
		if (itemId > 0) {
			builder.addItemId(itemId);
			builder.addItemNum(itemNum);
		}
		if (itemId2 > 0) {
			builder.addItemId(itemId2);
			builder.addItemNum(itemNum2);
		}
		builder.setSendMemo(sendMemo);
		builder.setIsTimingMail(timingMail);
		if(timingMail){
			builder.setPlanSendTime(planSendTime.getTime());
		}
		MailRpcService.BlockingInterface mailService = Managers.getServiceManager().getMailService();
		SendMailResponse response = mailService.sendMail(null, SendMailRequest.newBuilder().setMail(builder.build())
				.build());
		String param = MessageFormat
				.format("receiveHumanIds：{0}, mailTheme：{1}, content：{2}, itemId：{3}, hasExpireDate:{4}, expireDate:{5},memo:{6}",
						receiveHumanIds, mailTheme, content, itemId, hasExpireDate, new Date(builder.getExpireDate()),
						sendMemo);
		ManageLogger.log(ManageLogType.SEND_MAIL, PermissionType.SEND_MAIL, param, response.getResult());
		if (response.getResult()) {
			return "success";
		} else {
			return "input";
		}
	}

	public String copyMail() {
		String mailIdStr = ServletActionContext.getRequest().getParameter("mailId");
		if(mailIdStr==null || mailIdStr.length()==0){
			return "failed";
		}
		long mailId = Long.parseLong(mailIdStr);
		Object mailListObj = ActionContext.getContext().getSession().get("mailList");
		if(mailListObj == null){
			return "failed";
		}
		@SuppressWarnings("unchecked")
		List<MailModel> mailList = (List<MailModel>)mailListObj;
		MailModel mail = null;
		for(MailModel model : mailList){
			if(model.getMailId() == mailId){
				mail = model;
				break;
			}
		}
		if(mail == null){
			return "failed";
		}
		this.mailTheme = mail.getTheme();
		this.content = mail.getContent();
		this.hasExpireDate = mail.isHasExpireDate();
		this.itemId = mail.getItemId();
		this.itemId2 = mail.getItemId2();
		this.itemNum = mail.getItemNum();
		this.itemNum2 = mail.getItemNum2();
		this.receiveHumanIds = mail.getReceiveHumanIds();
		this.expireDate = mail.getExpireDate();
		this.sendMemo = mail.getSendMemo();
		return "success";
	}
	
	public String updateTimingMailForward(){
		String mailIdStr = ServletActionContext.getRequest().getParameter("mailId");
		if(mailIdStr==null || mailIdStr.length()==0){
			return "failed";
		}
		long mailId = Long.parseLong(mailIdStr);
		Object mailListObj = ActionContext.getContext().getSession().get("mailList");
		if(mailListObj == null){
			return "failed";
		}
		@SuppressWarnings("unchecked")
		List<MailModel> mailList = (List<MailModel>)mailListObj;
		MailModel mail = null;
		for(MailModel model : mailList){
			if(model.getMailId() == mailId){
				mail = model;
				break;
			}
		}
		if(mail == null){
			return "failed";
		}
		this.mailTheme = mail.getTheme();
		this.content = mail.getContent();
		this.hasExpireDate = mail.isHasExpireDate();
		this.itemId = mail.getItemId();
		this.itemId2 = mail.getItemId2();
		this.receiveHumanIds = mail.getReceiveHumanIds();
		this.expireDate = mail.getExpireDate();
		this.sendMemo = mail.getSendMemo();
		this.timingMail = mail.isTimingMail();
		this.hasExpireDate = mail.isHasExpireDate();
		if(timingMail){
			this.planSendTime = mail.getPlanSendTime();
			this.planSendTimeString = this.dateFormat.format(this.planSendTime);
		}
		this.editState = true;
		return "success";
	}
	
	public String updateTimingMail() throws ServiceException {
		MailObject.Builder builder = MailObject.newBuilder();
		builder.setMailId(mailId);
		builder.setMailType(1);
		builder.setSendHumanId(0);
		builder.setSendHumanName("GM");
		builder.setReceiveHumanIds(receiveHumanIds);
		builder.setTheme(mailTheme);
		builder.setContent(content);
		Calendar cal = Calendar.getInstance();
		if (this.hasExpireDate) {
			cal.setTime(expireDate);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			builder.setExpireDate(cal.getTimeInMillis());
		} else {
			cal.set(2050, 12, 12);
		}
		builder.setExpireDate(cal.getTimeInMillis());
		if (itemId > 0) {
			builder.addItemId(itemId);
		}
		if (itemId2 > 0) {
			builder.addItemId(itemId2);
		}
		builder.setSendMemo(sendMemo);
		builder.setIsTimingMail(timingMail);
		if(timingMail){
			builder.setPlanSendTime(planSendTime.getTime());
		}
		MailRpcService.BlockingInterface mailService = Managers.getServiceManager().getMailService();
		UpdateTimingMailResponse response = mailService.updateTimingMail(null, UpdateTimingMailRequest.newBuilder().setMail(builder.build())
				.build());
		String param = MessageFormat
				.format("receiveHumanIds：{0}, mailTheme：{1}, content：{2}, itemId：{3}, hasExpireDate:{4}, expireDate:{5},memo:{6}",
						receiveHumanIds, mailTheme, content, itemId, hasExpireDate, new Date(builder.getExpireDate()),
						sendMemo);
		ManageLogger.log(ManageLogType.UPDATE_TIMING_MAIL, PermissionType.UPDATE_TIMING_MAIL, param, response.getResult());
		if (response.getResult()) {
			return "success";
		} else {
			return "input";
		}
	}

	public void validateSendMail() {
		mailInfoValidate();
	}
	
	public void validateUpdateTimingMail(){
		mailInfoValidate();
	}
	
	private void mailInfoValidate(){
		if (receiveHumanIds == null || receiveHumanIds.length() == 0) {
			this.addFieldError("receiveHumanIds", "收件人不能为空");
		}
		if (this.content == null || this.content.length() == 0) {
			this.addFieldError("content", "邮件内容不能为空");
		}
		if (this.mailTheme == null || this.mailTheme.length() == 0) {
			this.addFieldError("mailTheme", "邮件主题不能为空");
		}
		if (this.hasExpireDate) {
			if (this.expireDate == null) {
				this.addFieldError("expireDate", "邮件过期时间不能为空");
			}
			if (!this.expireDate.after(new Date())) {
				//以manage_server时间为准
			}
		}
		if(timingMail){
			try {
				planSendTime = dateFormat.parse(planSendTimeString);
			} catch (ParseException e) {
				this.addFieldError("planSendTimeString", "定时发送时间格式不正确，正确格式为:2013-01-09");
			}
		}
	}
}
