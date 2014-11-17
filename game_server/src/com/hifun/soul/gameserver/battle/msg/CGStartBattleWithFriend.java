package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求和指定好友角色开始战斗
 * 
 * @author SevenSoul
 */
@Component
public class CGStartBattleWithFriend extends CGMessage{
	
	/** 角色GUID */
	private long humanGuid;
	
	public CGStartBattleWithFriend (){
	}
	
	public CGStartBattleWithFriend (
			long humanGuid ){
			this.humanGuid = humanGuid;
	}
	
	@Override
	protected boolean readImpl() {
		humanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_START_BATTLE_WITH_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_BATTLE_WITH_FRIEND";
	}

	public long getHumanGuid(){
		return humanGuid;
	}
		
	public void setHumanGuid(long humanGuid){
		this.humanGuid = humanGuid;
	}

	@Override
	public void execute() {
	}
}