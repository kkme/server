package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团成员标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionMemberTab extends GCMessage{
	
	/** 军团成员列表  */
	private com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] legionMembers;

	public GCShowLegionMemberTab (){
	}
	
	public GCShowLegionMemberTab (
			com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] legionMembers ){
			this.legionMembers = legionMembers;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		legionMembers = new com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo objlegionMembers = new com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo();
			legionMembers[i] = objlegionMembers;
					objlegionMembers.setMemberId(readLong());
							objlegionMembers.setMemberName(readString());
							objlegionMembers.setLevel(readInteger());
							objlegionMembers.setArenaRank(readInteger());
							objlegionMembers.setPositionName(readString());
							objlegionMembers.setTodayContribution(readInteger());
							objlegionMembers.setTotalContribution(readInteger());
							objlegionMembers.setOffLineTimeInterval(readInteger());
							objlegionMembers.setTransferButtonVisible(readBoolean());
							objlegionMembers.setRemoveButtonVisible(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(legionMembers.length);
	for(int i=0; i<legionMembers.length; i++){
	com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo objlegionMembers = legionMembers[i];
				writeLong(objlegionMembers.getMemberId());
				writeString(objlegionMembers.getMemberName());
				writeInteger(objlegionMembers.getLevel());
				writeInteger(objlegionMembers.getArenaRank());
				writeString(objlegionMembers.getPositionName());
				writeInteger(objlegionMembers.getTodayContribution());
				writeInteger(objlegionMembers.getTotalContribution());
				writeInteger(objlegionMembers.getOffLineTimeInterval());
				writeBoolean(objlegionMembers.getTransferButtonVisible());
				writeBoolean(objlegionMembers.getRemoveButtonVisible());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_MEMBER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_MEMBER_TAB";
	}

	public com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] getLegionMembers(){
		return legionMembers;
	}

	public void setLegionMembers(com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] legionMembers){
		this.legionMembers = legionMembers;
	}	
}