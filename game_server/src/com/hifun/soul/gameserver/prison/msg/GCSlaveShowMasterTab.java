package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应奴隶展示主人标签页 
 *
 * @author SevenSoul
 */
@Component
public class GCSlaveShowMasterTab extends GCMessage{
	
	/** 剩余互动次数 */
	private int remainInteractNum;
	/** 剩余反抗次数 */
	private int remainRevoltNum;
	/** 剩余求救次数 */
	private int remainSosNum;
	/** 主人信息  */
	private com.hifun.soul.gameserver.prison.msg.MasterInfo master;

	public GCSlaveShowMasterTab (){
	}
	
	public GCSlaveShowMasterTab (
			int remainInteractNum,
			int remainRevoltNum,
			int remainSosNum,
			com.hifun.soul.gameserver.prison.msg.MasterInfo master ){
			this.remainInteractNum = remainInteractNum;
			this.remainRevoltNum = remainRevoltNum;
			this.remainSosNum = remainSosNum;
			this.master = master;
	}

	@Override
	protected boolean readImpl() {
		remainInteractNum = readInteger();
		remainRevoltNum = readInteger();
		remainSosNum = readInteger();
		master = new com.hifun.soul.gameserver.prison.msg.MasterInfo();
						master.setHumanId(readLong());
						master.setHumanName(readString());
						master.setHumanLevel(readInteger());
						master.setIdentityType(readInteger());
						master.setLegionId(readLong());
						master.setLegionName(readString());
						master.setOccupationType(readInteger());
						master.setInteractTimeDiff(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainInteractNum);
		writeInteger(remainRevoltNum);
		writeInteger(remainSosNum);
		writeLong(master.getHumanId());
		writeString(master.getHumanName());
		writeInteger(master.getHumanLevel());
		writeInteger(master.getIdentityType());
		writeLong(master.getLegionId());
		writeString(master.getLegionName());
		writeInteger(master.getOccupationType());
		writeInteger(master.getInteractTimeDiff());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLAVE_SHOW_MASTER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLAVE_SHOW_MASTER_TAB";
	}

	public int getRemainInteractNum(){
		return remainInteractNum;
	}
		
	public void setRemainInteractNum(int remainInteractNum){
		this.remainInteractNum = remainInteractNum;
	}

	public int getRemainRevoltNum(){
		return remainRevoltNum;
	}
		
	public void setRemainRevoltNum(int remainRevoltNum){
		this.remainRevoltNum = remainRevoltNum;
	}

	public int getRemainSosNum(){
		return remainSosNum;
	}
		
	public void setRemainSosNum(int remainSosNum){
		this.remainSosNum = remainSosNum;
	}

	public com.hifun.soul.gameserver.prison.msg.MasterInfo getMaster(){
		return master;
	}
		
	public void setMaster(com.hifun.soul.gameserver.prison.msg.MasterInfo master){
		this.master = master;
	}
}