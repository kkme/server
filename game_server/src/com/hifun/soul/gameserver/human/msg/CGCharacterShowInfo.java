package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 角色信息展示请求
 * 
 * @author SevenSoul
 */
@Component
public class CGCharacterShowInfo extends CGMessage{
	
	/** 角色id */
	private long humanGuid;
	
	public CGCharacterShowInfo (){
	}
	
	public CGCharacterShowInfo (
			long humanGuid ){
			this.humanGuid = humanGuid;
	}
	
	@Override
	protected boolean readImpl() {
		humanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHARACTER_SHOW_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHARACTER_SHOW_INFO";
	}

	public long getHumanGuid(){
		return humanGuid;
	}
		
	public void setHumanGuid(long humanGuid){
		this.humanGuid = humanGuid;
	}

	@Override
	public void execute() {
	}
}