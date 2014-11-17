package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求装备精灵
 * 
 * @author SevenSoul
 */
@Component
public class CGEquipSprite extends CGMessage{
	
	/** 精灵UUID */
	private String spriteUUID;
	
	public CGEquipSprite (){
	}
	
	public CGEquipSprite (
			String spriteUUID ){
			this.spriteUUID = spriteUUID;
	}
	
	@Override
	protected boolean readImpl() {
		spriteUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(spriteUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EQUIP_SPRITE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EQUIP_SPRITE";
	}

	public String getSpriteUUID(){
		return spriteUUID;
	}
		
	public void setSpriteUUID(String spriteUUID){
		this.spriteUUID = spriteUUID;
	}

	@Override
	public void execute() {
	}
}