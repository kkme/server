package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 剩余好友体力领取次数
 *
 * @author SevenSoul
 */
@Component
public class GCLeftFriendRewardTimes extends GCMessage{
	
	/** 剩余领取次数 */
	private int leftTimes;

	public GCLeftFriendRewardTimes (){
	}
	
	public GCLeftFriendRewardTimes (
			int leftTimes ){
			this.leftTimes = leftTimes;
	}

	@Override
	protected boolean readImpl() {
		leftTimes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(leftTimes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEFT_FRIEND_REWARD_TIMES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEFT_FRIEND_REWARD_TIMES";
	}

	public int getLeftTimes(){
		return leftTimes;
	}
		
	public void setLeftTimes(int leftTimes){
		this.leftTimes = leftTimes;
	}
}