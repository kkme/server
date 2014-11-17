package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应奴隶展示求救标签页
 *
 * @author SevenSoul
 */
@Component
public class GCSlaveShowSosTab extends GCMessage{
	
	/** 结果 */
	private int result;
	/** 求救列表  */
	private com.hifun.soul.gameserver.prison.msg.HelperInfo[] helperInfoList;

	public GCSlaveShowSosTab (){
	}
	
	public GCSlaveShowSosTab (
			int result,
			com.hifun.soul.gameserver.prison.msg.HelperInfo[] helperInfoList ){
			this.result = result;
			this.helperInfoList = helperInfoList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		result = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		helperInfoList = new com.hifun.soul.gameserver.prison.msg.HelperInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.HelperInfo objhelperInfoList = new com.hifun.soul.gameserver.prison.msg.HelperInfo();
			helperInfoList[i] = objhelperInfoList;
					objhelperInfoList.setHumanId(readLong());
							objhelperInfoList.setHumanName(readString());
							objhelperInfoList.setHumanLevel(readInteger());
							objhelperInfoList.setIdentityType(readInteger());
							objhelperInfoList.setLegionId(readLong());
							objhelperInfoList.setLegionName(readString());
							objhelperInfoList.setIsForHelped(readBoolean());
							objhelperInfoList.setOccupationType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
	writeShort(helperInfoList.length);
	for(int i=0; i<helperInfoList.length; i++){
	com.hifun.soul.gameserver.prison.msg.HelperInfo objhelperInfoList = helperInfoList[i];
				writeLong(objhelperInfoList.getHumanId());
				writeString(objhelperInfoList.getHumanName());
				writeInteger(objhelperInfoList.getHumanLevel());
				writeInteger(objhelperInfoList.getIdentityType());
				writeLong(objhelperInfoList.getLegionId());
				writeString(objhelperInfoList.getLegionName());
				writeBoolean(objhelperInfoList.getIsForHelped());
				writeInteger(objhelperInfoList.getOccupationType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLAVE_SHOW_SOS_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLAVE_SHOW_SOS_TAB";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public com.hifun.soul.gameserver.prison.msg.HelperInfo[] getHelperInfoList(){
		return helperInfoList;
	}

	public void setHelperInfoList(com.hifun.soul.gameserver.prison.msg.HelperInfo[] helperInfoList){
		this.helperInfoList = helperInfoList;
	}	
}