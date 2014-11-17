package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 玩家创建角色
 * 
 * @author SevenSoul
 */
@Component
public class CGCreateChar extends CGMessage{
	
	/** 角色名称  */
	private String name;
	/** 角色职业  */
	private int occupation;
	
	public CGCreateChar (){
	}
	
	public CGCreateChar (
			String name,
			int occupation ){
			this.name = name;
			this.occupation = occupation;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		occupation = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		writeInteger(occupation);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CREATE_CHAR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CREATE_CHAR";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public int getOccupation(){
		return occupation;
	}
		
	public void setOccupation(int occupation){
		this.occupation = occupation;
	}

	@Override
	public void execute() {
	}
}