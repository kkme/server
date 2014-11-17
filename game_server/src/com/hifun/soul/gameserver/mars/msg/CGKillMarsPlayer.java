package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求击杀战神之巅玩家
 * 
 * @author SevenSoul
 */
@Component
public class CGKillMarsPlayer extends CGMessage{
	
	/** 房间类型 */
	private int roomType;
	/** 下注倍数 */
	private int multiple;
	
	public CGKillMarsPlayer (){
	}
	
	public CGKillMarsPlayer (
			int roomType,
			int multiple ){
			this.roomType = roomType;
			this.multiple = multiple;
	}
	
	@Override
	protected boolean readImpl() {
		roomType = readInteger();
		multiple = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomType);
		writeInteger(multiple);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_KILL_MARS_PLAYER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_KILL_MARS_PLAYER";
	}

	public int getRoomType(){
		return roomType;
	}
		
	public void setRoomType(int roomType){
		this.roomType = roomType;
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}

	@Override
	public void execute() {
	}
}