package com.hifun.soul.gameserver.bulletin.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 公告列表
 *
 * @author SevenSoul
 */
@Component
public class GCBulletinList extends GCMessage{
	
	/** 公告列表  */
	private com.hifun.soul.gameserver.bulletin.assist.CommonBulletin[] bulletins;

	public GCBulletinList (){
	}
	
	public GCBulletinList (
			com.hifun.soul.gameserver.bulletin.assist.CommonBulletin[] bulletins ){
			this.bulletins = bulletins;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		bulletins = new com.hifun.soul.gameserver.bulletin.assist.CommonBulletin[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.bulletin.assist.CommonBulletin objbulletins = new com.hifun.soul.gameserver.bulletin.assist.CommonBulletin();
			bulletins[i] = objbulletins;
					objbulletins.setId(readInteger());
							objbulletins.setContent(readString());
							objbulletins.setShowTime(readInteger());
							objbulletins.setLevel(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(bulletins.length);
	for(int i=0; i<bulletins.length; i++){
	com.hifun.soul.gameserver.bulletin.assist.CommonBulletin objbulletins = bulletins[i];
				writeInteger(objbulletins.getId());
				writeString(objbulletins.getContent());
				writeInteger(objbulletins.getShowTime());
				writeInteger(objbulletins.getLevel());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BULLETIN_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BULLETIN_LIST";
	}

	public com.hifun.soul.gameserver.bulletin.assist.CommonBulletin[] getBulletins(){
		return bulletins;
	}

	public void setBulletins(com.hifun.soul.gameserver.bulletin.assist.CommonBulletin[] bulletins){
		this.bulletins = bulletins;
	}	
}