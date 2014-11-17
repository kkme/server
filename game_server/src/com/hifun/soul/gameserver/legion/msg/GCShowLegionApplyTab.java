package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团申请标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionApplyTab extends GCMessage{
	
	/** 军团申请列表  */
	private com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo[] legionApplies;

	public GCShowLegionApplyTab (){
	}
	
	public GCShowLegionApplyTab (
			com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo[] legionApplies ){
			this.legionApplies = legionApplies;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		legionApplies = new com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo objlegionApplies = new com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo();
			legionApplies[i] = objlegionApplies;
					objlegionApplies.setApplyHumanId(readLong());
							objlegionApplies.setApplyHumanName(readString());
							objlegionApplies.setApplyHumanLevel(readInteger());
							objlegionApplies.setApplyArenaRank(readInteger());
							objlegionApplies.setCheckButtionVisible(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(legionApplies.length);
	for(int i=0; i<legionApplies.length; i++){
	com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo objlegionApplies = legionApplies[i];
				writeLong(objlegionApplies.getApplyHumanId());
				writeString(objlegionApplies.getApplyHumanName());
				writeInteger(objlegionApplies.getApplyHumanLevel());
				writeInteger(objlegionApplies.getApplyArenaRank());
				writeBoolean(objlegionApplies.getCheckButtionVisible());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_APPLY_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_APPLY_TAB";
	}

	public com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo[] getLegionApplies(){
		return legionApplies;
	}

	public void setLegionApplies(com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo[] legionApplies){
		this.legionApplies = legionApplies;
	}	
}