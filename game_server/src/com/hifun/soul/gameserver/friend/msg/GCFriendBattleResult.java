package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 好友切磋结果
 *
 * @author SevenSoul
 */
@Component
public class GCFriendBattleResult extends GCMessage{
	
	/** 战斗结果（胜利或失败） */
	private boolean battleResult;
	/** 好友的openId */
	private String friendOpenId;

	public GCFriendBattleResult (){
	}
	
	public GCFriendBattleResult (
			boolean battleResult,
			String friendOpenId ){
			this.battleResult = battleResult;
			this.friendOpenId = friendOpenId;
	}

	@Override
	protected boolean readImpl() {
		battleResult = readBoolean();
		friendOpenId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(battleResult);
		writeString(friendOpenId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_BATTLE_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_BATTLE_RESULT";
	}

	public boolean getBattleResult(){
		return battleResult;
	}
		
	public void setBattleResult(boolean battleResult){
		this.battleResult = battleResult;
	}

	public String getFriendOpenId(){
		return friendOpenId;
	}
		
	public void setFriendOpenId(String friendOpenId){
		this.friendOpenId = friendOpenId;
	}
}