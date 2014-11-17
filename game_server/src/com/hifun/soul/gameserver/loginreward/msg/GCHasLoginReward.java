package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 是否有可领取的奖励
 *
 * @author SevenSoul
 */
@Component
public class GCHasLoginReward extends GCMessage{
	
	/** 是否有可领取的奖励 */
	private boolean hasReward;

	public GCHasLoginReward (){
	}
	
	public GCHasLoginReward (
			boolean hasReward ){
			this.hasReward = hasReward;
	}

	@Override
	protected boolean readImpl() {
		hasReward = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(hasReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HAS_LOGIN_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HAS_LOGIN_REWARD";
	}

	public boolean getHasReward(){
		return hasReward;
	}
		
	public void setHasReward(boolean hasReward){
		this.hasReward = hasReward;
	}
}