package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 招募精灵响应 
 *
 * @author SevenSoul
 */
@Component
public class GCRecruitSprite extends GCMessage{
	
	/** 招募类型 */
	private int recruitType;
	/** 招募到的精灵id */
	private int spriteId;

	public GCRecruitSprite (){
	}
	
	public GCRecruitSprite (
			int recruitType,
			int spriteId ){
			this.recruitType = recruitType;
			this.spriteId = spriteId;
	}

	@Override
	protected boolean readImpl() {
		recruitType = readInteger();
		spriteId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(recruitType);
		writeInteger(spriteId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECRUIT_SPRITE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECRUIT_SPRITE";
	}

	public int getRecruitType(){
		return recruitType;
	}
		
	public void setRecruitType(int recruitType){
		this.recruitType = recruitType;
	}

	public int getSpriteId(){
		return spriteId;
	}
		
	public void setSpriteId(int spriteId){
		this.spriteId = spriteId;
	}
}