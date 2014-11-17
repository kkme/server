package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应搜索军团
 *
 * @author SevenSoul
 */
@Component
public class GCSearchLegion extends GCMessage{
	
	/** 军团列表  */
	private com.hifun.soul.gameserver.legion.msg.LegionListInfo[] legions;

	public GCSearchLegion (){
	}
	
	public GCSearchLegion (
			com.hifun.soul.gameserver.legion.msg.LegionListInfo[] legions ){
			this.legions = legions;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		legions = new com.hifun.soul.gameserver.legion.msg.LegionListInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.msg.LegionListInfo objlegions = new com.hifun.soul.gameserver.legion.msg.LegionListInfo();
			legions[i] = objlegions;
					objlegions.setLegionId(readLong());
							objlegions.setLegionName(readString());
							objlegions.setCommanderName(readString());
							objlegions.setLegionLevel(readInteger());
							objlegions.setMemberLimit(readInteger());
							objlegions.setMemberNum(readInteger());
							objlegions.setApplyButtonStatus(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(legions.length);
	for(int i=0; i<legions.length; i++){
	com.hifun.soul.gameserver.legion.msg.LegionListInfo objlegions = legions[i];
				writeLong(objlegions.getLegionId());
				writeString(objlegions.getLegionName());
				writeString(objlegions.getCommanderName());
				writeInteger(objlegions.getLegionLevel());
				writeInteger(objlegions.getMemberLimit());
				writeInteger(objlegions.getMemberNum());
				writeInteger(objlegions.getApplyButtonStatus());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SEARCH_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SEARCH_LEGION";
	}

	public com.hifun.soul.gameserver.legion.msg.LegionListInfo[] getLegions(){
		return legions;
	}

	public void setLegions(com.hifun.soul.gameserver.legion.msg.LegionListInfo[] legions){
		this.legions = legions;
	}	
}