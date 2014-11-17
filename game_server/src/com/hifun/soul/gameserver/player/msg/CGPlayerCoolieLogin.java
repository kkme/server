package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 玩家使用cookie登录
 * 
 * @author SevenSoul
 */
@Component
public class CGPlayerCoolieLogin extends CGMessage{
	
	/** 使用json字符串传递的信息 */
	private String jsonValue;
	
	public CGPlayerCoolieLogin (){
	}
	
	public CGPlayerCoolieLogin (
			String jsonValue ){
			this.jsonValue = jsonValue;
	}
	
	@Override
	protected boolean readImpl() {
		jsonValue = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(jsonValue);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_COOLIE_LOGIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_COOLIE_LOGIN";
	}

	public String getJsonValue(){
		return jsonValue;
	}
		
	public void setJsonValue(String jsonValue){
		this.jsonValue = jsonValue;
	}

	@Override
	public void execute() {
	}
}