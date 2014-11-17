package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端响应战斗邀请
 * 
 * @author SevenSoul
 */
@Component
public class CGJoinBattleResponse extends CGMessage{
	
	/** 挑战者GUID */
	private long challengerGuid;
	/** 是否同意战斗 */
	private boolean isAgree;
	
	public CGJoinBattleResponse (){
	}
	
	public CGJoinBattleResponse (
			long challengerGuid,
			boolean isAgree ){
			this.challengerGuid = challengerGuid;
			this.isAgree = isAgree;
	}
	
	@Override
	protected boolean readImpl() {
		challengerGuid = readLong();
		isAgree = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(challengerGuid);
		writeBoolean(isAgree);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_JOIN_BATTLE_RESPONSE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JOIN_BATTLE_RESPONSE";
	}

	public long getChallengerGuid(){
		return challengerGuid;
	}
		
	public void setChallengerGuid(long challengerGuid){
		this.challengerGuid = challengerGuid;
	}

	public boolean getIsAgree(){
		return isAgree;
	}
		
	public void setIsAgree(boolean isAgree){
		this.isAgree = isAgree;
	}

	@Override
	public void execute() {
	}
}