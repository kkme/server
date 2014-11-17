package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求申请加入军团
 * 
 * @author SevenSoul
 */
@Component
public class CGApplyJoinLegion extends CGMessage{
	
	/** 加入军团ID */
	private long joinLegionId;
	
	public CGApplyJoinLegion (){
	}
	
	public CGApplyJoinLegion (
			long joinLegionId ){
			this.joinLegionId = joinLegionId;
	}
	
	@Override
	protected boolean readImpl() {
		joinLegionId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(joinLegionId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_APPLY_JOIN_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_APPLY_JOIN_LEGION";
	}

	public long getJoinLegionId(){
		return joinLegionId;
	}
		
	public void setJoinLegionId(long joinLegionId){
		this.joinLegionId = joinLegionId;
	}

	@Override
	public void execute() {
	}
}