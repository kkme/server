package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开招募面板响应
 *
 * @author SevenSoul
 */
@Component
public class GCOpenRecruitPanel extends GCMessage{
	
	/** 招募类型 */
	private int recruitType;
	/** 精灵集合 */
	private com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] spriteList;

	public GCOpenRecruitPanel (){
	}
	
	public GCOpenRecruitPanel (
			int recruitType,
			com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] spriteList ){
			this.recruitType = recruitType;
			this.spriteList = spriteList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		recruitType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		spriteList = new com.hifun.soul.gameserver.sprite.model.SpritePubInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpritePubInfo objspriteList = new com.hifun.soul.gameserver.sprite.model.SpritePubInfo();
			spriteList[i] = objspriteList;
					objspriteList.setSpriteId(readInteger());
							objspriteList.setIconId(readInteger());
							objspriteList.setName(readString());
							objspriteList.setQuality(readInteger());
							objspriteList.setSoulType(readInteger());
							objspriteList.setSoulNum(readInteger());
							objspriteList.setWin(readBoolean());
							objspriteList.setPropId(readInteger());
							objspriteList.setPropValue(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(recruitType);
	writeShort(spriteList.length);
	for(int i=0; i<spriteList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpritePubInfo objspriteList = spriteList[i];
				writeInteger(objspriteList.getSpriteId());
				writeInteger(objspriteList.getIconId());
				writeString(objspriteList.getName());
				writeInteger(objspriteList.getQuality());
				writeInteger(objspriteList.getSoulType());
				writeInteger(objspriteList.getSoulNum());
				writeBoolean(objspriteList.getWin());
				writeInteger(objspriteList.getPropId());
				writeInteger(objspriteList.getPropValue());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_RECRUIT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_RECRUIT_PANEL";
	}

	public int getRecruitType(){
		return recruitType;
	}
		
	public void setRecruitType(int recruitType){
		this.recruitType = recruitType;
	}

	public com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] getSpriteList(){
		return spriteList;
	}

	public void setSpriteList(com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] spriteList){
		this.spriteList = spriteList;
	}	
}