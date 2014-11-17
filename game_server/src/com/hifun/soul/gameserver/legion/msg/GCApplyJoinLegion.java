package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应申请加入军团
 *
 * @author SevenSoul
 */
@Component
public class GCApplyJoinLegion extends GCMessage{
	
	/** 加入军团ID */
	private long joinLegionId;
	/** 申请按钮状态 */
	private int applyButtonStatus;

	public GCApplyJoinLegion (){
	}
	
	public GCApplyJoinLegion (
			long joinLegionId,
			int applyButtonStatus ){
			this.joinLegionId = joinLegionId;
			this.applyButtonStatus = applyButtonStatus;
	}

	@Override
	protected boolean readImpl() {
		joinLegionId = readLong();
		applyButtonStatus = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(joinLegionId);
		writeInteger(applyButtonStatus);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_APPLY_JOIN_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_JOIN_LEGION";
	}

	public long getJoinLegionId(){
		return joinLegionId;
	}
		
	public void setJoinLegionId(long joinLegionId){
		this.joinLegionId = joinLegionId;
	}

	public int getApplyButtonStatus(){
		return applyButtonStatus;
	}
		
	public void setApplyButtonStatus(int applyButtonStatus){
		this.applyButtonStatus = applyButtonStatus;
	}
}