package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求丢弃精灵
 * 
 * @author SevenSoul
 */
@Component
public class CGDropSprite extends CGMessage{
	
	/** 精灵UUID */
	private String spriteUUID;
	
	public CGDropSprite (){
	}
	
	public CGDropSprite (
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
		return MessageType.CG_DROP_SPRITE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DROP_SPRITE";
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