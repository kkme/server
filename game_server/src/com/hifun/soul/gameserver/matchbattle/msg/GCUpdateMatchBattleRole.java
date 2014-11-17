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
public class GCUpdateMatchBattleRole extends GCMessage{
	
	/** 状态更改的人员 */
	private com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] updateRoles;

	public GCUpdateMatchBattleRole (){
	}
	
	public GCUpdateMatchBattleRole (
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] updateRoles ){
			this.updateRoles = updateRoles;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		updateRoles = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo objupdateRoles = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo();
			updateRoles[i] = objupdateRoles;
					objupdateRoles.setRoleId(readLong());
							objupdateRoles.setRoleName(readString());
							objupdateRoles.setBattleState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(updateRoles.length);
	for(int i=0; i<updateRoles.length; i++){
	com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo objupdateRoles = updateRoles[i];
				writeLong(objupdateRoles.getRoleId());
				writeString(objupdateRoles.getRoleName());
				writeInteger(objupdateRoles.getBattleState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MATCH_BATTLE_ROLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MATCH_BATTLE_ROLE";
	}

	public com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] getUpdateRoles(){
		return updateRoles;
	}

	public void setUpdateRoles(com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] updateRoles){
		this.updateRoles = updateRoles;
	}	
}