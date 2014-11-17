package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求打开邮件列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMaillist extends CGMessage{
	
	
	public CGShowMaillist (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_MAILLIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MAILLIST";
	}

	@Override
	public void execute() {
	}
}