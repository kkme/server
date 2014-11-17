package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应解锁战神之巅房间
 *
 * @author SevenSoul
 */
@Component
public class GCUnlockMarsRoom extends GCMessage{
	
	/** 房间类型 */
	private int roomType;
	/** 是否锁定 */
	private int isLocked;

	public GCUnlockMarsRoom (){
	}
	
	public GCUnlockMarsRoom (
			int roomType,
			int isLocked ){
			this.roomType = roomType;
			this.isLocked = isLocked;
	}

	@Override
	protected boolean readImpl() {
		roomType = readInteger();
		isLocked = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomType);
		writeInteger(isLocked);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UNLOCK_MARS_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UNLOCK_MARS_ROOM";
	}

	public int getRoomType(){
		return roomType;
	}
		
	public void setRoomType(int roomType){
		this.roomType = roomType;
	}

	public int getIsLocked(){
		return isLocked;
	}
		
	public void setIsLocked(int isLocked){
		this.isLocked = isLocked;
	}
}