package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新参战人员
 *
 * @author SevenSoul
 */
@Component
public class GCAddMatchBattleRole extends GCMessage{
	
	/** 新参加的人员 */
	private com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] joinRoles;

	public GCAddMatchBattleRole (){
	}
	
	public GCAddMatchBattleRole (
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] joinRoles ){
			this.joinRoles = joinRoles;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		joinRoles = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo objjoinRoles = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo();
			joinRoles[i] = objjoinRoles;
					objjoinRoles.setRoleId(readLong());
							objjoinRoles.setRoleName(readString());
							objjoinRoles.setBattleState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(joinRoles.length);
	for(int i=0; i<joinRoles.length; i++){
	com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo objjoinRoles = joinRoles[i];
				writeLong(objjoinRoles.getRoleId());
				writeString(objjoinRoles.getRoleName());
				writeInteger(objjoinRoles.getBattleState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ADD_MATCH_BATTLE_ROLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_MATCH_BATTLE_ROLE";
	}

	public com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] getJoinRoles(){
		return joinRoles;
	}

	public void setJoinRoles(com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] joinRoles){
		this.joinRoles = joinRoles;
	}	
}