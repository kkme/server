package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 玩家角色职业模版
 *
 * @author SevenSoul
 */
@Component
public class GCCharacterTemplate extends GCMessage{
	
	/** 用于创建角色的模版  */
	private com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate[] characterTemplates;

	public GCCharacterTemplate (){
	}
	
	public GCCharacterTemplate (
			com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate[] characterTemplates ){
			this.characterTemplates = characterTemplates;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		characterTemplates = new com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate objcharacterTemplates = new com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate();
			characterTemplates[i] = objcharacterTemplates;
					objcharacterTemplates.setOccupationType(readInteger());
							objcharacterTemplates.setNameIcon(readInteger());
							objcharacterTemplates.setFeature(readString());
							objcharacterTemplates.setClasses(readString());
							objcharacterTemplates.setGiftIcon1(readInteger());
							objcharacterTemplates.setGiftName1(readString());
							objcharacterTemplates.setGiftDesc1(readString());
							objcharacterTemplates.setGiftIcon2(readInteger());
							objcharacterTemplates.setGiftName2(readString());
							objcharacterTemplates.setGiftDesc2(readString());
							objcharacterTemplates.setGiftIcon3(readInteger());
							objcharacterTemplates.setGiftName3(readString());
							objcharacterTemplates.setGiftDesc3(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(characterTemplates.length);
	for(int i=0; i<characterTemplates.length; i++){
	com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate objcharacterTemplates = characterTemplates[i];
				writeInteger(objcharacterTemplates.getOccupationType());
				writeInteger(objcharacterTemplates.getNameIcon());
				writeString(objcharacterTemplates.getFeature());
				writeString(objcharacterTemplates.getClasses());
				writeInteger(objcharacterTemplates.getGiftIcon1());
				writeString(objcharacterTemplates.getGiftName1());
				writeString(objcharacterTemplates.getGiftDesc1());
				writeInteger(objcharacterTemplates.getGiftIcon2());
				writeString(objcharacterTemplates.getGiftName2());
				writeString(objcharacterTemplates.getGiftDesc2());
				writeInteger(objcharacterTemplates.getGiftIcon3());
				writeString(objcharacterTemplates.getGiftName3());
				writeString(objcharacterTemplates.getGiftDesc3());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARACTER_TEMPLATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARACTER_TEMPLATE";
	}

	public com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate[] getCharacterTemplates(){
		return characterTemplates;
	}

	public void setCharacterTemplates(com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate[] characterTemplates){
		this.characterTemplates = characterTemplates;
	}	
}