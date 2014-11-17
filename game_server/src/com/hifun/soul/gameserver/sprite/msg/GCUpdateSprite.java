package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 精灵升级后信息更新 
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateSprite extends GCMessage{
	
	/** 精灵信息 */
	private com.hifun.soul.gameserver.sprite.model.SpriteInfo sprite;

	public GCUpdateSprite (){
	}
	
	public GCUpdateSprite (
			com.hifun.soul.gameserver.sprite.model.SpriteInfo sprite ){
			this.sprite = sprite;
	}

	@Override
	protected boolean readImpl() {
		sprite = new com.hifun.soul.gameserver.sprite.model.SpriteInfo();
						sprite.setUuid(readString());
						sprite.setSpriteId(readInteger());
						sprite.setIconId(readInteger());
						sprite.setLevel(readInteger());
						sprite.setName(readString());
						sprite.setQuality(readInteger());
						sprite.setSpriteType(readInteger());
						sprite.setLevelUpAura(readInteger());
						sprite.setPropId(readInteger());
						sprite.setPropValue(readInteger());
						sprite.setIsEquip(readBoolean());
						sprite.setDropReturnAura(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sprite.getUuid());
		writeInteger(sprite.getSpriteId());
		writeInteger(sprite.getIconId());
		writeInteger(sprite.getLevel());
		writeString(sprite.getName());
		writeInteger(sprite.getQuality());
		writeInteger(sprite.getSpriteType());
		writeInteger(sprite.getLevelUpAura());
		writeInteger(sprite.getPropId());
		writeInteger(sprite.getPropValue());
		writeBoolean(sprite.getIsEquip());
		writeInteger(sprite.getDropReturnAura());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_SPRITE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_SPRITE";
	}

	public com.hifun.soul.gameserver.sprite.model.SpriteInfo getSprite(){
		return sprite;
	}
		
	public void setSprite(com.hifun.soul.gameserver.sprite.model.SpriteInfo sprite){
		this.sprite = sprite;
	}
}