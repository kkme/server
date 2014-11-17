package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求添加好友
 * 
 * @author SevenSoul
 */
@Component
public class CGAddFriend extends CGMessage{
	
	/** 角色名称 */
	private String name;
	
	public CGAddFriend (){
	}
	
	public CGAddFriend (
			String name ){
			this.name = name;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ADD_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ADD_FRIEND";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	@Override
	public void execute() {
	}
}