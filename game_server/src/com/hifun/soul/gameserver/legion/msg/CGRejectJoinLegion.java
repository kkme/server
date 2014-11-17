package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 拒绝加入军团
 * 
 * @author SevenSoul
 */
@Component
public class CGRejectJoinLegion extends CGMessage{
	
	/** 加入角色ID */
	private long joinHumanGuid;
	
	public CGRejectJoinLegion (){
	}
	
	public CGRejectJoinLegion (
			long joinHumanGuid ){
			this.joinHumanGuid = joinHumanGuid;
	}
	
	@Override
	protected boolean readImpl() {
		joinHumanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(joinHumanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REJECT_JOIN_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REJECT_JOIN_LEGION";
	}

	public long getJoinHumanGuid(){
		return joinHumanGuid;
	}
		
	public void setJoinHumanGuid(long joinHumanGuid){
		this.joinHumanGuid = joinHumanGuid;
	}

	@Override
	public void execute() {
	}
}