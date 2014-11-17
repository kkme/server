package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新角色所在军团信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateHumanLegionInfo extends GCMessage{
	
	/** 是否加入军团 */
	private boolean joinedLegion;
	/** 军团ID */
	private long legionId;
	/** 军团名称 */
	private String legionName;

	public GCUpdateHumanLegionInfo (){
	}
	
	public GCUpdateHumanLegionInfo (
			boolean joinedLegion,
			long legionId,
			String legionName ){
			this.joinedLegion = joinedLegion;
			this.legionId = legionId;
			this.legionName = legionName;
	}

	@Override
	protected boolean readImpl() {
		joinedLegion = readBoolean();
		legionId = readLong();
		legionName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(joinedLegion);
		writeLong(legionId);
		writeString(legionName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_HUMAN_LEGION_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_HUMAN_LEGION_INFO";
	}

	public boolean getJoinedLegion(){
		return joinedLegion;
	}
		
	public void setJoinedLegion(boolean joinedLegion){
		this.joinedLegion = joinedLegion;
	}

	public long getLegionId(){
		return legionId;
	}
		
	public void setLegionId(long legionId){
		this.legionId = legionId;
	}

	public String getLegionName(){
		return legionName;
	}
		
	public void setLegionName(String legionName){
		this.legionName = legionName;
	}
}