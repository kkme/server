package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示解救标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowLooserTab extends GCMessage{
	
	/** 手下败将列表  */
	private com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] loosers;

	public GCShowLooserTab (){
	}
	
	public GCShowLooserTab (
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] loosers ){
			this.loosers = loosers;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		loosers = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo objloosers = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo();
			loosers[i] = objloosers;
					objloosers.setHumanId(readLong());
							objloosers.setHumanName(readString());
							objloosers.setHumanLevel(readInteger());
							objloosers.setIdentityType(readInteger());
							objloosers.setLegionId(readLong());
							objloosers.setLegionName(readString());
							objloosers.setOccupationType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(loosers.length);
	for(int i=0; i<loosers.length; i++){
	com.hifun.soul.gameserver.prison.msg.PrisonerInfo objloosers = loosers[i];
				writeLong(objloosers.getHumanId());
				writeString(objloosers.getHumanName());
				writeInteger(objloosers.getHumanLevel());
				writeInteger(objloosers.getIdentityType());
				writeLong(objloosers.getLegionId());
				writeString(objloosers.getLegionName());
				writeInteger(objloosers.getOccupationType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LOOSER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LOOSER_TAB";
	}

	public com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] getLoosers(){
		return loosers;
	}

	public void setLoosers(com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] loosers){
		this.loosers = loosers;
	}	
}