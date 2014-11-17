package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求抢夺房间
 * 
 * @author SevenSoul
 */
@Component
public class CGLootAbattoirRoom extends CGMessage{
	
	/** 房间号 */
	private int roomId;
	
	public CGLootAbattoirRoom (){
	}
	
	public CGLootAbattoirRoom (
			int roomId ){
			this.roomId = roomId;
	}
	
	@Override
	protected boolean readImpl() {
		roomId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LOOT_ABATTOIR_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LOOT_ABATTOIR_ROOM";
	}

	public int getRoomId(){
		return roomId;
	}
		
	public void setRoomId(int roomId){
		this.roomId = roomId;
	}

	@Override
	public void execute() {
	}
}