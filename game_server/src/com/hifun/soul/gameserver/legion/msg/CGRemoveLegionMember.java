package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求删除军团成员
 * 
 * @author SevenSoul
 */
@Component
public class CGRemoveLegionMember extends CGMessage{
	
	/** 删除角色ID */
	private long removeHumanGuid;
	
	public CGRemoveLegionMember (){
	}
	
	public CGRemoveLegionMember (
			long removeHumanGuid ){
			this.removeHumanGuid = removeHumanGuid;
	}
	
	@Override
	protected boolean readImpl() {
		removeHumanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(removeHumanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REMOVE_LEGION_MEMBER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REMOVE_LEGION_MEMBER";
	}

	public long getRemoveHumanGuid(){
		return removeHumanGuid;
	}
		
	public void setRemoveHumanGuid(long removeHumanGuid){
		this.removeHumanGuid = removeHumanGuid;
	}

	@Override
	public void execute() {
	}
}