package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示战俘营日志标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowPrisonLogTab extends GCMessage{
	
	/** 日志列表  */
	private com.hifun.soul.gameserver.prison.msg.PrisonLogInfo[] prisonLogInfoList;

	public GCShowPrisonLogTab (){
	}
	
	public GCShowPrisonLogTab (
			com.hifun.soul.gameserver.prison.msg.PrisonLogInfo[] prisonLogInfoList ){
			this.prisonLogInfoList = prisonLogInfoList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		prisonLogInfoList = new com.hifun.soul.gameserver.prison.msg.PrisonLogInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.PrisonLogInfo objprisonLogInfoList = new com.hifun.soul.gameserver.prison.msg.PrisonLogInfo();
			prisonLogInfoList[i] = objprisonLogInfoList;
					objprisonLogInfoList.setLogType(readInteger());
							objprisonLogInfoList.setFirstId(readLong());
							objprisonLogInfoList.setFirstName(readString());
							objprisonLogInfoList.setSecondId(readLong());
							objprisonLogInfoList.setSecondName(readString());
							objprisonLogInfoList.setThirdId(readLong());
							objprisonLogInfoList.setThirdName(readString());
							objprisonLogInfoList.setResult(readInteger());
							objprisonLogInfoList.setInteractType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(prisonLogInfoList.length);
	for(int i=0; i<prisonLogInfoList.length; i++){
	com.hifun.soul.gameserver.prison.msg.PrisonLogInfo objprisonLogInfoList = prisonLogInfoList[i];
				writeInteger(objprisonLogInfoList.getLogType());
				writeLong(objprisonLogInfoList.getFirstId());
				writeString(objprisonLogInfoList.getFirstName());
				writeLong(objprisonLogInfoList.getSecondId());
				writeString(objprisonLogInfoList.getSecondName());
				writeLong(objprisonLogInfoList.getThirdId());
				writeString(objprisonLogInfoList.getThirdName());
				writeInteger(objprisonLogInfoList.getResult());
				writeInteger(objprisonLogInfoList.getInteractType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_PRISON_LOG_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_PRISON_LOG_TAB";
	}

	public com.hifun.soul.gameserver.prison.msg.PrisonLogInfo[] getPrisonLogInfoList(){
		return prisonLogInfoList;
	}

	public void setPrisonLogInfoList(com.hifun.soul.gameserver.prison.msg.PrisonLogInfo[] prisonLogInfoList){
		this.prisonLogInfoList = prisonLogInfoList;
	}	
}