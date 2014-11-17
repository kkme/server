package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求添加公告
 * 
 * @author SevenSoul
 */
@Component
public class CGAddLegionNotice extends CGMessage{
	
	/** 军团公告 */
	private String legionNotice;
	
	public CGAddLegionNotice (){
	}
	
	public CGAddLegionNotice (
			String legionNotice ){
			this.legionNotice = legionNotice;
	}
	
	@Override
	protected boolean readImpl() {
		legionNotice = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(legionNotice);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ADD_LEGION_NOTICE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ADD_LEGION_NOTICE";
	}

	public String getLegionNotice(){
		return legionNotice;
	}
		
	public void setLegionNotice(String legionNotice){
		this.legionNotice = legionNotice;
	}

	@Override
	public void execute() {
	}
}