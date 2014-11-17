package com.hifun.soul.gameserver.bulletin.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 停播公告
 *
 * @author SevenSoul
 */
@Component
public class GCBulletinStop extends GCMessage{
	
	/** 公告id */
	private int id;

	public GCBulletinStop (){
	}
	
	public GCBulletinStop (
			int id ){
			this.id = id;
	}

	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BULLETIN_STOP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BULLETIN_STOP";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}
}