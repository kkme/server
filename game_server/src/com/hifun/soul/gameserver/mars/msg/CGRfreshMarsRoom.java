package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求刷新战神之巅房间
 * 
 * @author SevenSoul
 */
@Component
public class CGRfreshMarsRoom extends CGMessage{
	
	/** 房间类型 */
	private int roomType;
	
	public CGRfreshMarsRoom (){
	}
	
	public CGRfreshMarsRoom (
			int roomType ){
			this.roomType = roomType;
	}
	
	@Override
	protected boolean readImpl() {
		roomType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RFRESH_MARS_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RFRESH_MARS_ROOM";
	}

	public int getRoomType(){
		return roomType;
	}
		
	public void setRoomType(int roomType){
		this.roomType = roomType;
	}

	@Override
	public void execute() {
	}
}