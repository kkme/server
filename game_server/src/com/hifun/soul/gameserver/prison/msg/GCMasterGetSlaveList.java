package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应主人获取奴隶列表
 *
 * @author SevenSoul
 */
@Component
public class GCMasterGetSlaveList extends GCMessage{
	
	/** 奴隶列表  */
	private com.hifun.soul.gameserver.prison.msg.SlaveInfo[] SlaveInfoList;

	public GCMasterGetSlaveList (){
	}
	
	public GCMasterGetSlaveList (
			com.hifun.soul.gameserver.prison.msg.SlaveInfo[] SlaveInfoList ){
			this.SlaveInfoList = SlaveInfoList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		SlaveInfoList = new com.hifun.soul.gameserver.prison.msg.SlaveInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.SlaveInfo objSlaveInfoList = new com.hifun.soul.gameserver.prison.msg.SlaveInfo();
			SlaveInfoList[i] = objSlaveInfoList;
					objSlaveInfoList.setHumanId(readLong());
							objSlaveInfoList.setHumanName(readString());
							objSlaveInfoList.setHumanLevel(readInteger());
							objSlaveInfoList.setIdentityType(readInteger());
							objSlaveInfoList.setLegionId(readLong());
							objSlaveInfoList.setLegionName(readString());
							objSlaveInfoList.setCurrentExperience(readInteger());
							objSlaveInfoList.setTotalExperience(readInteger());
							objSlaveInfoList.setSlaveTimeDiff(readInteger());
							objSlaveInfoList.setBeInteractedTimeDiff(readInteger());
							objSlaveInfoList.setOccupationType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(SlaveInfoList.length);
	for(int i=0; i<SlaveInfoList.length; i++){
	com.hifun.soul.gameserver.prison.msg.SlaveInfo objSlaveInfoList = SlaveInfoList[i];
				writeLong(objSlaveInfoList.getHumanId());
				writeString(objSlaveInfoList.getHumanName());
				writeInteger(objSlaveInfoList.getHumanLevel());
				writeInteger(objSlaveInfoList.getIdentityType());
				writeLong(objSlaveInfoList.getLegionId());
				writeString(objSlaveInfoList.getLegionName());
				writeInteger(objSlaveInfoList.getCurrentExperience());
				writeInteger(objSlaveInfoList.getTotalExperience());
				writeInteger(objSlaveInfoList.getSlaveTimeDiff());
				writeInteger(objSlaveInfoList.getBeInteractedTimeDiff());
				writeInteger(objSlaveInfoList.getOccupationType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MASTER_GET_SLAVE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MASTER_GET_SLAVE_LIST";
	}

	public com.hifun.soul.gameserver.prison.msg.SlaveInfo[] getSlaveInfoList(){
		return SlaveInfoList;
	}

	public void setSlaveInfoList(com.hifun.soul.gameserver.prison.msg.SlaveInfo[] SlaveInfoList){
		this.SlaveInfoList = SlaveInfoList;
	}	
}