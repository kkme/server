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
public class GCShowRescueTab extends GCMessage{
	
	/** 结果 */
	private int result;
	/** 解救列表  */
	private com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] toRescueList;

	public GCShowRescueTab (){
	}
	
	public GCShowRescueTab (
			int result,
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] toRescueList ){
			this.result = result;
			this.toRescueList = toRescueList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		result = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		toRescueList = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo objtoRescueList = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo();
			toRescueList[i] = objtoRescueList;
					objtoRescueList.setHumanId(readLong());
							objtoRescueList.setHumanName(readString());
							objtoRescueList.setHumanLevel(readInteger());
							objtoRescueList.setIdentityType(readInteger());
							objtoRescueList.setLegionId(readLong());
							objtoRescueList.setLegionName(readString());
							objtoRescueList.setOccupationType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
	writeShort(toRescueList.length);
	for(int i=0; i<toRescueList.length; i++){
	com.hifun.soul.gameserver.prison.msg.PrisonerInfo objtoRescueList = toRescueList[i];
				writeLong(objtoRescueList.getHumanId());
				writeString(objtoRescueList.getHumanName());
				writeInteger(objtoRescueList.getHumanLevel());
				writeInteger(objtoRescueList.getIdentityType());
				writeLong(objtoRescueList.getLegionId());
				writeString(objtoRescueList.getLegionName());
				writeInteger(objtoRescueList.getOccupationType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_RESCUE_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_RESCUE_TAB";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] getToRescueList(){
		return toRescueList;
	}

	public void setToRescueList(com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] toRescueList){
		this.toRescueList = toRescueList;
	}	
}