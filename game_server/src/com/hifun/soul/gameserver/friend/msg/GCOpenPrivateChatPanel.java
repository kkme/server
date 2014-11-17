package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开私聊面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenPrivateChatPanel extends GCMessage{
	
	/** 私聊玩家信息 */
	private com.hifun.soul.common.model.human.CharacterInfo characterInfo;

	public GCOpenPrivateChatPanel (){
	}
	
	public GCOpenPrivateChatPanel (
			com.hifun.soul.common.model.human.CharacterInfo characterInfo ){
			this.characterInfo = characterInfo;
	}

	@Override
	protected boolean readImpl() {
		characterInfo = new com.hifun.soul.common.model.human.CharacterInfo();
						characterInfo.setGuid(readLong());
						characterInfo.setName(readString());
						characterInfo.setLevel(readInteger());
						characterInfo.setOccupation(readInteger());
						characterInfo.setLegionId(readLong());
						characterInfo.setLegionName(readString());
						characterInfo.setTitleName(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(characterInfo.getGuid());
		writeString(characterInfo.getName());
		writeInteger(characterInfo.getLevel());
		writeInteger(characterInfo.getOccupation());
		writeLong(characterInfo.getLegionId());
		writeString(characterInfo.getLegionName());
		writeString(characterInfo.getTitleName());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_PRIVATE_CHAT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_PRIVATE_CHAT_PANEL";
	}

	public com.hifun.soul.common.model.human.CharacterInfo getCharacterInfo(){
		return characterInfo;
	}
		
	public void setCharacterInfo(com.hifun.soul.common.model.human.CharacterInfo characterInfo){
		this.characterInfo = characterInfo;
	}
}