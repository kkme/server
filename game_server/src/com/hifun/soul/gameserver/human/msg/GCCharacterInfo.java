package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器给客户端下发人角色基本
 *
 * @author SevenSoul
 */
@Component
public class GCCharacterInfo extends GCMessage{
	
	/** 玩家 */
	private com.hifun.soul.common.model.human.CharacterInfo human;

	public GCCharacterInfo (){
	}
	
	public GCCharacterInfo (
			com.hifun.soul.common.model.human.CharacterInfo human ){
			this.human = human;
	}

	@Override
	protected boolean readImpl() {
		human = new com.hifun.soul.common.model.human.CharacterInfo();
						human.setGuid(readLong());
						human.setName(readString());
						human.setLevel(readInteger());
						human.setOccupation(readInteger());
						human.setLegionId(readLong());
						human.setLegionName(readString());
						human.setTitleName(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(human.getGuid());
		writeString(human.getName());
		writeInteger(human.getLevel());
		writeInteger(human.getOccupation());
		writeLong(human.getLegionId());
		writeString(human.getLegionName());
		writeString(human.getTitleName());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARACTER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARACTER_INFO";
	}

	public com.hifun.soul.common.model.human.CharacterInfo getHuman(){
		return human;
	}
		
	public void setHuman(com.hifun.soul.common.model.human.CharacterInfo human){
		this.human = human;
	}
}