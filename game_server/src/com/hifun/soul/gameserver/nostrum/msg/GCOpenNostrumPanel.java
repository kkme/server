package com.hifun.soul.gameserver.nostrum.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开秘药面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenNostrumPanel extends GCMessage{
	
	/** 秘药信息 */
	private com.hifun.soul.gameserver.nostrum.msg.NostrumInfo[] nostrums;

	public GCOpenNostrumPanel (){
	}
	
	public GCOpenNostrumPanel (
			com.hifun.soul.gameserver.nostrum.msg.NostrumInfo[] nostrums ){
			this.nostrums = nostrums;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		nostrums = new com.hifun.soul.gameserver.nostrum.msg.NostrumInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.nostrum.msg.NostrumInfo objnostrums = new com.hifun.soul.gameserver.nostrum.msg.NostrumInfo();
			nostrums[i] = objnostrums;
					objnostrums.setPropertyId(readInteger());
							objnostrums.setPropertyAmendRate(readInteger());
							objnostrums.setEndTime(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(nostrums.length);
	for(int i=0; i<nostrums.length; i++){
	com.hifun.soul.gameserver.nostrum.msg.NostrumInfo objnostrums = nostrums[i];
				writeInteger(objnostrums.getPropertyId());
				writeInteger(objnostrums.getPropertyAmendRate());
				writeString(objnostrums.getEndTime());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_NOSTRUM_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_NOSTRUM_PANEL";
	}

	public com.hifun.soul.gameserver.nostrum.msg.NostrumInfo[] getNostrums(){
		return nostrums;
	}

	public void setNostrums(com.hifun.soul.gameserver.nostrum.msg.NostrumInfo[] nostrums){
		this.nostrums = nostrums;
	}	
}