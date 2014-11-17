package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求发送邮件
 * 
 * @author SevenSoul
 */
@Component
public class CGSendMail extends CGMessage{
	
	/** 收件人 */
	private String receiveHumanName;
	/** 主题 */
	private String theme;
	/** 内容 */
	private String content;
	
	public CGSendMail (){
	}
	
	public CGSendMail (
			String receiveHumanName,
			String theme,
			String content ){
			this.receiveHumanName = receiveHumanName;
			this.theme = theme;
			this.content = content;
	}
	
	@Override
	protected boolean readImpl() {
		receiveHumanName = readString();
		theme = readString();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(receiveHumanName);
		writeString(theme);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SEND_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SEND_MAIL";
	}

	public String getReceiveHumanName(){
		return receiveHumanName;
	}
		
	public void setReceiveHumanName(String receiveHumanName){
		this.receiveHumanName = receiveHumanName;
	}

	public String getTheme(){
		return theme;
	}
		
	public void setTheme(String theme){
		this.theme = theme;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	@Override
	public void execute() {
	}
}