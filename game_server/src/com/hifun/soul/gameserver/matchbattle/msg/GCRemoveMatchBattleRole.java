package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 人员离开
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveMatchBattleRole extends GCMessage{
	
	/** 离开的人员id */
	private long[] leaveRolesId;

	public GCRemoveMatchBattleRole (){
	}
	
	public GCRemoveMatchBattleRole (
			long[] leaveRolesId ){
			this.leaveRolesId = leaveRolesId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		leaveRolesId = new long[count];
		for(int i=0; i<count; i++){
			leaveRolesId[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(leaveRolesId.length);
	for(int i=0; i<leaveRolesId.length; i++){
	Long objleaveRolesId = leaveRolesId[i];
			writeLong(objleaveRolesId);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REMOVE_MATCH_BATTLE_ROLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_MATCH_BATTLE_ROLE";
	}

	public long[] getLeaveRolesId(){
		return leaveRolesId;
	}

	public void setLeaveRolesId(long[] leaveRolesId){
		this.leaveRolesId = leaveRolesId;
	}	
}