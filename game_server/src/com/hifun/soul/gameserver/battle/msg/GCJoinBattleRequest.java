package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知有战斗邀请
 *
 * @author SevenSoul
 */
@Component
public class GCJoinBattleRequest extends GCMessage{
	
	/** 挑战者GUID */
	private long challengerGuid;
	/** 挑战者名称 */
	private String challengerName;
	/** 挑战内容 */
	private String content;

	public GCJoinBattleRequest (){
	}
	
	public GCJoinBattleRequest (
			long challengerGuid,
			String challengerName,
			String content ){
			this.challengerGuid = challengerGuid;
			this.challengerName = challengerName;
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		challengerGuid = readLong();
		challengerName = readString();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(challengerGuid);
		writeString(challengerName);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_BATTLE_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_BATTLE_REQUEST";
	}

	public long getChallengerGuid(){
		return challengerGuid;
	}
		
	public void setChallengerGuid(long challengerGuid){
		this.challengerGuid = challengerGuid;
	}

	public String getChallengerName(){
		return challengerName;
	}
		
	public void setChallengerName(String challengerName){
		this.challengerName = challengerName;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}