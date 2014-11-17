package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 丢弃精灵响应 
 *
 * @author SevenSoul
 */
@Component
public class GCDropSprite extends GCMessage{
	
	/** 精灵UUID */
	private String spriteUUID;

	public GCDropSprite (){
	}
	
	public GCDropSprite (
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
		return MessageType.GC_DROP_SPRITE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DROP_SPRITE";
	}

	public String getSpriteUUID(){
		return spriteUUID;
	}
		
	public void setSpriteUUID(String spriteUUID){
		this.spriteUUID = spriteUUID;
	}
}