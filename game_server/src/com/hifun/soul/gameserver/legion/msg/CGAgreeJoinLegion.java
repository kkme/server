package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 同意加入军团
 * 
 * @author SevenSoul
 */
@Component
public class CGAgreeJoinLegion extends CGMessage{
	
	/** 加入角色ID */
	private long joinHumanGuid;
	
	public CGAgreeJoinLegion (){
	}
	
	public CGAgreeJoinLegion (
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
		return MessageType.CG_AGREE_JOIN_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_AGREE_JOIN_LEGION";
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