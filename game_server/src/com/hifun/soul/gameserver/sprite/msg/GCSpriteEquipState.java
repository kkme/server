package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 精灵装备状态
 *
 * @author SevenSoul
 */
@Component
public class GCSpriteEquipState extends GCMessage{
	
	/** 精灵UUID */
	private String spriteUUID;
	/** 是否已经装备 */
	private boolean isEquip;

	public GCSpriteEquipState (){
	}
	
	public GCSpriteEquipState (
			String spriteUUID,
			boolean isEquip ){
			this.spriteUUID = spriteUUID;
			this.isEquip = isEquip;
	}

	@Override
	protected boolean readImpl() {
		spriteUUID = readString();
		isEquip = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(spriteUUID);
		writeBoolean(isEquip);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SPRITE_EQUIP_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SPRITE_EQUIP_STATE";
	}

	public String getSpriteUUID(){
		return spriteUUID;
	}
		
	public void setSpriteUUID(String spriteUUID){
		this.spriteUUID = spriteUUID;
	}

	public boolean getIsEquip(){
		return isEquip;
	}
		
	public void setIsEquip(boolean isEquip){
		this.isEquip = isEquip;
	}
}