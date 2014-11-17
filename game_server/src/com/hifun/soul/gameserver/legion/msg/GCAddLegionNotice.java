package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应添加公告 
 *
 * @author SevenSoul
 */
@Component
public class GCAddLegionNotice extends GCMessage{
	
	/** 军团公告 */
	private String legionNotice;

	public GCAddLegionNotice (){
	}
	
	public GCAddLegionNotice (
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
		return MessageType.GC_ADD_LEGION_NOTICE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_LEGION_NOTICE";
	}

	public String getLegionNotice(){
		return legionNotice;
	}
		
	public void setLegionNotice(String legionNotice){
		this.legionNotice = legionNotice;
	}
}