package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 勇者之路战斗邀请
 *
 * @author SevenSoul
 */
@Component
public class GCJoinWarriorBattleRequest extends GCMessage{
	
	/** 挑战者GUID */
	private long challengerGuid;
	/** 挑战说明 */
	private String content;

	public GCJoinWarriorBattleRequest (){
	}
	
	public GCJoinWarriorBattleRequest (
			long challengerGuid,
			String content ){
			this.challengerGuid = challengerGuid;
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		challengerGuid = readLong();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(challengerGuid);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_WARRIOR_BATTLE_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_WARRIOR_BATTLE_REQUEST";
	}

	public long getChallengerGuid(){
		return challengerGuid;
	}
		
	public void setChallengerGuid(long challengerGuid){
		this.challengerGuid = challengerGuid;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}